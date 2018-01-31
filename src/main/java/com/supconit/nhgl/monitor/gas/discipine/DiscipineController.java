package com.supconit.nhgl.monitor.gas.discipine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DateUtils;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaHour;
import com.supconit.nhgl.analyse.gas.area.service.NhQAreaHourService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.GraphUtils;
import com.supconit.nhgl.utils.StatisticElectricUtils;

@Controller("DiscipineGasController")
@RequestMapping("nhgl/monitor/gas/discipine")
public class DiscipineController {
	@Autowired
	private ConfigManagerService configManagerService;
	@Autowired
	private NhQAreaHourService nhQAreaHourService;
	private Integer num = 0;

	@RequestMapping(value = "index")
	public String index(ModelMap model, String flag) {
		NhQAreaHour nh = new NhQAreaHour();
		List<NhQAreaHour> nhList = null;
		List<NhQAreaHour> nhLastList = null;
		JDateTime date = new JDateTime(new Date());
		nh.setNhType(Constant.NH_TYPE_Q);
		nh.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		nh.setCollectTime(new Date());
		String sTime=date.toString("YYYY-MM-DD");
		nhList=nhQAreaHourService.getAllSubGas(nh);
		nh.setCollectDate(DateUtils.truncate(DateUtils.addDay(new Date(), -1), Calendar.DAY_OF_MONTH));
		nh.setCollectTime(DateUtils.addDay(new Date(), -1));
		nhLastList = nhQAreaHourService.getAllSubGas(nh);
		List<NhQAreaHour> sublst = StatisticElectricUtils.getQSubList(nhList, nhLastList);
		
		model.put("subTree", sublst);
		model.put("subName", "分项");
		model.put("sTime", sTime);
		return "nhgl/monitor/gas/discipine/sub_count_tab";
	}

