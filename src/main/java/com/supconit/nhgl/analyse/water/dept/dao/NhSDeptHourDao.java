package com.supconit.nhgl.analyse.water.dept.dao;

import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptHour;


public interface NhSDeptHourDao extends BasicDao<NhSDeptHour, Long>{

	
	List<NhSDeptHour> getDayofDeptWater(NhSDeptHour em);
	List<NhSDeptHour> getAllSubWater(NhSDeptHour em);
	List<NhSDeptHour> getDayofSubWater(NhSDeptHour em);
	NhSDeptHour getTwoDayofSubWater(NhSDeptHour em);
}
