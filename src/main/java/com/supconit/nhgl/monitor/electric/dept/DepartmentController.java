package com.supconit.nhgl.monitor.electric.dept;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
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
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptHour;
import com.supconit.nhgl.analyse.electric.dept.service.NhDDeptHourService;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.GraphUtils;
import com.supconit.nhgl.utils.StatisticElectricUtils;

@Controller("deptController")
@RequestMapping("nhgl/monitor/electric/dept")
public class DepartmentController {
	@Autowired
	private NhDeptService nhDeptService;
	@Autowired
	private NhDDeptHourService nhDDeptHourService;
	@Autowired
	private ConfigManagerService configManagerService;
	@Autowired
	private NhItemService subService;
	private NhItem subinfo = new NhItem();
	private Integer num = 0;

	@RequestMapping(value = "deptindex")
	public String index(ModelMap model, String flag, String fxCode,String deptId) {
		NhDDeptHour nh=new NhDDeptHour();
		List<NhDDeptHour> nhList=null;
		List<NhDDeptHour> nhLastList=null;
		nh.setNhType(Constant.NH_TYPE_D);
		nh.setStandardCode(fxCode);
		nh.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		nh.setCollectTime(new Date());
		nhList=nhDDeptHourService.getAllSubElectricty(nh);
		nh.setCollectDate(DateUtils.truncate(DateUtils.addDay(new Date(), -1), Calendar.DAY_OF_MONTH));
		nh.setCollectTime(DateUtils.addDay(new Date(), -1));
		nhLastList = nhDDeptHourService.getAllSubElectricty(nh);
 		List<NhDDeptHour> sublst = StatisticElectricUtils.getDDeptSubList(nhList, nhLastList);
		subinfo.setParentCode("0");
		subinfo.setNhType(Constant.NH_TYPE_D);
		List<NhItem> slist = subService.findByCon(subinfo);
		JDateTime jdt = new JDateTime(Calendar.getInstance().getTime());
		String sTime=jdt.toString("YYYY-MM-DD");
		model.put("rootId", nhDeptService.findRootId());
		model.put("deptName", "部门");
		model.put("subTree", sublst);
		model.put("slist", slist);
		model.put("sTime", sTime);
		model.put("fxCode", fxCode);
		model.put("deptId", deptId);
		return "nhgl/monitor/electric/dept/deptjc_tab";
	}
	
	@RequestMapping("allYear")
	public String allYearData(ModelMap model, String deptName, String fxCode,
			Integer deptId, String eleTotal, String dateTime) throws ParseException {
		num = num + 1;
		NhDDeptHour nh=new NhDDeptHour();
		JDateTime jdt = new JDateTime(Calendar.getInstance().getTime());
		Integer hour = jdt.getHour();
		String deptNames = deptName == null ? "" : deptName;
		model.put("title",jdt.toString("YYYY年MM月DD日")  + deptNames + "用电量趋势分析");
		
		nh.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		nh.setCollectTime(new Date());
		nh.setNhType(Constant.NH_TYPE_D);
		nh.setStandardCode(fxCode);
		nh.setDeptId(deptId);
		
		List<NhDDeptHour> emlist = nhDDeptHourService.getDayofSubElectricty(nh);
		NhDDeptHour electricD=new NhDDeptHour();
		NhDDeptHour electricN=new NhDDeptHour();
		electricD.setNhType(Constant.NH_TYPE_D);
		electricD.setStandardCode(fxCode);
		electricD.setDeptId(deptId);
		
		electricN.setNhType(Constant.NH_TYPE_D);
		electricN.setStandardCode(fxCode);
		electricN.setDeptId(deptId);
		
		NhDDeptHour dayTimeEle=null;
		NhDDeptHour nightEle=null;
		NhDDeptHour nightEles=null;
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
				electricD.setLastDay(JdayTimeMin.convertToDate());
				electricD.setNow(jdt.convertToDate());
			}else if(jdt.compareTo(JdayTimeMax)==1){
				electricD.setLastDay(JdayTimeMin.convertToDate());
				electricD.setNow(JdayTimeMax.convertToDate());
			}
			dayTimeEle=nhDDeptHourService.getTwoDayofSubElectricty(electricD);
		}
		if(configNight.getFlag()==1){
			String nightMin=configNight.getNightMin();
			String nightMax=configNight.getNightMax();
			JDateTime lindian=new JDateTime(jdt.toString("YYYY-MM-DD")+" 00:00:00");
			JDateTime JnightMin=new JDateTime(jdt.toString("YYYY-MM-DD")+" "+nightMin+":00");
			JDateTime JnightMax=new JDateTime(jdt.toString("YYYY-MM-DD")+" "+nightMax+":00");
			if(jdt.compareTo(JnightMax)==1 && jdt.compareTo(JnightMin)==-1){
				electricN.setLastDay(lindian.convertToDate());
				electricN.setNow(JnightMax.convertToDate());
			}else if(jdt.compareTo(JnightMax)==-1){
				electricN.setLastDay(lindian.convertToDate());
				electricN.setNow(jdt.convertToDate());
			}else if(jdt.compareTo(JnightMin)==1){
				electricN.setLastDay(lindian.convertToDate());
				electricN.setNow(JnightMax.convertToDate());
				nightEles=nhDDeptHourService.getTwoDayofSubElectricty(electricN);
				if(nightEles != null){
					total = nightEles.getTotal();
					totalL=nightEles.getLastTotal();
				}
				electricN.setLastDay(JnightMin.convertToDate());
				electricN.setNow(jdt.convertToDate());
			}
			nightEle=nhDDeptHourService.getTwoDayofSubElectricty(electricN);
			if(nightEle == null){
				nightEle = new NhDDeptHour();
				nightEle.setTotal(new BigDecimal(0));
				nightEle.setLastTotal(new BigDecimal(0));
			}
			nightEle.setTotal(nightEle.getTotal().add(total));
			nightEle.setLastTotal(nightEle.getLastTotal().add(totalL));
		}
		if(dayTimeEle==null){
			dayTimeEle=new NhDDeptHour();
			dayTimeEle.setTotal(new BigDecimal(0));		
			dayTimeEle.setLastTotal(new BigDecimal(0));		
		}
		if(nightEle==null){
			nightEle=new NhDDeptHour();
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
		model.put("deptName", deptName);
		model.put("new", newMap);
		model.put("num", num);
		model.put("nightEle", nightEle);
		model.put("dayTimeEle", dayTimeEle);
		return "nhgl/monitor/electric/dept/deptjc_all";
	}
	
}
