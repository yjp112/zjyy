package com.supconit.jobschedule.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MethodInvoker;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.utils.DateUtils;
import com.supconit.jobschedule.entities.ScheduleJob;
import com.supconit.jobschedule.entities.ScheduleLog;
import com.supconit.jobschedule.services.ScheduleJobService;
import com.supconit.jobschedule.services.ScheduleLogService;
import com.supconit.jobschedule.services.SchedulerManagerService;
import com.supconit.jobschedule.utils.CronExpressionEx;

@Service
@Transactional
public class SchedulerManagerServiceImpl implements ApplicationContextAware,
		SchedulerManagerService{
	private static final Logger log = LoggerFactory
			.getLogger(SchedulerManagerServiceImpl.class);
	@Resource
	private Scheduler scheduler;
	@Resource
	private ScheduleJobService scheduleJobService;
	@Resource
	private ScheduleLogService scheduleLogService;
	private ApplicationContext applicationContext;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public Date scheduleJob(ScheduleJob scheduleJob) {

		try {
			if (scheduleJob == null) {
				log.warn("ignore unknowed scheduleJobId:" + scheduleJob);
				return null;
			}
			Date now = new Date();
			Date startDate = scheduleJob.getStartDate();
			Date endDate = scheduleJob.getEndDate();
			if (startDate == null||startDate.before(now)) {
				startDate = now;
			}

			if (endDate != null) {
				if (startDate.after(endDate)) {
					log.error("起始时间不能大于结束时间。");
					return null;
				}
				if (endDate.before(now)) {
					log.warn("JOB结束时间[" + DateUtils.format(endDate)
							+ "],小于当前时间[" + DateUtils.format(now)
							+ "],JOB无需调度。");
					return null;
				}
			}

			log.info("scheduling job:" + scheduleJob.toString());

			CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(
					scheduleJob.getTriggerName(), scheduleJob.getGroupName());
			Date nextDate;
			if (null == trigger) {
				MethodInvoker methodInvoker = new MethodInvoker();
				if ("class".equalsIgnoreCase(scheduleJob.getBeanOrClass())) {
					methodInvoker.setTargetClass(Class.forName(scheduleJob
							.getTargetObject()));
					methodInvoker.setTargetObject(methodInvoker
							.getTargetClass().newInstance());
				} else {
					methodInvoker.setTargetObject(this.applicationContext
							.getBean(scheduleJob.getTargetObject()));
				}
				methodInvoker.setTargetMethod(scheduleJob.getTargetMethod());
				Object[] arguments = StringUtils.split(
						scheduleJob.getTargetArguments(), ";");
				if(StringUtils.endsWith(scheduleJob.getTargetArguments(), ";")){
					Object[] arguments2=new Object[arguments.length+1];
					System.arraycopy(arguments, 0, arguments2, 0, arguments.length);
					methodInvoker.setArguments(arguments2);
					
				}else{
					methodInvoker.setArguments(arguments);
				}				
				methodInvoker.prepare();

				Class<?> jobClass = ("Y".equalsIgnoreCase(scheduleJob
						.getConcurrent()) ? MethodInvokingJobEx.class
						: StatefulMethodInvokingJobEx.class);
				JobDetail jobDetail = new JobDetail(scheduleJob.getJobName(),
						scheduleJob.getGroupName(), jobClass);

				jobDetail.getJobDataMap().put("methodInvoker", methodInvoker);
				jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);
				jobDetail.getJobDataMap().put("scheduleLogService",
						this.scheduleLogService);
				jobDetail.getJobDataMap().put("scheduleJobService",
						this.scheduleJobService);

				trigger = new CronTrigger(scheduleJob.getTriggerName(),
						scheduleJob.getGroupName(),
						scheduleJob.getCronExpression());

				trigger.setStartTime(startDate);
				if (endDate != null) {
					trigger.setEndTime(endDate);
					Date d = getNextValidTimeAfter(trigger.getCronExpression(), now, endDate);
					if (d==null) {
						return null;
					}
				}
				nextDate = this.scheduler.scheduleJob(jobDetail, trigger);
			} else {
				trigger.setCronExpression(scheduleJob.getCronExpression());

				trigger.setStartTime(startDate);
				if (endDate != null) {
					trigger.setEndTime(endDate);
					Date d = trigger.getFireTimeAfter(now);
					if (d.before(now)) {
						log.warn("当前时间为" + DateUtils.format(now)
								+ ",JOB下次执行时间为：" + DateUtils.format(d)
								+ "，已经过期，JOB不再调度执行");
						return null;
					}
				}
				nextDate = this.scheduler.rescheduleJob(trigger.getName(),
						trigger.getGroup(), trigger);
			}
			scheduleJob.setNextDate(nextDate);
			scheduleJob.setStatus(STATUS_READY);
			this.scheduleJobService.updateByPrimaryKeySelective(scheduleJob);

			return nextDate;
		} catch (Exception e) {
			throw new BusinessDoneException(e.getMessage(), e);
		}
	}

	public Date rescheduleJob(ScheduleJob scheduleJob) {
		try {
			if (scheduleJob == null) {
				log.warn("ignore unknowed scheduleJobId:" + scheduleJob);
				return null;
			}
			Date now = new Date();
			Date startDate = scheduleJob.getStartDate();
			Date endDate = scheduleJob.getEndDate();
			if (startDate == null||startDate.before(now)) {
				startDate = now;
			}

			if (endDate != null) {
				if (startDate.after(endDate)) {
					log.error("起始时间不能大于结束时间。");
					return null;
				}
				if (endDate.before(now)) {
					log.warn("JOB结束时间[" + DateUtils.format(endDate)
							+ "],小于当前时间[" + DateUtils.format(now)
							+ "],JOB无需调度。");
					return null;
				}
			}

			CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(
					scheduleJob.getTriggerName(), scheduleJob.getGroupName());

			if (null == trigger) {
				throw new BusinessDoneException(
						"trigger not exist:scheduleJobId="
								+ scheduleJob.getId());
			}

			trigger.setCronExpression(scheduleJob.getCronExpression());

			trigger.setStartTime(startDate);
			if (endDate != null) {
				trigger.setEndTime(endDate);
				Date d = getNextValidTimeAfter(trigger.getCronExpression(), now, endDate);
				if (d==null) {
					return null;
				}
			}

			Date nextDate = this.scheduler.rescheduleJob(trigger.getName(),
					trigger.getGroup(), trigger);

			scheduleJob.setStatus(STATUS_READY);
			scheduleJob.setNextDate(nextDate);
			this.scheduleJobService.updateByPrimaryKeySelective(scheduleJob);
			return nextDate;
		} catch (Exception e) {
			throw new BusinessDoneException(e.getMessage(), e);
		}
	}

	public void stopJob(ScheduleJob scheduleJob) {
		try {
			if (scheduleJob == null) {
				log.warn("ignore unknowed scheduleJobId:" + scheduleJob);
				return;
			}

			CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(
					scheduleJob.getTriggerName(), scheduleJob.getGroupName());

			if (null != trigger) {
				this.scheduler.deleteJob(scheduleJob.getJobName(),
						scheduleJob.getGroupName());
			}

			scheduleJob.setStatus(STATUS_STOP);
			this.scheduleJobService.updateByPrimaryKeySelective(scheduleJob);
		} catch (Exception e) {
			throw new BusinessDoneException(e.getMessage(), e);
		}

	}

	public void deleteJob(ScheduleJob scheduleJob) {
		try {
			if (scheduleJob == null) {
				log.warn("ignore unknowed scheduleJobId:" + scheduleJob);
				return;
			}

			CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(
					scheduleJob.getTriggerName(), scheduleJob.getGroupName());

			if (null != trigger) {
				this.scheduler.deleteJob(scheduleJob.getJobName(),
						scheduleJob.getGroupName());
			}

			scheduleJob.setStatus(STATUS_DELETE);
			this.scheduleJobService.updateByPrimaryKeySelective(scheduleJob);
		} catch (Exception e) {
			throw new BusinessDoneException(e.getMessage(), e);
		}

	}

	public void pauseJob(ScheduleJob scheduleJob) {
		try {
			if (scheduleJob == null) {
				log.warn("ignore unknowed scheduleJobId:" + scheduleJob);
				return;
			}

			CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(
					scheduleJob.getTriggerName(), scheduleJob.getGroupName());

			if (null != trigger)
				this.scheduler.pauseJob(scheduleJob.getJobName(),
						scheduleJob.getGroupName());
			scheduleJob.setStatus(STATUS_PAUSING);
			this.scheduleJobService.updateByPrimaryKeySelective(scheduleJob);

		} catch (Exception e) {
			throw new BusinessDoneException(e.getMessage(), e);
		}

	}

	public void resumeJob(ScheduleJob scheduleJob) {
		try {
			if (scheduleJob == null) {
				log.warn("ignore unknowed scheduleJobId:" + scheduleJob);
				return;
			}

			CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(
					scheduleJob.getTriggerName(), scheduleJob.getGroupName());

			if (null != trigger)
				this.scheduler.resumeJob(scheduleJob.getJobName(),
						scheduleJob.getGroupName());
			scheduleJob.setStatus(STATUS_READY);
			scheduleJob.setNextDate(trigger.getNextFireTime());
			this.scheduleJobService.updateByPrimaryKeySelective(scheduleJob);

		} catch (Exception e) {
			throw new BusinessDoneException(e.getMessage(), e);
		}

	}

	/**获取下一个调度的时间
	 * @param cronExpression
	 * @param afterDate
	 * @param endDate
	 * @return
	 */
	protected static Date getNextValidTimeAfter(String cronExpression,Date afterDate,Date endDate ) {
		try {
			CronExpressionEx cronExpressionEx = new CronExpressionEx(cronExpression);
			Date result=cronExpressionEx.getNextValidTimeAfter(afterDate);
			if(endDate==null||endDate.compareTo(result)>=0){
				return result;
			}else if(endDate.before(result)){
				log.warn("JOB结束时间为" + DateUtils.format(endDate)
						+ ",JOB下次执行时间为：" + DateUtils.format(result)
						+ "，已经过期。");
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static class MethodInvokingJobEx extends QuartzJobBean {
		private static final Log log = LogFactory
				.getLog(MethodInvokingJobEx.class);
		private MethodInvoker methodInvoker;
		private ScheduleJob scheduleJob;
		private ScheduleLogService scheduleLogService;
		private ScheduleJobService scheduleJobService;

		public void setMethodInvoker(MethodInvoker methodInvoker) {
			this.methodInvoker = methodInvoker;
		}

		public void setScheduleLogService(ScheduleLogService schedulerLogService) {
			this.scheduleLogService = schedulerLogService;
		}

		public void setScheduleJobService(ScheduleJobService scheduleJobService) {
			this.scheduleJobService = scheduleJobService;
		}

		public void setScheduleJob(ScheduleJob jobCfg) {
			this.scheduleJob = jobCfg;
		}

		protected void executeInternal(JobExecutionContext context)
				throws JobExecutionException {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			setScheduleJob((ScheduleJob) dataMap.get("scheduleJob"));
			setScheduleLogService((ScheduleLogService) dataMap
					.get("scheduleLogService"));
			setScheduleJobService((ScheduleJobService) dataMap
					.get("scheduleJobService"));
			long startTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				if (StringUtils.isNotBlank(this.scheduleJob.getDescription())) {
					log.debug("正在执行任务" + this.scheduleJob.getJobName() + "["
							+ this.scheduleJob.getDescription() + "]。");
				} else {
					log.debug("正在执行任务" + this.scheduleJob.getJobName() + "。");
				}
			}

			Date nextFireTime=null;
			ScheduleLog scheduleLog = new ScheduleLog();
			scheduleLog.setStartTime(new Date(startTime));
			scheduleLog.setScheduleId(this.scheduleJob.getId());
			scheduleLog.setSuccess("I");
			scheduleLog.setTargetArguments(scheduleJob.getTargetArguments());
			try {
				this.scheduleLogService.insertSelective(scheduleLog);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}		
			long endTime = 0;
			try {
				context.setResult(this.methodInvoker.invoke());

				endTime = System.currentTimeMillis();
				scheduleLog.setSuccess("Y");
				scheduleLog.setEndTime(new Date(endTime));
				scheduleLog.setExcuteTime(buildTime(startTime, endTime));
				scheduleLogService.update(scheduleLog);
				nextFireTime=getNextValidTimeAfter(scheduleJob.getCronExpression(),new Date(),scheduleJob.getEndDate());
			} catch (Exception ex) {
				endTime = System.currentTimeMillis();
				scheduleLog.setEndTime(new Date(endTime));
				StringWriter strWriter = new StringWriter();
				PrintWriter writer = new PrintWriter(strWriter);
				ex.printStackTrace(writer);
				writer.close();
				String errorMsg = strWriter.toString();
				scheduleLog.setErrorMsg(errorMsg.length() < 1900 ? errorMsg
						: errorMsg.substring(0, 1900));
				scheduleLog.setExcuteTime(buildTime(startTime, endTime));
				scheduleLog.setSuccess("N");
				this.scheduleLogService.update(scheduleLog);
				log.error("任务" + this.scheduleJob.getJobName()
						+ "执行过程发生错误,请检查任务的错误日志信息.", ex);

				if ("N".equalsIgnoreCase(this.scheduleJob.getIgnoreError())){
					stopInError(context);
				}else{
					nextFireTime=getNextValidTimeAfter(scheduleJob.getCronExpression(),new Date(),scheduleJob.getEndDate());
				}


			}
			
			ScheduleJob loadJob=scheduleJobService.getById(scheduleJob.getId());
			loadJob.setNextDate(nextFireTime);
			scheduleJobService.updateByPrimaryKeySelective(loadJob);
			
			if (log.isDebugEnabled()) {
				log.debug("任务" + this.scheduleJob.getJobName() + "执行完毕,耗时"
						+ buildTime(startTime, endTime));
			}
		}

		private String buildTime(long startTime, long endTime) {
			return (endTime - startTime) + "毫秒";
		}

		private void stopInError(JobExecutionContext context) {
			try {
				log.error("因发生异常,终止任务" + this.scheduleJob.getJobName()
						+ "的后续调度");
				context.getScheduler().unscheduleJob(
						this.scheduleJob.getJobName(),
						this.scheduleJob.getGroupName());
				context.getScheduler().deleteJob(this.scheduleJob.getJobName(),
						this.scheduleJob.getGroupName());
				scheduleJob.setStatus(STATUS_STOP);
				this.scheduleJobService.updateByPrimaryKeySelective(scheduleJob);				
			} catch (SchedulerException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public boolean runImmediately(ScheduleJob scheduleJob) {
		if (scheduleJob == null) {
			log.warn("ignore unknowed scheduleJobId:" + scheduleJob);
			return false;
		}

		log.info("scheduling job:" + scheduleJob.toString());
		Date startTime=new Date();
		Date endTime = null;
		ScheduleLog scheduleLog = new ScheduleLog();
		scheduleLog.setScheduleId(scheduleJob.getId());
		try {
			MethodInvoker methodInvoker = new MethodInvoker();
			if ("class".equalsIgnoreCase(scheduleJob.getBeanOrClass())) {
				methodInvoker.setTargetClass(Class.forName(scheduleJob
						.getTargetObject()));
				methodInvoker.setTargetObject(methodInvoker.getTargetClass()
						.newInstance());
			} else {
				methodInvoker.setTargetObject(this.applicationContext
						.getBean(scheduleJob.getTargetObject()));
			}
			methodInvoker.setTargetMethod(scheduleJob.getTargetMethod());
			Object[] arguments = StringUtils.split(
					scheduleJob.getTargetArguments(), ";");
			if(StringUtils.endsWith(scheduleJob.getTargetArguments(), ";")){
				Object[] arguments2=new Object[arguments.length+1];
				System.arraycopy(arguments, 0, arguments2, 0, arguments.length);
				methodInvoker.setArguments(arguments2);
				
			}else{
				methodInvoker.setArguments(arguments);
			}
			methodInvoker.prepare();
			startTime=new Date();
			scheduleLog.setStartTime(startTime);
			scheduleLog.setScheduleId(scheduleJob.getId());
			scheduleLog.setSuccess("I");
			scheduleLog.setTargetArguments(scheduleJob.getTargetArguments());
			try {
				this.scheduleLogService.insert(scheduleLog);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			methodInvoker.invoke();
			endTime = new Date();
			scheduleLog.setSuccess("Y");
			scheduleLog.setEndTime(endTime);
			/*scheduleLog.setExcuteTime((endTime.getTime() - startTime.getTime())
					+ "毫秒");*/
			scheduleLog.setExcuteTime(DateUtils.dateBetweenHumanRead(startTime, endTime));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter strWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(strWriter);
			e.printStackTrace(writer);
			writer.close();
			String errorMsg = strWriter.toString();
			endTime = new Date();
			scheduleLog.setStartTime(startTime);
			scheduleLog.setSuccess("N");
			scheduleLog.setEndTime(endTime);
			/*scheduleLog.setExcuteTime((endTime.getTime() - startTime.getTime())
					+ "毫秒");*/
			scheduleLog.setExcuteTime(DateUtils.dateBetweenHumanRead(startTime, endTime));
			scheduleLog.setErrorMsg(errorMsg.length() < 1900 ? errorMsg
					: errorMsg.substring(0, 1900));
		}
		try {
			if("Y".equalsIgnoreCase(scheduleLog.getSuccess())){
				scheduleLog.setErrorMsg("");
			}
			this.scheduleLogService.update(scheduleLog);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return "Y".equals(scheduleLog.getSuccess());
	}
	public static class StatefulMethodInvokingJobEx extends MethodInvokingJobEx
			implements StatefulJob {

		// No implementation, just an addition of the tag interface StatefulJob
		// in order to allow stateful method invoking jobs.
	}

}
