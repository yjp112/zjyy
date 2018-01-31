package com.supconit.nhgl.analyse.electric.dept.dao;

import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptHour;

public interface NhDDeptHourDao extends BasicDao<NhDDeptHour, Long>{

	
	List<NhDDeptHour> getDayofDeptElectricty(NhDDeptHour em);
	List<NhDDeptHour> getAllSubElectricty(NhDDeptHour em);
	List<NhDDeptHour> getDayofSubElectricty(NhDDeptHour em);
	NhDDeptHour getTwoDayofSubElectricty(NhDDeptHour em);
	List<NhDDeptHour> getSubInfoDetailByDept(NhDDeptHour elecDept);
}
