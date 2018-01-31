package com.supconit.nhgl.analyse.water.dept.service;

import java.util.List;

import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptHour;



public interface NhSDeptHourService {
	public List<NhSDeptHour> getDayofDeptWater(NhSDeptHour em);
	List<NhSDeptHour> getAllSubWater(NhSDeptHour em);
	List<NhSDeptHour> getDayofSubWater(NhSDeptHour em);
	NhSDeptHour getTwoDayofSubWater(NhSDeptHour em);
}
