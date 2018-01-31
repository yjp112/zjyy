package com.supconit.nhgl.analyse.water.dept.dao.impl;


import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.water.dept.dao.NhSDeptHourDao;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptHour;


@Repository
public class NhSDeptHourDaoImpl extends AbstractBasicDaoImpl<NhSDeptHour,Long> implements NhSDeptHourDao {
	private static final String NAMESPACE=NhSDeptHour.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<NhSDeptHour> getAllSubWater(NhSDeptHour em){
		return selectList("getAllSubWater", em);
	}

	@Override
	public List<NhSDeptHour> getDayofSubWater(NhSDeptHour em) {
		return selectList("getDayofSubWater",em);
	}


	@Override
	public NhSDeptHour getTwoDayofSubWater(NhSDeptHour em) {
		return selectOne("getTwoDayofSubWater",em);
	}

	@Override
	public List<NhSDeptHour> getDayofDeptWater(NhSDeptHour em) {
		return selectList("getDayofDeptWater",em);
	}
}
