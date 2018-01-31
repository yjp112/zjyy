package com.supconit.nhgl.query.collect.entities;

import java.io.Serializable;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;


public class ElectricMeterMonth extends AuditExtend implements Serializable{

	private static final long serialVersionUID = -1604499901761447940L;

	
	private Long id;//
	
	private Integer deviceId;//设备编码
	
	private String monthKey;//对应的天
	
	private String peakValue;//峰电
	
	private String vallyValue;//谷电
	
	private String commonValue;//平电
	
	private String monthDayTimeValue;//白天
	
	private String monthNightValue;//晚上
	
	private String monthWeekenValue;//周末
	
	private String totalMonthValue ;//耗电总量
	
	private String totalYoy;//总量同比
	
	
	//虚拟字段
	private String start;
	private String end;
	private String deptName;//部门名称
	private Long propType;//属性类型：1:部门；2：地理区域；3：分项：4：特殊区域
	private String propKey;//属性编码值：保存类型中对应的code
	
	private Long xpropType;//属性类型：1:部门；2：地理区域；3：分项：4：特殊区域
	private String xpropKey;//虚拟字段属性编码值：保存类型中对应的code
	private String isp;//是否是父节点
	
	private Long dpropType;//部门
	private String dpropKey;//部门key
	
	private Long fpropType;//部门
	private String fpropKey;//部门key
	
	private String group;
	private String categoryCode;//设备code
	private Long CategoryId;
	
	private String dayOfMonth;
	private String elecTotal;//每月区域内耗电总量
	private Integer areaId;//区域id
	private Integer deptId;//部门id
	private List<Long> deptIdlst;
	private String subCode;//分项code
	private String subCodeName;
	
	private String lastMonth;
	private String lastYear;
	private String fullLevelName;
	private Long dpid;
	private List<Long> lstLocationId;
	private Long locationId;
	private String tbzl;//标识用电量上涨，下降，持平
	private String tbzlN;
	private String tbzlD;
	private float percent;
	private float percentD;
	private float percentN;
	private List<Long> lstDpId;
	private String maxTime;
	
	
	public List<Long> getDeptIdlst() {
		return deptIdlst;
	}

	public void setDeptIdlst(List<Long> deptIdlst) {
		this.deptIdlst = deptIdlst;
	}

	public String getSubCodeName() {
		return subCodeName;
	}

	public void setSubCodeName(String subCodeName) {
		this.subCodeName = subCodeName;
	}

	public String getTbzlN() {
		return tbzlN;
	}

	public void setTbzlN(String tbzlN) {
		this.tbzlN = tbzlN;
	}

	public String getTbzlD() {
		return tbzlD;
	}

	public void setTbzlD(String tbzlD) {
		this.tbzlD = tbzlD;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public float getPercentD() {
		return percentD;
	}

	public void setPercentD(float percentD) {
		this.percentD = percentD;
	}

	public float getPercentN() {
		return percentN;
	}

	public void setPercentN(float percentN) {
		this.percentN = percentN;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public String getTbzl() {
		return tbzl;
	}

	public void setTbzl(String tbzl) {
		this.tbzl = tbzl;
	}

	public List<Long> getLstDpId() {
		return lstDpId;
	}

	public void setLstDpId(List<Long> lstDpId) {
		this.lstDpId = lstDpId;
	}

	public Long getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(Long categoryId) {
		CategoryId = categoryId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public List<Long> getLstLocationId() {
		return lstLocationId;
	}

	public void setLstLocationId(List<Long> lstLocationId) {
		this.lstLocationId = lstLocationId;
	}

	public String getLastMonth() {
		return lastMonth;
	}

	public void setLastMonth(String lastMonth) {
		this.lastMonth = lastMonth;
	}

	public String getLastYear() {
		return lastYear;
	}

	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}
	
	public String getFullLevelName() {
		return fullLevelName;
	}

	public void setFullLevelName(String fullLevelName) {
		this.fullLevelName = fullLevelName;
	}



	public Long getDpid() {
		return dpid;
	}

	public void setDpid(Long dpid) {
		this.dpid = dpid;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getElecTotal() {
		return elecTotal;
	}

	public void setElecTotal(String elecTotal) {
		this.elecTotal = elecTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getPeakValue() {
		return peakValue;
	}

	public void setPeakValue(String peakValue) {
		this.peakValue = peakValue;
	}

	public String getVallyValue() {
		return vallyValue;
	}

	public void setVallyValue(String vallyValue) {
		this.vallyValue = vallyValue;
	}

	public String getCommonValue() {
		return commonValue;
	}

	public void setCommonValue(String commonValue) {
		this.commonValue = commonValue;
	}

	public String getTotalYoy() {
		return totalYoy;
	}

	public void setTotalYoy(String totalYoy) {
		this.totalYoy = totalYoy;
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

	public Long getPropType() {
		return propType;
	}

	public void setPropType(Long propType) {
		this.propType = propType;
	}

	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public Long getXpropType() {
		return xpropType;
	}

	public void setXpropType(Long xpropType) {
		this.xpropType = xpropType;
	}

	public String getXpropKey() {
		return xpropKey;
	}

	public void setXpropKey(String xpropKey) {
		this.xpropKey = xpropKey;
	}

	public String getMonthKey() {
		return monthKey;
	}

	public void setMonthKey(String monthKey) {
		this.monthKey = monthKey;
	}

	public String getMonthDayTimeValue() {
		return monthDayTimeValue;
	}

	public void setMonthDayTimeValue(String monthDayTimeValue) {
		this.monthDayTimeValue = monthDayTimeValue;
	}

	public String getMonthNightValue() {
		return monthNightValue;
	}

	public void setMonthNightValue(String monthNightValue) {
		this.monthNightValue = monthNightValue;
	}

	public String getTotalMonthValue() {
		return totalMonthValue;
	}

	public void setTotalMonthValue(String totalMonthValue) {
		this.totalMonthValue = totalMonthValue;
	}

	public String getMonthWeekenValue() {
		return monthWeekenValue;
	}

	public void setMonthWeekenValue(String monthWeekenValue) {
		this.monthWeekenValue = monthWeekenValue;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Long getDpropType() {
		return dpropType;
	}

	public void setDpropType(Long dpropType) {
		this.dpropType = dpropType;
	}

	public String getDpropKey() {
		return dpropKey;
	}

	public void setDpropKey(String dpropKey) {
		this.dpropKey = dpropKey;
	}

	public Long getFpropType() {
		return fpropType;
	}

	public void setFpropType(Long fpropType) {
		this.fpropType = fpropType;
	}

	public String getFpropKey() {
		return fpropKey;
	}

	public void setFpropKey(String fpropKey) {
		this.fpropKey = fpropKey;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
}
