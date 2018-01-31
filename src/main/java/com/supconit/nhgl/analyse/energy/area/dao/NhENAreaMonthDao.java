package com.supconit.nhgl.analyse.energy.area.dao;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaMonth;

public interface NhENAreaMonthDao {

	List<NhENAreaMonth> getAreaSubEnergy(NhENAreaMonth ems);
	List<NhENAreaMonth> getAreaElectricMonth(NhENAreaMonth ems);
	List<NhENAreaMonth> getAreaList(NhENAreaMonth ems);
	List<NhENAreaMonth> getAreaMonthList(NhENAreaMonth ems);
	NhENAreaMonth getAreaDayNight(NhENAreaMonth ems);

	List<NhENAreaMonth> getAreaTree(NhENAreaMonth area);

	Pageable<NhENAreaMonth> findByCondition(Pageable<NhENAreaMonth> pager,
			NhENAreaMonth condition);

}
