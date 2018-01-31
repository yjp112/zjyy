package com.supconit.meeting.entities;

import com.supconit.common.utils.excel.ExcelAnnotation;
import com.supconit.common.web.entities.AuditExtend;

/**
 * 用户部门类
 * @author yuhuan
 */
public class MeetingPerson extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	@ExcelAnnotation(exportName = "姓名")
	private String name;//姓名
	private Long deptId;//科室ID
	@ExcelAnnotation(exportName = "部门")
	private String deptName;//科室名称
	private Long deptPid;//父部门ID
	private String deptPname;//父部门名称
	private String tel;//手机
	private String email;//邮箱
	private String code;//编码
	
	private String meetingCompereName;//会议主持人姓名
	private String meetingSubject;//会议主题
	@ExcelAnnotation(exportName = "签到状态")
	private String isSign;//签到状态
	private Long reserveId;//预定会议ID
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getDeptPid() {
		return deptPid;
	}
	public void setDeptPid(Long deptPid) {
		this.deptPid = deptPid;
	}
	public String getDeptPname() {
		return deptPname;
	}
	public void setDeptPname(String deptPname) {
		this.deptPname = deptPname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMeetingCompereName() {
		return meetingCompereName;
	}
	public void setMeetingCompereName(String meetingCompereName) {
		this.meetingCompereName = meetingCompereName;
	}
	public String getMeetingSubject() {
		return meetingSubject;
	}
	public void setMeetingSubject(String meetingSubject) {
		this.meetingSubject = meetingSubject;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public Long getReserveId() {
		return reserveId;
	}
	public void setReserveId(Long reserveId) {
		this.reserveId = reserveId;
	}
	
}
