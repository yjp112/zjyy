package com.supconit.nhgl.analyse.energy.night.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
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

/**
 * 能耗分析，夜间用能趋势分析
 *
 */
@Controller("NightEnergyCountController")
@RequestMapping("nhgl/analyse/energy/night")
public class NightCountController {
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
	private NhItem subinfo = new NhItem();
	private Integer num = 0;
	
	@RequestMapping("index")
	public String index(ModelMap model,String start,String standardCode,Integer flag,String code,String areaId,String deptId)
	{
		List<NhENAreaDay> nhAlst = null;
		List<NhENDeptDay> nhDlst = null;
		NhENAreaDay nhA = new NhENAreaDay();
		NhENDeptDay nhD = new NhENDeptDay();
		JDateTime dateTime = new JDateTime(new Date());
		String end="";
		if(start==null ||start.equals(dateTime.toString("YYYY-MM"))){//当前月份
			start = dateTime.toString("YYYY-MM")+"-01";
			end =dateTime.toString("YYYY-MM-DD");
		}else{
			start=start+"-01";
			JDateTime jdt = new JDateTime(start);
			end=GraphUtils.getDateString(jdt.getYear(), jdt.getMonth(), jdt.getMonthLength());
		}
		
		nhA.setEndTime(end);
		nhD.setEndTime(end);
		nhA.setStartTime(start);
		nhD.setStartTime(start);
		nhA.setNhType(Constant.NH_TYPE_EN);
		nhD.setNhType(Constant.NH_TYPE_EN);
		nhA.setItemCode(standardCode);
		nhD.setItemCode(standardCode);
		nhAlst=nhENAreaDayService.getAreaListCompareLastYear(nhA);
		nhDlst=nhENDeptDayService.getDeptListCompareLastYear(nhD);
		if(flag==null){
			flag=1;
		}
		subinfo.setParentCode("0");
		subinfo.setNhType(Constant.NH_TYPE_EN);
		List<NhItem> slist = subService.findByCon(subinfo);
		model.put("rootDId", nhDeptService.findRootId());
		model.put("rootAId", nhAreaService.findRootId());
		model.put("start", start.substring(0, 7));
		model.put("standardCode", standardCode);
		model.put("slist",slist);
		model.put("list",nhDlst);
		model.put("glist",nhAlst);
		model.put("flag", flag);
		model.put("code", code);
		model.put("areaId", areaId);
		model.put("deptId", deptId);
		return "nhgl/analyse/energy/night/night_count_tab";
	}
	
	@RequestMapping("allYear")
	public String allYearData(ModelMap model,String start, String deptName, String standardCode, Integer id, String flag) throws ParseException
	{
		JDateTime dateTime = new JDateTime(new Date());
		JDateTime jdt = new JDateTime(start);
		String end="";
		if(start.equals(dateTime.toString("YYYY-MM"))){//当前月份
			start = dateTime.toString("YYYY-MM")+"-01";
			end=dateTime.toString("YYYY-MM-DD");
		}else{
			end = GraphUtils.getDateString(jdt.getYear(), jdt.getMonth(), jdt.getMonthLength());
			start = GraphUtils.getDateString(jdt.getYear(), jdt.getMonth(), 1);
		}
		deptName = (deptName==null?"":deptName);
		String title = jdt.toString("YYYY年MM月")+deptName+"用能量统计分析";
		model.put("title", title);
		JDateTime endTime = new JDateTime(end);
		if("1".equals(flag)){
			NhENDeptDay nhd=new NhENDeptDay();
			NhENDeptDay nhDay=null;
			NhENDeptDay nhDayLast=null;
			List<NhENDeptDay> nhlst=new ArrayList<NhENDeptDay>();
			
			nhd.setDeptId(id);
			nhd.setStartTime(start);
			nhd.setEndTime(end);
			nhd.setNhType(Constant.NH_TYPE_EN);
			nhd.setItemCode(standardCode);
			nhlst=nhENDeptDayService.getDeptDayNightList(nhd);
			Map<String,Object> newMap=GraphUtils.getDayData4DayNight(nhlst,jdt.getMonthLength());
			model.put("new", newMap);
			
			nhDay=nhENDeptDayService.getDeptDayNight(nhd);//这个月白天晚上
			//上一年这个月白天晚上
			nhd.setStartTime(GraphUtils.getDateString(endTime.getYear()-1,endTime.getMonth(),1));
			nhd.setEndTime(GraphUtils.addYear(endTime.toString("YYYY-MM-DD"), -1));
			nhDayLast=nhENDeptDayService.getDeptDayNight(nhd);
			
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
			nhDay.setDayOfMonthKey(start);
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
			NhENAreaDay nhDay=null;
			NhENAreaDay nhDayLast=null;
			List<NhENAreaDay> nhlst=new ArrayList<NhENAreaDay>();
			
			nhd.setAreaId(id);
			nhd.setStartTime(start);
			nhd.setEndTime(end);
			nhd.setNhType(Constant.NH_TYPE_EN);
			nhd.setItemCode(standardCode);
			nhlst=nhENAreaDayService.getAreaDayNightList(nhd);
			Map<String,Object> newMap=GraphUtils.getDayData4DayNight(nhlst,jdt.getMonthLength());
			model.put("new", newMap);
			
			nhDay=nhENAreaDayService.getAreaDayNight(nhd);//这个月白天晚上
			//上一年这个月白天晚上
			nhd.setStartTime(GraphUtils.getDateString(endTime.getYear()-1,endTime.getMonth(),1));
			nhd.setEndTime(GraphUtils.addYear(endTime.toString("YYYY-MM-DD"), -1));
			nhDayLast=nhENAreaDayService.getAreaDayNight(nhd);
			
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
			nhDay.setDayOfMonthKey(start);
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
		model.put("start", jdt.toString("YYYY-MM"));
		model.put("num", num);
		return "nhgl/analyse/energy/night/night_count_all";
	}
}
