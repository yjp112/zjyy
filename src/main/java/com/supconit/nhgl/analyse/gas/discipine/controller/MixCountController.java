package com.supconit.nhgl.analyse.gas.discipine.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.common.utils.Constant;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaMonth;
import com.supconit.nhgl.analyse.gas.area.service.NhQAreaMonthService;
import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptMonth;
import com.supconit.nhgl.analyse.gas.dept.service.NhQDeptMonthService;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.GraphUtils;
import com.supconit.nhgl.utils.HolidayConstans;

/**
 * 能耗分析 分项用能趋势分析
 * 
 * 
 */
@Controller("MixGasCountController")
@RequestMapping("nhgl/analyse/gas/discipine")
public class MixCountController {
	@Autowired
	private NhQAreaMonthService nhQAreaMonthService;
	@Autowired
	private NhQDeptMonthService nhQDeptMonthService;
	@Autowired
	private ConfigManagerService configManagerService;
	@Autowired
	private NhDeptService nhDeptService;
	@Autowired
	private NhAreaService nhAreaService;
	@RequestMapping("index")
	public String index(ModelMap model, String start, Integer flag,String code,String areaId,String deptId) {
		List<NhQAreaMonth> nhAlst = null;
		List<NhQDeptMonth> nhDlst = null;
		NhQAreaMonth nhA = new NhQAreaMonth();
		NhQDeptMonth nhQ = new NhQDeptMonth();
		JDateTime dateTime = new JDateTime(new Date());
		if(start == null){//当前月份
			start = dateTime.toString("YYYY-MM");
			nhA.setEndTime(dateTime.toString("YYYY-MM"));
			nhQ.setEndTime(dateTime.toString("YYYY-MM"));
		}else{
			nhA.setEndTime(start);
			nhQ.setEndTime(start);
		}
		nhA.setStartTime(start);
		nhQ.setStartTime(start);
		nhA.setNhType(Constant.NH_TYPE_Q);
		nhQ.setNhType(Constant.NH_TYPE_Q);
		nhAlst=nhQAreaMonthService.getAreaList(nhA);
		nhDlst=nhQDeptMonthService.getDeptList(nhQ);
		if(flag==null){
			flag=1;
		}
		model.put("rootDId", nhDeptService.findRootId());
		model.put("rootAId", nhAreaService.findRootId());
		model.put("start", start);
		model.put("list", nhDlst);
		model.put("glist", nhAlst);
		model.put("flag", flag);
		model.put("code", code);
		model.put("areaId", areaId);
		model.put("deptId", deptId);
		return "nhgl/analyse/gas/discipine/mix_count_tab";
	}

	@RequestMapping("allYear")
	public String allYearData(ModelMap model,String start, String deptName, Integer id,
			Integer flag){
		JDateTime startTime = new JDateTime(start);
		deptName = (deptName == null ? "" : deptName);
		String title = startTime.toString("YYYY年MM月") + deptName + "用汽量统计分析";
		model.put("title", title);
		StringBuffer sb = new StringBuffer("[");
		int i=0;
		if(flag==1){
			NhQDeptMonth nh=new NhQDeptMonth();
			NhQDeptMonth nhMonth=null;
			NhQDeptMonth nhMonthLast=null;
			List<NhQDeptMonth> nhlst=new ArrayList<NhQDeptMonth>();
			
			nh.setDeptId(id);
			nh.setStartTime(start);
			nh.setEndTime(start);
			nh.setNhType(Constant.NH_TYPE_Q);
			nh.setParentCode("0");
			nhlst=nhQDeptMonthService.getDeptSubGas(nh);
			model.put("slist", nhlst);
			
			nhMonth=nhQDeptMonthService.getDeptDayNight(nh);//这个月白天晚上
			//上一年这个月白天晚上
			nh.setStartTime(GraphUtils.getDateString(startTime.getYear()-1,startTime.getMonth(),null));
			nh.setEndTime(GraphUtils.getDateString(startTime.getYear()-1,startTime.getMonth(),null));
			nhMonthLast=nhQDeptMonthService.getDeptDayNight(nh);
			
			if(nhMonth==null){
				nhMonth=new NhQDeptMonth();
				nhMonth.setMonthDaytimeValue(new BigDecimal(0));
				nhMonth.setMonthNightValue(new BigDecimal(0));
			}
			if(nhMonthLast==null){
				nhMonthLast=new NhQDeptMonth();
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
			nhMonth.setMonthKey(start);
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
			model.put("nhMonth", nhMonth);
			for(NhQDeptMonth n:nhlst){
				if(n.getTotal().compareTo(new BigDecimal(0))==0){
					i++;
				}
				sb.append("{").append("value:").append(n.getTotal()).append(",name:");
				sb.append("'" + n.getItemName() + "'},");
				if(i==nhlst.size()){
					sb= new StringBuffer("[");
				}
			}
			
		}else{
			NhQAreaMonth nh=new NhQAreaMonth();
			NhQAreaMonth nhMonth=null;
			NhQAreaMonth nhMonthLast=null;
			List<NhQAreaMonth> nhlst=new ArrayList<NhQAreaMonth>();
			
			nh.setAreaId(id);
			nh.setStartTime(start);
			nh.setEndTime(start);
			nh.setNhType(Constant.NH_TYPE_Q);
			nh.setParentCode("0");
			nhlst=nhQAreaMonthService.getAreaSubGas(nh);
			model.put("slist", nhlst);
			
			nhMonth=nhQAreaMonthService.getAreaDayNight(nh);//这个月白天晚上
			//上一年这个月白天晚上
			nh.setStartTime(GraphUtils.getDateString(startTime.getYear()-1,startTime.getMonth(),null));
			nh.setEndTime(GraphUtils.getDateString(startTime.getYear()-1,startTime.getMonth(),null));
			nhMonthLast=nhQAreaMonthService.getAreaDayNight(nh);
			
			if(nhMonth==null){
				nhMonth=new NhQAreaMonth();
				nhMonth.setMonthDaytimeValue(new BigDecimal(0));
				nhMonth.setMonthNightValue(new BigDecimal(0));
			}
			if(nhMonthLast==null){
				nhMonthLast=new NhQAreaMonth();
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
			nhMonth.setMonthKey(start);
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
			model.put("nhMonth", nhMonth);
			for(NhQAreaMonth n:nhlst){
				if(n.getTotal().compareTo(new BigDecimal(0))==0){
					i++;
				}
				sb.append("{").append("value:").append(n.getTotal()).append(",name:");
				sb.append("'" + n.getItemName() + "'},");
				if(i==nhlst.size()){
					sb= new StringBuffer("[");
				}
			}
		}
		String pieString = sb.length() > 1 ? sb.toString().substring(0,
				sb.toString().length() - 1) : "[";
		model.put("pieString", pieString + "]");
		model.put("start", start);

		return "nhgl/analyse/gas/discipine/mix_count_all";
	}
}
