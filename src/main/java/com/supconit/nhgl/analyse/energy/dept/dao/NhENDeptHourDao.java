package com.supconit.nhgl.analyse.energy.dept.dao;

import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptHour;


public interface NhENDeptHourDao extends BasicDao<NhENDeptHour, Long>{

	
	List<NhENDeptHour> getDayofDeptEnergy(NhENDeptHour em);
	List<NhENDeptHour> getAllSubEnergy(NhENDeptHour em);
	List<NhENDeptHour> getDayofSubEnergy(NhENDeptHour em);
	NhENDeptHour getTwoDayofSubEnergy(NhENDeptHour em);
}
