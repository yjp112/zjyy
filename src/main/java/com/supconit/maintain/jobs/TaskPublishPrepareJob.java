package com.supconit.maintain.jobs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supconit.base.daos.DutyGroupPersonDao;
import com.supconit.common.daos.SimpleJdbc;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.maintain.daos.MaintainTaskDao;
import com.supconit.maintain.entities.MaintainTask;

import hc.bpm.entities.ApprovalRecord;
import hc.bpm.entities.UserTask;
import hc.bpm.services.ApprovalRecordService;
import hc.bpm.services.ProcessService;
import hc.bpm.services.UserTaskService;

/**
 * 根据保养计划生成保养单
 * 
 * @author
 *
 */
public class TaskPublishPrepareJob {
	private transient static final Logger log = LoggerFactory.getLogger(TaskPublishPrepareJob.class);
	private static SimpleJdbc jdbc = (SimpleJdbc) SpringContextHolder.getBean(SimpleJdbc.class);
	private static MaintainTaskDao taskDao = SpringContextHolder.getBean(MaintainTaskDao.class);
	private static ProcessService processService = SpringContextHolder.getBean(ProcessService.class);
	private static ApprovalRecordService approvalRecordService = SpringContextHolder.getBean(ApprovalRecordService.class);
	private static UserTaskService userTaskService = SpringContextHolder.getBean(UserTaskService.class);
	private static DutyGroupPersonDao dutyGroupPersonDao = SpringContextHolder.getBean(DutyGroupPersonDao.class);

	public void readyForTask() throws ParseException {
		Date start = new Date();
		if (log.isInfoEnabled()) {
			log.info("保养计划生产保养单JOB开始执行【{}】.........", DateUtils.format(start, "yyyy-MM-dd HH:mm:ss:ssss"));
		}
		taskDao.readyForTask();
		List<MaintainTask> tasks = taskDao.findReadyTask();
		List<Long> maintainTaskIds = new ArrayList<>();
		for (MaintainTask task : tasks) {
			String oldMaintainCode = task.getMaintainCode();
			String newMaintainCode = SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.MAINTAIN_ORDER);
			String updateMaintainCode1 = "update maintain_task set maintain_code='"+newMaintainCode+"' where ID="+task.getId();
			String updateMaintainCode2 = "update maintain_task_content set maintain_code='"+newMaintainCode+"' where maintain_code='"+oldMaintainCode+"'";
			task.setMaintainCode(newMaintainCode);
			jdbc.batchUpdate(new String[]{updateMaintainCode1,updateMaintainCode2});
			task.setProcessInstanceName("保养单:" + task.getMaintainCode());
			// taskService.startProcess(task);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("_initiator", "admin");
			params.put("task", task);
			params.put("groupId", task.getMaintainGroupId());
			params.put("postId", 1);//组长
			List<String> codeList = dutyGroupPersonDao.findPersonCodeByGroupIdAndPostId(params);
			if(null != codeList && codeList.size() > 0) task.setHandlePersonCode(codeList.get(0));
			processService.start("DEVICE_MAINTAIN_PROCESS", null, task.getId().toString(), task, null, params);
			maintainTaskIds.add(task.getId());
		}
		List<Long> taskIds  = taskDao.findProcessIds(maintainTaskIds);
		for (Long taskId : taskIds) {
			UserTask userTask = userTaskService.getById(taskId);
			ApprovalRecord ar = new ApprovalRecord();
			Date now = new Date();
		    ar.setUsername("admin");
		    ar.setActivityId(userTask.getActivityId());
	        ar.setActivityName("发布保养任务");
		    ar.setBusinessKey(userTask.getBusinessKey());
		    ar.setExecutionId(userTask.getExecutionId());
		    ar.setHandleComment("");
		    ar.setHandleTime(now);
		    ar.setOperateName("提交");
		    ar.setProcessDefinitionId(userTask.getProcessDefinitionId());
		    ar.setProcessDefinitionName(userTask.getProcessDefinitionName());
		    ar.setProcessInstanceId(userTask.getProcessInstanceId());
		    ar.setSaves(false);
		    ar.setTaskId(userTask.getTaskId());
		    ar.setFormUrl(userTask.getFormUrl());
		    ar.setTransitionIds("BYD_SEND_ID");
		    ar.setGenerateTime(now);
		    ar.setUserTaskId(taskId);
		    ar.setDescription(userTask.getDescription());
		    approvalRecordService.save(ar);
		}
		if (log.isInfoEnabled()) {
			log.info("保养计划生产保养单JOB执行完毕【{}】，本次执行耗时【{}】ms",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:ssss"),
					DateUtils.dateBetweenHumanRead(start, new Date()));
		}
	}

}
