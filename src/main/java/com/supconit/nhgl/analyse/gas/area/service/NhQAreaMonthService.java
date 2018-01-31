package com.supconit.nhgl.analyse.gas.area.service;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaMonth;

public interface NhQAreaMonthService {

	List<NhQAreaMonth> getAreaSubGas(NhQAreaMonth area);
	List<NhQAreaMonth> getAreaTree(NhQAreaMonth area);
	List<NhQAreaMonth> getAreaList(NhQAreaMonth ems);
	List<NhQAreaMonth> getAreaMonthList(NhQAreaMonth ems);
	NhQAreaMonth getAreaDayNight(NhQAreaMonth ems);
	Pageable<NhQAreaMonth> findByCondition(Pagination<NhQAreaMonth> pager,
			NhQAreaMonth condition);

}
