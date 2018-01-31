package com.supconit.nhgl.analyse.gas.area.dao;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaMonth;

public interface NhQAreaMonthDao {

	List<NhQAreaMonth> getAreaSubGas(NhQAreaMonth ems);
	List<NhQAreaMonth> getAreaElectricMonth(NhQAreaMonth ems);
	List<NhQAreaMonth> getAreaList(NhQAreaMonth ems);
	List<NhQAreaMonth> getAreaMonthList(NhQAreaMonth ems);
	NhQAreaMonth getAreaDayNight(NhQAreaMonth ems);

	List<NhQAreaMonth> getAreaTree(NhQAreaMonth area);

	Pageable<NhQAreaMonth> findByCondition(Pageable<NhQAreaMonth> pager,
			NhQAreaMonth condition);

}
