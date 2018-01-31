package com.supconit.nhgl.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaDay;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaHour;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptDay;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptHour;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaHour;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaHour;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaHour;

public class StatisticElectricUtils {
	
	
	/**
	 * 文档管理 文件夹根目录parentId
	 */
	public static final Long CATEGORY_PARENT_ID = (long)0;

	public static Map<String, Object> getDeptHolidayMap(List<NhDDeptDay> nhlst){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder perStr = new StringBuilder();
		StringBuilder data = new StringBuilder();
		StringBuilder lastData = new StringBuilder();
		StringBuilder holiday = new StringBuilder();
		if(nhlst == null || nhlst.size() == 0)
			return null;
		else{
			for(NhDDeptDay n : nhlst){
				BigDecimal percent = new BigDecimal("0.00");
				BigDecimal tb =new BigDecimal("0");
				BigDecimal totalYoy = new BigDecimal("0");
				BigDecimal totalDayValue = new BigDecimal("0");
				if(n.getTotalYoy() != null){
					totalYoy = n.getTotalYoy().setScale(0, RoundingMode.HALF_UP);
				}
				if(n.getTotalDayValue() != null){
					totalDayValue = n.getTotalDayValue().setScale(0, RoundingMode.HALF_UP);
				}
				if(totalDayValue.compareTo(new BigDecimal("0"))!=0){
					tb = totalDayValue.subtract(totalYoy);
				}
				if(totalYoy.compareTo(new BigDecimal("0"))!=0){
					percent = BigDecimalUtil.divide4(tb,totalYoy).multiply(new BigDecimal(100));
				}else if(tb.compareTo(new BigDecimal("0"))==1){
					percent = new BigDecimal("100.00");
				}
				n.setPercent(percent);
				perStr.append(new DecimalFormat("0.00").format(percent)).append(",");
				data.append(totalDayValue).append(",");
				lastData.append(totalYoy).append(",");
				holiday.append(n.getHolidayName()).append(",");
			}
		}
		String dayString = data.length()>1?data.toString().substring(0,data.toString().length()-1):"";
		String dayLastString = lastData.length()>1?lastData.toString().substring(0,lastData.toString().length()-1):"";
		String precent = perStr.length()>1?perStr.toString().substring(0,perStr.toString().length()-1):"";
		String dayList = holiday.length()>1?holiday.toString().substring(0,holiday.toString().length()-1):"";
		map.put("dayList", dayList);
		map.put("precent", precent);
		map.put("dayString", dayString);
		map.put("dayLastString", dayLastString);
		return map;
	}
	public static Map<String, Object> getAreaHolidayMap(List<NhDAreaDay> nhlst){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder perStr = new StringBuilder();
		StringBuilder data = new StringBuilder();
		StringBuilder lastData = new StringBuilder();
		StringBuilder holiday = new StringBuilder();
		if(nhlst == null || nhlst.size() == 0)
			return null;
		else{
			for(NhDAreaDay n : nhlst){
				BigDecimal percent = new BigDecimal("0.00");
				BigDecimal tb =new BigDecimal("0");
				BigDecimal totalYoy = new BigDecimal("0");
				BigDecimal totalDayValue = new BigDecimal("0");
				if(n.getTotalYoy() != null){
					totalYoy = n.getTotalYoy().setScale(0,RoundingMode.HALF_UP);
				}
				if(n.getTotalDayValue() != null){
					totalDayValue = n.getTotalDayValue().setScale(0,RoundingMode.HALF_UP);
				}
				if(totalDayValue.compareTo(new BigDecimal("0"))!=0){
					tb = totalDayValue.subtract(totalYoy);
				}
				if(totalYoy.compareTo(new BigDecimal("0"))!=0){
					percent = BigDecimalUtil.divide4(tb,totalYoy).multiply(new BigDecimal(100));
				}else if(tb.compareTo(new BigDecimal("0"))==1){
					percent = new BigDecimal("100.00");
				}
				n.setPercent(percent);
				perStr.append(new DecimalFormat("0.00").format(percent)).append(",");
				data.append(totalDayValue).append(",");
				lastData.append(totalYoy).append(",");
				holiday.append(n.getHolidayName()).append(",");
			}
		}
		String dayString = data.length()>1?data.toString().substring(0,data.toString().length()-1):"";
		String dayLastString = lastData.length()>1?lastData.toString().substring(0,lastData.toString().length()-1):"";
		String precent = perStr.length()>1?perStr.toString().substring(0,perStr.toString().length()-1):"";
		String dayList = holiday.length()>1?holiday.toString().substring(0,holiday.toString().length()-1):"";
		map.put("dayList", dayList);
		map.put("precent", precent);
		map.put("dayString", dayString);
		map.put("dayLastString", dayLastString);
		return map;
	}
	public static List<NhDAreaHour> getDSubList(List<NhDAreaHour> nhList, List<NhDAreaHour> nhLastList)
	{	
		BigDecimal tb = new BigDecimal(0);
		if(nhList != null && nhList.size() > 0){
			for(int i=0;i<nhLastList.size();i++){
				NhDAreaHour nhLast = nhLastList.get(i);
				NhDAreaHour nh=nhList.get(i);
				tb=nh.getTotal().subtract(nhLast.getTotal());
				if(tb.compareTo(BigDecimal.ZERO) == 1){
					nhList.get(i).setTb(1);
				}else if(tb.compareTo(BigDecimal.ZERO) == -1){
					nhList.get(i).setTb(-1);
				}else{
					nhList.get(i).setTb(0);
				}
			}
		}
		return nhList;
	}
	public static List<NhDDeptHour> getDDeptSubList(List<NhDDeptHour> nhList, List<NhDDeptHour> nhLastList)
	{	
		BigDecimal tb = new BigDecimal(0);
		if(nhList != null && nhList.size() > 0){
			for(int i=0;i<nhList.size();i++){
				int j=0;
				for(NhDDeptHour nl:nhLastList){
					if(nhList.get(i).getDeptId().equals(nl.getDeptId())){
						tb=nhList.get(i).getTotal().subtract(nl.getTotal());
						if(tb.compareTo(BigDecimal.ZERO) == 1){
							nhList.get(i).setTb(1);
						}else if(tb.compareTo(BigDecimal.ZERO) == -1){
							nhList.get(i).setTb(-1);
						}else{
							nhList.get(i).setTb(0);
						}
					}else{
						j++;
						if(j==nhLastList.size()){
							nhList.get(i).setTb(1);
						}
					}
				}
			}
		}
		return nhList;
	}
	public static List<NhDAreaHour> getDAreaSubList(List<NhDAreaHour> nhList, List<NhDAreaHour> nhLastList)
	{	
		BigDecimal tb = new BigDecimal(0);
		if(nhList != null && nhList.size() > 0){
			for(int i=0;i<nhList.size();i++){
				int j=0;
				for(NhDAreaHour nl:nhLastList){
					if(nhList.get(i).getAreaId().equals(nl.getAreaId())){
						tb=nhList.get(i).getTotal().subtract(nl.getTotal());
						if(tb.compareTo(BigDecimal.ZERO) == 1){
							nhList.get(i).setTb(1);
						}else if(tb.compareTo(BigDecimal.ZERO) == -1){
							nhList.get(i).setTb(-1);
						}else{
							nhList.get(i).setTb(0);
						}
					}else{
						j++;
						if(j==nhLastList.size()){
							nhList.get(i).setTb(1);
						}
					}
				}
			}
		}
		return nhList;
	}
	public static List<NhENAreaHour> getENSubList(List<NhENAreaHour> nhList, List<NhENAreaHour> nhLastList)
	{	
		BigDecimal tb = new BigDecimal(0);
		if(nhList != null && nhList.size() > 0){
			for(int i=0;i<nhLastList.size();i++){
				NhENAreaHour nhLast = nhLastList.get(i);
				NhENAreaHour nh=nhList.get(i);
				tb=nh.getTotal().subtract(nhLast.getTotal());
				if(tb.compareTo(BigDecimal.ZERO) == 1){
					nhList.get(i).setTb(1);
				}else if(tb.compareTo(BigDecimal.ZERO) == -1){
					nhList.get(i).setTb(-1);
				}else{
					nhList.get(i).setTb(0);
				}
			}
		}
		return nhList;
	}
	public static List<NhSAreaHour> getSSubList(List<NhSAreaHour> nhList, List<NhSAreaHour> nhLastList)
	{	
		BigDecimal tb = new BigDecimal(0);
		if(nhList != null && nhList.size() > 0){
			for(int i=0;i<nhLastList.size();i++){
				NhSAreaHour nhLast = nhLastList.get(i);
				NhSAreaHour nh=nhList.get(i);
				tb=nh.getTotal().subtract(nhLast.getTotal());
				if(tb.compareTo(BigDecimal.ZERO) == 1){
					nhList.get(i).setTb(1);
				}else if(tb.compareTo(BigDecimal.ZERO) == -1){
					nhList.get(i).setTb(-1);
				}else{
					nhList.get(i).setTb(0);
				}
			}
		}
		return nhList;
	}
	public static List<NhQAreaHour> getQSubList(List<NhQAreaHour> nhList, List<NhQAreaHour> nhLastList)
	{	
		BigDecimal tb = new BigDecimal(0);
		if(nhList != null && nhList.size() > 0){
			for(int i=0;i<nhLastList.size();i++){
				NhQAreaHour nhLast = nhLastList.get(i);
				NhQAreaHour nh=nhList.get(i);
				tb=nh.getTotal().subtract(nhLast.getTotal());
				if(tb.compareTo(BigDecimal.ZERO) == 1){
					nhList.get(i).setTb(1);
				}else if(tb.compareTo(BigDecimal.ZERO) == -1){
					nhList.get(i).setTb(-1);
				}else{
					nhList.get(i).setTb(0);
				}
			}
		}
		return nhList;
	}
}
