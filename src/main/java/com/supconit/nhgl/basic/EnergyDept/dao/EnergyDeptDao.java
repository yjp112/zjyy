package com.supconit.nhgl.basic.EnergyDept.dao;

import java.util.List;

import com.supconit.nhgl.basic.EnergyDept.entities.EnergyDept;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;

public interface EnergyDeptDao extends BasicDao<EnergyDept,Long>{
	Pageable<EnergyDept> findByCondition(Pageable<EnergyDept> pager, EnergyDept condition);

	List<EnergyDept> findDmByCondition(EnergyDept dm);


	int insert(EnergyDept dm);
}
