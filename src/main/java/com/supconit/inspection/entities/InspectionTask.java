package com.supconit.inspection.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;
import com.supconit.inspection.entities.InspectionSpare;
import com.supconit.inspection.entities.InspectionTaskContent;

import hc.bpm.Bpmable;

public class InspectionTask  extends AuditExtend  implements Bpmable{
	private static final long serialVersionUID = 1L;

    private String processInstanceId;
    private String inspectionCode;
    private String inspectionDescription;
    private Long inspectionGroupId;
    private String inspectionGroupName;
    private Long inspectionPersonId;
    private String inspectionPersonName;
    private String inspectionWorkerIds;
    private String inspectionWorkerNames;
    private Long planId;
    private Date expectStartTime;
    private Date actualStartTime;
    private Date actualEndTime;
    private Integer status;
    private String remark;
    private String processInstanceName;
    
    //扩展
    private String deviceName;//设备名称
    private String deviceCode;//设备code
	private String locationName;//安装位置
    private Date beginTime;//巡检时间始
	private Date endTime;//巡检时间止
	private String statuss;
	private String handlePersonCode;
	private String currentNode;//当前节点
	private String bpm_ts_id;
    private Long categoryId;
	private Long areaId;
	private List<Long> deviceCategoryChildIds;
	private List<Long> geoAreaChildIds;
	private List<String> inspectionCodeList;
	private List<InspectionTaskContent> inspectionTaskContentList;
	private List<InspectionSpare> inspectionSpareList;
	
	@Override
	public String callbackUpdateSQL() {
		return "update inspection_Task set process_instance_id =? where id = " + this.getId();
	}
	
	public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    public String getInspectionCode() {
        return inspectionCode;
    }

    public void setInspectionCode(String inspectionCode) {
        this.inspectionCode = inspectionCode == null ? null : inspectionCode.trim();
    }

    public String getInspectionDescription() {
        return inspectionDescription;
    }

    public void setInspectionDescription(String inspectionDescription) {
        this.inspectionDescription = inspectionDescription == null ? null : inspectionDescription.trim();
    }

    public Long getInspectionGroupId() {
        return inspectionGroupId;
    }

    public void setInspectionGroupId(Long inspectionGroupId) {
        this.inspectionGroupId = inspectionGroupId;
    }

    public String getInspectionGroupName() {
        return inspectionGroupName;
    }

    public void setInspectionGroupName(String inspectionGroupName) {
        this.inspectionGroupName = inspectionGroupName == null ? null : inspectionGroupName.trim();
    }

    public Long getInspectionPersonId() {
        return inspectionPersonId;
    }

    public void setInspectionPersonId(Long inspectionPersonId) {
        this.inspectionPersonId = inspectionPersonId;
    }

    public String getInspectionPersonName() {
        return inspectionPersonName;
    }

    public void setInspectionPersonName(String inspectionPersonName) {
        this.inspectionPersonName = inspectionPersonName == null ? null : inspectionPersonName.trim();
    }

    public String getInspectionWorkerIds() {
        return inspectionWorkerIds;
    }

    public void setInspectionWorkerIds(String inspectionWorkerIds) {
        this.inspectionWorkerIds = inspectionWorkerIds == null ? null : inspectionWorkerIds.trim();
    }

    public String getInspectionWorkerNames() {
        return inspectionWorkerNames;
    }

    public void setInspectionWorkerNames(String inspectionWorkerNames) {
        this.inspectionWorkerNames = inspectionWorkerNames == null ? null : inspectionWorkerNames.trim();
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Date getExpectStartTime() {
        return expectStartTime;
    }

    public void setExpectStartTime(Date expectStartTime) {
        this.expectStartTime = expectStartTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
	public String getProcessInstanceName() {
		return processInstanceName;
	}

	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public List<String> getInspectionCodeList() {
		return inspectionCodeList;
	}

	public void setInspectionCodeList(List<String> inspectionCodeList) {
		this.inspectionCodeList = inspectionCodeList;
	}

	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}
	
	public String getHandlePersonCode() {
		return handlePersonCode;
	}

	public void setHandlePersonCode(String handlePersonCode) {
		this.handlePersonCode = handlePersonCode;
	}

	public String getBpm_ts_id() {
		return bpm_ts_id;
	}

	public void setBpm_ts_id(String bpm_ts_id) {
		this.bpm_ts_id = bpm_ts_id;
	}

	public List<InspectionTaskContent> getInspectionTaskContentList() {
		return inspectionTaskContentList;
	}

	public void setInspectionTaskContentList(
			List<InspectionTaskContent> inspectionTaskContentList) {
		this.inspectionTaskContentList = inspectionTaskContentList;
	}

	public List<InspectionSpare> getInspectionSpareList() {
		return inspectionSpareList;
	}

	public void setInspectionSpareList(List<InspectionSpare> inspectionSpareList) {
		this.inspectionSpareList = inspectionSpareList;
	}

	public String getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	//-------------------------------------------------------------------
	public String getStatuss() {
		return DictUtils.getDictLabel(DictTypeEnum.MAINTAIN_TASK_STATUS, this.statuss);
	}
}