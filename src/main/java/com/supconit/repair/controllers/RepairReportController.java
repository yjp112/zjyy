package com.supconit.repair.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jodd.datetime.JDateTime;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.DateUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.utils.GraphUtils;
import com.supconit.repair.entities.RepairReportByCategory;
import com.supconit.repair.entities.RepairReportByHours;
import com.supconit.repair.entities.RepairReportBySpare;
import com.supconit.repair.entities.RepairReportNum;
import com.supconit.repair.services.RepairReportByCategoryService;
import com.supconit.repair.services.RepairReportByHoursService;
import com.supconit.repair.services.RepairReportBySpareService;
import com.supconit.repair.services.RepairReportNumService;

@RequestMapping("repair/repairReport")
@Controller
public class RepairReportController extends BaseControllerSupport {
	@Autowired
	private RepairReportByCategoryService	reportService;
	@Autowired
	private RepairReportByHoursService rrhService;
	@Autowired
	private RepairReportBySpareService rrsService;
	@Autowired
	private RepairReportNumService rrnService;

	@RequestMapping("page/{type}")
	public String page(@PathVariable(value = "type") int type,ModelMap map,String flag) {
		String path = "";
		switch (type) {
		case 1:
			path = "repair/repair/repair_by_category";
			break;
		case 2:
			path = "repair/repair/repair_by_hours";
			break;
		case 3:
			path = "repair/repair/repair_by_spare";
			break;
		default:
			break;
		}
		map.put("flag", flag);
		return path;
	}

	@RequestMapping("repair_by_category")
	@ResponseBody
	public Pageable<RepairReportByCategory> repairByDevice(RepairReportByCategory report,String flag) {
		if(flag.equals("0")){
			report.setRepairMode(0);
		}
		List<RepairReportByCategory> rateList = reportService.queryRepairByCategory(report);
		Pageable<RepairReportByCategory> pager = new Pagination<RepairReportByCategory>(rateList);
		pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(rateList.size());
		pager.addAll(rateList);
		return pager;
	}
	@RequestMapping("repair_by_hours")
	@ResponseBody
	public Pageable<RepairReportByHours> repair_by_hours(RepairReportByHours report,String flag) {
//		SimpleDateFormat sf=new SimpleDateFormat("yyyy");
//		report.setRepairMonth(sf.format(new Date()));
		if(flag.equals("0")){
			report.setRepairMode(0);
		}else if(flag.equals("1")){
			report.setRepairMode(1);
		}
		List<RepairReportByHours> rateList = rrhService.queryRepairByHours(report);
		Pageable<RepairReportByHours> pager = new Pagination<RepairReportByHours>(rateList);
		pager.setPageNo(1);
		pager.setPageSize(Integer.MAX_VALUE);
		pager.setTotal(rateList.size());
		return pager;
	}
	@RequestMapping("repair_by_spare")
	@ResponseBody
	public Pageable<RepairReportBySpare> repair_by_spare(RepairReportBySpare report,String flag) {
		if(flag.equals("0")){
			report.setRepairMode(0);
		}
		List<RepairReportBySpare> rateList = rrsService.queryRepairBySpare(report);
		Pageable<RepairReportBySpare> pager = new Pagination<RepairReportBySpare>(rateList);
		pager.setPageNo(1);
		pager.setPageSize(Integer.MAX_VALUE);
		pager.setTotal(rateList.size());
		return pager;
	}
	
	
	@RequestMapping(value="countAll")
	public String countAll(ModelMap model) {
    	JDateTime jdt = new JDateTime(new Date());
    	model.put("year", jdt.getYear());
    	return "repair/repair/countAll";
    }
    
    
    @RequestMapping(value="countAllYear")
	public String countAllYear(ModelMap model,Integer year) {
    	JDateTime jdt = new JDateTime(new Date());
    	List<String> monthBuilder = new ArrayList<String>();//月份
    	RepairReportNum repair = new RepairReportNum();
    	repair.setRepairYear(String.valueOf(year));
    	
		Integer month = 12;
		if(year == jdt.getYear()){
			month = jdt.getMonth();
		}
		for (int i = 0; i < month; i++) {
			String monthItem=String.valueOf(String.valueOf(year)+"-"+StringUtils.leftPad(i+1+"", 2, "0"));
			monthBuilder.add(monthItem);
		}
		
		List<Integer> nums = new ArrayList<Integer>();
		List<Integer> finishNums = new ArrayList<Integer>();
		List<RepairReportNum> listAll = rrnService.queryRepairByYearAll(repair);
		List<RepairReportNum> listFinish = rrnService.queryRepairByYearFinished(repair);
		for (String months : monthBuilder) {
			int countAll = 0;
			int countFinish = 0;
			for (RepairReportNum repairReportNum : listAll) {
				if(months.equals(repairReportNum.getRepairMonth())){
					nums.add(repairReportNum.getRepairTimes());
					countAll++;
					break;
				}
			}
			for (RepairReportNum repairReportNum : listFinish) {
				if(months.equals(repairReportNum.getRepairMonth())){
					finishNums.add(repairReportNum.getRepairFinishTimes());
					countFinish++;
					break;
				}
			}
			if(countAll==0){
				nums.add(0);
			}
			if(countFinish==0){
				finishNums.add(0);
			}
		}
		
		model.put("xTime", StringUtils.join(monthBuilder,","));
		model.put("year", year);
		model.put("nums", StringUtils.join(nums,","));
		model.put("finishNums", StringUtils.join(finishNums,","));
		model.put("title", year + "年维修单数统计");
		return "repair/repair/countAllYearAndMonth";
	}
    
