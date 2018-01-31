package com.supconit.nhgl.base.dao.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.nhgl.base.dao.NhDeviceDao;
import com.supconit.nhgl.base.entities.NhDevice;


@Repository
public class NhDeviceDaoImpl extends AbstractBaseDao<NhDevice, Long> implements NhDeviceDao {
	private static final String	NAMESPACE	= NhDevice.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public Pageable<NhDevice> findByCondition(Pagination<NhDevice> pager,
			NhDevice condition) {
		// TODO Auto-generated method stub
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	
}
