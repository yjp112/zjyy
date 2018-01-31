package com.supconit.nhgl.analyse.energy.holiday.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.common.utils.Constant;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaDay;
import com.supconit.nhgl.analyse.energy.area.service.NhENAreaDayService;
import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptDay;
import com.supconit.nhgl.analyse.energy.dept.service.NhENDeptDayService;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.GraphUtils;
import com.supconit.nhgl.utils.HolidayConstans;
import com.supconit.nhgl.utils.StatisticEnergyUtils;

import jodd.datetime.JDateTime;

/**
 * 能耗分析，法定节假日用能趋势分析
 *
 */
@Controller("HoildayEnergyCountController")
@RequestMapping("nhgl/analyse/energy/hoilday")
public class HoildayCountController {
	@Autowired
	private NhItemService subService;
	@Autowired
	private NhENAreaDayService nhENAreaDayService;
	@Autowired
	private NhENDeptDayService nhENDeptDayService;
	@Autowired
	private ConfigManagerService configManagerService;
	@Autowired
	private NhDeptService nhDeptService;
	@Autowired
	private NhAreaService nhAreaService;
	private NhItem subInfo = new NhItem();
	private Integer num = 0;
	
	@RequestMapping("index")
	public String index(ModelMap model,Integer year,String flag,String standardCode,String code,String areaId,String deptId)
	{
		subInfo.setParentCode("0");
		subInfo.setNhType(Constant.NH_TYPE_EN);
		List<NhItem> slist = subService.findByCon(subInfo);
		List<NhENAreaDay> nhAlst = null;
		List<NhENDeptDay> nhDlst = null;
		NhENAreaDay nhA = new NhENAreaDay();
		NhENDeptDay nhD = new NhENDeptDay();
		JDateTime dateTime = new JDateTime(new Date());
		Integer yearParam = dateTime.getYear();
		String endTime="";
		if(year==yearParam || year==null){//今年年份
			endTime=dateTime.toString("YYYY-MM-DD");
			year=yearParam;
		}else{
			endTime=year+"-12-31";
		}
		nhA.setEndTime(endTime);
		nhD.setEndTime(endTime);
		nhA.setItemCode(standardCode);
		nhA.setStartTime(year+"-01-01");
		nhA.setNhType(Constant.NH_TYPE_EN);
		
		nhD.setItemCode(standardCode);
		nhD.setStartTime(year+"-01-01");
		nhD.setNhType(Constant.NH_TYPE_EN);
		
		nhAlst=nhENAreaDayService.getAreaHolidayList(nhA);
		nhDlst=nhENDeptDayService.getDeptHolidayList(nhD);
		if(flag==null || flag.equals("")){
			flag="1";
		}
		model.put("rootDId", nhDeptService.findRootId());
		model.put("rootAId", nhAreaService.findRootId());
		model.put("year", year);
		model.put("slist",slist);
		model.put("standardCode", standardCode);
		model.put("list",nhDlst);
		model.put("glist",nhAlst);
		model.put("flag", flag);
		model.put("code", code);
		model.put("areaId", areaId);
		model.put("deptId", deptId);
		return "nhgl/analyse/energy/holiday/holiday_count_tab";
	}
	
