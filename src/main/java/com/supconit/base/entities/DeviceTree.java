package com.supconit.base.entities;


import java.util.Date;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.RandomUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;

public class DeviceTree  {

    //
    private Long id;
    //
	private String deviceCode;
    //
	private String deviceName;
    //
	private String deviceSpec;
	
	private Long deviceType;
    //
	private Long categoryId;
    //
	private Long locationId;
	//
	private String locationName;
	
	private String managePersonIds;
    //
	private String managePersonName;
	
	private Long manageDepartmentId;
	
	private String manageDepartmentName;
    //
    private String categoryName;
    
	private Long useDepartmentId;
	
	private String useDepartmentName;
	
	private Long maitainCycle;
	
	private Date enableDate;
	
	private String assetsCode;
	
	private String barcode;
	
	private Double assetsCost;
	

	private String statusName;

    private boolean hasChildren;

    private Long parentId;


    private Long lft;
    private Long rgt;
	// -----


    public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	//
	private Long status;
	public Long getStatus() {
		return status;
	}
	public Long getMaitainCycle() {
		return maitainCycle;
	}

	public void setMaitainCycle(Long maitainCycle) {
		this.maitainCycle = maitainCycle;
	}

	public Date getEnableDate() {
		return enableDate;
	}

	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}

	public String getAssetsCode() {
		return assetsCode;
	}

	public void setAssetsCode(String assetsCode) {
		this.assetsCode = assetsCode;
	}

	public Double getAssetsCost() {
		return assetsCost;
	}

	public void setAssetsCost(Double assetsCost) {
		this.assetsCost = assetsCost;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	
    public Long getManageDepartmentId() {
		return manageDepartmentId;
	}

	public void setManageDepartmentId(Long manageDepartmentId) {
		this.manageDepartmentId = manageDepartmentId;
	}

	public String getManageDepartmentName() {
		if(manageDepartmentId!=null){
			return OrganizationUtils.getFullDeptNameByDeptId(manageDepartmentId);
		}
		return "";
	}

	public void setManageDepartmentName(String manageDepartmentName) {
		this.manageDepartmentName = manageDepartmentName;
	}

	public Long getUseDepartmentId() {
		return useDepartmentId;
	}

	public void setUseDepartmentId(Long useDepartmentId) {
		this.useDepartmentId = useDepartmentId;
	}

	public String getUseDepartmentName() {
		if(useDepartmentId!=null){
			return OrganizationUtils.getFullDeptNameByDeptId(useDepartmentId);
		}
		return "";
	}

	public void setUseDepartmentName(String useDepartmentName) {
		this.useDepartmentName = useDepartmentName;
	}
	
	public Long getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceCode() {
        if(deviceCode==null){
            return "";
        }
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSpec() {
        return deviceSpec;
    }

    public void setDeviceSpec(String deviceSpec) {
        this.deviceSpec = deviceSpec;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
    	 if(locationName==null){
             return "";
         }
         return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getManagePersonIds() {
        return managePersonIds;
    }

    public void setManagePersonIds(String managePersonIds) {
        this.managePersonIds = managePersonIds;
    }

    public String getManagePersonName() {
        if(managePersonName==null){
            return  "";
        }
        return managePersonName;
    }

    public void setManagePersonName(String managePersonName) {
        this.managePersonName = managePersonName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatusName() {
    	if(this.status != null){
			return DictUtils.getDictLabel(DictTypeEnum.DEVICE_STATUS, this.status.toString());
		}else{
			return "";
		}
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    public Long getParentId() {
        if(parentId==null||parentId==0L){
            return null;
        }
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getRgt() {
        return rgt;
    }

    public void setRgt(Long rgt) {
        this.rgt = rgt;
    }

    public Long getLft() {
        return lft;
    }

    public void setLft(Long lft) {
        this.lft = lft;
    }

    public boolean isHasChildren() {
    	if((this.rgt==null)||(this.lft==null)){
    		return false;
    	}
        if(this.rgt-this.lft==1){
            hasChildren= false;
        }else{
            hasChildren= true;
        }
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
