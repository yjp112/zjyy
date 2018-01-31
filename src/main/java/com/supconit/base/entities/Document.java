package com.supconit.base.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

public class Document extends AuditExtend{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String processInstanceId;
	private Integer docType;
	private Long deviceId;
	private Long areaId;
	private Long categoryId;
	private String docNo;
	private String docTitle;
	private String docKey;
	private String abstracr;
	private String location;
	private String fileType;
	private String fileName;
	private Long fileSize;
	private String storePath;
	private String remark;
	private Long status;
	private Long times;
	
	private Date startTime;
	private Date endTime;
	private String docTypeName;
	private String categoryName;
	private String areaName;
	private String deviceName;
	private String deviceCode;
	private  List<Long> categoryIds;
	private  List<Long> areaIds;
	
	public String getDocTypeName(){
		return DictUtils.getDictLabel(DictTypeEnum.DOCUMENT_TYPE, this.docType.toString());
	}
	
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Integer getDocType() {
		return docType;
	}
	public void setDocType(Integer docType) {
		this.docType = docType;
	}
	
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
	public List<Long> getCategoryIds() {
		return categoryIds;
	}
	public List<Long> getAreaIds() {
		return areaIds;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getDocKey() {
		return docKey;
	}
	public void setDocKey(String docKey) {
		this.docKey = docKey;
	}
	public String getAbstracr() {
		return abstracr;
	}
	public void setAbstracr(String abstracr) {
		this.abstracr = abstracr;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getStorePath() {
		return storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getTimes() {
		if(times == null)
			times = (long)0;
		return times;
	}
	public void setTimes(Long times) {
		this.times = times;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}
	public void timesJY(Long times) {
		this.times = times + 1;
	}
	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}
	public void setAreaIds(List<Long> areaIds) {
		this.areaIds = areaIds;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
	
}
