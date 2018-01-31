package com.supconit.nhgl.analyse.water.area.service;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.water.area.entities.NhSAreaMonth;

public interface NhSAreaMonthService {

	List<NhSAreaMonth> getAreaSubWater(NhSAreaMonth area);
	List<NhSAreaMonth> getAreaTree(NhSAreaMonth area);

	Pageable<NhSAreaMonth> findByCondition(Pagination<NhSAreaMonth> pager,
			NhSAreaMonth condition);
	Pageable<NhSAreaMonth> findByConditionForNhgs(Pageable<NhSAreaMonth> pager,
			NhSAreaMonth condition);
	List<NhSAreaMonth> getAreaMonthList(NhSAreaMonth ems);
	NhSAreaMonth getAreaDayNight(NhSAreaMonth ems);

	/**
	 * 获取一级区域用水量前10
	 * @param areaWMonth
	 * @return
	 */
	List<NhSAreaMonth> getParentAreaMonthWater(NhSAreaMonth areaWMonth);
	List<NhSAreaMonth> getAreaList(NhSAreaMonth ems);
	List<NhSAreaMonth> getUntilAreaWater(NhSAreaMonth areaWMonth);

}
