package com.supconit.nhgl.basic.electricDept.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.electricDept.dao.ElectricDeptDao;
import com.supconit.nhgl.basic.electricDept.entities.ElectricDept;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class ElectricDeptDaoImpl extends AbstractBasicDaoImpl<ElectricDept,Long> implements ElectricDeptDao {
	private static final String NAMESPACE=ElectricDept.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public Pageable<ElectricDept> findByCondition(Pageable<ElectricDept> pager,
			ElectricDept condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public List<ElectricDept> findDmByCondition(ElectricDept dm) {
		return selectList("findByCondition", dm);
	}

}