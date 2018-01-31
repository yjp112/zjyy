package com.supconit.nhgl.basic.electricDept.dao;

import java.util.List;

import com.supconit.nhgl.basic.electricDept.entities.ElectricDept;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;



public interface ElectricDeptDao extends BasicDao<ElectricDept,Long>{
	Pageable<ElectricDept> findByCondition(Pageable<ElectricDept> pager, ElectricDept condition);

	List<ElectricDept> findDmByCondition(ElectricDept dm);


	int insert(ElectricDept dm);

}
