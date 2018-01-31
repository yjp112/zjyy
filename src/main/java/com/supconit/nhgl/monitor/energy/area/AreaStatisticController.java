package com.supconit.nhgl.monitor.energy.area;

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

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DateUtils;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaHour;
import com.supconit.nhgl.analyse.energy.area.service.NhENAreaHourService;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.GraphUtils;
import com.supconit.nhgl.utils.StatisticEnergyUtils;

@Controller("AreaEnergyStatisticController")
@RequestMapping("nhgl/monitor/energy/area")
public class AreaStatisticController {
	@Autowired
	private NhAreaService nhAreaService;
	@Autowired
	private NhItemService subService;
	@Autowired
	private NhENAreaHourService nhENAreaHourService;
	@Autowired
	private ConfigManagerService configManagerService;
	private NhItem subinfo = new NhItem();
	private Integer num = 0;

	@RequestMapping("index")
	public String index(ModelMap model, String flag, String subCode,String areaId) {
		NhENAreaHour nh=new NhENAreaHour();
		List<NhENAreaHour> nhList=null;
		List<NhENAreaHour> nhLastList=null;
		nh.setNhType(Constant.NH_TYPE_EN);
		nh.setStandardCode(subCode);
		nh.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		nh.setCollectTime(new Date());
		nhList=nhENAreaHourService.getAllAreaEnergy(nh);
		nh.setCollectDate(DateUtils.truncate(DateUtils.addDay(new Date(), -1), Calendar.DAY_OF_MONTH));
		nh.setCollectTime(DateUtils.addDay(new Date(), -1));
		nhLastList = nhENAreaHourService.getAllAreaEnergy(nh);
 		List<NhENAreaHour> sublst = StatisticEnergyUtils.getENAreaSubList(nhList, nhLastList);
		subinfo.setParentCode("0");
		subinfo.setNhType(Constant.NH_TYPE_EN);
		List<NhItem> slist = subService.findByCon(subinfo);
		JDateTime jdt = new JDateTime(Calendar.getInstance().getTime());
		String sTime=jdt.toString("YYYY-MM-DD");
		model.put("rootId", nhAreaService.findRootId());
		model.put("sTime", sTime);
		model.put("fullLevelName", "区域");
		model.put("slist", slist);
		model.put("list", sublst);
		model.put("subCode", subCode);
		model.put("areaId", areaId);
		return "nhgl/monitor/energy/area/area_count_tab";
	}

	@RequestMapping("allEnergy")
	public String allYearData(ModelMap model, String fullLevelName, String isp,
			Integer areaId, String subCode, String eleTotal, String dateTime) {
		num = num + 1;
		NhENAreaHour nh=new NhENAreaHour();
		JDateTime jdt = new JDateTime(Calendar.getInstance().getTime());
		Integer hour = jdt.getHour();
		String fullLevelNames = (fullLevelName == null ? "" : fullLevelName);
		model.put("title",jdt.toString("YYYY年MM月DD日")  + fullLevelNames + "用能量趋势分析");
		
		nh.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		nh.setCollectTime(new Date());
		nh.setNhType(Constant.NH_TYPE_EN);
		nh.setStandardCode(subCode);
		nh.setAreaId(areaId);
		
		List<NhENAreaHour> emlist = nhENAreaHourService.getDayofAreaEnergy(nh);
		NhENAreaHour energyD=new NhENAreaHour();
		NhENAreaHour energyN=new NhENAreaHour();
		energyD.setNhType(Constant.NH_TYPE_EN);
		energyD.setStandardCode(subCode);
		energyD.setAreaId(areaId);
		
		energyN.setNhType(Constant.NH_TYPE_EN);
		energyN.setStandardCode(subCode);
		energyN.setAreaId(areaId);
		
		NhENAreaHour dayTimeEle=null;
		NhENAreaHour nightEle=null;
		NhENAreaHour nightEles=null;
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
				energyD.setLastDay(JdayTimeMin.convertToDate());
				energyD.setNow(jdt.convertToDate());
			}else if(jdt.compareTo(JdayTimeMax)==1){
				energyD.setLastDay(JdayTimeMin.convertToDate());
				energyD.setNow(JdayTimeMax.convertToDate());
			}
			dayTimeEle=nhENAreaHourService.getTwoDayofAreaEnergy(energyD);
		}
		if(configNight.getFlag()==1){
			String nightMin=configNight.getNightMin();
			String nightMax=configNight.getNightMax();
			JDateTime lindian=new JDateTime(jdt.toString("YYYY-MM-DD")+" 00:00:00");
			JDateTime JnightMin=new JDateTime(jdt.toString("YYYY-MM-DD")+" "+nightMin+":00");
			JDateTime JnightMax=new JDateTime(jdt.toString("YYYY-MM-DD")+" "+nightMax+":00");
			if(jdt.compareTo(JnightMax)==1 && jdt.compareTo(JnightMin)==-1){
				energyN.setLastDay(lindian.convertToDate());
				energyN.setNow(JnightMax.convertToDate());
			}else if(jdt.compareTo(JnightMax)==-1){
				energyN.setLastDay(lindian.convertToDate());
				energyN.setNow(jdt.convertToDate());
			}else if(jdt.compareTo(JnightMin)==1){
				energyN.setLastDay(lindian.convertToDate());
				energyN.setNow(JnightMax.convertToDate());
				nightEles=nhENAreaHourService.getTwoDayofAreaEnergy(energyN);
				if(nightEles != null){
					total = nightEles.getTotal();
					totalL=nightEles.getLastTotal();
				}
				energyN.setLastDay(JnightMin.convertToDate());
				energyN.setNow(jdt.convertToDate());
			}
			nightEle=nhENAreaHourService.getTwoDayofAreaEnergy(energyN);
			if(nightEle == null){
				nightEle = new NhENAreaHour();
				nightEle.setTotal(new BigDecimal(0));
				nightEle.setLastTotal(new BigDecimal(0));
			}
			nightEle.setTotal(nightEle.getTotal().add(total));
			nightEle.setLastTotal(nightEle.getLastTotal().add(totalL));
		}
		if(dayTimeEle==null){
			dayTimeEle=new NhENAreaHour();
			dayTimeEle.setTotal(new BigDecimal(0));		
			dayTimeEle.setLastTotal(new BigDecimal(0));
		}
		if(nightEle==null){
			nightEle=new NhENAreaHour();
			nightEle.setTotal(new BigDecimal(0));
			nightEle.setLastTotal(new BigDecimal(0));
		}
		dayTimeEle.setsTime(jdt.toString("YYYY-MM"));
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
		model.put("fullLevelName", fullLevelName);
		
		model.put("nightEle", nightEle);
		model.put("dayTimeEle", dayTimeEle);
		model.put("newlist", newMap);
		model.put("num", num);
		return "nhgl/monitor/energy/area/area_count_all";
	}

}
