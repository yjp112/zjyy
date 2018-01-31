package com.supconit.employee.leave.entities;

import java.util.Date;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

import hc.bpm.Bpmable;

/**
 * 请假申请对象
 * @author wangbo
 */

public class LeaveApply extends AuditExtend implements Bpmable{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String processInstanceId; //流程ID
        private String leaveCode;//请假单号
        private Integer leaveType;//请假类型1:事假2:病假3:丧假4:年休假5:婚假6:探亲假7:产前假8:产假9:哺乳假10:陪产假11:计划生育假12:工伤假13:疗养假
        private Integer leaveDays;//天数
        private Integer leaveHours;//小时
        private String processInstanceName;

		private Date startDate;//请假开始时间
        private Date endDate;//请假结束时间
        private String reason;//请假事由
        private Long postId; //职务
		
		private Integer result;//审批状态
        private Integer status;//申请单状态
        private Date applyStart;//查询开始时间
        private Date applyEnd;//查询结束时间
    	private Long deptId; //部门ID
		private String deptName;//部门名称
        private Double days;//请假天数
        private String startTime;//请假申请开始时间
        private String endTime;//请假结束时间       
        private String startTime1;//请假申请开始时间
		private String endTime1;//请假结束时间
		private String bpm_ts_id;
		private Double leaveNum;

		public String getBpm_ts_id() {
			return bpm_ts_id;
		}

		public void setBpm_ts_id(String bpm_ts_id) {
			this.bpm_ts_id = bpm_ts_id;
		}

		private Long relObjectId;
	    private String handlePersonCode;
		private String handlePersonName;
	    private Date completionTime;
	    public Long getRelObjectId() {
			return relObjectId;
		}

		public void setRelObjectId(Long relObjectId) {
			this.relObjectId = relObjectId;
		}

		public String getHandlePersonCode() {
			return handlePersonCode;
		}

		public void setHandlePersonCode(String handlePersonCode) {
			this.handlePersonCode = handlePersonCode;
		}

		public String getHandlePersonName() {
			return handlePersonName;
		}

		public void setHandlePersonName(String handlePersonName) {
			this.handlePersonName = handlePersonName;
		}
		
		public String getProcessInstanceName() {
				return processInstanceName;
		}

		public void setProcessInstanceName(String processInstanceName) {
			this.processInstanceName = processInstanceName;
		}

		public Date getCompletionTime() {
			return completionTime;
		}

		public void setCompletionTime(Date completionTime) {
			this.completionTime = completionTime;
		}
	    
	    @Override
	    public String callbackUpdateSQL()
	    {
	        StringBuffer sql = new StringBuffer(
	                "update leave_apply set process_instance_id=? where id="
	                        + this.getId());
	        return sql.toString();
	    }
	    
        public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public String getStartTime1() {
			return startTime1;
		}
		public void setStartTime1(String startTime1) {
			this.startTime1 = startTime1;
		}
		public String getEndTime1() {
			return endTime1;
		}
		public void setEndTime1(String endTime1) {
			this.endTime1 = endTime1;
		}
        public Double getDays() {
			return days;
		}
		public void setDays(Double days) {
			this.days = days;
		}
		public String getProcessInstanceId() {
			return processInstanceId;
		}
		public void setProcessInstanceId(String processInstanceId) {
			this.processInstanceId = processInstanceId;
		}
		public String getLeaveCode() {
			return leaveCode;
		}
		public void setLeaveCode(String leaveCode) {
			this.leaveCode = leaveCode;
		}
		public Integer getLeaveType() {
			return leaveType;
		}
		public void setLeaveType(Integer leaveType) {
			this.leaveType = leaveType;
		}
		public Integer getLeaveDays() {
			return leaveDays;
		}
		public void setLeaveDays(Integer leaveDays) {
			this.leaveDays = leaveDays;
		}
		public Integer getLeaveHours() {
			return leaveHours;
		}
		public void setLeaveHours(Integer leaveHours) {
			this.leaveHours = leaveHours;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public Integer getResult() {
			return result;
		}
		public void setResult(Integer result) {
			this.result = result;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}   
		
        public Date getApplyStart() {
			return applyStart;
		}
		public void setApplyStart(Date applyStart) {
			this.applyStart = applyStart;
		}
		public Date getApplyEnd() {
			return applyEnd;
		}
		public void setApplyEnd(Date applyEnd) {
			this.applyEnd = applyEnd;
		}
    	public Long getDeptId() {
			return deptId;
		}
		public void setDeptId(Long deptId) {
			this.deptId = deptId;
		}
		public String getDeptName() {
			return deptName;
		}
		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}
		public String getLeaveTypeName(){
			return DictUtils.getDictLabel(DictTypeEnum.LEAVE_TYPE, this.leaveType.toString());
		}
		
		public String getResultName(){
			return DictUtils.getDictLabel(DictTypeEnum.LEAVE_RESULT, this.result.toString());
		}
		
		public Long getPostId() {
			return postId;
		}

		public void setPostId(Long postId) {
			this.postId = postId;
		}

		public Double getLeaveNum() {
			String tmp = this.leaveDays + "." + this.leaveHours;
			return Double.parseDouble(tmp);
		}

		public void setLeaveNum(Double leaveNum) {
			this.leaveNum = leaveNum;
		}

}

