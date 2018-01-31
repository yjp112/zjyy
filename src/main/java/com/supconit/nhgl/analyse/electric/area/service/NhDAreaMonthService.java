package com.supconit.nhgl.analyse.electric.area.service;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaMonth;

public interface NhDAreaMonthService {

	List<NhDAreaMonth> getAreaTree(NhDAreaMonth area);
	List<NhDAreaMonth> getAreaSubElectricty(NhDAreaMonth area);
	Pageable<NhDAreaMonth> findByCondition(Pagination<NhDAreaMonth> pager,
			NhDAreaMonth condition);
	List<NhDAreaMonth> getAreaList(NhDAreaMonth area);
	List<NhDAreaMonth> getAreaMonthList(NhDAreaMonth area);
	NhDAreaMonth getAreaDayNight(NhDAreaMonth area);
	Pageable<NhDAreaMonth> findByConditionForNhgs(Pageable<NhDAreaMonth> pager,
			NhDAreaMonth condition);
	/**
	 * 获取一级区域用电量前10
	 * @param areaEMonth
	 * @return
	 */
	List<NhDAreaMonth> getParentAreaMonthElectricty(NhDAreaMonth areaEMonth);

	List<NhDAreaMonth> getUntilAreaElectricty(NhDAreaMonth areaEMonth);

	Pageable<NhDAreaMonth> findUntilByCondition(Pagination<NhDAreaMonth> pager,
			NhDAreaMonth condition);
	NhDAreaMonth getMonthElectricityTotal(NhDAreaMonth etm);

}
