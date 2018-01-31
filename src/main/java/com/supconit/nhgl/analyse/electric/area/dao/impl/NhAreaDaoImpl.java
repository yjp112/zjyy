package com.supconit.nhgl.analyse.electric.area.dao.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.area.dao.NhAreaDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhArea;

@Repository
public class NhAreaDaoImpl extends AbstractBasicDaoImpl<NhArea, Long> implements NhAreaDao {

	private static final String NAMESPACE = NhArea.class.getName();
	
	@Override
	public List<NhArea> findAreaTypeByNhType(NhArea nhArea) {
		return selectList("findAreaTypeByNhType", nhArea);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<NhArea> getTreeById(Integer id) {
		return selectList("getTreeById", id);
	}

	@Override
	public List<NhArea> getDistrictArea(NhArea areaEMonth) {
		return selectList("getDistrictArea", areaEMonth);
	}

	@Override
	public Pageable<NhArea> findByCondition(Pagination<NhArea> pager,
			NhArea condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public Long findRootId() {
		return selectOne("findRootId");
	}

}
