/**
 * 
 */
package com.supconit.nhgl.schedule.entites;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.supconit.jobschedule.entities.ScheduleJob;

import hc.base.domains.OrderPart;
/**
 * @author 
 * @create 2014-06-05 11:26:12 
 * @since 
 * 
 */
public class PlanLog extends ScheduleJob implements hc.base.domains.Orderable{

	private static final long	serialVersionUID	= 1L;
		
			
	private Long scheduleId;		
	private Date startTime;		
	private Date endTime;		
	private String excuteTime;		
	private String success;		
	private String errorMsg;		
	private OrderPart[]			orderParts;	
	//虚拟字段
	private String taskName;//任务名称
	private String categoryText;//任务分类
	private Integer taskVesting;//任务归属
	private Integer executeType;
	private String typeName;//任务归属名
	private String strStatus;//任务执行状态
	private String executeTypeName;//执行频率类别名
	private Integer taskId;
	
	
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getExecuteTypeName() {
		return executeTypeName;
	}
	public void setExecuteTypeName(String executeTypeName) {
		this.executeTypeName = executeTypeName;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCategoryText() {
		return categoryText;
	}
	public void setCategoryText(String categoryText) {
		this.categoryText = categoryText;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getTaskVesting() {
		return taskVesting;
	}
	public void setTaskVesting(Integer taskVesting) {
		this.taskVesting = taskVesting;
	}
	public Integer getExecuteType() {
		return executeType;
	}
	public void setExecuteType(Integer executeType) {
		this.executeType = executeType;
	}
					
	public Long getScheduleId() {
		return scheduleId;
	}
	
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
				
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
				
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
				
	public String getExcuteTime() {
		return excuteTime;
	}
	
	public void setExcuteTime(String excuteTime) {
		this.excuteTime = excuteTime;
	}
				
	public String getSuccess() {
		return success;
	}
	
	public void setSuccess(String success) {
		this.success = success;
	}
				
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
	//============
	
	public String getSuccessText(){
		if(StringUtils.isBlank(success)){
			return "";
		}else if("Y".equalsIgnoreCase(success)){
			return "成功";
		}else if("N".equalsIgnoreCase(success)){
			return "失败";
		}
		return "";
	}
}
