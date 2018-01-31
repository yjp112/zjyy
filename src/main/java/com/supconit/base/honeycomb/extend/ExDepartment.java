package com.supconit.base.honeycomb.extend;

import com.supconit.honeycomb.business.organization.entities.Department;

public class ExDepartment extends Department {

	private static final long	serialVersionUID	= 1558241884964607607L;

	private Long				locationId;									//楼层位置
	
	//树形菜单需要使用
    private Long cid;
    
	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}
	

}
