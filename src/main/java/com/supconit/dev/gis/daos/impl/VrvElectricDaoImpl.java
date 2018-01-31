package com.supconit.dev.gis.daos.impl;

import org.springframework.stereotype.Repository;

import com.supconit.dev.gis.daos.VrvElectricDao;
import com.supconit.dev.gis.entities.VrvElectric;
import com.supconit.hl.common.daos.impl.AbstractBaseDao;

import hc.base.domains.Pageable;

@Repository
public class VrvElectricDaoImpl extends AbstractBaseDao<VrvElectric, Long>
		implements VrvElectricDao {
	private static final String	NAMESPACE	= VrvElectric.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<VrvElectric> findByCondition(Pageable<VrvElectric> page, VrvElectric condition) {
		return findByPager(page,"findByCondition", "countByCondition", condition);
	}


}
