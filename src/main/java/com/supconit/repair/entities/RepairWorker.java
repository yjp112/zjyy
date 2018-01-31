package com.supconit.repair.entities;

import com.supconit.common.web.entities.AuditExtend;


public class RepairWorker extends AuditExtend {

	private String repairCode;		// 维修单编号
	private Integer	serialNo;		// 序号
	private Integer	workerMode;		// 人工来源 0：委外 1：自修
	private Integer	workerType;		// 工种
	private Long workerId;          //工人ID
	private String workerName;      //工人名称
	private String	descripton;	// 项目说明
	private String	unit;			// 计价单位
	private Long	qty;			// 数量
	private Double workHours;		// 工时
	private Double money;			// 总价
	

	public String getRepairCode() {
		return repairCode;
	}
	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	public Integer getWorkerMode() {
		return workerMode;
	}
	public void setWorkerMode(Integer workerMode) {
		this.workerMode = workerMode;
	}
	public Integer getWorkerType() {
		return workerType;
	}
	public void setWorkerType(Integer workerType) {
		this.workerType = workerType;
	}
	public Long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public Double getWorkHours() {
		return workHours;
	}
	public void setWorkHours(Double workHours) {
		this.workHours = workHours;
	}
	public String getDescripton() {
		return descripton;
	}
	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		if(null!= unit && unit.length()>30){
			unit = unit.substring(0, 30);
		}
		this.unit = unit;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}

}
