package com.supconit.nhgl.cost.entities;

import java.math.BigDecimal;

import hc.base.domains.LongId;


public class Cost extends LongId{
	private static final long serialVersionUID = 1L;
	
	private String deptName;
	private Long deptId;
	private Long ppId;
	private String bitNo;
	private String deviceSpec;
	private String deviceName;
	private String extended2;
	private BigDecimal lastData;
	private BigDecimal currentData;
	private BigDecimal incremental;
	private BigDecimal electric;
	private BigDecimal electricCost;
	private BigDecimal sumElectric;
	private BigDecimal sumElectricCost;
	private BigDecimal water;
	private BigDecimal waterCost;
	private BigDecimal sumWater;
	private BigDecimal sumWaterCost;
	private BigDecimal energy;
	private BigDecimal energyCost;
	private BigDecimal sumEnergy;
	private BigDecimal sumEnergyCost;
	private BigDecimal gas;
	private BigDecimal gasCost;
	private BigDecimal sumGas;
	private BigDecimal sumGasCost;
	private String start;
	private String end;
	
	private Long lft;
	private Long rgt;
	
	
	
	public Long getLft() {
		return lft;
	}
	public void setLft(Long lft) {
		this.lft = lft;
	}
	public Long getRgt() {
		return rgt;
	}
	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}
	public Long getPpId() {
		return ppId;
	}
	public void setPpId(Long ppId) {
		this.ppId = ppId;
	}
	public BigDecimal getElectric() {
		return electric;
	}
	public void setElectric(BigDecimal electric) {
		this.electric = electric;
	}
	public BigDecimal getElectricCost() {
		return electricCost;
	}
	public void setElectricCost(BigDecimal electricCost) {
		this.electricCost = electricCost;
	}
	public BigDecimal getSumElectric() {
		return sumElectric;
	}
	public void setSumElectric(BigDecimal sumElectric) {
		this.sumElectric = sumElectric;
	}
	public BigDecimal getSumElectricCost() {
		return sumElectricCost;
	}
	public void setSumElectricCost(BigDecimal sumElectricCost) {
		this.sumElectricCost = sumElectricCost;
	}
	public BigDecimal getEnergy() {
		return energy;
	}
	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}
	public BigDecimal getEnergyCost() {
		return energyCost;
	}
	public void setEnergyCost(BigDecimal energyCost) {
		this.energyCost = energyCost;
	}
	public BigDecimal getSumEnergy() {
		return sumEnergy;
	}
	public void setSumEnergy(BigDecimal sumEnergy) {
		this.sumEnergy = sumEnergy;
	}
	public BigDecimal getSumEnergyCost() {
		return sumEnergyCost;
	}
	public void setSumEnergyCost(BigDecimal sumEnergyCost) {
		this.sumEnergyCost = sumEnergyCost;
	}
	public BigDecimal getSumWater() {
		return sumWater;
	}
	public void setSumWater(BigDecimal sumWater) {
		this.sumWater = sumWater;
	}
	public BigDecimal getSumWaterCost() {
		return sumWaterCost;
	}
	public void setSumWaterCost(BigDecimal sumWaterCost) {
		this.sumWaterCost = sumWaterCost;
	}
	public BigDecimal getSumGas() {
		return sumGas;
	}
	public void setSumGas(BigDecimal sumGas) {
		this.sumGas = sumGas;
	}
	public BigDecimal getSumGasCost() {
		return sumGasCost;
	}
	public void setSumGasCost(BigDecimal sumGasCost) {
		this.sumGasCost = sumGasCost;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public BigDecimal getWater() {
		return water;
	}
	public void setWater(BigDecimal water) {
		this.water = water;
	}
	public BigDecimal getWaterCost() {
		return waterCost;
	}
	public void setWaterCost(BigDecimal waterCost) {
		this.waterCost = waterCost;
	}
	public BigDecimal getGas() {
		return gas;
	}
	public void setGas(BigDecimal gas) {
		this.gas = gas;
	}
	public BigDecimal getGasCost() {
		return gasCost;
	}
	public void setGasCost(BigDecimal gasCost) {
		this.gasCost = gasCost;
	}
	public String getBitNo() {
		return bitNo;
	}
	public void setBitNo(String bitNo) {
		this.bitNo = bitNo;
	}
	public String getDeviceSpec() {
		return deviceSpec;
	}
	public void setDeviceSpec(String deviceSpec) {
		this.deviceSpec = deviceSpec;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public BigDecimal getLastData() {
		return lastData;
	}
	public void setLastData(BigDecimal lastData) {
		this.lastData = lastData;
	}
	public BigDecimal getCurrentData() {
		return currentData;
	}
	public void setCurrentData(BigDecimal currentData) {
		this.currentData = currentData;
	}
	public BigDecimal getIncremental() {
		return incremental;
	}
	public void setIncremental(BigDecimal incremental) {
		this.incremental = incremental;
	}
	public String getExtended2() {
		return extended2;
	}
	public void setExtended2(String extended2) {
		this.extended2 = extended2;
	}
	
}
