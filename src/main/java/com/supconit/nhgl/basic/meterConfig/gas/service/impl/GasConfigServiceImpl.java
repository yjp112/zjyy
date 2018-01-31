package com.supconit.nhgl.basic.meterConfig.gas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.basic.meterConfig.gas.dao.GasConfigDao;
import com.supconit.nhgl.basic.meterConfig.gas.entities.GasConfig;
import com.supconit.nhgl.basic.meterConfig.gas.service.GasConfigService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class GasConfigServiceImpl extends AbstractBasicOrmService<GasConfig,Long> implements GasConfigService{

	@Autowired
	private GasConfigDao gasConfigDao;
	@Autowired
	GeoAreaDao geoAreaDao;
	@Value("${gas_category}")
	private String gasCatagoryCode;
	
	@Override
	public GasConfig getById(Long arg0) {
		return gasConfigDao.getById(arg0);
	}

	@Override
	public void insert(GasConfig entity) {
		gasConfigDao.insert(entity);
	}

	@Override
	public void update(GasConfig entity) {
		gasConfigDao.update(entity);
	}

	@Override
	public void delete(GasConfig entity) {
		gasConfigDao.delete(entity);
	}

	public void setParameter(GasConfig condition){
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
	public Pageable<GasConfig> findByConditonPower(
			Pageable<GasConfig> pager, GasConfig condition) {
		setParameter(condition);
		condition.setBarcode(gasCatagoryCode);//能量表的设备类别编码
		return gasConfigDao.findByConditionPower(pager, condition);
	}

	@Override
	public void update(List<GasConfig> lst) {
		if(!UtilTool.isEmptyList(lst)){
			for (GasConfig d:lst) {
				gasConfigDao.update(d);
			}   
		}
	}

}
