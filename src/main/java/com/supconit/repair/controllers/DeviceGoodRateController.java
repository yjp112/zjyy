package com.supconit.repair.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.repair.entities.DeviceGoodRate;
import com.supconit.repair.services.DeviceGoodRateService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文件名: DeviceReportController
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
@RequestMapping("repair/deviceGoodRate")
@Controller
public class DeviceGoodRateController extends BaseControllerSupport {
	@Autowired
	private DeviceGoodRateService	reportService;

	@RequestMapping("page")
	public String page( ModelMap map) {
		return "repair/repair/device_good_rate";
	}

	@RequestMapping("device_good_rate")
	@ResponseBody
	public Pageable<DeviceGoodRate> deviceGoodRate(DeviceGoodRate deviceGoodRate) {
		
		List<DeviceGoodRate> rateList = reportService.getDeviceGoodRate(deviceGoodRate);

		if (null != deviceGoodRate && null != deviceGoodRate.getOrderField()) {
			if (deviceGoodRate.getOrderField().equalsIgnoreCase("rate asc")) {
				Collections.sort(rateList, new Comparator<DeviceGoodRate>() {
					@Override
					public int compare(DeviceGoodRate deviceGoodRate, DeviceGoodRate deviceGoodRate2) {
						return deviceGoodRate.getGoodRate().compareTo(deviceGoodRate2.getGoodRate());
					}
				});
			} else if (deviceGoodRate.getOrderField().equalsIgnoreCase("rate desc")) {
				Collections.sort(rateList, new Comparator<DeviceGoodRate>() {
					@Override
					public int compare(DeviceGoodRate deviceGoodRate, DeviceGoodRate deviceGoodRate2) {
						return deviceGoodRate2.getGoodRate().compareTo(deviceGoodRate.getGoodRate());
					}
				});
			}
		}
		Pageable<DeviceGoodRate> pager = new Pagination<DeviceGoodRate>(rateList);
		pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(rateList.size());
		return pager;
	}

/*	@RequestMapping("repair_by_device")
	@ResponseBody
	public JsonPager<RepairReportByDevice> repairByDevice(RepairReportByDevice deviceGoodRate) {
		Pager<RepairReportByDevice> pager = new Pager<RepairReportByDevice>();
		List<RepairReportByDevice> rateList = reportService.getRepairByDevice(deviceGoodRate);
		pager.addAll(rateList);
		return toJsonPager(pager);
		//return JSONArray.parseArray(JSON.toJSONString(rateList, true));
	}

	@RequestMapping("repair_by_category")
	@ResponseBody
	public JsonPager<RepairReportByCategory> repairByCategory(RepairReportByCategory deviceGoodRate) {
		Pager<RepairReportByCategory> pager = new Pager<RepairReportByCategory>();
		List<RepairReportByCategory> rateList = reportService.getRepairByCategory(deviceGoodRate);
		pager.addAll(rateList);
		return toJsonPager(pager);
	}
*/
	private String generateBarAndLineOption(List<DeviceGoodRate> list) {
		StringBuffer categoryBuffer = new StringBuffer();
		StringBuffer value1Buffer = new StringBuffer();
		StringBuffer value2Buffer = new StringBuffer();
		for (DeviceGoodRate rate : list) {
			if (categoryBuffer.length() > 0) {
				categoryBuffer.append(",");
				value1Buffer.append(",");
				value2Buffer.append(",");
			}
			categoryBuffer.append("'" + rate.getCategoryName() + "'");
			value1Buffer.append(rate.getDeviceFaultNum());
			value2Buffer.append(rate.getGoodRate());
		}
		String legends = "legend: {data:['故障次数','设备完好率']}";

		String xAxis = "xAxis:[{type:'category',data:[" + categoryBuffer.toString() + "]}]";
		String yAxis = "yAxis:[{type : 'value',name : '次数',axisLabel : {    formatter: '{value} 次'},splitArea : {show : true}},"
				+ "{type : 'value',name : '百分比',axisLabel : {  formatter: '{value} %'},splitLine : {show : false} }]";
		String series = "series:[{name:'故障次数',type:'bar',data:[" + value1Buffer.toString() + "]},"
				+ "{name:'设备完好率',type:'line',yAxisIndex: 1,data:[" + value2Buffer.toString() + "]}]";
		String option = "{tooltip:{trigger: 'axis'},"
				+ " toolbox:{show:true,feature:{mark : true,dataView : {readOnly: false},magicType:['line', 'bar'],restore : true,saveAsImage : true}},"
				+ "calculable : false," + // 设置拖拽重新计算
				legends + "," + xAxis + "," + yAxis + "," + series + "}";
		return option;
	}

/*	private String generateBarAndLineOptionWithReportByCategory(List<RepairReportByCategory> list) {
		StringBuffer categoryBuffer = new StringBuffer();
		StringBuffer value1Buffer = new StringBuffer();
		StringBuffer value2Buffer = new StringBuffer();
		for (RepairReportByCategory rate : list) {
			if (categoryBuffer.length() > 0) {
				categoryBuffer.append(",");
				value1Buffer.append(",");
				value2Buffer.append(",");
			}
			categoryBuffer.append("'" + rate.getCategoryName() + "'");
			value1Buffer.append(rate.getRepairTimes());
			value2Buffer.append(rate.getMoney());
		}
		String legends = "legend: {data:['维修次数','维修费用']}";

		String xAxis = "xAxis:[{type:'category',data:[" + categoryBuffer.toString() + "]}]";
		String yAxis = "yAxis:[{type : 'value',name : '次数',axisLabel : {    formatter: '{value} 次'},splitArea : {show : true}},"
				+ "{type : 'value',name : '元',axisLabel : {  formatter: '{value} 元'},splitLine : {show : false} }]";
		String series = "series:[{name:'维修次数',type:'bar',data:[" + value1Buffer.toString() + "]},"
				+ "{name:'维修费用',type:'line',yAxisIndex: 1,data:[" + value2Buffer.toString() + "]}]";
		String option = "{tooltip:{trigger: 'axis'},"
				+ " toolbox:{show:true,feature:{mark : true,dataView : {readOnly: false},magicType:['line', 'bar'],restore : true,saveAsImage : true}},"
				+ "calculable : false," + // 设置拖拽重新计算
				legends + "," + xAxis + "," + yAxis + "," + series + "}";
		return option;
	}*/
}
