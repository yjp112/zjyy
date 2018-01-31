package com.supconit.nhgl.basic.waterDept.dao;

import java.util.List;

import com.supconit.nhgl.basic.waterDept.entities.WaterDept;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;

public interface WaterDeptDao extends BasicDao<WaterDept,Long>{
	Pageable<WaterDept> findByCondition(Pageable<WaterDept> pager, WaterDept condition);

	List<WaterDept> findDmByCondition(WaterDept dm);


	int insert(WaterDept dm);
}
