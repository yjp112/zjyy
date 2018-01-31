package com.supconit.nhgl.analyse.gas.dept.dao;

import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptHour;


public interface NhQDeptHourDao extends BasicDao<NhQDeptHour, Long>{

	
	List<NhQDeptHour> getDayofDeptGas(NhQDeptHour em);
	List<NhQDeptHour> getAllSubGas(NhQDeptHour em);
	List<NhQDeptHour> getDayofSubGas(NhQDeptHour em);
	NhQDeptHour getTwoDayofSubGas(NhQDeptHour em);
}
