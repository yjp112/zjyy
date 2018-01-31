package com.supconit.nhgl.analyse.water.week.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.common.utils.Constant;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.electric.entities.StatisticalPropDay;
import com.supconit.nhgl.analyse.electric.service.StatisticalPropDayService;
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
 * 周末用能分析
 * 
 * 
 */
@Controller("StaWaterController")
@RequestMapping("nhgl/analyse/water/week")
public class StaController {

	@Autowired
	private NhItemService subService;
	@Autowired
	private NhSAreaDayService nhSAreaDayService;
	@Autowired
	private NhSDeptDayService nhSDeptDayService;
	@Autowired
	private ConfigManagerService configManagerService;
	@Autowired
	private StatisticalPropDayService stService;
	@Autowired
	private NhDeptService nhDeptService;
	@Autowired
	private NhAreaService nhAreaService;
	private NhItem subinfo = new NhItem();
	private Integer num = 0;

	@RequestMapping(value = "index")
	public String index(ModelMap model, String start, String standardCode, Integer number,String areaId,String deptId) {
		List<NhSAreaDay> nhAlst = null;
		List<NhSDeptDay> nhDlst = null;
		NhSAreaDay nhA = new NhSAreaDay();
		NhSDeptDay nhD = new NhSDeptDay();
		JDateTime dateTime = new JDateTime(new Date());
		StatisticalPropDay sp = new StatisticalPropDay();
		
		if(start == null){//当前月份
			start = dateTime.toString("YYYY-MM");
		}
		sp.setStart(start);
		StatisticalPropDay spd = stService.getWeekTime(sp);
		if(spd.getStartTime() != null && !("").equals(spd.getStartTime())){
			nhA.setStartTime(spd.getStartTime().substring(0, 10));
			nhD.setStartTime(spd.getStartTime().substring(0, 10));
		}
		if(spd.getEndTime() != null && !("").equals(spd.getEndTime())){
			if(start.substring(0, 4).equals(dateTime.toString("YYYY"))){
				nhA.setEndTime(dateTime.toString("YYYY-MM-DD"));
				nhD.setEndTime(dateTime.toString("YYYY-MM-DD"));
			}else{
				nhA.setEndTime(spd.getEndTime().substring(0, 10));
				nhD.setEndTime(spd.getEndTime().substring(0, 10));
			}
		}
		
		
		
		nhA.setNhType(Constant.NH_TYPE_S);
		nhD.setNhType(Constant.NH_TYPE_S);
		nhA.setItemCode(standardCode);
		nhD.setItemCode(standardCode);
		nhAlst=nhSAreaDayService.getAreaListCompareLastYear(nhA);
		nhDlst=nhSDeptDayService.getDeptListCompareLastYear(nhD);
		if(number==null){
			number=1;
		}
		
		
		subinfo.setParentCode("0");
		subinfo.setNhType(Constant.NH_TYPE_S);
		List<NhItem> slist = subService.findByCon(subinfo);
		model.put("rootDId", nhDeptService.findRootId());
		model.put("rootAId", nhAreaService.findRootId());
		model.put("start", start);
		model.put("slist", slist);
		model.put("standardCode", standardCode);
		model.put("list",nhDlst );
		model.put("glist", nhAlst);
		model.put("number", number);
		model.put("areaId", areaId);
		model.put("deptId", deptId);
		return "nhgl/analyse/water/week/week_count_tab";
	}

