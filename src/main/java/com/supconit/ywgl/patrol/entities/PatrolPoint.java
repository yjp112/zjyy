package com.supconit.ywgl.patrol.entities;

import com.supconit.common.web.entities.AuditExtend;

//巡更点
public class PatrolPoint extends AuditExtend {

	private static final long	serialVersionUID	= 1L;
	
	private String pointCode;   //POINT_CODE	巡更点编号		VARCHAR(20)
	private String pointNmae;   //POINT_NAME	巡更点名称		VARCHAR(30)
	private String pointAddress;//POINT_ADDRESS	巡更点位置		VARCHAR(100)
	private Long areaId;        //AREA_ID	区域ID		BIGINT
	private String remark;      //REMARK	备注		VARCHAR(200)
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}
	public String getPointNmae() {
		return pointNmae;
	}
	public void setPointNmae(String pointNmae) {
		this.pointNmae = pointNmae;
	}
	public String getPointAddress() {
		return pointAddress;
	}
	public void setPointAddress(String pointAddress) {
		this.pointAddress = pointAddress;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
