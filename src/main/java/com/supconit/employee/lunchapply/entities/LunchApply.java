package com.supconit.employee.lunchapply.entities;

import java.util.Date;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

import hc.bpm.Bpmable;

public class LunchApply extends AuditExtend implements Bpmable{
	private static final long serialVersionUID = 1L;

	private String processInstanceId;//流程ID
	private String processInstanceName;//流程名称这个名称由多个部分组成
	private String lunchcode;//申请单号
	private String lunchType;//就餐类别：1.晚餐
	private String lunchTypeName;
	private Date lunchDate;//就餐日期
	private String reason;//申请事由
	private String result;//审批结果0：待审批;1：不通过 ;2：通过
	private String resultName;
	private long deptId;//部门ID
	private String deptName;//部门名称
	
	//扩展
	private String handlePersonCode;//下一步骤处理人
	private String processType;//01提交;02驳回
	private Integer deptLevel;//部门级别//1：科室;2：部门
	private Integer status;//1:同意;2：不同意
	private Date startTime;
	private Date endTime;
	private Date currentDay;
	private String bpm_ts_id;
	
	@Override
	public String callbackUpdateSQL() {
		StringBuffer sql = new StringBuffer("update LUNCH_APPLY set process_instance_id=? where id=" + this.getId());
		return sql.toString();
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessInstanceName() {
		return processInstanceName;
	}
	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}
	public String getLunchcode() {
		return lunchcode;
	}
	public void setLunchcode(String lunchcode) {
		this.lunchcode = lunchcode;
	}
	public String getLunchType() {
		return lunchType;
	}
	public void setLunchType(String lunchType) {
		this.lunchType = lunchType;
	}
	public Date getLunchDate() {
		return lunchDate;
	}
	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public Date getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(Date currentDay) {
		this.currentDay = currentDay;
	}
	public String getHandlePersonCode() {
		return handlePersonCode;
	}
	public void setHandlePersonCode(String handlePersonCode) {
		this.handlePersonCode = handlePersonCode;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public long getDeptId() {
		return deptId;
	}
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getDeptLevel() {
		return deptLevel;
	}
	public void setDeptLevel(Integer deptLevel) {
		this.deptLevel = deptLevel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBpm_ts_id() {
		return bpm_ts_id;
	}
	public void setBpm_ts_id(String bpm_ts_id) {
		this.bpm_ts_id = bpm_ts_id;
	}

	//转义-----------------------------------------
	public String getLunchTypeName() {
		return DictUtils.getDictLabel(DictTypeEnum.LUNCH_TYPE, this.lunchType);
	}
	public String getResultName() {
		return DictUtils.getDictLabel(DictTypeEnum.LUNCH_RESULT, this.result);
	}


}
