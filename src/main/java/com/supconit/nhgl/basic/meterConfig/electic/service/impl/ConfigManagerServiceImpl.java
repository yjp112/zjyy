package com.supconit.nhgl.basic.meterConfig.electic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.meterConfig.electic.dao.ConfigManageDao;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.HolidayConstans;

@Service
public class ConfigManagerServiceImpl implements ConfigManagerService{
	private static final String dayTimeCode=HolidayConstans.ELECTRIC_DAY_CODE;
	private static final String nightCode=HolidayConstans.ELECTRIC_NIGHT_CODE;
	@Autowired
	private ConfigManageDao cmdao;
	
	@Override
	public List<ConfigManager> getConfigInfo() {
		return cmdao.getConfigInfo();
	}

	@Override
	public void update(ConfigManager cm) {

		cmdao.update(cm);
	}

	@Override
	public List<ConfigManager> getConfigValue(ConfigManager cm) {
		return cmdao.getConfigValue(cm);
	}

	@Override
	public ConfigManager findByCode(String code) {
		return cmdao.findByCode(code);
	}

	@Override
	public List<ConfigManager> findValidByCode(String typeCode) {
		return cmdao.findValidByCode(typeCode);
	}

	@Override
	public ConfigManager getDayTimeConfig() {
		ConfigManager configDayTime=cmdao.findByCode(dayTimeCode);
		String dayTimeMin=configDayTime.getConfigValue().substring(0,5);
		String dayTimeMax=configDayTime.getConfigValue().substring(6, 11);
		dayTimeMax=String.valueOf(Integer.valueOf(dayTimeMax.substring(0, dayTimeMax.length()-3))+1);
		if(dayTimeMax.length()<2){
			dayTimeMax="0"+dayTimeMax;
		}
		dayTimeMax=dayTimeMax+":00";
		configDayTime.setDayTimeMin(dayTimeMin);
		configDayTime.setDayTimeMax(dayTimeMax);
		return configDayTime;
	}

	@Override
	public ConfigManager getNightConfig() {
		ConfigManager configNight=cmdao.findByCode(nightCode);
		String NightMin=configNight.getConfigValue().substring(0,5);
		String NightMax=configNight.getConfigValue().substring(6, 11);
		NightMax=String.valueOf(Integer.valueOf(NightMax.substring(0, NightMax.length()-3))+1);
		if(NightMax.length()<2){
			NightMax="0"+NightMax;
		}
		NightMax=NightMax+":00";
		configNight.setNightMin(NightMin);
		configNight.setNightMax(NightMax);
		return configNight;
	}
}
