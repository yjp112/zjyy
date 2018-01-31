package com.supconit.nhgl.basic.GasDept.dao;

import java.util.List;

import com.supconit.nhgl.basic.GasDept.entities.GasDept;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;

public interface GasDeptDao extends BasicDao<GasDept,Long>{
	Pageable<GasDept> findByCondition(Pageable<GasDept> pager, GasDept condition);

	List<GasDept> findDmByCondition(GasDept dm);


	int insert(GasDept dm);
}
