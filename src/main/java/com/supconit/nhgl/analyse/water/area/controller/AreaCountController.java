package com.supconit.nhgl.analyse.water.area.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaDay;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaHour;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaMonth;
import com.supconit.nhgl.analyse.water.area.service.NhSAreaDayService;
import com.supconit.nhgl.analyse.water.area.service.NhSAreaHourService;
import com.supconit.nhgl.analyse.water.area.service.NhSAreaMonthService;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.GraphUtils;
import com.supconit.nhgl.utils.HolidayConstans;

/**
 * 能耗分析，区域用能趋势分析
 *
 */
@Controller("AreaWaterCountController")
@RequestMapping("nhgl/analyse/water/area")
public class AreaCountController {
	@Autowired
	private NhItemService subService;
	@Autowired
	private NhSAreaDayService nhSAreaDayService;
	@Autowired
	private NhSAreaHourService nhSAreaHourService;
	@Autowired
	private NhSAreaMonthService nhSAreaMonthService;
	@Autowired
	private ConfigManagerService configManagerService;
	@Autowired
	private NhAreaService nhAreaService;
	private NhItem subInfo = new NhItem();
	

	@RequestMapping("index")
	public String index(ModelMap model, Integer yearParam, String flag, String fxCode,String code,String areaId) {
		subInfo.setParentCode("0");
		subInfo.setNhType(Constant.NH_TYPE_S);
		List<NhItem> slist = subService.findByCon(subInfo);
		
		List<NhSAreaMonth> nhlst = null;
		NhSAreaMonth nh = new NhSAreaMonth();
		JDateTime dateTime = new JDateTime(new Date());
		Integer year = dateTime.getYear();
		if(yearParam == null){//今年年份
			yearParam = year;
			nh.setEndTime(dateTime.toString("YYYY-MM"));
		}else{
			nh.setEndTime(yearParam+"-12");
		}
		Integer nextYear = yearParam - 1;//去年年份
		nh.setItemCode(fxCode);
		nh.setStartTime(yearParam+"-01");
		nh.setNhType(Constant.NH_TYPE_S);
		nhlst=nhSAreaMonthService.getAreaList(nh);
		model.put("rootId", nhAreaService.findRootId());
		model.put("year", yearParam);
		model.put("nextyear", nextYear);
		model.put("slist", slist);
		model.put("fxCode", fxCode);
		model.put("list", nhlst);
		model.put("code", code);
		model.put("areaId", areaId);
		
		return "nhgl/analyse/water/area/area_count_tab";
	}

