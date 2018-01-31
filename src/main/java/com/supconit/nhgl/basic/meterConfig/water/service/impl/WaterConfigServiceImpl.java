package com.supconit.nhgl.basic.meterConfig.water.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.basic.meterConfig.water.dao.WaterConfigDao;
import com.supconit.nhgl.basic.meterConfig.water.entities.WaterConfig;
import com.supconit.nhgl.basic.meterConfig.water.service.WaterConfigService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;
@Service
public class WaterConfigServiceImpl extends AbstractBasicOrmService<WaterConfig,Long> 
	implements WaterConfigService{

	@Autowired
	WaterConfigDao waterConfigDao;
	@Autowired
	GeoAreaDao geoAreaDao;
	@Value("${water_category}")
	private String waterCatagoryCode;
	
	public void setParameter(WaterConfig condition){
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
	public Pageable<WaterConfig> findByConditonPower(Pageable<WaterConfig> pager,
			WaterConfig condition) {
		setParameter(condition);
		condition.setBarcode(waterCatagoryCode);//水表的设备类别编码
		return waterConfigDao.findByConditionPower(pager, condition);
	}
	@Override
	@Transactional
	public void update(List<WaterConfig> lst) {
		if(!UtilTool.isEmptyList(lst)){
			for (WaterConfig d:lst) {
				waterConfigDao.update(d);
			}   
		}
	}
	@Override
	public WaterConfig getById(Long arg0) {
		return waterConfigDao.getById(arg0);
	}
	@Override
	public void insert(WaterConfig entity) {
		waterConfigDao.insert(entity);
	}
	@Override
	public void update(WaterConfig entity) {
		waterConfigDao.update(entity);
	}
	@Override
	public void delete(WaterConfig entity) {
		waterConfigDao.delete(entity);
	}
}
