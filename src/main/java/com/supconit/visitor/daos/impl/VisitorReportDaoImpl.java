package com.supconit.visitor.daos.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.visitor.daos.VisitorReportDao;
import com.supconit.visitor.entities.VisitorReport;


@Repository
public class VisitorReportDaoImpl extends AbstractBaseDao<VisitorReport, Long> implements VisitorReportDao {
	private static final SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String	NAMESPACE	= VisitorReport.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<VisitorReport> findList(VisitorReport report, int type) {
		String statement="";
		Map param = new HashMap(1);
		switch (type) {
        case 1:
        	setDeptOrPurposeCondition(report);
        	statement = "deptReport";
        	param.put("condition", report);
            break;
        case 2:
        	setYearCondition(report,param);
        	statement = "yearReport";
            break;
        case 3:
        	setDeptOrPurposeCondition(report);
        	statement = "purposeReport";
        	param.put("condition", report);
            break;
        default:
        	setDeptOrPurposeCondition(report);
        	statement = "deptReport";
        	param.put("condition", report);
            break;
        }
		return selectList(statement, param);
	}
	
	/**
	 * 设置部门或事由查询条件
	 * @param dept
	 */
	private void setDeptOrPurposeCondition(VisitorReport report){
		String suffix = "-01";
    	Calendar cal = Calendar.getInstance();
    	Date start;
    	Date end;
		try {
			start = sdf_day.parse(report.getStartMonths()+suffix);
			end = sdf_day.parse(report.getEndMonths()+suffix);
			cal.setTime(end);
        	cal.add(Calendar.MONTH, 1);
        	report.setStartMonth(start);
        	report.setEndMonth(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置年度查询条件
	 * @param dept
	 */
	private void setYearCondition(VisitorReport report,Map map){
		String queryYear = report.getVisitYear();
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
}
