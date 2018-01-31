package com.supconit.base.services.impl.pojos;

import java.util.Date;


public class ImpLog{
	private String errorMsg;
	private String importNum;
	private Date createTime;
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getImportNum() {
		return importNum;
	}
	public void setImportNum(String importNum) {
		this.importNum = importNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


    public  ImpLog(){}

    public ImpLog(String hpid,String error ){
        this.importNum = hpid;
        this.errorMsg = error;

    }
}
