package com.supconit.nhgl.analyse.electric.area.service;

import java.util.List;

import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaHour;



public interface NhDAreaHourService {
	
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
