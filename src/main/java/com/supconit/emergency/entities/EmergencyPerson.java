package com.supconit.emergency.entities;

import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

public class EmergencyPerson extends AuditExtend{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long personId;
	private String personName;
	private Long groupId;
	private String groupName;
	private Integer header;
	private String remark;
	private String headerText;
	private List<Long> searchEmergencyGroupList;
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public Long getGroupId() {
		return groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Integer getHeader() {
		return header;
	}
	public void setHeader(Integer header) {
		this.header = header;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Long> getSearchEmergencyGroupList() {
		return searchEmergencyGroupList;
	}
	public void setSearchEmergencyGroupList(List<Long> searchEmergencyGroupList) {
		this.searchEmergencyGroupList = searchEmergencyGroupList;
	}
	public String getHeaderText() {
		return DictUtils.getDictLabel(DictTypeEnum.EMERGENCY_HEADER, this.header.toString());
		
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	
	
}
