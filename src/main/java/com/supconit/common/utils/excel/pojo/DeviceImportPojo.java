package com.supconit.common.utils.excel.pojo;

import java.io.File;
import java.util.Collection;

import com.supconit.common.utils.excel.ExcelAnnotation;
import com.supconit.common.utils.excel.ExcelImportHelper;

/**
 * 
 */
public class DeviceImportPojo {
	@ExcelAnnotation(exportName = "hpid(必填项)")
	private String hpid;
	@ExcelAnnotation(exportName = "设备名称(必填项)")
	private String deviceName;
	@ExcelAnnotation(exportName = "设备类别(必填项)")
	private String deviceCategory;

	public String getHpid() {
		return hpid;
	}

	public void setHpid(String hpid) {
		this.hpid = hpid;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceCategory() {
		return deviceCategory;
	}

	public void setDeviceCategory(String deviceCategory) {
		this.deviceCategory = deviceCategory;
	}

	public static void main(String[] args) {
		ExcelImportHelper<DeviceImportPojo> test = new ExcelImportHelper(DeviceImportPojo.class);
		File file = new File("C:/Users/DELL/Desktop/todo/device-import-停车场.xls");
		Long befor = System.currentTimeMillis();
		Collection<DeviceImportPojo> result = test.importExcel(file);
		for (DeviceImportPojo pojo : result) {
			String hpid=pojo.getHpid();
			String[] hpidArr=hpid.split("_");
			System.out.println(hpidArr[1]+"车位"+hpidArr[4]);
		}
	}
}
