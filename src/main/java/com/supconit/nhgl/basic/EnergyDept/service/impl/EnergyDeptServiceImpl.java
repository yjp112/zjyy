package com.supconit.nhgl.basic.EnergyDept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.EnergyDept.dao.EnergyDeptDao;
import com.supconit.nhgl.basic.EnergyDept.entities.EnergyDept;
import com.supconit.nhgl.basic.EnergyDept.service.EnergyDeptService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class EnergyDeptServiceImpl extends AbstractBasicOrmService<EnergyDept,Long> implements EnergyDeptService{

	@Autowired
	private EnergyDeptDao energyDeptDao;
	@Override
	public EnergyDept getById(Long arg0) {
		return energyDeptDao.getById(arg0);
	}

	@Override
	public void insert(EnergyDept entity) {
		 energyDeptDao.insert(entity);
	}

	@Override
	public void update(EnergyDept entity) {
		energyDeptDao.update(entity);
	}

	@Override
	public void delete(EnergyDept entity) {
		energyDeptDao.delete(entity);
	}

	@Override
	public Pageable<EnergyDept> findByCondition(Pageable<EnergyDept> pager,
			EnergyDept condition) {
		return energyDeptDao.findByCondition(pager, condition);
	}

	@Override
	public List<EnergyDept> findDmByCondition(EnergyDept dm) {
		return energyDeptDao.findDmByCondition(dm);
	}

}
