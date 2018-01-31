package com.supconit.nhgl.analyse.energy.area.dao;

import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaHour;


public interface NhENAreaHourDao extends BasicDao<NhENAreaHour, Long>{

	
	List<NhENAreaHour> getAllSubEnergy(NhENAreaHour em);
	List<NhENAreaHour> getDayofSubEnergy(NhENAreaHour em);
	NhENAreaHour getSumDayofSubEnergy(NhENAreaHour em);
	List<NhENAreaHour> getTwoDayofSubEnergy(NhENAreaHour em);
	List<NhENAreaHour> getAllAreaEnergy(NhENAreaHour em);
	List<NhENAreaHour> getDayofAreaEnergy(NhENAreaHour em);
	NhENAreaHour getTwoDayofAreaEnergy(NhENAreaHour em);
}
