package com.supconit.nhgl.cost.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.base.dao.NhDeptDao;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.cost.dao.CostDao;
import com.supconit.nhgl.cost.entities.Cost;
import com.supconit.nhgl.cost.entities.RealTimeCost;
import com.supconit.nhgl.cost.service.CostService;

import hc.orm.AbstractBasicOrmService;

@Service
public class CostServiceImpl extends AbstractBasicOrmService<Cost, Long>
		implements CostService {

	@Autowired
	private CostDao costDao;
	@Autowired
	private NhDeptDao nhDeptDao;

	@Override
	public Cost getById(Long arg0) {
		return costDao.getById(arg0);
	}

	@Override
	public void insert(Cost entity) {
		costDao.insert(entity);
	}

	@Override
	public void update(Cost entity) {
		costDao.update(entity);
	}

	@Override
	public void delete(Cost entity) {
		costDao.delete(entity);
	}

	@Override
	public List<Cost> findByCondition(Cost condition) {
		if (condition.getDeptId() != null) {
			NhDept nhDept = nhDeptDao.getById(condition.getDeptId());
			condition.setLft(nhDept.getLft());
			condition.setRgt(nhDept.getRgt());
		}
		return costDao.findByCondition(condition);
	}

	@Override
	public List<RealTimeCost> findZkCost(RealTimeCost condition) {
		return costDao.findZkCost(condition);
	}

	@Override
	public List<Cost> findByConditionEletricity(Cost condition) {
		if (condition.getDeptId() != null) {
			NhDept nhDept = (NhDept) this.nhDeptDao.getById(condition.getDeptId());
			// 当前部门为总部门，设置部门id为null
			if (nhDept.getpId() != 0) {
				condition.setLft(nhDept.getLft());
				condition.setRgt(nhDept.getRgt());
			} else {
				condition.setDeptId(null);
			}
		}
		return costDao.findByConditionEletricity(condition);
	}

	public List<Cost> findByConditionWater(Cost condition) {
		if (condition.getDeptId() != null) {
			NhDept nhDept = (NhDept) this.nhDeptDao.getById(condition.getDeptId());
			// 当前部门为总部门，设置部门id为null
			if (nhDept.getpId() != 0) {
				condition.setLft(nhDept.getLft());
				condition.setRgt(nhDept.getRgt());
			} else {
				condition.setDeptId(null);
			}
		}
		return costDao.findByConditionWater(condition);
	}

}
