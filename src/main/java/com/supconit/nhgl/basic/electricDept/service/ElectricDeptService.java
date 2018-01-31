package com.supconit.nhgl.basic.electricDept.service;

import java.util.List;

import com.supconit.nhgl.basic.electricDept.entities.ElectricDept;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;




public interface ElectricDeptService extends BasicOrmService<ElectricDept,Long>{
	Pageable<ElectricDept> findByCondition(Pageable<ElectricDept> pager,ElectricDept condition);
//	String buildTree(List<SubSystemInfo> slist);

	List<ElectricDept> findDmByCondition(ElectricDept dm);
}
