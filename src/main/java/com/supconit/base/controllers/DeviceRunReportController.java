package com.supconit.base.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Device;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DeviceService;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文件名: DeviceInfoReportController
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
@RequestMapping("device/deviceRun")
@Controller
public class DeviceRunReportController extends BaseControllerSupport {
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	/*
	*设备台帐查询
	*/
	@RequestMapping("list")
	public String list( ModelMap model) {
//		DeviceCategory dc = deviceCategoryService.getByCode(Constant.DC_AHU);//默认空调新风机组
//		Device device = new Device();
//		device.setCategoryId(dc.getId());
//		device.setCategoryName(dc.getCategoryName());
//		model.put("device",device); 
		return "base/device/device_run";
	}

	@RequestMapping("page")
	@ResponseBody
	public Pageable<Device> page(Pagination<Device> pager, @ModelAttribute Device device, ModelMap model) {
		if(device.getCreateDate()==null){
			device.setCreateDate(DateUtils.addYear(new Date(),-50));
		}
		if(device.getUpdateDate()==null){
			device.setUpdateDate(new Date());
		}
		return deviceService.findRunReportByCondition(pager, device);
	}
 

}
