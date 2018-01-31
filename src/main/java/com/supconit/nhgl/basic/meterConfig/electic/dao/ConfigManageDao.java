package com.supconit.nhgl.basic.meterConfig.electic.dao;

import java.util.List;

import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;

import hc.orm.BasicDao;

public interface ConfigManageDao extends BasicDao<ConfigManager, Long>{
	
	//获取配置信息
	public List<ConfigManager> getConfigInfo();
	public List<ConfigManager> findValidByCode(String typeCode);
	public int update(ConfigManager cm);
	public ConfigManager findByCode(String code);
	public List<ConfigManager> getConfigValue(ConfigManager cm);
}
