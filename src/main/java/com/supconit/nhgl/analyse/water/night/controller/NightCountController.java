package com.supconit.nhgl.analyse.water.night.controller;

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
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaDay;
import com.supconit.nhgl.analyse.water.area.service.NhSAreaDayService;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptDay;
import com.supconit.nhgl.analyse.water.dept.service.NhSDeptDayService;
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
@Controller("NightWaterCountController")
@RequestMapping("nhgl/analyse/water/night")
public class NightCountController {
	@Autowired
	private NhItemService subService;
	@Autowired
	private NhSAreaDayService nhSAreaDayService;
	@Autowired
	private NhSDeptDayService nhSDeptDayService;
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
		List<NhSAreaDay> nhAlst = null;
		List<NhSDeptDay> nhDlst = null;
		NhSAreaDay nhA = new NhSAreaDay();
		NhSDeptDay nhD = new NhSDeptDay();
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
		nhA.setNhType(Constant.NH_TYPE_S);
		nhD.setNhType(Constant.NH_TYPE_S);
		nhA.setItemCode(standardCode);
		nhD.setItemCode(standardCode);
		nhAlst=nhSAreaDayService.getAreaListCompareLastYear(nhA);
		nhDlst=nhSDeptDayService.getDeptListCompareLastYear(nhD);
		if(flag==null){
			flag=1;
		}
		subinfo.setParentCode("0");
		subinfo.setNhType(Constant.NH_TYPE_S);
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
		return "nhgl/analyse/water/night/night_count_tab";
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
		String title = jdt.toString("YYYY年MM月")+deptName+"用水量统计分析";
		model.put("title", title);
		JDateTime endTime = new JDateTime(end);
		if("1".equals(flag)){
			NhSDeptDay nhd=new NhSDeptDay();
			NhSDeptDay nhMonth=null;
			NhSDeptDay nhMonthLast=null;
			List<NhSDeptDay> nhlst=new ArrayList<NhSDeptDay>();
			
			nhd.setDeptId(id);
			nhd.setStartTime(start);
			nhd.setEndTime(end);
			nhd.setNhType(Constant.NH_TYPE_S);
			nhd.setItemCode(standardCode);
			nhlst=nhSDeptDayService.getDeptDayNightList(nhd);
			Map<String,Object> newMap=GraphUtils.getDayData4DayNight(nhlst,jdt.getMonthLength());
			model.put("new", newMap);
			
			nhMonth=nhSDeptDayService.getDeptDayNight(nhd);//这个月白天晚上
			//上一年这个月白天晚上
			nhd.setStartTime(GraphUtils.getDateString(endTime.getYear()-1,endTime.getMonth(),1));
			nhd.setEndTime(GraphUtils.addYear(endTime.toString("YYYY-MM-DD"), -1));
			nhMonthLast=nhSDeptDayService.getDeptDayNight(nhd);
			
			if(nhMonth==null){
				nhMonth=new NhSDeptDay();
				nhMonth.setDayDaytimeValue(new BigDecimal(0));
				nhMonth.setDayNightValue(new BigDecimal(0));
			}
			if(nhMonthLast==null){
				nhMonthLast=new NhSDeptDay();
				nhMonthLast.setDayDaytimeValue(new BigDecimal(0));
				nhMonthLast.setDayNightValue(new BigDecimal(0));
			}
			
			BigDecimal tbzlD=nhMonth.getDayDaytimeValue().subtract(nhMonthLast.getDayDaytimeValue());
			BigDecimal tbzlN=nhMonth.getDayNightValue().subtract(nhMonthLast.getDayNightValue());
			BigDecimal perD = new BigDecimal(0);
			BigDecimal perN = new BigDecimal(0);
			if(nhMonthLast.getDayDaytimeValue().compareTo(new BigDecimal(0))==1){
				perD = BigDecimalUtil.divide4(tbzlD,nhMonthLast.getDayDaytimeValue());
			}else if(tbzlD.compareTo(new BigDecimal(0))==1){
				perD=new BigDecimal(1);
			}else if(tbzlD.compareTo(new BigDecimal(0))==0){
				perD=new BigDecimal(0);
			}
			if(nhMonthLast.getDayNightValue().compareTo(new BigDecimal(0))==1){
				perN=BigDecimalUtil.divide4(tbzlN,nhMonthLast.getDayNightValue());
			}else if(tbzlN.compareTo(new BigDecimal(0))==1){
				perN=new BigDecimal(1);
			}else if(tbzlN.compareTo(new BigDecimal(0))==0){
				perN=new BigDecimal(0);
			}
			nhMonth.setPercentD(perD.multiply(new BigDecimal(100)));
			nhMonth.setPercentN(perN.multiply(new BigDecimal(100)));
			nhMonth.setDayOfMonthKey(start);
			String dayTimeCode=HolidayConstans.ELECTRIC_DAY_CODE;
			String nightCode=HolidayConstans.ELECTRIC_NIGHT_CODE;
			ConfigManager configDayTime=configManagerService.findByCode(dayTimeCode);
			ConfigManager configNight=configManagerService.findByCode(nightCode);
			if(configDayTime.getFlag()!=1){
				nhMonth.setDayDaytimeValue(new BigDecimal(0));
				nhMonth.setPercentD(new BigDecimal(0));
			}
			if(configNight.getFlag()!=1){
				nhMonth.setDayNightValue(new BigDecimal(0));
				nhMonth.setPercentN(new BigDecimal(0));
			}
			model.put("nhMonth", nhMonth);
		}else{
			NhSAreaDay nhd=new NhSAreaDay();
			NhSAreaDay nhMonth=null;
			NhSAreaDay nhMonthLast=null;
			List<NhSAreaDay> nhlst=new ArrayList<NhSAreaDay>();
			
			nhd.setAreaId(id);
			nhd.setStartTime(start);
			nhd.setEndTime(end);
			nhd.setNhType(Constant.NH_TYPE_S);
			nhd.setItemCode(standardCode);
			nhlst=nhSAreaDayService.getAreaDayNightList(nhd);
			Map<String,Object> newMap=GraphUtils.getDayData4DayNight(nhlst,jdt.getMonthLength());
			model.put("new", newMap);
			
			nhMonth=nhSAreaDayService.getAreaDayNight(nhd);//这个月白天晚上
			//上一年这个月白天晚上
			nhd.setStartTime(GraphUtils.getDateString(endTime.getYear()-1,endTime.getMonth(),1));
			nhd.setEndTime(GraphUtils.addYear(endTime.toString("YYYY-MM-DD"), -1));
			nhMonthLast=nhSAreaDayService.getAreaDayNight(nhd);
			
			if(nhMonth==null){
				nhMonth=new NhSAreaDay();
				nhMonth.setDayDaytimeValue(new BigDecimal(0));
				nhMonth.setDayNightValue(new BigDecimal(0));
			}
			if(nhMonthLast==null){
				nhMonthLast=new NhSAreaDay();
				nhMonthLast.setDayDaytimeValue(new BigDecimal(0));
				nhMonthLast.setDayNightValue(new BigDecimal(0));
			}
			
			BigDecimal tbzlD=nhMonth.getDayDaytimeValue().subtract(nhMonthLast.getDayDaytimeValue());
			BigDecimal tbzlN=nhMonth.getDayNightValue().subtract(nhMonthLast.getDayNightValue());
			BigDecimal perD = new BigDecimal(0);
			BigDecimal perN = new BigDecimal(0);
			if(nhMonthLast.getDayDaytimeValue().compareTo(new BigDecimal(0))==1){
				perD = BigDecimalUtil.divide4(tbzlD,nhMonthLast.getDayDaytimeValue());
			}else if(tbzlD.compareTo(new BigDecimal(0))==1){
				perD=new BigDecimal(1);
			}else if(tbzlD.compareTo(new BigDecimal(0))==0){
				perD=new BigDecimal(0);
			}
			if(nhMonthLast.getDayNightValue().compareTo(new BigDecimal(0))==1){
				perN=BigDecimalUtil.divide4(tbzlN,nhMonthLast.getDayNightValue());
			}else if(tbzlN.compareTo(new BigDecimal(0))==1){
				perN=new BigDecimal(1);
			}else if(tbzlN.compareTo(new BigDecimal(0))==0){
				perN=new BigDecimal(0);
			}
			nhMonth.setPercentD(perD.multiply(new BigDecimal(100)));
			nhMonth.setPercentN(perN.multiply(new BigDecimal(100)));
			nhMonth.setDayOfMonthKey(start);
			String dayTimeCode=HolidayConstans.ELECTRIC_DAY_CODE;
			String nightCode=HolidayConstans.ELECTRIC_NIGHT_CODE;
			ConfigManager configDayTime=configManagerService.findByCode(dayTimeCode);
			ConfigManager configNight=configManagerService.findByCode(nightCode);
			if(configDayTime.getFlag()!=1){
				nhMonth.setDayDaytimeValue(new BigDecimal(0));
				nhMonth.setPercentD(new BigDecimal(0));
			}
			if(configNight.getFlag()!=1){
				nhMonth.setDayNightValue(new BigDecimal(0));
				nhMonth.setPercentN(new BigDecimal(0));
			}
			model.put("nhMonth", nhMonth);
		}
		model.put("start", jdt.toString("YYYY-MM"));
		model.put("num", num);
		return "nhgl/analyse/water/night/night_count_all";
	}

}
