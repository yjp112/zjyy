package com.supconit.nhgl.analyse.gas.dept.dao.impl;


import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.gas.dept.dao.NhQDeptHourDao;
import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptHour;


@Repository
public class NhQDeptHourDaoImpl extends AbstractBasicDaoImpl<NhQDeptHour,Long> implements NhQDeptHourDao {
	private static final String NAMESPACE=NhQDeptHour.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<NhQDeptHour> getAllSubGas(NhQDeptHour em){
		return selectList("getAllSubGas", em);
	}

	@Override
	public List<NhQDeptHour> getDayofSubGas(NhQDeptHour em) {
		return selectList("getDayofSubGas",em);
	}


	@Override
	public NhQDeptHour getTwoDayofSubGas(NhQDeptHour em) {
		return selectOne("getTwoDayofSubGas",em);
	}

	@Override
	public List<NhQDeptHour> getDayofDeptGas(NhQDeptHour em) {
		return selectList("getDayofDeptGas",em);
	}
}
