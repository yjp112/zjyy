package com.supconit.nhgl.analyse.water.dept.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DateUtils;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptDay;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptHour;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptMonth;
import com.supconit.nhgl.analyse.water.dept.service.NhSDeptDayService;
import com.supconit.nhgl.analyse.water.dept.service.NhSDeptHourService;
import com.supconit.nhgl.analyse.water.dept.service.NhSDeptMonthService;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.GraphUtils;
import com.supconit.nhgl.utils.HolidayConstans;

/**
 * 能耗分析，部门用能趋势分析
 *
 */
@Controller("DeptWaterCountController")
@RequestMapping("nhgl/analyse/water/dept")
public class DeptCountController {
	@Autowired
	private NhItemService subService;
	@Autowired
	private NhSDeptMonthService nhSDeptMonthService;
	@Autowired
	private ConfigManagerService configManagerService;
	@Autowired
	private NhSDeptDayService nhSDeptDayService;
	@Autowired
	private NhSDeptHourService nhSDeptHourService;
	@Autowired
	private NhDeptService nhDeptService;
	private NhItem subInfo = new NhItem();
	private Integer num = 0;
	
	@RequestMapping("index")
	public String index(ModelMap model, Integer year, String flag, String fxCode, String menuName,String code,String deptId) {
		subInfo.setParentCode("0");
		subInfo.setNhType(Constant.NH_TYPE_S);
		List<NhItem> slist = subService.findByCon(subInfo);
		List<NhSDeptMonth> nhlst = null;
		NhSDeptMonth nh = new NhSDeptMonth();
		JDateTime dateTime = new JDateTime(new Date());
		Integer yearParam = dateTime.getYear();
		if(year == null){//今年年份
			year = yearParam;
			nh.setEndTime(dateTime.toString("YYYY-MM"));
		}else{
			nh.setEndTime(year+"-12");
		}
		Integer nextYear = year - 1;//去年年份
		nh.setItemCode(fxCode);
		nh.setStartTime(year+"-01");
		nh.setNhType(Constant.NH_TYPE_S);
		nhlst=nhSDeptMonthService.getDeptList(nh);
		model.put("rootId", nhDeptService.findRootId());
		model.put("year", year);
		model.put("nextyear", nextYear);
		model.put("slist", slist);
		model.put("list", nhlst);
		model.put("fxCode", fxCode);
		model.put("code", code);
		model.put("deptId", deptId);
		return "nhgl/analyse/water/dept/dept_count_tab";
	}

	@RequestMapping("allYear")
	public String allYearData(ModelMap model, String deptName, Integer year,String fxCode,
			Integer deptId, String isp) {
		num = num + 1;
		JDateTime jdt = new JDateTime(new Date());
		NhSDeptMonth nh=new NhSDeptMonth();
		NhSDeptMonth nhMonth=null;
		NhSDeptMonth nhMonthLast=null;
		List<NhSDeptMonth> nhlst=new ArrayList<NhSDeptMonth>();
		Integer month=12;
		if(year!=jdt.getYear()){
			nh.setEndTime(year+"-12");
		}else{
			nh.setEndTime(jdt.toString("YYYY-MM"));
			month=jdt.getMonth();
		}
		nh.setItemCode(fxCode);
		nh.setDeptId(deptId);
		nh.setStartTime(year+"-01");
		nh.setNhType(Constant.NH_TYPE_S);
		nhlst=nhSDeptMonthService.getDeptMonthList(nh);
		
		nhMonth=nhSDeptMonthService.getDeptDayNight(nh);//year年白天晚上
		
		//上一年白天晚上
		nh.setStartTime(year-1+"-01");
		nh.setEndTime(GraphUtils.getDateString(year - 1,month,null));
		nhMonthLast=nhSDeptMonthService.getDeptDayNight(nh);
		
		if(nhMonth==null){
			nhMonth=new NhSDeptMonth();
			nhMonth.setMonthDaytimeValue(new BigDecimal(0));
			nhMonth.setMonthNightValue(new BigDecimal(0));
		}
		if(nhMonthLast==null){
			nhMonthLast=new NhSDeptMonth();
			nhMonthLast.setMonthDaytimeValue(new BigDecimal(0));
			nhMonthLast.setMonthNightValue(new BigDecimal(0));
		}
		
		BigDecimal tbzlD=nhMonth.getMonthDaytimeValue().subtract(nhMonthLast.getMonthDaytimeValue());
		BigDecimal tbzlN=nhMonth.getMonthNightValue().subtract(nhMonthLast.getMonthNightValue());
		BigDecimal perD = new BigDecimal(0);
		BigDecimal perN = new BigDecimal(0);
		if(nhMonthLast.getMonthDaytimeValue().compareTo(new BigDecimal(0))==1){
			perD = BigDecimalUtil.divide4(tbzlD,nhMonthLast.getMonthDaytimeValue());
		}else if(tbzlD.compareTo(new BigDecimal(0))==1){
			perD=new BigDecimal(1);
		}else if(tbzlD.compareTo(new BigDecimal(0))==0){
			perD=new BigDecimal(0);
		}
		if(nhMonthLast.getMonthNightValue().compareTo(new BigDecimal(0))==1){
			perN=BigDecimalUtil.divide4(tbzlN,nhMonthLast.getMonthNightValue());
		}else if(tbzlN.compareTo(new BigDecimal(0))==1){
			perN=new BigDecimal(1);
		}else if(tbzlN.compareTo(new BigDecimal(0))==0){
			perN=new BigDecimal(0);
		}
		nhMonth.setPercentD(perD.multiply(new BigDecimal(100)));
		nhMonth.setPercentN(perN.multiply(new BigDecimal(100)));
		nhMonth.setMonthKey(year+"年");
		String dayTimeCode=HolidayConstans.ELECTRIC_DAY_CODE;
		String nightCode=HolidayConstans.ELECTRIC_NIGHT_CODE;
		ConfigManager configDayTime=configManagerService.findByCode(dayTimeCode);
		ConfigManager configNight=configManagerService.findByCode(nightCode);
		if(configDayTime.getFlag()!=1){
			nhMonth.setMonthDaytimeValue(new BigDecimal(0));
			nhMonth.setPercentD(new BigDecimal(0));
		}
		if(configNight.getFlag()!=1){
			nhMonth.setMonthNightValue(new BigDecimal(0));
			nhMonth.setPercentN(new BigDecimal(0));
		}
		
		
		String deptNames = (deptName == null ? "" : deptName);
		model.put("title", year + "年" + deptNames + "用水量统计分析");
		Map<String, Object> newMap = GraphUtils.getMonthData(nhlst,year,month);
		model.put("deptId", deptId);
		model.put("sTime", year+"年");
		model.put("nhMonth", nhMonth);
		model.put("new", newMap);
		model.put("num", num);
		return "nhgl/analyse/water/dept/dept_count_all";
	}

