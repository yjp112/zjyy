package com.supconit.nhgl.basic.extractData.daos.impl;

import hc.base.domains.Pageable;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.nhgl.basic.extractData.daos.ExtractDataDao;
import com.supconit.nhgl.basic.extractData.entities.ExtractData;

@Repository
public class ExtractDataDaoImpl extends AbstractBaseDao<ExtractData, Long> implements ExtractDataDao{

	private static final String	NAMESPACE = ExtractData.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public Pageable<ExtractData> findByPager(Pageable<ExtractData> pager,
			ExtractData condition) {
		return findByPager(pager, "selectPager", "countPager", condition);
	}

}
