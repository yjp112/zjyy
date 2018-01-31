package com.supconit.inspection.jobs;

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
import com.supconit.inspection.daos.InspectionTaskDao;
import com.supconit.inspection.entities.InspectionTask;

import hc.bpm.entities.ApprovalRecord;
import hc.bpm.entities.UserTask;
import hc.bpm.services.ApprovalRecordService;
import hc.bpm.services.ProcessService;
import hc.bpm.services.UserTaskService;

/**
 * 根据巡检计划生成巡检单
 * 
 * @author
 *
 */
public class TaskPublishPrepareJob {
	private transient static final Logger log = LoggerFactory.getLogger(TaskPublishPrepareJob.class);
	private static SimpleJdbc jdbc = (SimpleJdbc) SpringContextHolder.getBean(SimpleJdbc.class);
	private static InspectionTaskDao taskDao = SpringContextHolder.getBean(InspectionTaskDao.class);
	private static ProcessService processService = SpringContextHolder.getBean(ProcessService.class);
	private static ApprovalRecordService approvalRecordService = SpringContextHolder.getBean(ApprovalRecordService.class);
	private static UserTaskService userTaskService = SpringContextHolder.getBean(UserTaskService.class);
	private static DutyGroupPersonDao dutyGroupPersonDao = SpringContextHolder.getBean(DutyGroupPersonDao.class);

	public void readyForTask() throws ParseException {
		Date start = new Date();
		if (log.isInfoEnabled()) {
			log.info("巡检计划生产巡检单JOB开始执行【{}】.........", DateUtils.format(start, "yyyy-MM-dd HH:mm:ss:ssss"));
		}
		taskDao.readyForTask();
		List<InspectionTask> tasks = taskDao.findReadyTask();
		List<Long> inspectionTaskIds = new ArrayList<>();
		for (InspectionTask task : tasks) {
			String oldinspectionCode = task.getInspectionCode();
			String newinspectionCode = SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.INSPECTION_ORDER);
			String updateinspectionCode1 = "update inspection_task set inspection_code='"+newinspectionCode+"' where ID="+task.getId();
			String updateinspectionCode2 = "update inspection_task_content set inspection_code='"+newinspectionCode+"' where inspection_code='"+oldinspectionCode+"'";
			task.setInspectionCode(newinspectionCode);
			jdbc.batchUpdate(new String[]{updateinspectionCode1,updateinspectionCode2});
			task.setProcessInstanceName("巡检单:" + task.getInspectionCode());
			// taskService.startProcess(task);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("_initiator", "admin");
			params.put("task", task);
			params.put("groupId", task.getInspectionGroupId());
			params.put("postId", 1);//组长
			List<String> codeList = dutyGroupPersonDao.findPersonCodeByGroupIdAndPostId(params);
			if(null != codeList && codeList.size() > 0) task.setHandlePersonCode(codeList.get(0));
			processService.start("DEVICE_INSPECTION_PROCESS", null, task.getId().toString(), task, null, params);
			inspectionTaskIds.add(task.getId());
		}
		List<Long> taskIds  = taskDao.findProcessIds(inspectionTaskIds);
		for (Long taskId : taskIds) {
			UserTask userTask = userTaskService.getById(taskId);
			ApprovalRecord ar = new ApprovalRecord();
			Date now = new Date();
		    ar.setUsername("admin");
		    ar.setActivityId(userTask.getActivityId());
	        ar.setActivityName("发布巡检任务");
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
		    ar.setTransitionIds("XJD_SEND_ID");
		    ar.setGenerateTime(now);
		    ar.setUserTaskId(taskId);
		    ar.setDescription(userTask.getDescription());
		    approvalRecordService.save(ar);
		}
		if (log.isInfoEnabled()) {
			log.info("巡检计划生产巡检单JOB执行完毕【{}】，本次执行耗时【{}】ms",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:ssss"),
					DateUtils.dateBetweenHumanRead(start, new Date()));
		}
	}

}
