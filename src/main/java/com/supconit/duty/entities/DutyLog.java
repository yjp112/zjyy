package com.supconit.duty.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;
/*
 * 值班日志
 */
public class DutyLog extends AuditExtend {

	private static final long	serialVersionUID	= 1L;
	private Long dutyType;              //值班类型       1:消防 2：安防 3：总值班4：呼叫中心
	private Date dutyDate;              //值班日期		         			
	private Long orderType;             //班次	 1:早班 2：中班 3：晚班
	private String orderName;
	private Long groupType;             //班组	       字典表定义	
	private Date startTime;             //开始时间		
	private Date endTime;               //结束时间		               		
	private Date replaceTime;           //交接时间	
	private Long replacePersonId;       //交接人ID		
	private String replacePersonName;   //交接人姓名			
	private Long personId;              //值班人ID					
	private String personName; 	        //值班人姓名			
	private String replaceDesc;         //交班遗留问题
	
	private String dutyDesc1;           //消防信息        总值班日志填写
	private String dutyDesc2;           //安防信息
	private String dutyDesc3;           //更夫信息
	private String dutyDesc4;           //其他信息
	
	private Long status;                //编辑状态		0:暂存 1：提交	
	
	private Long updateId;				//更新人ID
	private String updateName;			//更新人姓名
	private Date updatedate;      		//更新时间
	private List<DutyLogDetail> dutyLogDetailList=new ArrayList<DutyLogDetail>();
	
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Long getDutyType() {
		return dutyType;
	}
	public void setDutyType(Long dutyType) {
		this.dutyType = dutyType;
	}
	public Date getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}
	public Long getOrderType() {
		return orderType;
	}
	public void setOrderType(Long orderType) {
		this.orderType = orderType;
	}
	public Long getGroupType() {
		return groupType;
	}
	public void setGroupType(Long groupType) {
		this.groupType = groupType;
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
	public Date getReplaceTime() {
		return replaceTime;
	}
	public void setReplaceTime(Date replaceTime) {
		this.replaceTime = replaceTime;
	}
	public Long getReplacePersonId() {
		return replacePersonId;
	}
	public void setReplacePersonId(Long replacePersonId) {
		this.replacePersonId = replacePersonId;
	}
	public String getReplacePersonName() {
		return replacePersonName;
	}
	public void setReplacePersonName(String replacePersonName) {
		this.replacePersonName = replacePersonName;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getReplaceDesc() {
		return replaceDesc;
	}
	public void setReplaceDesc(String replaceDesc) {
		this.replaceDesc = replaceDesc;
	}
	public String getDutyDesc1() {
		return dutyDesc1;
	}
	public void setDutyDesc1(String dutyDesc1) {
		this.dutyDesc1 = dutyDesc1;
	}
	public String getDutyDesc2() {
		return dutyDesc2;
	}
	public void setDutyDesc2(String dutyDesc2) {
		this.dutyDesc2 = dutyDesc2;
	}
	public String getDutyDesc3() {
		return dutyDesc3;
	}
	public void setDutyDesc3(String dutyDesc3) {
		this.dutyDesc3 = dutyDesc3;
	}
	public String getDutyDesc4() {
		return dutyDesc4;
	}
	public void setDutyDesc4(String dutyDesc4) {
		this.dutyDesc4 = dutyDesc4;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public List<DutyLogDetail> getDutyLogDetailList() {
		return dutyLogDetailList;
	}
	public void setDutyLogDetailList(List<DutyLogDetail> dutyLogDetailList) {
		this.dutyLogDetailList = dutyLogDetailList;
	}
	
	
	
}
