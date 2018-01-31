package com.supconit.repair.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.repair.daos.DeviceGoodRateDao;
import com.supconit.repair.entities.DeviceGoodRate;

/**
 * @文件名: DeviceReportDaoImpl
 * @创建日期: 13-9-9
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
@Repository
public class DeviceGoodRateDaoImpl extends AbstractBaseDao<DeviceGoodRate,Long> implements DeviceGoodRateDao {

	private static final String	NAMESPACE	= DeviceGoodRate.class.getName();

	@Override
	public List<DeviceGoodRate> queryDeviceGoodRate(DeviceGoodRate deviceGoodRate) {
		return selectList("queryDeviceGoodRate", deviceGoodRate);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	


}
