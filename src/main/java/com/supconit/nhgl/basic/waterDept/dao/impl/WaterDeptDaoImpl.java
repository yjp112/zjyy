package com.supconit.nhgl.basic.waterDept.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.waterDept.dao.WaterDeptDao;
import com.supconit.nhgl.basic.waterDept.entities.WaterDept;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class WaterDeptDaoImpl extends AbstractBasicDaoImpl<WaterDept,Long> implements WaterDeptDao{

	private static final String NAMESPACE=WaterDept.class.getName();
	@Override
	public Pageable<WaterDept> findByCondition(Pageable<WaterDept> pager,
			WaterDept condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public List<WaterDept> findDmByCondition(WaterDept dm) {
		return selectList("findByCondition", dm);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

}
