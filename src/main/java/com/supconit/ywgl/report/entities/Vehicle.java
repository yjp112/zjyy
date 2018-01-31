package com.supconit.ywgl.report.entities;


import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;
/*
 * 值班日志
 */
public class Vehicle extends AuditExtend {

	private static final long	serialVersionUID	= 1L;

	private String carNo;                               //车牌号
	private String cardNo;                              //卡号
	private Long roadGateId;                            //道闸ID
	private String roadGateName;                        //道闸名称
	private Date inTime;                                //进入时间
	private Date outTime;                               //出入时间
	
	private String vehicleYear;                                //通过年获取数据
	private String vehicleLastYear;
	
	
	public String getVehicleYear() {
		return vehicleYear;
	}
	public void setVehicleYear(String vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
	
	public String getVehicleLastYear() {
		return vehicleLastYear;
	}
	public void setVehicleLastYear(String vehicleLastYear) {
		this.vehicleLastYear = vehicleLastYear;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Long getRoadGateId() {
		return roadGateId;
	}
	public void setRoadGateId(Long roadGateId) {
		this.roadGateId = roadGateId;
	}
	public String getRoadGateName() {
		return roadGateName;
	}
	public void setRoadGateName(String roadGateName) {
		this.roadGateName = roadGateName;
	}
	public Date getInTime() {
		return inTime;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
