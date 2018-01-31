package com.supconit.nhgl.analyse.electric.dept.entities;

import java.math.BigDecimal;
import java.util.List;

import com.supconit.common.utils.NumberFormatUtils;
import com.supconit.common.web.entities.AuditExtend;
import com.supconit.nhgl.domain.CollectMonthable;
import com.supconit.nhgl.utils.OrganizationUtils;

public class NhDDeptMonth extends AuditExtend implements CollectMonthable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4550102759715533342L;
	
	private Integer deptId;
	private String itemCode;//分项编码
	private String monthKey;//月份
	private BigDecimal peakValue;//峰电
	private BigDecimal vallyValue;//谷电
	private BigDecimal commonValue;//平峰用电
	private BigDecimal monthDaytimeValue;//白天用电
	private BigDecimal monthNightValue;//晚上用电
	private BigDecimal monthWeekValue;//周末用电
	private BigDecimal totalMonthValue;//月用电
	private BigDecimal totalYoy;//去年周期
	//虚拟字段
	private Integer nhType;
	private String tbzl;//是否上涨，1上涨，0与上月用电量相同或则上月或当前月的数据不存在，-1表示下降
	private String deptName;
	private Integer pid;
	private String itemName;//分项名称
	private BigDecimal lastMonth;
	private List<Integer> areaIdList;//多个areaId拼接成的字符串
	private List<Integer> deptIdList;//多个deptId拼接成的字符串
	private List<String> itemCodeList;
	private String start;//开始时间
	private String end;//结束时间
	private String startTime;
	private String endTime;
	private BigDecimal total;
	private BigDecimal PercentD;
	private BigDecimal PercentN;
	private Integer parentId;
	private Integer tb;
	private BigDecimal untilEle;//单位面积耗电量
	private String area;//部门面积
	private String parentCode;//父分项code
	
	
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public BigDecimal getUntilEle() {
		return untilEle;
	}
	public void setUntilEle(BigDecimal untilEle) {
		this.untilEle = untilEle;
	}
	public String getArea() {
		return NumberFormatUtils.formatThousand2(area);
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getTb() {
		return tb;
	}
	public void setTb(Integer tb) {
		this.tb = tb;
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
	public List<String> getItemCodeList() {
		return itemCodeList;
	}
	public void setItemCodeList(List<String> itemCodeList) {
		this.itemCodeList = itemCodeList;
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
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getDeptName() {
		 return this.deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTbzl() {
		return tbzl;
	}
	public void setTbzl(String tbzl) {
		this.tbzl = tbzl;
	}
	public Integer getNhType() {
		return nhType;
	}
	public void setNhType(Integer nhType) {
		this.nhType = nhType;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getMonthKey() {
		return monthKey;
	}
	public void setMonthKey(String monthKey) {
		this.monthKey = monthKey;
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
	
	public BigDecimal getMonthDaytimeValue() {
		return monthDaytimeValue;
	}
	public void setMonthDaytimeValue(BigDecimal monthDaytimeValue) {
		this.monthDaytimeValue = monthDaytimeValue;
	}
	public BigDecimal getMonthNightValue() {
		return monthNightValue;
	}
	public void setMonthNightValue(BigDecimal monthNightValue) {
		this.monthNightValue = monthNightValue;
	}
	public BigDecimal getMonthWeekValue() {
		return monthWeekValue;
	}
	public void setMonthWeekValue(BigDecimal monthWeekValue) {
		this.monthWeekValue = monthWeekValue;
	}
	public BigDecimal getTotalMonthValue() {
		return totalMonthValue;
	}
	public void setTotalMonthValue(BigDecimal totalMonthValue) {
		this.totalMonthValue = totalMonthValue;
	}
	public BigDecimal getTotalYoy() {
		return totalYoy;
	}
	public void setTotalYoy(BigDecimal totalYoy) {
		this.totalYoy = totalYoy;
	}
	public String getDeptFullName() {
		if(deptId==null){
			return "";
		}
		return OrganizationUtils.getFullDeptNameByDeptId(deptId);
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
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
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
	
}
