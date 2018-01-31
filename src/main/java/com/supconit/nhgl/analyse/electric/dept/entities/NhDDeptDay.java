package com.supconit.nhgl.analyse.electric.dept.entities;

import java.math.BigDecimal;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;
import com.supconit.nhgl.domain.CollectDayable;

public class NhDDeptDay extends AuditExtend implements CollectDayable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4149956116935959301L;
	private Integer deptId;
	private String itemCode;//分项编码
	private String dayOfMonthKey;//月份
	private BigDecimal peakValue;//峰电
	private BigDecimal vallyValue;//谷电
	private BigDecimal commonValue;//平峰用电
	private BigDecimal dayDaytimeValue;//白天用电
	private BigDecimal dayNightValue;//晚上用电
	private BigDecimal totalDayValue;//天用电
	private BigDecimal totalYoy;//上个月天用电量
	//虚拟字段
	private Integer nhType;
	private String deptName;
	private Integer tbzl;//是否上涨，1上涨，0与上月用电量相同或则上月或当前月的数据不存在，-1表示下降
	private Integer tb;
	private Integer parentId;
	private String itemName;//分项名称
	private BigDecimal lastMonth;//上月环比
	private List<Integer> areaIdList;//多个areaId拼接成的字符串
	private List<Integer> deptIdList;//多个deptId拼接成的字符串
	private List<String> itemCodeList;
	private String startTime;
	private String endTime;
	private BigDecimal total;
	private BigDecimal percent;
	private BigDecimal PercentD;
	private BigDecimal PercentN;
	private BigDecimal weekendTotal;
	private BigDecimal workTotal;
	private String weekName;
	private String dayOfWeekName;
	private String holidayName;
	
	
	
	
	
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public String getDayOfWeekName() {
		return dayOfWeekName;
	}
	public void setDayOfWeekName(String dayOfWeekName) {
		this.dayOfWeekName = dayOfWeekName;
	}
	public BigDecimal getWeekendTotal() {
		return weekendTotal;
	}
	public void setWeekendTotal(BigDecimal weekendTotal) {
		this.weekendTotal = weekendTotal;
	}
	public BigDecimal getWorkTotal() {
		return workTotal;
	}
	public void setWorkTotal(BigDecimal workTotal) {
		this.workTotal = workTotal;
	}
	public String getWeekName() {
		return weekName;
	}
	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public BigDecimal getPercentD() {
		return PercentD;
	}
	public void setPercentD(BigDecimal percentD) {
		PercentD = percentD;
	}
	public BigDecimal getPercentN() {
		return PercentN;
	}
	public void setPercentN(BigDecimal percentN) {
		PercentN = percentN;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Integer getTb() {
		return tb;
	}
	public void setTb(Integer tb) {
		this.tb = tb;
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
	public List<String> getItemCodeList() {
		return itemCodeList;
	}
	public void setItemCodeList(List<String> itemCodeList) {
		this.itemCodeList = itemCodeList;
	}
	public List<Integer> getAreaIdList() {
		return areaIdList;
	}
	public void setAreaIdList(List<Integer> areaIdList) {
		this.areaIdList = areaIdList;
	}
	public List<Integer> getDeptIdList() {
		return deptIdList;
	}
	public void setDeptIdList(List<Integer> deptIdList) {
		this.deptIdList = deptIdList;
	}
	public BigDecimal getLastMonth() {
		return lastMonth;
	}
	public void setLastMonth(BigDecimal lastMonth) {
		this.lastMonth = lastMonth;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getTbzl() {
		return tbzl;
	}
	public void setTbzl(Integer tbzl) {
		this.tbzl = tbzl;
	}
	public Integer getNhType() {
		return nhType;
	}
	public void setNhType(Integer nhType) {
		this.nhType = nhType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public BigDecimal getPeakValue() {
		return peakValue;
	}
	public void setPeakValue(BigDecimal peakValue) {
		this.peakValue = peakValue;
	}
	public BigDecimal getVallyValue() {
		return vallyValue;
	}
	public void setVallyValue(BigDecimal vallyValue) {
		this.vallyValue = vallyValue;
	}
	public BigDecimal getCommonValue() {
		return commonValue;
	}
	public void setCommonValue(BigDecimal commonValue) {
		this.commonValue = commonValue;
	}
	public BigDecimal getTotalYoy() {
		return totalYoy;
	}
	public void setTotalYoy(BigDecimal totalYoy) {
		this.totalYoy = totalYoy;
	}
	public String getDayOfMonthKey() {
		return dayOfMonthKey;
	}
	public void setDayOfMonthKey(String dayOfMonthKey) {
		this.dayOfMonthKey = dayOfMonthKey;
	}
	public BigDecimal getDayDaytimeValue() {
		return dayDaytimeValue;
	}
	public void setDayDaytimeValue(BigDecimal dayDaytimeValue) {
		this.dayDaytimeValue = dayDaytimeValue;
	}
	public BigDecimal getDayNightValue() {
		return dayNightValue;
	}
	public void setDayNightValue(BigDecimal dayNightValue) {
		this.dayNightValue = dayNightValue;
	}
	public BigDecimal getTotalDayValue() {
		return totalDayValue;
	}
	public void setTotalDayValue(BigDecimal totalDayValue) {
		this.totalDayValue = totalDayValue;
	}
}
