package com.supconit.nhgl.analyse.energy.area.service;

import java.util.List;

import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaHour;



public interface NhENAreaHourService {
	
	List<NhENAreaHour> getAllSubEnergy(NhENAreaHour em);
	List<NhENAreaHour> getDayofSubEnergy(NhENAreaHour em);
	NhENAreaHour getSumDayofSubEnergy(NhENAreaHour em);
	List<NhENAreaHour> getTwoDayofSubEnergy(NhENAreaHour em);
	List<NhENAreaHour> getAllAreaEnergy(NhENAreaHour em);
	List<NhENAreaHour> getDayofAreaEnergy(NhENAreaHour em);
	NhENAreaHour getTwoDayofAreaEnergy(NhENAreaHour em);
}
