package com.supconit.nhgl.basic.sysConfig.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.basic.sysConfig.service.SysConfigService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class SysConfigServiceImpl extends AbstractBasicOrmService<Device,Long> implements SysConfigService{

	@Autowired
	DeviceDao deviceDao;
	@Autowired
	GeoAreaDao geoAreaDao;
	@Value("${electric_category}")
	private String electricCatagoryCode;
	

	@Override
	public Pageable<Device> findByConditon(Pageable<Device> pager,
			Device condition) {
		setParameter(condition);
		condition.setCategoryCode(electricCatagoryCode);//水表的设备类别编码
		return deviceDao.findByConditionSys(pager, condition);
	}
	
	@Override
	public Pageable<Device> findByAllConditon(Pageable<Device> pager,
			Device condition) {
		return deviceDao.findByAllConditonSys(pager, condition);
	}
	
	@Override
	public List<Device> findListByCondition(Device condition){
		
		return deviceDao.findListByConditionSys(condition);
	}
	
	@Override
	public Pageable<Device> findDeptByElectric(Pageable<Device> pager,Device condition){
		
		return deviceDao.findDeptByElectricSys(pager,condition);
	}
	
	@Override
	public Pageable<Device> findDeptByWater(Pageable<Device> pager,Device condition){
		
		return deviceDao.findDeptByWaterSys(pager,condition);
	}
	
	@Override
	public Pageable<Device> findDeptByGas(Pageable<Device> pager,Device condition){
		
		return deviceDao.findDeptByGasSys(pager,condition);
	}
	
	@Override
	public Pageable<Device> findDeptByEnergy(Pageable<Device> pager,Device condition){
		
		return deviceDao.findDeptByEnergySys(pager,condition);
	}

	@Override
	public List<Device> findById(Long id) {
		return deviceDao.findByIdSys(id);
	}
	public void setParameter(Device condition){
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
	public Device getById(Long arg0) {
		return deviceDao.getByIdSys(arg0);
	}

	@Override
	public void insert(Device entity) {
		deviceDao.insertSys(entity);
	}

	@Override
	public void update(Device entity) {
		deviceDao.updateSys(entity);
	}

	@Override
	public void delete(Device entity) {
		deviceDao.deleteSys(entity);
	}
	@Override
	public Pageable<Device> findByConditonPower(Pageable<Device> pager,
			Device condition) {
		setParameter(condition);
		condition.setBarcode(electricCatagoryCode);//水表的设备类别编码
		return deviceDao.findByConditionPowerSys(pager, condition);
	}
	@Override
	@Transactional
	public void update(List<Device> lst) {
		if(!UtilTool.isEmptyList(lst)){
			for (Device d:lst) {
				deviceDao.updateSys(d);
			}   
		}
	}
}
