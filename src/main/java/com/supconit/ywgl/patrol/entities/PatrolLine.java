package com.supconit.ywgl.patrol.entities;

import com.supconit.common.web.entities.AuditExtend;

public class PatrolLine extends AuditExtend {

	private static final long	serialVersionUID	= 1L;

	private String lineCode;      //LINE_CODE	巡更路线编号		VARCHAR(20)
	private String lineName;      //LINE_NAME	巡更路线名称		VARCHAR(30)
	private String remark;         //REMARK	备注		VARCHAR(200)
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
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