	@RequestMapping("weekCount")
	public String weekCount(ModelMap model,  String deptName, String start, Integer id, String standardCode, String number) throws ParseException{
		num = num + 1;
		deptName = (deptName == null ? "" : deptName);
		JDateTime dateTime = new JDateTime(new Date());
		JDateTime jdtime = new JDateTime(start);
		String title = jdtime.toString("YYYY年MM月") + deptName + "周用水量统计分析";
		model.put("title", title);
		
		StatisticalPropDay sp = new StatisticalPropDay();
		sp.setStart(start);
		StatisticalPropDay spd = stService.getWeekTime(sp);
		String startTime="";
		String endTime="";
		if(spd.getStartTime() != null && !("").equals(spd.getStartTime())){
			startTime=spd.getStartTime().substring(0, 10);
		}
		if(spd.getEndTime() != null && !("").equals(spd.getEndTime())){
			if(start.substring(0, 4).equals(dateTime.toString("YYYY"))){
				endTime=dateTime.toString("YYYY-MM-DD");
			}else{
				endTime=spd.getEndTime().substring(0, 10);
			}
		}
		JDateTime stime = new JDateTime(startTime);
		JDateTime etime = new JDateTime(endTime);
		StringBuffer work = new StringBuffer("[");
		StringBuffer week = new StringBuffer("[");
		StringBuffer weekCount = new StringBuffer("[");
		if(number.equals("1")){
			NhSDeptDay nh=new NhSDeptDay();
			List<NhSDeptDay> nhlst=new ArrayList<NhSDeptDay>();
			NhSDeptDay nhDay=null;
			NhSDeptDay nhDayLast=null;
			
			nh.setDeptId(id);
			nh.setStartTime(startTime);
			nh.setEndTime(endTime);
			nh.setNhType(Constant.NH_TYPE_S);
			nh.setItemCode(standardCode);
			
			nhlst=nhSDeptDayService.getDeptWeekList(nh);
			
			if(nhlst.size() > 0){
				for(NhSDeptDay n : nhlst){
					work.append(n.getWorkTotal()).append(",");
					week.append(n.getWeekendTotal()).append(",");
					weekCount.append("\"" + n.getWeekName() + "\"").append(",");
				}
				model.put("first", nhlst.get(0).getWeekName());
				weekCounts(model, nhlst.get(0).getWeekName() + "", deptName, number,id,standardCode,endTime);
			}else{
				JDateTime date = new JDateTime(start+"-01");
				work.append(0).append(",");
				week.append(0).append(",");
				weekCount.append("\"" + date.getYear() + "年第" + date.getWeekOfYear() + "周\"")
				.append(",");
			}
			
			
			nhDay=nhSDeptDayService.getDeptDayNight(nh);//这个月白天晚上
			//上一年这个月白天晚上GraphUtils.addYear(etime.toString("YYYY-MM-DD"), -1)
			nh.setStartTime(GraphUtils.addYear(stime.toString("YYYY-MM-DD"), -1));
			nh.setEndTime(GraphUtils.addYear(etime.toString("YYYY-MM-DD"), -1));
			nhDayLast=nhSDeptDayService.getDeptDayNight(nh);
			
			if(nhDay==null){
				nhDay=new NhSDeptDay();
				nhDay.setDayDaytimeValue(new BigDecimal(0));
				nhDay.setDayNightValue(new BigDecimal(0));
			}
			if(nhDayLast==null){
				nhDayLast=new NhSDeptDay();
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
			NhSAreaDay nh=new NhSAreaDay();
			List<NhSAreaDay> nhlst=new ArrayList<NhSAreaDay>();
			NhSAreaDay nhDay=null;
			NhSAreaDay nhDayLast=null;
			
			nh.setAreaId(id);
			nh.setStartTime(startTime);
			nh.setEndTime(endTime);
			nh.setNhType(Constant.NH_TYPE_S);
			nh.setItemCode(standardCode);
			
			nhlst=nhSAreaDayService.getAreaWeekList(nh);
			
			if(nhlst.size() > 0){
				for(NhSAreaDay n : nhlst){
					work.append(n.getWorkTotal()).append(",");
					week.append(n.getWeekendTotal()).append(",");
					weekCount.append("\"" + n.getWeekName() + "\"").append(",");
				}
				model.put("first", nhlst.get(0).getWeekName());
				weekCounts(model, nhlst.get(0).getWeekName() + "", deptName, number,id,standardCode,endTime);
			}else{
				JDateTime date = new JDateTime(start+"-01");
				work.append(0).append(",");
				week.append(0).append(",");
				weekCount.append("\"" + date.getYear() + "年第" + date.getWeekOfYear() + "周\"")
				.append(",");
			}
			
			
			nhDay=nhSAreaDayService.getAreaDayNight(nh);//这个月白天晚上
			//上一年这个月白天晚上
			nh.setStartTime(GraphUtils.addYear(stime.toString("YYYY-MM-DD"), -1));
			nh.setEndTime(GraphUtils.addYear(etime.toString("YYYY-MM-DD"), -1));
			nhDayLast=nhSAreaDayService.getAreaDayNight(nh);
			
			
			if(nhDay==null){
				nhDay=new NhSAreaDay();
				nhDay.setDayDaytimeValue(new BigDecimal(0));
				nhDay.setDayNightValue(new BigDecimal(0));
			}
			if(nhDayLast==null){
				nhDayLast=new NhSAreaDay();
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
		
		
		String pieString = week.length() > 1 ? week.toString().substring(0,
				week.toString().length() - 1) : "[";
		String depString = work.length() > 1 ? work.toString().substring(0,
				work.toString().length() - 1) : "[";
		String weekString = weekCount.length() > 1 ? weekCount.toString()
				.substring(0, weekCount.toString().length() - 1) : "[";
		model.put("work", depString + "]");
		model.put("week", pieString + "]");
		model.put("weekList", weekString + "]");
		model.put("id", id);
		model.put("number", number);
		model.put("num", num);
		return "nhgl/analyse/water/week/week_count_all";
	}

	@ResponseBody
	@RequestMapping(value = "dayCount", method = RequestMethod.POST)
	public String dayCount(ModelMap model, String first, String number, String standardCode, Integer id, String isp,String start) {
		num = num + 1;
		JDateTime dateTime = new JDateTime(new Date());
		StatisticalPropDay sp = new StatisticalPropDay();
		sp.setStart(start);
		StatisticalPropDay spd = stService.getWeekTime(sp);
		String endTime="";
		if(spd.getEndTime() != null && !("").equals(spd.getEndTime())){
			if(start.substring(0, 4).equals(dateTime.toString("YYYY"))){
				endTime=dateTime.toString("YYYY-MM-DD");
			}else{
				endTime=spd.getEndTime().substring(0, 10);
			}
		}
		
		
		StringBuffer week = new StringBuffer();
		StringBuilder days = new StringBuilder();
		if("1".equals(number)){
			List<NhSDeptDay> nhlst=new ArrayList<NhSDeptDay>();
			NhSDeptDay nh=new NhSDeptDay();
			
			nh.setDeptId(id);
			nh.setWeekName(first);
			nh.setItemCode(standardCode);
			nh.setEndTime(endTime);
			
			nhlst=nhSDeptDayService.getDeptWeekDays(nh);
			
			for(NhSDeptDay n : nhlst){
				if (n != null && n.getTotalDayValue() != null) {
					week.append(n.getTotalDayValue().setScale(0, RoundingMode.HALF_UP)).append(",");
				}else{
					week.append("0").append(",");
				}
				days.append(n.getDayOfWeekName()).append(",");
			}
		}else{
			List<NhSAreaDay> nhlst=new ArrayList<NhSAreaDay>();
			NhSAreaDay nh=new NhSAreaDay();
			
			nh.setAreaId(id);
			nh.setWeekName(first);
			nh.setItemCode(standardCode);
			nh.setEndTime(endTime);
			
			nhlst=nhSAreaDayService.getAreaWeekDays(nh);
			
			for(NhSAreaDay n : nhlst){
				if (n != null && n.getTotalDayValue() != null) {
					week.append(n.getTotalDayValue().setScale(0, RoundingMode.HALF_UP)).append(",");
				}else{
					week.append("0").append(",");
				}
				days.append(n.getDayOfWeekName()).append(",");
			}
		}
		String pieString = week.length() > 1 ? week.toString().substring(0,
				week.toString().length() - 1) : "";
		String dayList = days.length() > 1 ? days.toString().substring(0,
				days.toString().length() - 1) : "";
		model.put("num", num);
		return pieString+"|"+dayList;
	}

	private void weekCounts(ModelMap model, String first, String deptName, String flag, Integer id, String standardCode,String endTime){
		deptName = deptName == null ? "" : deptName;
		String titles = first + deptName + "用水量统计分析";
		model.put("titles", titles);
		
		StringBuffer week = new StringBuffer();
		StringBuilder days = new StringBuilder();
		if("1".equals(flag)){
			List<NhSDeptDay> nhlst=new ArrayList<NhSDeptDay>();
			NhSDeptDay nh=new NhSDeptDay();
			
			nh.setDeptId(id);
			nh.setWeekName(first);
			nh.setItemCode(standardCode);
			nh.setEndTime(endTime);
			
			nhlst=nhSDeptDayService.getDeptWeekDays(nh);
			
			for(NhSDeptDay n : nhlst){
				if (n != null && n.getTotalDayValue() != null) {
					week.append(n.getTotalDayValue().setScale(0, RoundingMode.HALF_UP)).append(",");
				}else{
					week.append("0").append(",");
				}
				days.append(n.getDayOfWeekName()).append(",");
			}
		}else{
			List<NhSAreaDay> nhlst=new ArrayList<NhSAreaDay>();
			NhSAreaDay nh=new NhSAreaDay();
			
			nh.setAreaId(id);
			nh.setWeekName(first);
			nh.setItemCode(standardCode);
			nh.setEndTime(endTime);
			
			nhlst=nhSAreaDayService.getAreaWeekDays(nh);
			
			for(NhSAreaDay n : nhlst){
				if (n != null && n.getTotalDayValue() != null) {
					week.append(n.getTotalDayValue().setScale(0, RoundingMode.HALF_UP)).append(",");
				}else{
					week.append("0").append(",");
				}
				days.append(n.getDayOfWeekName()).append(",");
			}
		}
		String pieString = week.length() > 1 ? week.toString().substring(0,
				week.toString().length() - 1) : "";
		String dayList = days.length() > 1 ? days.toString().substring(0,
				days.toString().length() - 1) : "";
		model.put("weeks", pieString + "");
		model.put("dayLists", dayList + "");
		model.put("firsts", first);

	}

}
