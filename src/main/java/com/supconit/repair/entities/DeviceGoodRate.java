package com.supconit.repair.entities;

import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

/**
 * @文件名: DeviceGoodRate
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
public class DeviceGoodRate extends AuditExtend {
	private Long	categoryId;
	private String	categoryName;
	private int		deviceNum;		// 设备总数
	private int		deviceFaultNum; // 故障设备数
	private Float	goodRate;		// 完好率

	// ------Search Param -----
	private List<Long> categoryIds;
	private String	beginDate;
	private String	endDate;

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

	public int getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(int deviceNum) {
		this.deviceNum = deviceNum;
	}

	public int getDeviceFaultNum() {
		return deviceFaultNum;
	}

	public void setDeviceFaultNum(int deviceFaultNum) {
		this.deviceFaultNum = deviceFaultNum;
	}

	public Float getGoodRate() {
		if (null == this.goodRate || 0 == this.goodRate) {
			this.goodRate = Float.valueOf((Float.valueOf(deviceNum) - Float.valueOf(deviceFaultNum)) / Float.valueOf(deviceNum) * 100);
		}
        return  (float)(Math.round(goodRate*100))/100;
//		return goodRate;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public void setGoodRate(Float goodRate) {
		this.goodRate = goodRate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
