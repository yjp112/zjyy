package com.supconit.nhgl.analyse.electric.area.service;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import com.supconit.nhgl.analyse.electric.area.entities.NhArea;

public interface NhAreaService {
	Long findRootId();
	List<NhArea> findAreaTypeByNhType(NhArea nhArea);

	/**
	 * 通过区域id查询该区域及所有下级区域
	 * @param areaId
	 * @return
	 */
	List<NhArea> getTreeById(Integer areaId);

	/**
	 * 获取地理区域面积
	 * @param areaEMonth
	 * @return
	 */
	List<NhArea> getDistrictArea(NhArea areaEMonth);

	Pageable<NhArea> findByCondition(Pagination<NhArea> pager, NhArea condition);

}
