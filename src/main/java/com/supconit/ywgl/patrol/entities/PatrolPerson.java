package com.supconit.ywgl.patrol.entities;

import com.supconit.common.web.entities.AuditExtend;

public class PatrolPerson extends AuditExtend {

	private static final long	serialVersionUID	= 1L;
	
	private String personCode;    //PERSON_CODE	巡更人编号		VARCHAR(20)
	private String personName;    //PERSON_NAME	巡更人姓名		VARCHAR(30)
	private String personTel;     //PERSON_TEL	巡更人电话		VARCHAR(20)
	private String remark;        //REMARK	备注		VARCHAR(200)
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonTel() {
		return personTel;
	}
	public void setPersonTel(String personTel) {
		this.personTel = personTel;
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