	@RequestMapping(value="toTab",method=RequestMethod.GET)
	public String toTab(ModelMap model,String standardCode, String deptName, String eleTotal,String isParent) {
		num = num + 1;//画图区域的div id为div后跟着num
		NhQAreaHour em = new NhQAreaHour();
		JDateTime jdt = new JDateTime(Calendar.getInstance().getTime());
		Integer hour = jdt.getHour();
		String deptNames = (deptName == null ? "" : deptName);
		model.put("title", jdt.toString("YYYY年MM月DD日")+deptNames + "用蒸汽量趋势分析");
		em.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		em.setCollectTime(new Date());
		em.setNhType(Constant.NH_TYPE_Q);
		em.setStandardCode(standardCode);
		//获取当天各分项耗蒸汽量
		List<NhQAreaHour> emlist = nhQAreaHourService.getDayofAreaGas(em);
		NhQAreaHour gasD=new NhQAreaHour();
		NhQAreaHour gasN=new NhQAreaHour();
		gasD.setNhType(Constant.NH_TYPE_Q);
		gasD.setStandardCode(standardCode);
		
		gasN.setNhType(Constant.NH_TYPE_Q);
		gasN.setStandardCode(standardCode);
		
		NhQAreaHour dayTimeEle=null;
		NhQAreaHour nightEle=null;
		NhQAreaHour nightEles=null;
		BigDecimal total = new BigDecimal(0);
		BigDecimal totalL = new BigDecimal(0);
		ConfigManager configDayTime=configManagerService.getDayTimeConfig();
		ConfigManager configNight=configManagerService.getNightConfig();
		if(configDayTime.getFlag()==1){
			String dayTimeMin=configDayTime.getDayTimeMin();
			String dayTimeMax=configDayTime.getDayTimeMax();
			JDateTime JdayTimeMin=new JDateTime(jdt.toString("YYYY-MM-DD")+" "+dayTimeMin+":00");
			JDateTime JdayTimeMax=new JDateTime(jdt.toString("YYYY-MM-DD")+" "+dayTimeMax+":00");
			if(jdt.compareTo(JdayTimeMin)==1 && jdt.compareTo(JdayTimeMax)==-1){
				gasD.setLastDay(JdayTimeMin.convertToDate());
				gasD.setNow(jdt.convertToDate());
			}else if(jdt.compareTo(JdayTimeMax)==1){
				gasD.setLastDay(JdayTimeMin.convertToDate());
				gasD.setNow(JdayTimeMax.convertToDate());
			}
			dayTimeEle=nhQAreaHourService.getTwoDayofAreaGas(gasD);
		}
		if(configNight.getFlag()==1){
			String nightMin=configNight.getNightMin();
			String nightMax=configNight.getNightMax();
			JDateTime lindian=new JDateTime(jdt.toString("YYYY-MM-DD")+" 00:00:00");
			JDateTime JnightMin=new JDateTime(jdt.toString("YYYY-MM-DD")+" "+nightMin+":00");
			JDateTime JnightMax=new JDateTime(jdt.toString("YYYY-MM-DD")+" "+nightMax+":00");
			if(jdt.compareTo(JnightMax)==1 && jdt.compareTo(JnightMin)==-1){
				gasN.setLastDay(lindian.convertToDate());
				gasN.setNow(JnightMax.convertToDate());
			}else if(jdt.compareTo(JnightMax)==-1){
				gasN.setLastDay(lindian.convertToDate());
				gasN.setNow(jdt.convertToDate());
			}else if(jdt.compareTo(JnightMin)==1){
				gasN.setLastDay(lindian.convertToDate());
				gasN.setNow(JnightMax.convertToDate());
				nightEles=nhQAreaHourService.getTwoDayofAreaGas(gasN);
				if(nightEles != null){
					total = nightEles.getTotal();
					totalL=nightEles.getLastTotal();
				}
				gasN.setLastDay(JnightMin.convertToDate());
				gasN.setNow(jdt.convertToDate());
			}
			nightEle=nhQAreaHourService.getTwoDayofAreaGas(gasN);
			if(nightEle == null){
				nightEle = new NhQAreaHour();
				nightEle.setTotal(new BigDecimal(0));
				nightEle.setLastTotal(new BigDecimal(0));
			}
			nightEle.setTotal(nightEle.getTotal().add(total));
			nightEle.setLastTotal(nightEle.getLastTotal().add(totalL));
		}

		
		if(dayTimeEle==null){
			dayTimeEle=new NhQAreaHour();
			dayTimeEle.setTotal(new BigDecimal(0));		
			dayTimeEle.setLastTotal(new BigDecimal(0));	
			
		}
		if(nightEle==null){
			nightEle=new NhQAreaHour();
			nightEle.setTotal(new BigDecimal(0));
			nightEle.setLastTotal(new BigDecimal(0));
		}
		dayTimeEle.setStartTime(jdt.toString("YYYY-MM"));
		BigDecimal tbzlD = new BigDecimal(0);
		BigDecimal tbzlN = new BigDecimal(0);
		tbzlD = dayTimeEle.getTotal().subtract(dayTimeEle.getLastTotal());
		tbzlN  = nightEle.getTotal().subtract(nightEle.getLastTotal());
		String str = "0.00";
		if(configDayTime.getFlag()==1){
			if(dayTimeEle.getLastTotal().compareTo(new BigDecimal(0))==1){
				BigDecimal pre = BigDecimalUtil.divide4((tbzlD).multiply(new BigDecimal(100)),dayTimeEle.getLastTotal());
				str = new DecimalFormat("0.00").format(pre);
			}else if(dayTimeEle.getTotal().compareTo(new BigDecimal(0))==1){
				str = new DecimalFormat("0.00").format(new BigDecimal(100));
			}else{
				str = new DecimalFormat("0.00").format(0);
			}
			dayTimeEle.setPercent(Float.valueOf(str));
		}
		if(configNight.getFlag()==1){
			if(nightEle.getLastTotal().compareTo(new BigDecimal(0))==1){
				BigDecimal pre = BigDecimalUtil.divide4((tbzlN).multiply(new BigDecimal(100)),nightEle.getLastTotal());
				str = new DecimalFormat("0.00").format(pre);
			}else if(nightEle.getTotal().compareTo(new BigDecimal(0))==1){
				str = new DecimalFormat("0.00").format(new BigDecimal(100));
			}else{
				str = new DecimalFormat("0.00").format(0);
			}
			nightEle.setPercent(Float.valueOf(str));
		}
		
		Map<String, Object> newMap = GraphUtils.getHourData(emlist,hour);
		model.put("newlist", newMap);
		model.put("emlist", emlist);
		model.put("num", num);
		model.put("subName", deptNames);
		model.put("nightEle", nightEle);
		model.put("dayTimeEle", dayTimeEle);
		return "nhgl/monitor/gas/discipine/sub_count_all";
	}

}