	@RequestMapping("nightAllYear")
	public String allYearData(ModelMap model,String start, String deptName, String fxCode, Integer deptId, String isp) throws UnsupportedEncodingException, ParseException
	{
		NhSDeptDay nh=new NhSDeptDay();
		JDateTime startTime = new JDateTime(start);
		JDateTime now = new JDateTime(new Date());
		String endTime="";
		List<NhSDeptDay> nhlst=new ArrayList<NhSDeptDay>();
		
		nh.setItemCode(fxCode);
		if(startTime.toString("YYYY-MM").equals(now.toString("YYYY-MM"))){
			endTime=GraphUtils.getDateString(now.getYear(),now.getMonth(), now.getDay());
		}else{
			endTime=DateUtils.getMonthEnd(startTime.getYear(), startTime.getMonth());
		}
		nh.setStartTime(startTime.toString("YYYY-MM")+"-01");
		nh.setEndTime(endTime);
		nh.setDeptId(deptId);
		nh.setNhType(Constant.NH_TYPE_S);
		Integer dayOfMonth = Integer.parseInt(endTime.split("-")[2]);
		nhlst=nhSDeptDayService.getDeptDayList(nh);
		
		deptName = (deptName==null?"":deptName);
		String title = startTime.toString("YYYY年MM月")+deptName+"用水量统计分析";
		
		model.put("title", title);
		Map<String, Object> newMap = GraphUtils.getDayData(nhlst,dayOfMonth);
		model.put("deptName", deptName);
		model.put("new", newMap);
		model.put("start", start);
		model.put("deptId", deptId);
		return "nhgl/analyse/water/dept/dept_count_all";
	}
	
	@RequestMapping("dayAllYear")
	public String dayAllYear(ModelMap model,String start, String deptName, String fxCode, Integer deptId, String isp) throws ParseException {
		NhSDeptHour nh =new NhSDeptHour();
		JDateTime startTime = new JDateTime(start+" 23:59:59");
		deptName = (deptName == null ? "" : deptName);
		String title = startTime.toString("YYYY年MM月DD日")+deptName+"用水量统计分析";
		JDateTime now = new JDateTime(new Date());
		List<NhSDeptHour> nhlst=new ArrayList<NhSDeptHour>();
		List<NhSDeptHour> nhLastlst=new ArrayList<NhSDeptHour>();
		
		nh.setNhType(Constant.NH_TYPE_S);
		nh.setDeptId(deptId);
		nh.setStandardCode(fxCode);
		Integer hour=24;
		if(now.toString("YYYY-MM-DD").equals(startTime.toString("YYYY-MM-DD"))){
			nh.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
			nh.setCollectTime(new Date());
			nhlst=nhSDeptHourService.getDayofSubWater(nh);
			nh.setCollectDate(DateUtils.truncate(DateUtils.addDay(new Date(), -1), Calendar.DAY_OF_MONTH));
			nh.setCollectTime(DateUtils.addDay(new Date(), -1));
			nhLastlst=nhSDeptHourService.getDayofSubWater(nh);
			hour=now.getHour();
		}else{
			nh.setCollectDate(DateUtils.truncate(startTime.convertToDate(), Calendar.DAY_OF_MONTH));
			nh.setCollectTime(startTime.convertToDate());
			nhlst=nhSDeptHourService.getDayofSubWater(nh);
			nh.setCollectDate(DateUtils.truncate(DateUtils.addDay(startTime.convertToDate(), -1), Calendar.DAY_OF_MONTH));
			nh.setCollectTime(DateUtils.addDay(startTime.convertToDate(), -1));
			nhLastlst=nhSDeptHourService.getDayofSubWater(nh);
		}
		
		Map<String, Object> newMap = GraphUtils.getHourData(nhlst,nhLastlst,hour);
		model.put("title",title);
		model.put("new", newMap);
		model.put("start", start);
		model.put("deptName", deptName);
		model.put("deptId", deptId);
		return "nhgl/analyse/water/dept/dept_count_all";
	}
}