    @RequestMapping(value="countAllMonth")
    public String countAllMonth(ModelMap model,String month) {
    	List<String> dayBuilder = new ArrayList<String>();//一个月中的每天
    	JDateTime startTime = new JDateTime(month);
		JDateTime now = new JDateTime(new Date());
		RepairReportNum repair = new RepairReportNum();
    	repair.setRepairMonth(month);
		String endTime = "";
		if(startTime.toString("YYYY-MM").equals(now.toString("YYYY-MM"))){
			endTime=GraphUtils.getDateString(now.getYear(),now.getMonth(), now.getDay());
		}else{
			endTime=DateUtils.getMonthEnd(startTime.getYear(), startTime.getMonth());
		}
		Integer dayOfMonth = Integer.parseInt(endTime.split("-")[2]);
		for (int i = 0; i < dayOfMonth; i++) {
			String monthIndex = String.valueOf(i+1);
			dayBuilder.add(monthIndex);
		}
		
		List<Integer> nums = new ArrayList<Integer>();
		List<Integer> finishNums = new ArrayList<Integer>();
		List<RepairReportNum> listAll = rrnService.queryRepairByMonthAll(repair);
		List<RepairReportNum> listFinish = rrnService.queryRepairByMonthFinished(repair);
		for (String days : dayBuilder) {
			int countAll = 0;
			int countFinish = 0;
			for (RepairReportNum repairReportNum : listAll) {
				Integer dayNum = Integer.parseInt(repairReportNum.getRepairDay().split("-")[2]);
				if(days.equals(String.valueOf(dayNum))){
					nums.add(repairReportNum.getRepairTimes());
					countAll++;
					break;
				}
			}
			for (RepairReportNum repairReportNum : listFinish) {
				Integer dayNum = Integer.parseInt(repairReportNum.getRepairDay().split("-")[2]);
				if(days.equals(String.valueOf(dayNum))){
					finishNums.add(repairReportNum.getRepairFinishTimes());
					countFinish++;
					break;
				}
			}
			if(countAll==0){
				nums.add(0);
			}
			if(countFinish==0){
				finishNums.add(0);
			}
		}
		
		model.put("xTime", StringUtils.join(dayBuilder,","));
		model.put("nums", StringUtils.join(nums,","));
		model.put("finishNums", StringUtils.join(finishNums,","));
		String[] mon = month.split("-");
		model.put("title", mon[0] + "年" + mon[1]+ "月维修单数统计");
    	return "repair/repair/countAllYearAndMonth";
    }
    
    /*	
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

	private String generateBarAndLineOptionWithReportByCategory(List<RepairReportByCategory> list) {
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
