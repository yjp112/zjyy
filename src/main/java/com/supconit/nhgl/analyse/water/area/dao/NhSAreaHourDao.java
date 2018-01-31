package com.supconit.nhgl.analyse.water.area.dao;

import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.water.area.entities.NhSAreaHour;



public interface NhSAreaHourDao extends BasicDao<NhSAreaHour, Long>{

	
	List<NhSAreaHour> getAllSubWater(NhSAreaHour em);
	List<NhSAreaHour> getDayofSubWater(NhSAreaHour em);
	NhSAreaHour getSumDayofSubWater(NhSAreaHour em);
	List<NhSAreaHour> getTwoDayofSubWater(NhSAreaHour em);
	List<NhSAreaHour> getAllAreaWater(NhSAreaHour em);
	List<NhSAreaHour> getDayofAreaWater(NhSAreaHour em);
	NhSAreaHour getTwoDayofAreaWater(NhSAreaHour em);
}
