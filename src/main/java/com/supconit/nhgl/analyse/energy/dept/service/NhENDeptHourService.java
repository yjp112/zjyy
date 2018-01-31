package com.supconit.nhgl.analyse.energy.dept.service;

import java.util.List;

import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptHour;



public interface NhENDeptHourService {
	List<NhENDeptHour> getDayofDeptEnergy(NhENDeptHour em);
	List<NhENDeptHour> getAllSubEnergy(NhENDeptHour em);
	List<NhENDeptHour> getDayofSubEnergy(NhENDeptHour em);
	NhENDeptHour getTwoDayofSubEnergy(NhENDeptHour em);
}
