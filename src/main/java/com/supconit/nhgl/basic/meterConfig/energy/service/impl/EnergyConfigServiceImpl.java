package com.supconit.nhgl.basic.meterConfig.energy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.basic.meterConfig.energy.dao.EnergyConfigDao;
import com.supconit.nhgl.basic.meterConfig.energy.entities.EnergyConfig;
import com.supconit.nhgl.basic.meterConfig.energy.service.EnergyConfigService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class EnergyConfigServiceImpl extends AbstractBasicOrmService<EnergyConfig,Long> implements EnergyConfigService{

	@Autowired
	private EnergyConfigDao energyConfigDao;
	@Autowired
	GeoAreaDao geoAreaDao;
	@Value("${energy_category}")
	private String energyCatagoryCode;
	
	@Override
	public EnergyConfig getById(Long arg0) {
		return energyConfigDao.getById(arg0);
	}

	@Override
	public void insert(EnergyConfig entity) {
		energyConfigDao.insert(entity);
	}

	@Override
	public void update(EnergyConfig entity) {
		energyConfigDao.update(entity);
	}

	@Override
	public void delete(EnergyConfig entity) {
		energyConfigDao.delete(entity);
	}

	public void setParameter(EnergyConfig condition){
        if(condition.getLocationId()!=null){
            List<GeoArea> listGeoAreas = geoAreaDao.findById(condition.getLocationId());
            List<Long> lstLocationId = new ArrayList<Long>();
            if(!UtilTool.isEmptyList(listGeoAreas)){
            	for(GeoArea g:listGeoAreas){
            		lstLocationId.add(g.getId());
            	}
            }
            condition.setLstLocationId(lstLocationId);
          }	
	}
	
	@Override
	public Pageable<EnergyConfig> findByConditonPower(
			Pageable<EnergyConfig> pager, EnergyConfig condition) {
		setParameter(condition);
		condition.setBarcode(energyCatagoryCode);//能量表的设备类别编码
		return energyConfigDao.findByConditionPower(pager, condition);
	}

	@Override
	public void update(List<EnergyConfig> lst) {
		if(!UtilTool.isEmptyList(lst)){
			for (EnergyConfig d:lst) {
				energyConfigDao.update(d);
			}   
		}
	}

}
