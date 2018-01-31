/**
 * 
 */
package com.supconit.nhgl.schedule.entites;

import java.util.Date;
import java.util.List;

																

/**
 * @author 
 * @create 2014-06-16 18:01:51 
 * @since 
 * 
 */
public class MonitorTask extends hc.base.domains.LongId{

	private static final long	serialVersionUID	= 1L;
		
			
	private String taskCode;		
	private String taskName;		
	private Long taskType;		
	private Integer taskVesting;	//设备运行状态：1；电：2；水：3；气：4；	
	private String taskDesc;		
	private String noticeTmpl;		
	private String crCode;
	private Long jobId;
	private Long crLevel;
	//虚拟字段
	private String categoryText;
	private Date startDate;
	private Date endDate;
	private String typeName;
	
	public Long getCrLevel() {
		return crLevel;
	}

	public void setCrLevel(Long crLevel) {
		this.crLevel = crLevel;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCategoryText() {
		return categoryText;
	}

	public void setCategoryText(String categoryText) {
		this.categoryText = categoryText;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	private TaskExecutionPlan executionPlan;
	private List<MonitorObject> monitorObjects;
	private List<CriteriaDetail> criteriaDetails;
					
	public String getTaskCode() {
		return taskCode;
	}
	
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
				
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
				
	public Long getTaskType() {
		return taskType;
	}
	
	public void setTaskType(Long taskType) {
		this.taskType = taskType;
	}
				
	public Integer getTaskVesting() {
		return taskVesting;
	}
	
	public void setTaskVesting(Integer taskVesting) {
		this.taskVesting = taskVesting;
	}
				
	public String getTaskDesc() {
		return taskDesc;
	}
	
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
				
	public String getNoticeTmpl() {
		return noticeTmpl;
	}
	
	public void setNoticeTmpl(String noticeTmpl) {
		this.noticeTmpl = noticeTmpl;
	}
				
	public String getCrCode() {
		return crCode;
	}
	
	public void setCrCode(String crCode) {
		this.crCode = crCode;
	}

	public TaskExecutionPlan getExecutionPlan() {
		return executionPlan;
	}

	public void setExecutionPlan(TaskExecutionPlan executionPlan) {
		this.executionPlan = executionPlan;
	}

	public List<MonitorObject> getMonitorObjects() {
		return monitorObjects;
	}

	public void setMonitorObjects(List<MonitorObject> monitorObjects) {
		this.monitorObjects = monitorObjects;
	}

	public List<CriteriaDetail> getCriteriaDetails() {
		return criteriaDetails;
	}

	public void setCriteriaDetails(List<CriteriaDetail> criteriaDetails) {
		this.criteriaDetails = criteriaDetails;
	}
			
	
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}


	private String deviceIdListStr;//deviceId 以";"分隔的字符串

	public String getDeviceIdListStr() {
		return deviceIdListStr;
	}

	public void setDeviceIdListStr(String deviceIdListStr) {
		this.deviceIdListStr = deviceIdListStr;
	}	
}
