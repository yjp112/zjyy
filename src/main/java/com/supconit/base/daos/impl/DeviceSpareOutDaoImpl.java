package com.supconit.base.daos.impl;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceSpareOutDao;
import com.supconit.base.entities.DeviceSpareOut;
import com.supconit.common.daos.AbstractBaseDao;
@Repository
public class DeviceSpareOutDaoImpl extends AbstractBaseDao<DeviceSpareOut, Long> implements
	DeviceSpareOutDao{
	private static final String NAMESPACE=DeviceSpareOut.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

}
