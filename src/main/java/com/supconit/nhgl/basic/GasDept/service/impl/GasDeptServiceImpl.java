package com.supconit.nhgl.basic.GasDept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.GasDept.dao.GasDeptDao;
import com.supconit.nhgl.basic.GasDept.entities.GasDept;
import com.supconit.nhgl.basic.GasDept.service.GasDeptService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class GasDeptServiceImpl extends AbstractBasicOrmService<GasDept,Long> implements GasDeptService{

	@Autowired
	private GasDeptDao gasDeptDao;
	@Override
	public GasDept getById(Long arg0) {
		return gasDeptDao.getById(arg0);
	}

	@Override
	public void insert(GasDept entity) {
		 gasDeptDao.insert(entity);
	}

	@Override
	public void update(GasDept entity) {
		gasDeptDao.update(entity);
	}

	@Override
	public void delete(GasDept entity) {
		gasDeptDao.delete(entity);
	}

	@Override
	public Pageable<GasDept> findByCondition(Pageable<GasDept> pager,
			GasDept condition) {
		return gasDeptDao.findByCondition(pager, condition);
	}

	@Override
	public List<GasDept> findDmByCondition(GasDept dm) {
		return gasDeptDao.findDmByCondition(dm);
	}

}
