/**
 * 
 */
package com.supconit.nhgl.schedule.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.utils.UniqueIdUtils;
import com.supconit.jobschedule.entities.ScheduleJob;
import com.supconit.jobschedule.services.ScheduleJobService;
import com.supconit.jobschedule.services.SchedulerManagerService;
import com.supconit.nhgl.schedule.dao.MonitorTaskDao;
import com.supconit.nhgl.schedule.entites.CriteriaDetail;
import com.supconit.nhgl.schedule.entites.MonitorObject;
import com.supconit.nhgl.schedule.entites.MonitorTask;
import com.supconit.nhgl.schedule.entites.TaskCatagory;
import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;
import com.supconit.nhgl.schedule.service.CriteriaDetailService;
import com.supconit.nhgl.schedule.service.MonitorObjectService;
import com.supconit.nhgl.schedule.service.TaskCatagoryService;
import com.supconit.nhgl.schedule.service.TaskExecutionPlanService;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:51 
 * @since 
 * 
 */
@Service("dlhmc_ycynjc_jcrw_service")
public class MonitorTaskServiceImpl extends hc.orm.AbstractBasicOrmService<MonitorTask, Long> implements com.supconit.nhgl.schedule.service.MonitorTaskService {
	
	private transient static final Logger	logger	= LoggerFactory.getLogger(MonitorTaskServiceImpl.class);
	
	@Autowired
	private MonitorTaskDao		taskDao;
	@Resource
	private MonitorObjectService objectService;
	@Resource
	private TaskExecutionPlanService planService;
	@Resource
	private CriteriaDetailService detailService;
	@Resource
	private TaskCatagoryService catagoryService;
	@Resource
	private ScheduleJobService jobService;
	@Resource
	private SchedulerManagerService schedulerManagerService;
	@Value("${job.unusual.device_runtime}")
	private String taskCodeDevice;
	@Value("${job.unusual.electric}")
	private String taskCodeElectric;
	@Value("${job.unusual.water}")
	private String taskCodeWater;
	@Override
	@Transactional(readOnly = true)
	public MonitorTask getById(Long id) {
		return this.taskDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(MonitorTask entity) {
		this.taskDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(MonitorTask entity) {
		this.taskDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(MonitorTask task) {
		this.taskDao.delete(task);
		planService.deleteByTaskCode(task.getTaskCode());
		objectService.deleteByTaskCode(task.getTaskCode());
		detailService.deleteByTaskCode(task.getTaskCode());
		if(task.getJobId()!=null){
			ScheduleJob job=jobService.getById(task.getJobId());
			if(job!=null){
				schedulerManagerService.stopJob(job);
				jobService.delete(job);
			}
			
		}
	}
	
	@Override
	@Transactional
	public void save(MonitorTask task) {
		if(StringUtils.isBlank(task.getTaskCode())){
			
			if(task.getTaskVesting().intValue()==1){
	        	//设备运行状态
				task.setTaskCode(UniqueIdUtils.next(taskCodeDevice));
	        }else if(task.getTaskVesting().intValue()==2){
	        	//电
	        	task.setTaskCode(UniqueIdUtils.next(taskCodeElectric));
	        }else if(task.getTaskVesting().intValue()==3){
	        	//水
	        	task.setTaskCode(UniqueIdUtils.next(taskCodeWater));
	        }else if(task.getTaskVesting().intValue()==4){
	        	//气
	        	task.setTaskCode(UniqueIdUtils.next(taskCodeDevice));//TODO 气暂未定义
	        }
		}
		
		super.save(task);
		TaskExecutionPlan taskExecutionPlan = task.getExecutionPlan();
		taskExecutionPlan.setTaskCode(task.getTaskCode());
		taskExecutionPlan.setExecuteParam(taskExecutionPlan.getExecuteParam());
		planService.save(taskExecutionPlan);
		objectService.deleteByTaskCode(task.getTaskCode());
		for (MonitorObject object : task.getMonitorObjects()) {
			object.setTaskCode(task.getTaskCode());
			objectService.save(object);
		}
		detailService.deleteByTaskCode(task.getTaskCode());
		for (CriteriaDetail detail : task.getCriteriaDetails()) {
			detail.setTaskCode(task.getTaskCode());
			detailService.save(detail);
		}
		ScheduleJob job=new ScheduleJob();
		job.setJobSubject(task.getTaskName());
		job.setJobName(task.getTaskName());
		TaskCatagory catagory=catagoryService.getById(task.getTaskType());
		if(catagory!=null){
			job.setGroupName(catagory.getCatagoryText());
		}
		job.setBeanOrClass("class");
		//TASK_VESTING	任务归属		INT		设备运行状态：1；电：2；水：3；气：4；
        if(task.getTaskVesting().intValue()==1){
        	//设备运行状态
    		job.setTargetObject("com.supconit.nhgl.job.DeviceRuntimeStatusMonitorJob");
    		job.setTargetMethod("doJob");
    		job.setTargetArguments(task.getTaskCode());
        }else if(task.getTaskVesting().intValue()==2){
        	//电
    		job.setTargetObject("com.supconit.nhgl.job.EnergyMonitorJob");
    		job.setTargetMethod("doJob");
    		job.setTargetArguments(task.getTaskCode());
        }else if(task.getTaskVesting().intValue()==3){
        	//水
    		job.setTargetObject("com.supconit.nhgl.job.EnergyMonitorJob");
    		job.setTargetMethod("doJob");
    		job.setTargetArguments(task.getTaskCode());
        }else if(task.getTaskVesting().intValue()==4){
        	//气
    		job.setTargetObject("com.supconit.nhgl.job.EnergyMonitorJob");
    		job.setTargetMethod("doJob");
    		job.setTargetArguments(task.getTaskCode());
        }
		job.setCronExpression(task.getExecutionPlan().getCronExpression());
		job.setStartDate(task.getExecutionPlan().getStartDate());
		//job.setNextDate(task.getExecutionPlan().getEndDate());
		job.setEndDate(task.getExecutionPlan().getEndDate());;
		job.setDescription(task.getTaskDesc());
		job.setIgnoreError("Y"); // 是否忽略错误，Y/N
		job.setConcurrent("Y"); // 是否允许并行，Y/N
		job.setStatus(SchedulerManagerService.STATUS_STOP); // 0运行;1暂停; 2;停止;-1删除
		if(task.getJobId()!=null){//update
			ScheduleJob tmmJob=jobService.getById(task.getJobId());
			if(tmmJob!=null){
				job.setId(task.getJobId());
				schedulerManagerService.stopJob(job);
			}
		}
		jobService.save(job);
		schedulerManagerService.scheduleJob(job);
		if(task.getJobId()==null){
			task.setJobId(job.getId());
			this.update(task);
		}
		
	}

	/*
	 * @see com.supconit.nhgl.ycynjc.services.MonitorTaskService#find(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.MonitorTask)
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<MonitorTask> find(Pageable<MonitorTask> pager, MonitorTask condition) {
		
		return this.taskDao.findByPager(pager, condition);
	}

	@Override
	public MonitorTask selectByTaskCode(String taskCode) {
		return taskDao.selectByTaskCode(taskCode);
	}

	@Override
	public Pageable<MonitorTask> findTaskList(Pageable<MonitorTask> pager,
			MonitorTask condition) {
		return this.taskDao.findTaskList(pager, condition);
	}

	@Override
	public MonitorTask findById(Integer id) {
		return this.taskDao.findById(id);
	}
	
	@Override
	public List<MonitorTask> getByTaskType(Long taskType){
		return this.taskDao.getByTaskType(taskType);
	}
}
