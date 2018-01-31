package com.supconit.nhgl.analyse.water.area.dao.impl;


import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.water.area.dao.NhSAreaHourDao;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaHour;



@Repository
public class NhSAreaHourDaoImpl extends AbstractBasicDaoImpl<NhSAreaHour,Long> implements NhSAreaHourDao {
	private static final String NAMESPACE=NhSAreaHour.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<NhSAreaHour> getAllSubWater(NhSAreaHour em){
		return selectList("getAllSubWater", em);
	}

	@Override
	public List<NhSAreaHour> getDayofSubWater(NhSAreaHour em) {
		return selectList("getDayofSubWater",em);
	}

	@Override
	public List<NhSAreaHour> getTwoDayofSubWater(NhSAreaHour em) {
		return selectList("getTwoDayofSubWater",em);
	}

	@Override
	public NhSAreaHour getSumDayofSubWater(NhSAreaHour em) {
		return selectOne("getSumDayofSubWater",em);
	}

	@Override
	public List<NhSAreaHour> getAllAreaWater(NhSAreaHour em) {
		return selectList("getAllAreaWater",em);
	}

	@Override
	public List<NhSAreaHour> getDayofAreaWater(NhSAreaHour em) {
		return selectList("getDayofAreaWater",em);
	}

	@Override
	public NhSAreaHour getTwoDayofAreaWater(NhSAreaHour em) {
		return selectOne("getTwoDayofAreaWater",em);
	}
}
