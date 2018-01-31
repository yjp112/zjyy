package com.supconit.nhgl.query.complex.service.impl;

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
import com.supconit.nhgl.query.complex.dao.ComplexDao;
import com.supconit.nhgl.query.complex.entities.Complex;
import com.supconit.nhgl.query.complex.service.ComplexService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class ComplexServiceImpl extends AbstractBasicOrmService<Complex,Long> implements ComplexService{

	@Autowired
	ComplexDao deviceDao;
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
	public Pageable<Complex> findByConditon(Pageable<Complex> pager,
			Complex condition) {
		//setParameter(condition);
		//condition.setCategoryCode(electricCatagoryCode);//水表的设备类别编码
		return deviceDao.findByCondition(pager, condition);
	}

	@Override
	public List<Complex> findById(Long id) {
		return deviceDao.findById(id);
	}
	public void setParameter(Complex condition){
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
	public Complex getById(Long arg0) {
		return null;
	}
	
	@Override
	public double sumByCondition(Complex condition) {
		return deviceDao.sumByCondition(condition);
	}
	@Override
	public void insert(Complex entity) {
		deviceDao.insert(entity);
	}

	@Override
	public void update(Complex entity) {
		deviceDao.update(entity);
	}

	@Override
	public void delete(Complex entity) {
		deviceDao.delete(entity);
	}
	@Override
	public Pageable<Complex> findByConditonPower(Pageable<Complex> pager,
			Complex condition) {
		setParameter(condition);
		condition.setBarcode(electricCatagoryCode);//水表的设备类别编码
		return deviceDao.findByConditionPower(pager, condition);
	}
	@Override
	@Transactional
	public void update(List<Complex> lst) {
		if(!UtilTool.isEmptyList(lst)){
			for (Complex d:lst) {
				deviceDao.update(d);
			}   
		}
	}

	@Override
	public Pageable<Complex> findByWaterConditon(Pageable<Complex> pager,
			Complex condition) {
		//setParameter(condition);
		// condition.setCategoryCode(waterCatagoryCode);//水表的设备类别编码
		return deviceDao.findByWaterCondition(pager, condition);
	}
	@Override
	public Pageable<Complex> findByEnergyConditon(Pageable<Complex> pager,
			Complex condition) {
		//setParameter(condition);
		//condition.setCategoryCode(energyCatagoryCode);//能量表的设备类别编码
		return deviceDao.findByEnergyCondition(pager, condition);
	}
	
	@Override
	public Pageable<Complex> findByGasConditon(Pageable<Complex> pager,
			Complex condition) {
		//setParameter(condition);
		//condition.setCategoryCode(gasCatagoryCode);//能量表的设备类别编码
		return deviceDao.findByGasCondition(pager, condition);
	}
}
