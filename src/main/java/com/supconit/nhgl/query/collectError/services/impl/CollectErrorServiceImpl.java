package com.supconit.nhgl.query.collectError.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.basic.ngArea.dao.NgAreaDao;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.query.collectError.daos.CollectErrorDao;
import com.supconit.nhgl.query.collectError.entities.CollectError;
import com.supconit.nhgl.query.collectError.services.CollectErrorService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class CollectErrorServiceImpl extends AbstractBasicOrmService<CollectError,Long> implements CollectErrorService{

	@Autowired
	CollectErrorDao deviceDao;
	@Autowired
	NgAreaDao ngAreaDao;
	@Value("${electric_category}")
	private String electricCatagoryCode;
	@Value("${water_category}")
	private String waterCatagoryCode;
	@Value("${energy_category}")
	private String energyCatagoryCode;
	@Value("${gas_category}")
	private String gasCatagoryCode;

	@Override
	public Pageable<CollectError> findByConditon(Pageable<CollectError> pager,
			CollectError condition) {
		//setParameter(condition);
		//condition.setCategoryCode(electricCatagoryCode);//水表的设备类别编码
		return deviceDao.findByCondition(pager, condition);
	}

	@Override
	public List<CollectError> findById(Long id) {
		return deviceDao.findById(id);
	}
	public void setParameter(CollectError condition){
        if(condition.getLocationId()!=null){
            List<NgArea> listGeoAreas = ngAreaDao.findById(condition.getLocationId());
            List<Long> lstLocationId = new ArrayList<Long>();
            if(!UtilTool.isEmptyList(listGeoAreas)){
            	for(NgArea g:listGeoAreas){
            		lstLocationId.add(g.getId());
            	}
            }
            condition.setLstLocationId(lstLocationId);
          }	
	}
	@Override
	public CollectError getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(CollectError entity) {
		deviceDao.insert(entity);
	}

	@Override
	public void update(CollectError entity) {
		deviceDao.update(entity);
	}

	@Override
	public void delete(CollectError entity) {
		deviceDao.delete(entity);
	}
	@Override
	public Pageable<CollectError> findByConditonPower(Pageable<CollectError> pager,
			CollectError condition) {
		setParameter(condition);
		condition.setBarcode(electricCatagoryCode);//水表的设备类别编码
		return deviceDao.findByConditionPower(pager, condition);
	}
	@Override
	@Transactional
	public void update(List<CollectError> lst) {
		if(!UtilTool.isEmptyList(lst)){
			for (CollectError d:lst) {
				deviceDao.update(d);
			}   
		}
	}

	@Override
	public Pageable<CollectError> findByWaterConditon(Pageable<CollectError> pager,
			CollectError condition) {
		//setParameter(condition);
		// condition.setCategoryCode(waterCatagoryCode);//水表的设备类别编码
		return deviceDao.findByWaterCondition(pager, condition);
	}
	@Override
	public Pageable<CollectError> findByEnergyConditon(Pageable<CollectError> pager,
			CollectError condition) {
		//setParameter(condition);
		//condition.setCategoryCode(energyCatagoryCode);//能量表的设备类别编码
		return deviceDao.findByEnergyCondition(pager, condition);
	}
	
	@Override
	public Pageable<CollectError> findByGasConditon(Pageable<CollectError> pager,
			CollectError condition) {
		//setParameter(condition);
		//condition.setCategoryCode(gasCatagoryCode);//能量表的设备类别编码
		return deviceDao.findByGasCondition(pager, condition);
	}
}
