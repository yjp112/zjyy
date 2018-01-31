package com.supconit.nhgl.analyse.gas.area.dao.impl;


import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.gas.area.dao.NhQAreaHourDao;
import com.supconit.nhgl.analyse.gas.area.entities.NhQAreaHour;




@Repository
public class NhQAreaHourDaoImpl extends AbstractBasicDaoImpl<NhQAreaHour,Long> implements NhQAreaHourDao {
	private static final String NAMESPACE=NhQAreaHour.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<NhQAreaHour> getAllSubGas(NhQAreaHour em){
		return selectList("getAllSubGas", em);
	}

	@Override
	public List<NhQAreaHour> getDayofSubGas(NhQAreaHour em) {
		return selectList("getDayofSubGas",em);
	}

	@Override
	public NhQAreaHour getSumDayofSubGas(NhQAreaHour em) {
		return selectOne("getSumDayofSubGas",em);
	}

	@Override
	public List<NhQAreaHour> getTwoDayofSubGas(NhQAreaHour em) {
		return selectList("getTwoDayofSubGas",em);
	}

	@Override
	public List<NhQAreaHour> getAllAreaGas(NhQAreaHour em) {
		return selectList("getAllAreaGas",em);
	}

	@Override
	public List<NhQAreaHour> getDayofAreaGas(NhQAreaHour em) {
		return selectList("getDayofAreaGas",em);
	}

	@Override
	public NhQAreaHour getTwoDayofAreaGas(NhQAreaHour em) {
		return selectOne("getTwoDayofAreaGas",em);
	}
}
