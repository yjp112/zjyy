package com.supconit.common.web.startup.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supconit.common.utils.DateUtils;
import com.supconit.common.web.startup.IStartupPlugin;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.jobschedule.entities.ScheduleJob;
import com.supconit.jobschedule.services.ScheduleJobService;
import com.supconit.jobschedule.services.ScheduleLogService;
import com.supconit.jobschedule.services.SchedulerManagerService;

public class ScheduleJobStartupPlugin implements IStartupPlugin {
	private static final Logger log = LoggerFactory
			.getLogger(ScheduleJobStartupPlugin.class);
	private SchedulerManagerService	 schedulerManagerService=SpringContextHolder.getBean(SchedulerManagerService.class);
	private ScheduleJobService scheduleJobService=SpringContextHolder.getBean(ScheduleJobService.class);
	private ScheduleLogService scheduleLogService=SpringContextHolder.getBean(ScheduleLogService.class);
	@Override
	public void startup() {
		log.info("===============schedule job start...=================");
		this.scheduleLogService.updateErrorStatus();
		List<ScheduleJob> jobs = this.scheduleJobService.findAllEnabled();
		log.info("scheduling " + jobs.size() + " jobs");
		for (ScheduleJob job : jobs) {
			try {
				Date nextDate = schedulerManagerService.scheduleJob(job);
				if(log.isInfoEnabled()){
					log.info("JOB[" + job.toString() + "]调度成功,将于"
							+ DateUtils.format(nextDate) + "执行。");
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		log.info("===============schedule job finished.=================");
	}

}
