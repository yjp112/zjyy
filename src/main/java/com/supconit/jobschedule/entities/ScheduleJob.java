/**
 * 
 */
package com.supconit.jobschedule.entities;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.supconit.jobschedule.services.SchedulerManagerService;

import hc.base.domains.OrderPart;

/**
 * @author
 * @create 2014-06-05 11:21:48
 * @since
 * 
 */
public class ScheduleJob extends hc.base.domains.LongId implements
		hc.base.domains.Orderable {

	private static final long serialVersionUID = 1L;

	private String jobSubject;
	private String jobName;
	private String groupName;
	private String beanOrClass;
	private String targetObject;
	private String targetMethod;
	private String targetArguments;
	private String cronExpression;
	private Date startDate;
	private Date nextDate;
	private Date endDate;
	private String description;
	private String ignoreError; // 是否忽略错误，Y/N
	private String concurrent; // 是否允许并行，Y/N
	private Integer status; // 0运行;1暂停; 2;停止;-1删除
	private OrderPart[] orderParts;

	public String getJobSubject() {
		return jobSubject;
	}

	public void setJobSubject(String jobSubject) {
		this.jobSubject = jobSubject;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getBeanOrClass() {
		return beanOrClass;
	}

	public void setBeanOrClass(String beanOrClass) {
		this.beanOrClass = beanOrClass;
	}

	public String getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public String getTargetArguments() {
		return targetArguments;
	}

	public void setTargetArguments(String targetArguments) {
		this.targetArguments = targetArguments;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getNextDate() {
		return nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIgnoreError() {
		return ignoreError;
	}

	public void setIgnoreError(String ignoreError) {
		this.ignoreError = ignoreError;
	}

	public String getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/*
	 * @see hc.base.domains.Orderable#getOrderParts()
	 */
	@Override
	public OrderPart[] getOrderParts() {
		return this.orderParts;
	}

	/*
	 * @see hc.base.domains.Orderable#setOrderParts(hc.base.domains.OrderPart[])
	 */
	@Override
	public void setOrderParts(OrderPart[] orderParts) {
		this.orderParts = orderParts;
	}

	public String getTriggerName() {
		return getJobName() + "_Trigger";
	}

	/**
	 * 是否忽略错误，Y/N
	 * 
	 * @return
	 */
	public String getIgnoreErrorText() {
		if (StringUtils.isBlank(ignoreError)) {
			return "";
		} else if ("Y".equalsIgnoreCase(ignoreError)) {
			return "是";
		} else if ("N".equalsIgnoreCase(ignoreError)) {
			return "否";
		}
		return ignoreError;
	}

	/**
	 * 是否允许并行，Y/N
	 * 
	 * @return
	 */
	public String getConcurrentText() {
		if (StringUtils.isBlank(concurrent)) {
			return "";
		} else if ("Y".equalsIgnoreCase(concurrent)) {
			return "是";
		} else if ("N".equalsIgnoreCase(concurrent)) {
			return "否";
		}
		return concurrent;
	}

	/**
	 * 0运行;1暂停; 2;停止;-1删除
	 * 
	 * @return
	 */
	public String getStatusText() {
		if (status == null) {
			return "";
		} else if (status.intValue() == SchedulerManagerService.STATUS_READY) {
			return "运行";
		} else if (status.intValue() == SchedulerManagerService.STATUS_PAUSING) {
			return "暂停";
		} else if (status.intValue() == SchedulerManagerService.STATUS_STOP) {
			return "停止";
		}else if(status.intValue()==SchedulerManagerService.STATUS_DELETE){
			return "删除";
		}
		return status.toString();
	}

	public String getBeanOrClassText(){
		if(StringUtils.isBlank(beanOrClass)){
			return "";
		}
		if("bean".equalsIgnoreCase(beanOrClass)){
			return "Spring bean";
		}else if("class".equalsIgnoreCase(beanOrClass)){
			return "类名";
		}
		return beanOrClass;
	}
	@Override
	public String toString() {
		return "ScheduleJob [jobSubject=" + jobSubject + ", jobName=" + jobName
				+ ", groupName=" + groupName + ", beanOrClass=" + beanOrClass
				+ ", targetObject=" + targetObject + ", targetMethod="
				+ targetMethod + ", targetArguments=" + targetArguments
				+ ", cronExpression=" + cronExpression + ", startDate="
				+ startDate + ", nextDate=" + nextDate + ", endDate=" + endDate
				+ ", ignoreError=" + ignoreError + ", concurrent=" + concurrent
				+ ", description=" + description + ", status=" + status
				+ ", orderParts=" + Arrays.toString(orderParts) + "]";
	}

}
