package com.supconit.nhgl.analyse.water.area.service;

import java.util.List;

import com.supconit.nhgl.analyse.water.area.entities.NhSAreaHour;




public interface NhSAreaHourService {
	
	List<NhSAreaHour> getAllSubWater(NhSAreaHour em);
	List<NhSAreaHour> getDayofSubWater(NhSAreaHour em);
	NhSAreaHour getSumDayofSubWater(NhSAreaHour em);
	List<NhSAreaHour> getTwoDayofSubWater(NhSAreaHour em);
	List<NhSAreaHour> getAllAreaWater(NhSAreaHour em);
	List<NhSAreaHour> getDayofAreaWater(NhSAreaHour em);
	NhSAreaHour getTwoDayofAreaWater(NhSAreaHour em);
}
