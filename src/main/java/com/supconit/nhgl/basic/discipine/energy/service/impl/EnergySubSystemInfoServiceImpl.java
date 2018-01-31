package com.supconit.nhgl.basic.discipine.energy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.discipine.energy.dao.EnergySubSystemInfoDao;
import com.supconit.nhgl.basic.discipine.energy.entities.EnergySubSystemInfo;
import com.supconit.nhgl.basic.discipine.energy.service.EnergySubSystemInfoService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class EnergySubSystemInfoServiceImpl extends AbstractBasicOrmService<EnergySubSystemInfo,Long> implements EnergySubSystemInfoService{

	@Autowired
	private EnergySubSystemInfoDao energySubSystemInfoDao;
	
	@Override 
	public EnergySubSystemInfo getById(Long id) {
		return energySubSystemInfoDao.getById(id);
	}

	@Override
	public void insert(EnergySubSystemInfo entity) {
        energySubSystemInfoDao.insert(entity);		
	}

	@Override
	public void update(EnergySubSystemInfo entity) {
		energySubSystemInfoDao.update(entity);
	}

	@Override
	public void delete(EnergySubSystemInfo entity) {
		energySubSystemInfoDao.delete(entity);
	}

	@Override
	public Pageable<EnergySubSystemInfo> findByCondition(
			Pageable<EnergySubSystemInfo> pager, EnergySubSystemInfo condition) {
		if(condition.getParentId()!=null){
			List<String> standardCodes=energySubSystemInfoDao.selectChilrenStandardCodes(condition.getParentId());
			condition.setParentId(new String());
			condition.setStandardCodes(standardCodes);
		}
		return energySubSystemInfoDao.findByCondition(pager, condition);
	}

	@Override
	public EnergySubSystemInfo findById(Long id) {
		return energySubSystemInfoDao.findById(id);
	}

	@Override
	public int deleteById(Long id) {
		return energySubSystemInfoDao.deleteById(id);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		return energySubSystemInfoDao.deleteByIds(ids);
	}

	@Override
	public List<EnergySubSystemInfo> findAll() {
		return energySubSystemInfoDao.findAll();
	}

	@Override
	public List<EnergySubSystemInfo> selecCategories() {
		return energySubSystemInfoDao.selecCategories();
	}

	@Override
	public EnergySubSystemInfo findByStandardCode(String StandardCode) {
		return energySubSystemInfoDao.findByStandardCode(StandardCode);
	}

	@Override
	public List<EnergySubSystemInfo> findChildren(String standardCode) {
		return energySubSystemInfoDao.findChildren(standardCode);
	}

	@Override
	public List<EnergySubSystemInfo> findByCon(EnergySubSystemInfo ssi) {
		return energySubSystemInfoDao.findByCon(ssi);
	}

	@Override
	public List<EnergySubSystemInfo> findByConEnergy(EnergySubSystemInfo ssi) {
		return energySubSystemInfoDao.findByConEnergy(ssi);
	}

}
