package com.supconit.nhgl.basic.meterConfig.electic.service;

import java.util.List;

import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;

public interface ConfigManagerService {
	
	public List<ConfigManager> getConfigInfo();
	public ConfigManager getDayTimeConfig();
	public ConfigManager getNightConfig();
	public void update(ConfigManager cm);
	public List<ConfigManager> findValidByCode(String typeCode);
	public List<ConfigManager> getConfigValue(ConfigManager cm);
	public ConfigManager findByCode(String code);
}
