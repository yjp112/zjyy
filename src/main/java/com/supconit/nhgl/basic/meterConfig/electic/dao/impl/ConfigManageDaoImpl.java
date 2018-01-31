package com.supconit.nhgl.basic.meterConfig.electic.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.meterConfig.electic.dao.ConfigManageDao;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;

import hc.orm.AbstractBasicDaoImpl;

@Repository
public class ConfigManageDaoImpl extends AbstractBasicDaoImpl<ConfigManager, Long>implements ConfigManageDao{
	private static final String NAMESPACE = ConfigManager.class.getName();
	
	@Override
	public List<ConfigManager> getConfigInfo() {
		return selectList("getConfigInfo");
	}

	@Override
	public int update(ConfigManager cm) {

		return update("updateCm", cm);
	}

	@Override
	public List<ConfigManager> getConfigValue(ConfigManager cm) {
		return selectList("getConfigValue", cm);
	}

	@Override
	public ConfigManager findByCode(String code) {
		return selectOne("findByCode",code);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<ConfigManager> findValidByCode(String typeCode) {
		return selectList("findValidByCode",typeCode);
	}
}
