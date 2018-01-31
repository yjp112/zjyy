package com.supconit.nhgl.basic.waterDept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.waterDept.dao.WaterDeptDao;
import com.supconit.nhgl.basic.waterDept.entities.WaterDept;
import com.supconit.nhgl.basic.waterDept.service.WaterDeptService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class WaterDeptServiceImpl extends AbstractBasicOrmService<WaterDept,Long> implements WaterDeptService{

	@Autowired
	private WaterDeptDao waterDeptDao;
	@Override
	public WaterDept getById(Long arg0) {
		return waterDeptDao.getById(arg0);
	}

	@Override
	public void insert(WaterDept entity) {
		 waterDeptDao.insert(entity);
	}

	@Override
	public void update(WaterDept entity) {
		waterDeptDao.update(entity);
	}

	@Override
	public void delete(WaterDept entity) {
		waterDeptDao.delete(entity);
	}

	@Override
	public Pageable<WaterDept> findByCondition(Pageable<WaterDept> pager,
			WaterDept condition) {
		return waterDeptDao.findByCondition(pager, condition);
	}

	@Override
	public List<WaterDept> findDmByCondition(WaterDept dm) {
		return waterDeptDao.findDmByCondition(dm);
	}

}
