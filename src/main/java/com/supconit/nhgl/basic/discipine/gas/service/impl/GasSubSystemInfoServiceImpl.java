package com.supconit.nhgl.basic.discipine.gas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.discipine.gas.dao.GasSubSystemInfoDao;
import com.supconit.nhgl.basic.discipine.gas.entities.GasSubSystemInfo;
import com.supconit.nhgl.basic.discipine.gas.service.GasSubSystemInfoService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class GasSubSystemInfoServiceImpl extends AbstractBasicOrmService<GasSubSystemInfo,Long> implements GasSubSystemInfoService{

	@Autowired
	private GasSubSystemInfoDao gasSubSystemInfoDao;
	
	@Override 
	public GasSubSystemInfo getById(Long id) {
		return gasSubSystemInfoDao.getById(id);
	}

	@Override
	public void insert(GasSubSystemInfo entity) {
        gasSubSystemInfoDao.insert(entity);		
	}

	@Override
	public void update(GasSubSystemInfo entity) {
		gasSubSystemInfoDao.update(entity);
	}

	@Override
	public void delete(GasSubSystemInfo entity) {
		gasSubSystemInfoDao.delete(entity);
	}

	@Override
	public Pageable<GasSubSystemInfo> findByCondition(
			Pageable<GasSubSystemInfo> pager, GasSubSystemInfo condition) {
		if(condition.getParentId()!=null){
			List<String> standardCodes=gasSubSystemInfoDao.selectChilrenStandardCodes(condition.getParentId());
			condition.setParentId(new String());
			condition.setStandardCodes(standardCodes);
		}
		return gasSubSystemInfoDao.findByCondition(pager, condition);
	}

	@Override
	public GasSubSystemInfo findById(Long id) {
		return gasSubSystemInfoDao.findById(id);
	}

	@Override
	public int deleteById(Long id) {
		return gasSubSystemInfoDao.deleteById(id);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		return gasSubSystemInfoDao.deleteByIds(ids);
	}

	@Override
	public List<GasSubSystemInfo> findAll() {
		return gasSubSystemInfoDao.findAll();
	}

	@Override
	public List<GasSubSystemInfo> selecCategories() {
		return gasSubSystemInfoDao.selecCategories();
	}

	@Override
	public GasSubSystemInfo findByStandardCode(String StandardCode) {
		return gasSubSystemInfoDao.findByStandardCode(StandardCode);
	}

	@Override
	public List<GasSubSystemInfo> findChildren(String standardCode) {
		return gasSubSystemInfoDao.findChildren(standardCode);
	}

	@Override
	public List<GasSubSystemInfo> findByCon(GasSubSystemInfo gssi) {
		return gasSubSystemInfoDao.findByCon(gssi);
	}

	@Override
	public List<GasSubSystemInfo> findByConGas(GasSubSystemInfo gssi) {
		return gasSubSystemInfoDao.findByConGas(gssi);
	}

}
