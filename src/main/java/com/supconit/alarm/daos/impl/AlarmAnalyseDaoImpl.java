package com.supconit.alarm.daos.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supconit.alarm.daos.AlarmAnalyseDao;
import com.supconit.alarm.entities.AlarmAnalyse;
import com.supconit.common.daos.AbstractBaseDao;

@Repository
public class AlarmAnalyseDaoImpl extends AbstractBaseDao<AlarmAnalyse, Long> implements AlarmAnalyseDao{
	private static final SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
	private static final String	NAMESPACE = AlarmAnalyse.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<AlarmAnalyse> yearReport(AlarmAnalyse alarm) {
		Map param = new HashMap(1);
		setYearCondition(alarm,param);
		return selectList("yearReport", param);
	}
	
	@Override
	public Long findPie(AlarmAnalyse alarm, int type) {
		String statement="";
		Map param = new HashMap(1);
		switch (type) {
		case 1:
			setYearPieCondition(alarm);
			statement = "yearPieReport";
			break;
		case 2:
			setAreaOrDeptCondition(alarm);
			statement = "areaPieReport";
			break;
		case 3:
			setAreaOrDeptCondition(alarm);
			statement = "deptPieReport";
			break;
		default:
			setYearCondition(alarm,param);
			statement = "yearPieReport";
			break;
		}
		return selectOne(statement, alarm);
	}
	

	@Override
	public Long areaReport(AlarmAnalyse alarm) {
		setAreaOrDeptCondition(alarm);
		return selectOne("areaReport", alarm);
	}
	
	@Override
	public Long deptReport(AlarmAnalyse alarm) {
		setAreaOrDeptCondition(alarm);
		return selectOne("deptReport", alarm);
	}
	
	/**
	 * 设置月份圆饼查询条件
	 * @param alarm
	 */
	private void setYearPieCondition(AlarmAnalyse alarm){
		String startSuffix = "-01-01";
		String suffix = "-01";
		Calendar cal = Calendar.getInstance();
		Date start;
		Date end;
		try {
			String month = alarm.getAlarmMonths();
			if(StringUtils.isEmpty(month)){
				start = sdf_day.parse(alarm.getAlarmYear()+startSuffix);
				end = sdf_day.parse((Integer.parseInt(alarm.getAlarmYear())+1)+startSuffix);
			}else{
				start = sdf_day.parse(month+suffix);
				cal.setTime(start);
				cal.add(Calendar.MONTH, 1);
				end = cal.getTime();
			}
			alarm.setStartMonth(start);
			alarm.setEndMonth(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置年度查询条件
	 * @param dept
	 */
	private void setYearCondition(AlarmAnalyse alarm,Map map){
		String queryYear = alarm.getAlarmYear();
		String nextYear = String.valueOf(Integer.parseInt(queryYear)+1);
		try {
			map.put("jan",sdf_day.parse(queryYear+"-01-01"));
			map.put("feb",sdf_day.parse(queryYear+"-02-01"));
			map.put("mar",sdf_day.parse(queryYear+"-03-01"));
			map.put("apr",sdf_day.parse(queryYear+"-04-01"));
			map.put("may",sdf_day.parse(queryYear+"-05-01"));
			map.put("jun",sdf_day.parse(queryYear+"-06-01"));
			map.put("jul",sdf_day.parse(queryYear+"-07-01"));
			map.put("aug",sdf_day.parse(queryYear+"-08-01"));
			map.put("sep",sdf_day.parse(queryYear+"-09-01"));
			map.put("oct",sdf_day.parse(queryYear+"-10-01"));
			map.put("nov",sdf_day.parse(queryYear+"-11-01"));
			map.put("dece",sdf_day.parse(queryYear+"-12-01"));
			map.put("nextJun",sdf_day.parse(nextYear+"-01-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置区域或部门查询条件
	 * @param alarm
	 */
	private void setAreaOrDeptCondition(AlarmAnalyse alarm){
		String suffix = "-01";
    	Calendar cal = Calendar.getInstance();
    	Date start;
    	Date end;
		try {
			start = sdf_day.parse(alarm.getStartMonths()+suffix);
			end = sdf_day.parse(alarm.getEndMonths()+suffix);
			cal.setTime(end);
        	cal.add(Calendar.MONTH, 1);
        	alarm.setStartMonth(start);
        	alarm.setEndMonth(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
