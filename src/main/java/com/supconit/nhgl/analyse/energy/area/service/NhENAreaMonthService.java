package com.supconit.nhgl.analyse.energy.area.service;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaMonth;

public interface NhENAreaMonthService {
	List<NhENAreaMonth> getAreaSubEnergy(NhENAreaMonth ems);
	Pageable<NhENAreaMonth> findByCondition(Pagination<NhENAreaMonth> pager,
			NhENAreaMonth condition);
	List<NhENAreaMonth> getAreaList(NhENAreaMonth ems);
	List<NhENAreaMonth> getAreaTree(NhENAreaMonth area);
	List<NhENAreaMonth> getAreaMonthList(NhENAreaMonth ems);
	NhENAreaMonth getAreaDayNight(NhENAreaMonth ems);
}
