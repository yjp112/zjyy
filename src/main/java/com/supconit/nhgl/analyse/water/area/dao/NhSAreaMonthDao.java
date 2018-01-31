package com.supconit.nhgl.analyse.water.area.dao;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.nhgl.analyse.water.area.entities.NhSAreaMonth;

public interface NhSAreaMonthDao {

	List<NhSAreaMonth> getAreaSubWater(NhSAreaMonth ems);
	List<NhSAreaMonth> getAreaElectricMonth(NhSAreaMonth ems);
	List<NhSAreaMonth> getAreaList(NhSAreaMonth ems);
	List<NhSAreaMonth> getAreaMonthList(NhSAreaMonth ems);
	NhSAreaMonth getAreaDayNight(NhSAreaMonth ems);

	List<NhSAreaMonth> getAreaTree(NhSAreaMonth area);

	Pageable<NhSAreaMonth> findByCondition(Pageable<NhSAreaMonth> pager,
			NhSAreaMonth condition);
	Pageable<NhSAreaMonth> findByConditionForNhgs(Pageable<NhSAreaMonth> pager,
			NhSAreaMonth condition);

	List<NhSAreaMonth> getParentAreaMonthWater(NhSAreaMonth areaWMonth);

	List<NhSAreaMonth> getUntilAreaWater(NhSAreaMonth areaWMonth);

}
