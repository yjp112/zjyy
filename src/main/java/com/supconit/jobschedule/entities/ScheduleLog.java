/**
 * 
 */
package com.supconit.jobschedule.entities;
							import java.util.Date;

import org.apache.commons.lang.StringUtils;

import hc.base.domains.OrderPart;
/**
 * @author 
 * @create 2014-06-05 11:26:12 
 * @since 
 * 
 */
public class ScheduleLog extends ScheduleJob implements hc.base.domains.Orderable{

	private static final long	serialVersionUID	= 1L;
		
			
	private Long scheduleId;		
	private Date startTime;		
	private Date endTime;		
	private String excuteTime;		
	private String success;		
	private String errorMsg;	
	private OrderPart[]			orderParts;	
				
	
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
		}else if("I".equalsIgnoreCase(success)){
			return "正在执行";
		}
		return "";
	}
}
