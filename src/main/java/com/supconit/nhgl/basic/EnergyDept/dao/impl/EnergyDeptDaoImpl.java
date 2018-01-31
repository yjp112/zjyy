package com.supconit.nhgl.basic.EnergyDept.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.EnergyDept.dao.EnergyDeptDao;
import com.supconit.nhgl.basic.EnergyDept.entities.EnergyDept;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class EnergyDeptDaoImpl extends AbstractBasicDaoImpl<EnergyDept,Long> implements EnergyDeptDao{

	private static final String NAMESPACE=EnergyDept.class.getName();
	@Override
	public Pageable<EnergyDept> findByCondition(Pageable<EnergyDept> pager,
			EnergyDept condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public List<EnergyDept> findDmByCondition(EnergyDept dm) {
		return selectList("findByCondition", dm);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

}
