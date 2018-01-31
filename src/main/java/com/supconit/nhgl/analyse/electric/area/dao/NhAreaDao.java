package com.supconit.nhgl.analyse.electric.area.dao;

import java.util.List;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.BasicDao;

import com.supconit.nhgl.analyse.electric.area.entities.NhArea;

public interface NhAreaDao extends BasicDao<NhArea, Long>{

	List<NhArea> findAreaTypeByNhType(NhArea nhArea);
	
	List<NhArea> getTreeById(Integer id);
	Long findRootId();

	List<NhArea> getDistrictArea(NhArea areaEMonth);

	Pageable<NhArea> findByCondition(Pagination<NhArea> pager, NhArea condition);
}
