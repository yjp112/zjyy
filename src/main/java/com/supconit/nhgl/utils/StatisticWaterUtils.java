package com.supconit.nhgl.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaDay;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaHour;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptDay;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptHour;

public class StatisticWaterUtils {
	
	
	/**
	 * 文档管理 文件夹根目录parentId
	 */
	public static final Long CATEGORY_PARENT_ID = (long)0;

	public static List<NhSDeptHour> getSDeptSubList(List<NhSDeptHour> nhList, List<NhSDeptHour> nhLastList)
	{	
		BigDecimal tb = new BigDecimal(0);
		if(nhList != null && nhList.size() > 0){
			for(int i=0;i<nhList.size();i++){
				int j=0;
				for(NhSDeptHour nl:nhLastList){
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
	public static List<NhSAreaHour> getSAreaSubList(List<NhSAreaHour> nhList, List<NhSAreaHour> nhLastList)
	{	
		BigDecimal tb = new BigDecimal(0);
		if(nhList != null && nhList.size() > 0){
			for(int i=0;i<nhList.size();i++){
				int j=0;
				for(NhSAreaHour nl:nhLastList){
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
	public static Map<String, Object> getDeptHolidayMap(List<NhSDeptDay> nhlst){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder perStr = new StringBuilder();
		StringBuilder data = new StringBuilder();
		StringBuilder lastData = new StringBuilder();
		StringBuilder holiday = new StringBuilder();
		if(nhlst == null || nhlst.size() == 0)
			return null;
		else{
			for(NhSDeptDay n : nhlst){
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
				if(!totalDayValue.equals(new BigDecimal("0"))){
					tb = totalDayValue.subtract(totalYoy);
				}
				if(!totalYoy.equals(new BigDecimal("0"))){
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
	public static Map<String, Object> getAreaHolidayMap(List<NhSAreaDay> nhlst){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder perStr = new StringBuilder();
		StringBuilder data = new StringBuilder();
		StringBuilder lastData = new StringBuilder();
		StringBuilder holiday = new StringBuilder();
		if(nhlst == null || nhlst.size() == 0)
			return null;
		else{
			for(NhSAreaDay n : nhlst){
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
				if(!totalDayValue.equals(new BigDecimal("0"))){
					tb = totalDayValue.subtract(totalYoy);
				}
				if(!totalYoy.equals(new BigDecimal("0"))){
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
}
