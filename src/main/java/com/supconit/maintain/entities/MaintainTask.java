package com.supconit.maintain.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

import hc.bpm.Bpmable;

public class MaintainTask  extends AuditExtend  implements Bpmable{
	private static final long serialVersionUID = 1L;

    private String processInstanceId;
    private String maintainCode;
    private String maintainDescription;
    private Long maintainGroupId;
    private String maintainGroupName;
    private Long maintainPersonId;
    private String maintainPersonName;
    private String maintainWorkerIds;
    private String maintainWorkerNames;
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
    private Date beginTime;//保养时间始
	private Date endTime;//保养时间止
	private String statuss;
	private String handlePersonCode;
	private String currentNode;//当前节点
	private String bpm_ts_id;
    private Long categoryId;
	private Long areaId;
	private List<Long> deviceCategoryChildIds;
	private List<Long> geoAreaChildIds;
	private List<String> maintainCodeList;
	private List<MaintainTaskContent> maintainTaskContentList;
	private List<MaintainSpare> maintainSpareList;
	
	@Override
	public String callbackUpdateSQL() {
		return "update Maintain_Task set process_instance_id =? where id = " + this.getId();
	}
	
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    public String getMaintainCode() {
        return maintainCode;
    }

    public void setMaintainCode(String maintainCode) {
        this.maintainCode = maintainCode == null ? null : maintainCode.trim();
    }

    public String getMaintainDescription() {
        return maintainDescription;
    }

    public void setMaintainDescription(String maintainDescription) {
        this.maintainDescription = maintainDescription == null ? null : maintainDescription.trim();
    }

    public Long getMaintainGroupId() {
        return maintainGroupId;
    }

    public void setMaintainGroupId(Long maintainGroupId) {
        this.maintainGroupId = maintainGroupId;
    }

    public String getMaintainGroupName() {
        return maintainGroupName;
    }

    public void setMaintainGroupName(String maintainGroupName) {
        this.maintainGroupName = maintainGroupName == null ? null : maintainGroupName.trim();
    }

    public Long getMaintainPersonId() {
        return maintainPersonId;
    }

    public void setMaintainPersonId(Long maintainPersonId) {
        this.maintainPersonId = maintainPersonId;
    }

    public String getMaintainPersonName() {
        return maintainPersonName;
    }

    public void setMaintainPersonName(String maintainPersonName) {
        this.maintainPersonName = maintainPersonName == null ? null : maintainPersonName.trim();
    }

    public String getMaintainWorkerIds() {
        return maintainWorkerIds;
    }

    public void setMaintainWorkerIds(String maintainWorkerIds) {
        this.maintainWorkerIds = maintainWorkerIds == null ? null : maintainWorkerIds.trim();
    }

    public String getMaintainWorkerNames() {
        return maintainWorkerNames;
    }

    public void setMaintainWorkerNames(String maintainWorkerNames) {
        this.maintainWorkerNames = maintainWorkerNames == null ? null : maintainWorkerNames.trim();
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

	public List<String> getMaintainCodeList() {
		return maintainCodeList;
	}

	public void setMaintainCodeList(List<String> maintainCodeList) {
		this.maintainCodeList = maintainCodeList;
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

	public List<MaintainTaskContent> getMaintainTaskContentList() {
		return maintainTaskContentList;
	}

	public void setMaintainTaskContentList(
			List<MaintainTaskContent> maintainTaskContentList) {
		this.maintainTaskContentList = maintainTaskContentList;
	}

	public List<MaintainSpare> getMaintainSpareList() {
		return maintainSpareList;
	}

	public void setMaintainSpareList(List<MaintainSpare> maintainSpareList) {
		this.maintainSpareList = maintainSpareList;
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