	@RequestMapping("subYear")
	public String subYearData(ModelMap model, String fullLevelName, Integer year,
			Integer areaId, String fxCode, String isp, String eleTotal) {
		JDateTime jdt = new JDateTime(new Date());
		NhSAreaMonth nh=new NhSAreaMonth();
		NhSAreaMonth nhMonth=null;
		NhSAreaMonth nhMonthLast=null;
		List<NhSAreaMonth> nhlst=new ArrayList<NhSAreaMonth>();
		Integer month=12;
		if(year!=jdt.getYear()){
			nh.setEndTime(year+"-12");
		}else{
			nh.setEndTime(jdt.toString("YYYY-MM"));
			month=jdt.getMonth();
		}
		nh.setItemCode(fxCode);
		nh.setAreaId(areaId);
		nh.setStartTime(year+"-01");
		nh.setNhType(Constant.NH_TYPE_S);
		nhlst=nhSAreaMonthService.getAreaMonthList(nh);
		
		nhMonth=nhSAreaMonthService.getAreaDayNight(nh);//year年白天晚上
		
		//上一年白天晚上
		nh.setStartTime(year-1+"-01");
		nh.setEndTime(GraphUtils.getDateString(year - 1,month,null));
		nhMonthLast=nhSAreaMonthService.getAreaDayNight(nh);
		
		if(nhMonth==null){
			nhMonth=new NhSAreaMonth();
			nhMonth.setMonthDaytimeValue(new BigDecimal(0));
			nhMonth.setMonthNightValue(new BigDecimal(0));
		}
		if(nhMonthLast==null){
			nhMonthLast=new NhSAreaMonth();
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
		
		
		String fullLevelNames = (fullLevelName == null ? "" : fullLevelName);
		model.put("title", year + "年" + fullLevelNames + "用水量统计分析");
		Map<String, Object> newMap = GraphUtils.getMonthData(nhlst,year,month);
		
		model.put("fullLevelName", fullLevelName);
		model.put("nhMonth", nhMonth);
		model.put("new", newMap);
		model.put("fxCode", fxCode);
		model.put("id", areaId);
		return "nhgl/analyse/water/area/area_count_all";
	}

	@RequestMapping("nightAllYear")
	public String allYearData(ModelMap model,String start, String fullLevelName, String fxCode, Integer areaId, String isp) throws UnsupportedEncodingException, ParseException
	{
		NhSAreaDay nh=new NhSAreaDay();
		JDateTime startTime = new JDateTime(start);
		JDateTime now = new JDateTime(new Date());
		String endTime="";
		List<NhSAreaDay> nhlst=new ArrayList<NhSAreaDay>();
		
		nh.setItemCode(fxCode);
		if(startTime.toString("YYYY-MM").equals(now.toString("YYYY-MM"))){
			endTime=GraphUtils.getDateString(now.getYear(),now.getMonth(), now.getDay());
		}else{
			endTime=DateUtils.getMonthEnd(startTime.getYear(), startTime.getMonth());
		}
		nh.setStartTime(startTime.toString("YYYY-MM")+"-01");
		nh.setEndTime(endTime);
		nh.setAreaId(areaId);
		nh.setNhType(Constant.NH_TYPE_S);
		Integer dayOfMonth = Integer.parseInt(endTime.split("-")[2]);
		nhlst=nhSAreaDayService.getAreaDayList(nh);
		
		fullLevelName = (fullLevelName==null?"":fullLevelName);
		String title = startTime.toString("YYYY年MM月")+fullLevelName+"用水量统计分析";
		
		model.put("title", title);
		Map<String, Object> newMap = GraphUtils.getDayData(nhlst,dayOfMonth);
		model.put("fullLevelName", fullLevelName);
		model.put("new", newMap);
		model.put("start", start);
		model.put("id", areaId);
		return "nhgl/analyse/water/area/area_count_all";
	}
	
	@RequestMapping("dayAllYear")
	public String dayAllYear(ModelMap model,String start, String fullLevelName, String fxCode, Integer areaId, String isp) throws ParseException {
		NhSAreaHour nh =new NhSAreaHour();
		JDateTime startTime = new JDateTime(start+" 23:59:59");
		String title = startTime.toString("YYYY年MM月DD日")+fullLevelName+"用水量统计分析";
		JDateTime now = new JDateTime(new Date());
		List<NhSAreaHour> nhlst=new ArrayList<NhSAreaHour>();
		List<NhSAreaHour> nhLastlst=new ArrayList<NhSAreaHour>();
		
		nh.setNhType(Constant.NH_TYPE_S);
		nh.setAreaId(areaId);
		nh.setStandardCode(fxCode);
		Integer hour=24;
		if(now.toString("YYYY-MM-DD").equals(startTime.toString("YYYY-MM-DD"))){
			nh.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
			nh.setCollectTime(new Date());
			nhlst=nhSAreaHourService.getDayofAreaWater(nh);
			nh.setCollectDate(DateUtils.truncate(DateUtils.addDay(new Date(), -1), Calendar.DAY_OF_MONTH));
			nh.setCollectTime(DateUtils.addDay(new Date(), -1));
			nhLastlst=nhSAreaHourService.getDayofAreaWater(nh);
			hour=now.getHour();
		}else{
			nh.setCollectDate(DateUtils.truncate(startTime.convertToDate(), Calendar.DAY_OF_MONTH));
			nh.setCollectTime(startTime.convertToDate());
			nhlst=nhSAreaHourService.getDayofAreaWater(nh);
			nh.setCollectDate(DateUtils.truncate(DateUtils.addDay(startTime.convertToDate(), -1), Calendar.DAY_OF_MONTH));
			nh.setCollectTime(DateUtils.addDay(startTime.convertToDate(), -1));
			nhLastlst=nhSAreaHourService.getDayofAreaWater(nh);
		}
		
		Map<String, Object> newMap = GraphUtils.getHourData(nhlst,nhLastlst,hour);
		fullLevelName = (fullLevelName == null ? "" : fullLevelName);
		model.put("title",title);
		model.put("new", newMap);
		model.put("start", start);
		model.put("fullLevelName", fullLevelName);
		model.put("id", areaId);
		return "nhgl/analyse/water/area/area_count_all";
	}
}
