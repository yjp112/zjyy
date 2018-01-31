package com.supconit.nhgl.analyse.gas.area.dao;

import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaHour;



public interface NhQAreaHourDao extends BasicDao<NhQAreaHour, Long>{

	
	List<NhQAreaHour> getAllSubGas(NhQAreaHour em);
	List<NhQAreaHour> getDayofSubGas(NhQAreaHour em);
	NhQAreaHour getSumDayofSubGas(NhQAreaHour em);
	List<NhQAreaHour> getTwoDayofSubGas(NhQAreaHour em);
	List<NhQAreaHour> getAllAreaGas(NhQAreaHour em);
	List<NhQAreaHour> getDayofAreaGas(NhQAreaHour em);
	NhQAreaHour getTwoDayofAreaGas(NhQAreaHour em);
}
