package com.supconit.nhgl.basic.GasDept.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.GasDept.dao.GasDeptDao;
import com.supconit.nhgl.basic.GasDept.entities.GasDept;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class GasDeptDaoImpl extends AbstractBasicDaoImpl<GasDept,Long> implements GasDeptDao{

	private static final String NAMESPACE=GasDept.class.getName();
	@Override
	public Pageable<GasDept> findByCondition(Pageable<GasDept> pager,
			GasDept condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public List<GasDept> findDmByCondition(GasDept dm) {
		return selectList("findByCondition", dm);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

}
