package com.supconit.nhgl.analyse.electric.area.dao;

import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaHour;


public interface NhDAreaHourDao extends BasicDao<NhDAreaHour, Long>{

	
	List<NhDAreaHour> getAllSubElectricty(NhDAreaHour em);
	List<NhDAreaHour> getDayofSubElectricty(NhDAreaHour em);
	NhDAreaHour getSumDayofSubElectricty(NhDAreaHour em);
	NhDAreaHour getTwoDayofSubElectricty(NhDAreaHour em);
	List<NhDAreaHour> getAllAreaElectricty(NhDAreaHour em);
	List<NhDAreaHour> getDayofAreaElectricty(NhDAreaHour em);
	NhDAreaHour getTwoDayofAreaElectricty(NhDAreaHour em);
	List<NhDAreaHour> getSubInfoDetailByArea(NhDAreaHour elec);
	List<NhDAreaHour> getAreaDetail(NhDAreaHour elec);
	List<NhDAreaHour> getDayElectric(NhDAreaHour ectm);
}
