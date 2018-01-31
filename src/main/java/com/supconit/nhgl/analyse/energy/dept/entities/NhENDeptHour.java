package com.supconit.nhgl.analyse.energy.dept.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;
import com.supconit.nhgl.domain.CollectHourable;

public class NhENDeptHour extends AuditExtend implements CollectHourable,Serializable {

	private static final long serialVersionUID = 4863231232234276543L;

	private Long id;
	private String itemCode;//分项表code
	private Integer deptId;//部门id
	private BigDecimal total;//电表总度数
	private BigDecimal incremental;//增长度数
	private Date collectDate;//收集日期
	private Integer collectHour;//收集的小时
	private Date collectTime;

	
	private Integer nhType;
	private String parentCode;
	private String standardCode;
	//虚拟字段
	private Integer start=0;//开始的小时数
	private Integer end;//结束的小时数
	private Long propType;//属性类型：1:部门；2：地理区域；3：分项：4：特殊区域
	private String propKey;//属性编码值：保存类型中对应的code
	
	private Long xpropType;//属性类型：1:部门；2：地理区域；3：分项：4：特殊区域
	private String xpropKey;//虚拟字段属性编码值：保存类型中对应的code
	private Long parentId;//地理区域父节点
	private Date now;//当前时间
	private Date lastDay;//上一天
	private String electricityTotal;//当前用电总量
	private String name;//区域名
	private float percent;//总体概况百分比
	private Integer tb;//标识同比昨日是否上涨
	private String startTime;//开始的时间
	private String endTime;//结束的时间
	private String monthElectricityTotal;//本月的用电总量
	private Integer areaId;//区域Id
	private List<Long> deptIdlst;
	private String categoryCode;//设备类别code
	private String isp;//是否是父节点
	private String sTime;
	private String eTime;
	private Integer idxh;
	private String lastIncremental;//昨天用电量
	private String value;
	private BigDecimal lastTotal;

	
	
	
	public BigDecimal getLastTotal() {
		return lastTotal;
	}

	public void setLastTotal(BigDecimal lastTotal) {
		this.lastTotal = lastTotal;
	}

	public Date getLastDay() {
		return lastDay;
	}

	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getStandardCode() {
		return standardCode;
	}

	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
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

	public List<Long> getDeptIdlst() {
		return deptIdlst;
	}

	public void setDeptIdlst(List<Long> deptIdlst) {
		this.deptIdlst = deptIdlst;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLastIncremental() {
		return lastIncremental;
	}

	public void setLastIncremental(String lastIncremental) {
		this.lastIncremental = lastIncremental;
	}

	public Integer getIdxh() {
		return idxh;
	}

	public void setIdxh(Integer idxh) {
		this.idxh = idxh;
	}

	public Integer getTb() {
		return tb;
	}

	public void setTb(Integer bigDecimal) {
		this.tb = bigDecimal;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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

	public String getMonthElectricityTotal() {
		return monthElectricityTotal;
	}

	public void setMonthElectricityTotal(String monthElectricityTotal) {
		this.monthElectricityTotal = monthElectricityTotal;
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

	public float getPercent() {
		return percent;
	}

	public void setPercent(float string) {
		this.percent = string;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getElectricityTotal() {
		return electricityTotal;
	}

	public void setElectricityTotal(String electricityTotal) {
		this.electricityTotal = electricityTotal;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getIncremental() {
		return incremental;
	}

	public void setIncremental(BigDecimal incremental) {
		this.incremental = incremental;
	}

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public Integer getCollectHour() {
		return collectHour;
	}

	public void setCollectHour(Integer collectHour) {
		this.collectHour = collectHour;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
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
	
	
}
