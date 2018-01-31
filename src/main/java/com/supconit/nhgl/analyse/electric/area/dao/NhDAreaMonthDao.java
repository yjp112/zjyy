package com.supconit.nhgl.analyse.electric.area.dao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.BasicDao;

import java.util.List;

import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaMonth;

public interface NhDAreaMonthDao extends BasicDao<NhDAreaMonth, Long>{

	List<NhDAreaMonth> getAreaElectricMonth(NhDAreaMonth ems);

	List<NhDAreaMonth> getAreaSubElectricty(NhDAreaMonth area);
	List<NhDAreaMonth> getAreaTree(NhDAreaMonth area);
	List<NhDAreaMonth> getAreaList(NhDAreaMonth area);
	List<NhDAreaMonth> getAreaMonthList(NhDAreaMonth area);
	NhDAreaMonth getAreaDayNight(NhDAreaMonth area);

	Pageable<NhDAreaMonth> findByCondition(Pageable<NhDAreaMonth> pager,
			NhDAreaMonth condition);
	Pageable<NhDAreaMonth> findByConditionForNhgs(Pageable<NhDAreaMonth> pager,
			NhDAreaMonth condition);

	List<NhDAreaMonth> getParentAreaMonthElectricty(NhDAreaMonth areaEMonth);

	List<NhDAreaMonth> getUntilAreaElectricty(NhDAreaMonth areaEMonth);

	Pageable<NhDAreaMonth> findUntilByCondition(Pagination<NhDAreaMonth> pager,
			NhDAreaMonth condition);

	NhDAreaMonth getMonthElectricityTotal(NhDAreaMonth etm);

}
