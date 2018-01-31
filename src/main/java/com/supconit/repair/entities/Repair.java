package com.supconit.repair.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.excel.ExcelAnnotation;
import com.supconit.common.web.entities.AuditExtend;

import hc.bpm.Bpmable;

public class Repair extends AuditExtend  implements Bpmable{

	private static final long	serialVersionUID	= 1L;
	
	private String todoPage;//页面

	public String getTodoPage() {
		return todoPage;
	}
	public void setTodoPage(String todoPage) {
		this.todoPage = todoPage;
	}
	private String processInstanceId; //流程ID
    private String processInstanceName;//流程名字
	
	private Integer taskSource;//诉求来源
	
	@ExcelAnnotation(exportName = "诉求单号")
	private String taskCode;//诉求单号
	
	private Integer urgency;//紧急程度
	private String urgencyName;//紧急程度
	private Long categoryId;//事件分类
	private Long areaId;
	private String repairTel;
	private Long repairDeptId;
	private String repairDeptName;
	private String contact;//报修人
	private Long contactId;//报修人ID
	private Long alarmId;//报警ID
	private Long hisAlarmId;//历史报警ID
	
	public Long getHisAlarmId() {
		return hisAlarmId;
	}
	public void setHisAlarmId(Long hisAlarmId) {
		this.hisAlarmId = hisAlarmId;
	}
	public Long getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}
	@ExcelAnnotation(exportName = "报修人电话")
	private String contactTel;//报修人电话
	
	@ExcelAnnotation(exportName = "报修时间")
	private Date repairDate;//报修时间
	
	@ExcelAnnotation(exportName = "诉求详情")
	private String descripton;//诉求详情
	
	private String repairCode;//维修单号
	private Long deviceId;
	private String deviceName;//设备名称
	private String deviceCode;//设备名称
	private Long engineerId;//主管工程师ID
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	private String engineerName;//主管工程师姓名
	
	@ExcelAnnotation(exportName = "指派时间")
	private Date dispatchTime;//指派时间
	
	private Integer  repairMode;//维修方式    0：委外 1：自修
	private Integer  repairWorry;//是否抢修  0：否 1：是
	private Long repairGroupId;
	
	@ExcelAnnotation(exportName = "受理班组")
	private String repairGroupName;//受理班组
	
	private Long groupEmpId;//维修组长id
	
	@ExcelAnnotation(exportName = "受理组长")
	private String groupEmpName;//受理组长
	
	private Long repairPersonId;//维修负责人
	
	@ExcelAnnotation(exportName = "维修负责人")
	private String repairPersonName;//维修负责人
	
	private Integer stopHours;//故障时间
	
	@ExcelAnnotation(exportName = "要求完成时间")
	private Date expectFinishTime;//要求完成时间
	
	@ExcelAnnotation(exportName = "实际完成时间")
	private Date actualFinishTime;//实际完成时间
	
	@ExcelAnnotation(exportName = "延时时间")
	private String delayTime;
	
	private String result;
	private Integer causeType;//原因归类
	private String causeTypeName;//原因归类名称
	
	private String cause;
	private String propose;//改善意见
	private String costSaving;//节约成本
	private String lastCostSaving;//去年同期节约成本
	private BigDecimal pecent;//变化率
	private Integer  grade1;//评分1
	private Integer  grade2;
	private Integer  grade3;
	private Integer  grade4;
	private Integer  grade5;
	private String remark;
	private Integer status;//维修单状态
	
	private String startTime;
	private String endTime;
	private String causeValue;
	private String repairTypeValue;
	private String sumRepair;
	private String sumCause;
	private String sumMode;
	private String sumGrade3;
	private String modeValue;
	private String repairMonth;
	private String avgGrade;
	private String timeRate;
	private String categoryName;//事件分类名称
	private String areaName;//区域名称
	private List<RepairWorker> workerList =new ArrayList<RepairWorker>();
	private List<RepairSpare> spareList =new ArrayList<RepairSpare>();
	//查询用
	private Date startTimes;
	private Date endTimes;
	private Date startTimes1;
	private Date endTimes1;
	private Integer deviceType;//设备类别 0：公用 1：工艺
	
	private List<Long> deptChildIds;
	private List<Long> deviceCategoryChildIds;
	private List<Long> geoAreaChildIds;
	
	private String bpm_ts_id;
	private Long relObjectId;
    private String handlePersonCode;
	private String handlePersonName;
    private Date completionTime;
    private String subType;//提交方式 1:保存;2:提交
    private String repairTimes;
    
    
    
    
    public String getRepairTimes() {
		return repairTimes;
	}
	public void setRepairTimes(String repairTimes) {
		this.repairTimes = repairTimes;
	}
	public BigDecimal getPecent() {
		return pecent;
	}
	public void setPecent(BigDecimal pecent) {
		this.pecent = pecent;
	}
	public String getLastCostSaving() {
		return lastCostSaving;
	}
	public void setLastCostSaving(String lastCostSaving) {
		this.lastCostSaving = lastCostSaving;
	}
	public String getSumGrade3() {
		return sumGrade3;
	}
	public void setSumGrade3(String sumGrade3) {
		this.sumGrade3 = sumGrade3;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
    public Date getStartTimes1() {
		return startTimes1;
	}
	public void setStartTimes1(Date startTimes1) {
		this.startTimes1 = startTimes1;
	}
	public Date getEndTimes1() {
		return endTimes1;
	}
	public void setEndTimes1(Date endTimes1) {
		this.endTimes1 = endTimes1;
	}
	public List<RepairWorker> getWorkerList() {
		return workerList;
	}
	public void setWorkerList(List<RepairWorker> workerList) {
		this.workerList = workerList;
	}
	public List<RepairSpare> getSpareList() {
		return spareList;
	}
	public void setSpareList(List<RepairSpare> spareList) {
		this.spareList = spareList;
	}
	public String getBpm_ts_id() {
		return bpm_ts_id;
	}
	public void setBpm_ts_id(String bpm_ts_id) {
		this.bpm_ts_id = bpm_ts_id;
	}
	public Long getRelObjectId() {
		return relObjectId;
	}
	public void setRelObjectId(Long relObjectId) {
		this.relObjectId = relObjectId;
	}
	public String getHandlePersonCode() {
		return handlePersonCode;
	}
	public void setHandlePersonCode(String handlePersonCode) {
		this.handlePersonCode = handlePersonCode;
	}
	public String getHandlePersonName() {
		return handlePersonName;
	}
	public void setHandlePersonName(String handlePersonName) {
		this.handlePersonName = handlePersonName;
	}
	public Date getCompletionTime() {
		return completionTime;
	}
	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}
	public String getTimeRate() {
		return timeRate;
	}
	public void setTimeRate(String timeRate) {
		this.timeRate = timeRate;
	}
	public String getAvgGrade() {
		return avgGrade;
	}
	public void setAvgGrade(String avgGrade) {
		this.avgGrade = avgGrade;
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
	public String getRepairMonth() {
		return repairMonth;
	}
	public void setRepairMonth(String repairMonth) {
		this.repairMonth = repairMonth;
	}
	public String getModeValue() {
		return modeValue;
	}
	public void setModeValue(String modeValue) {
		this.modeValue = modeValue;
	}
	public String getSumMode() {
		return sumMode;
	}
	public void setSumMode(String sumMode) {
		this.sumMode = sumMode;
	}
	public String getSumRepair() {
		return sumRepair;
	}
	public void setSumRepair(String sumRepair) {
		this.sumRepair = sumRepair;
	}
	public String getSumCause() {
		return sumCause;
	}
	public void setSumCause(String sumCause) {
		this.sumCause = sumCause;
	}
	public String getCauseValue() {
		return causeValue;
	}
	public void setCauseValue(String causeValue) {
		this.causeValue = causeValue;
	}
	public String getRepairTypeValue() {
		return repairTypeValue;
	}
	public void setRepairTypeValue(String repairTypeValue) {
		this.repairTypeValue = repairTypeValue;
	}
	public String getProcessInstanceName() {
		return processInstanceName;
	}
	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}
	@Override
	public String callbackUpdateSQL() {
		return "update repair set process_instance_id =? where id = " + this.getId();
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Integer  getTaskSource() {
		return taskSource;
	}
	public void setTaskSource(Integer taskSource) {
		this.taskSource = taskSource;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public Integer getUrgency() {
		return urgency;
	}
	public void setUrgency(Integer urgency) {
		this.urgency = urgency;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getRepairTel() {
		return repairTel;
	}
	public void setRepairTel(String repairTel) {
		this.repairTel = repairTel;
	}
	public Long getRepairDeptId() {
		return repairDeptId;
	}
	public void setRepairDeptId(Long repairDeptId) {
		this.repairDeptId = repairDeptId;
	}
	public String getRepairDeptName() {
		return repairDeptName;
	}
	public void setRepairDeptName(String repairDeptName) {
		this.repairDeptName = repairDeptName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public Date getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}
	public String getDescripton() {
		return descripton;
	}
	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}
	public String getRepairCode() {
		return repairCode;
	}
	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getEngineerId() {
		return engineerId;
	}
	public void setEngineerId(Long engineerId) {
		this.engineerId = engineerId;
	}
	public String getEngineerName() {
		return engineerName;
	}
	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}
	public Date getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(Date dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public Integer getRepairMode() {
		return repairMode;
	}
	public void setRepairMode(Integer repairMode) {
		this.repairMode = repairMode;
	}
	public Integer getRepairWorry() {
		return repairWorry;
	}
	public void setRepairWorry(Integer repairWorry) {
		this.repairWorry = repairWorry;
	}
	public Long getRepairGroupId() {
		return repairGroupId;
	}
	public void setRepairGroupId(Long repairGroupId) {
		this.repairGroupId = repairGroupId;
	}
	public String getRepairGroupName() {
		return repairGroupName;
	}
	public void setRepairGroupName(String repairGroupName) {
		this.repairGroupName = repairGroupName;
	}
	public Long getGroupEmpId() {
		return groupEmpId;
	}
	public void setGroupEmpId(Long groupEmpId) {
		this.groupEmpId = groupEmpId;
	}
	public String getGroupEmpName() {
		return groupEmpName;
	}
	public void setGroupEmpName(String groupEmpName) {
		this.groupEmpName = groupEmpName;
	}
	public Long getRepairPersonId() {
		return repairPersonId;
	}
	public void setRepairPersonId(Long repairPersonId) {
		this.repairPersonId = repairPersonId;
	}
	public String getRepairPersonName() {
		return repairPersonName;
	}
	public void setRepairPersonName(String repairPersonName) {
		this.repairPersonName = repairPersonName;
	}
	public Integer getStopHours() {
		return stopHours;
	}
	public void setStopHours(Integer stopHours) {
		this.stopHours = stopHours;
	}
	public Date getExpectFinishTime() {
		return expectFinishTime;
	}
	public void setExpectFinishTime(Date expectFinishTime) {
		this.expectFinishTime = expectFinishTime;
	}
	public Date getActualFinishTime() {
		return actualFinishTime;
	}
	public void setActualFinishTime(Date actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
	}
	public String getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getCauseType() {
		return causeType;
	}
	public void setCauseType(Integer causeType) {
		this.causeType = causeType;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getPropose() {
		return propose;
	}
	public void setPropose(String propose) {
		this.propose = propose;
	}
	public String getCostSaving() {
		return costSaving;
	}
	public void setCostSaving(String costSaving) {
		this.costSaving = costSaving;
	}
	public Integer getGrade1() {
		return grade1;
	}
	public void setGrade1(Integer grade1) {
		this.grade1 = grade1;
	}
	public Integer getGrade2() {
		return grade2;
	}
	public void setGrade2(Integer grade2) {
		this.grade2 = grade2;
	}
	public Integer getGrade3() {
		return grade3;
	}
	public void setGrade3(Integer grade3) {
		this.grade3 = grade3;
	}
	public Integer getGrade4() {
		return grade4;
	}
	public void setGrade4(Integer grade4) {
		this.grade4 = grade4;
	}
	public Integer getGrade5() {
		return grade5;
	}
	public void setGrade5(Integer grade5) {
		this.grade5 = grade5;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStartTimes() {
		return startTimes;
	}
	public void setStartTimes(Date startTimes) {
		this.startTimes = startTimes;
	}
	public Date getEndTimes() {
		return endTimes;
	}
	public void setEndTimes(Date endTimes) {
		this.endTimes = endTimes;
	}
	public List<Long> getDeptChildIds() {
		return deptChildIds;
	}
	public void setDeptChildIds(List<Long> deptChildIds) {
		this.deptChildIds = deptChildIds;
	}
	public List<Long> getDeviceCategoryChildIds() {
		return deviceCategoryChildIds;
	}
	public void setDeviceCategoryChildIds(List<Long> deviceCategoryChildIds) {
		this.deviceCategoryChildIds = deviceCategoryChildIds;
	}
	public List<Long> getGeoAreaChildIds() {
		return geoAreaChildIds;
	}
	public void setGeoAreaChildIds(List<Long> geoAreaChildIds) {
		this.geoAreaChildIds = geoAreaChildIds;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getCauseTypeName() {
		if(this.causeType != null){
			return DictUtils.getDictLabel(DictTypeEnum.CAUSE_TYPE, this.causeType.toString());
		}else{
			return "";
		}
	}
	
	public String getUrgencyName() {
		if(this.urgency != null){
			return DictUtils.getDictLabel(DictTypeEnum.URGENCY, this.urgency.toString());
		}else{
			return "";
		}
	}

}