	@RequestMapping("allYear")
	public String allYearData(ModelMap model, String deptName, Integer year, Integer id, String isp, String flag, String standardCode) throws ParseException, UnsupportedEncodingException
	{
		deptName = (deptName==null?"":deptName);
		String title = year+"年"+deptName+"法定节假日用能量统计分析";
		String sTime=year+"年";
		Map<String,Object> holidayMap = null;
		JDateTime dateTime = new JDateTime(new Date());
		String endTime="";
		
		if(year==dateTime.getYear()){
			endTime=dateTime.toString("YYYY-MM-DD");
		}else{
			endTime=year+"-12-31";
		}
		if(flag.equals("1")){
			NhENDeptDay nhd=new NhENDeptDay();
			List<NhENDeptDay> nhlst=null;
			
			nhd.setDeptId(id);
			nhd.setItemCode(standardCode);
			nhd.setStartTime(year+"-01-01");
			nhd.setEndTime(endTime);
			
			nhlst=nhENDeptDayService.getDeptHolidays(nhd);
			holidayMap = StatisticEnergyUtils.getDeptHolidayMap(nhlst);
			model.put("hlist", nhlst);
			NhENDeptDay nhDay=null;
			NhENDeptDay nhDayLast=null;
			
			nhDay=nhENDeptDayService.getDeptHolidayDayNight(nhd);//year年白天晚上
			
			nhd.setStartTime(year-1+"-01-01");
			nhd.setEndTime(GraphUtils.addYear(endTime, -1));
			nhDayLast=nhENDeptDayService.getDeptHolidayDayNight(nhd);
			
			if(nhDay==null){
				nhDay=new NhENDeptDay();
				nhDay.setDayDaytimeValue(new BigDecimal(0));
				nhDay.setDayNightValue(new BigDecimal(0));
			}
			if(nhDayLast==null){
				nhDayLast=new NhENDeptDay();
				nhDayLast.setDayDaytimeValue(new BigDecimal(0));
				nhDayLast.setDayNightValue(new BigDecimal(0));
			}
			
			BigDecimal tbzlD=nhDay.getDayDaytimeValue().subtract(nhDayLast.getDayDaytimeValue());
			BigDecimal tbzlN=nhDay.getDayNightValue().subtract(nhDayLast.getDayNightValue());
			BigDecimal perD = new BigDecimal(0);
			BigDecimal perN = new BigDecimal(0);
			if(nhDayLast.getDayDaytimeValue().compareTo(new BigDecimal(0))==1){
				perD = BigDecimalUtil.divide4(tbzlD,nhDayLast.getDayDaytimeValue());
			}else if(tbzlD.compareTo(new BigDecimal(0))==1){
				perD=new BigDecimal(1);
			}else if(tbzlD.compareTo(new BigDecimal(0))==0){
				perD=new BigDecimal(0);
			}
			if(nhDayLast.getDayNightValue().compareTo(new BigDecimal(0))==1){
				perN=BigDecimalUtil.divide4(tbzlN,nhDayLast.getDayNightValue());
			}else if(tbzlN.compareTo(new BigDecimal(0))==1){
				perN=new BigDecimal(1);
			}else if(tbzlN.compareTo(new BigDecimal(0))==0){
				perN=new BigDecimal(0);
			}
			nhDay.setPercentD(perD.multiply(new BigDecimal(100)));
			nhDay.setPercentN(perN.multiply(new BigDecimal(100)));
			nhDay.setDayOfMonthKey(year+"年");
			String dayTimeCode=HolidayConstans.ELECTRIC_DAY_CODE;
			String nightCode=HolidayConstans.ELECTRIC_NIGHT_CODE;
			ConfigManager configDayTime=configManagerService.findByCode(dayTimeCode);
			ConfigManager configNight=configManagerService.findByCode(nightCode);
			if(configDayTime.getFlag()!=1){
				nhDay.setDayDaytimeValue(new BigDecimal(0));
				nhDay.setPercentD(new BigDecimal(0));
			}
			if(configNight.getFlag()!=1){
				nhDay.setDayNightValue(new BigDecimal(0));
				nhDay.setPercentN(new BigDecimal(0));
			}
			model.put("nhDay", nhDay);
		}else{
			NhENAreaDay nhd=new NhENAreaDay();
			List<NhENAreaDay> nhlst=null;
			
			nhd.setAreaId(id);
			nhd.setItemCode(standardCode);
			nhd.setStartTime(year+"-01-01");
			nhd.setEndTime(endTime);
			
			nhlst=nhENAreaDayService.getAreaHolidays(nhd);
			holidayMap = StatisticEnergyUtils.getAreaHolidayMap(nhlst);
			model.put("hlist", nhlst);
			NhENAreaDay nhDay=null;
			NhENAreaDay nhDayLast=null;
			
			nhDay=nhENAreaDayService.getAreaHolidayDayNight(nhd);//year年白天晚上
			
			nhd.setStartTime(year-1+"-01-01");
			nhd.setEndTime(GraphUtils.addYear(endTime, -1));
			nhDayLast=nhENAreaDayService.getAreaHolidayDayNight(nhd);
			
			if(nhDay==null){
				nhDay=new NhENAreaDay();
				nhDay.setDayDaytimeValue(new BigDecimal(0));
				nhDay.setDayNightValue(new BigDecimal(0));
			}
			if(nhDayLast==null){
				nhDayLast=new NhENAreaDay();
				nhDayLast.setDayDaytimeValue(new BigDecimal(0));
				nhDayLast.setDayNightValue(new BigDecimal(0));
			}
			
			BigDecimal tbzlD=nhDay.getDayDaytimeValue().subtract(nhDayLast.getDayDaytimeValue());
			BigDecimal tbzlN=nhDay.getDayNightValue().subtract(nhDayLast.getDayNightValue());
			BigDecimal perD = new BigDecimal(0);
			BigDecimal perN = new BigDecimal(0);
			if(nhDayLast.getDayDaytimeValue().compareTo(new BigDecimal(0))==1){
				perD = BigDecimalUtil.divide4(tbzlD,nhDayLast.getDayDaytimeValue());
			}else if(tbzlD.compareTo(new BigDecimal(0))==1){
				perD=new BigDecimal(1);
			}else if(tbzlD.compareTo(new BigDecimal(0))==0){
				perD=new BigDecimal(0);
			}
			if(nhDayLast.getDayNightValue().compareTo(new BigDecimal(0))==1){
				perN=BigDecimalUtil.divide4(tbzlN,nhDayLast.getDayNightValue());
			}else if(tbzlN.compareTo(new BigDecimal(0))==1){
				perN=new BigDecimal(1);
			}else if(tbzlN.compareTo(new BigDecimal(0))==0){
				perN=new BigDecimal(0);
			}
			nhDay.setPercentD(perD.multiply(new BigDecimal(100)));
			nhDay.setPercentN(perN.multiply(new BigDecimal(100)));
			nhDay.setDayOfMonthKey(year+"年");
			String dayTimeCode=HolidayConstans.ELECTRIC_DAY_CODE;
			String nightCode=HolidayConstans.ELECTRIC_NIGHT_CODE;
			ConfigManager configDayTime=configManagerService.findByCode(dayTimeCode);
			ConfigManager configNight=configManagerService.findByCode(nightCode);
			if(configDayTime.getFlag()!=1){
				nhDay.setDayDaytimeValue(new BigDecimal(0));
				nhDay.setPercentD(new BigDecimal(0));
			}
			if(configNight.getFlag()!=1){
				nhDay.setDayNightValue(new BigDecimal(0));
				nhDay.setPercentN(new BigDecimal(0));
			}
			model.put("nhDay", nhDay);
		}
		
		model.put("sTime", sTime);
		num = num + 1;
		model.put("title", title);
		model.put("holidayMap", holidayMap);
		model.put("id", id);
		model.put("num", num);
		return "nhgl/analyse/energy/holiday/holiday_count_all";
	}
	
}
