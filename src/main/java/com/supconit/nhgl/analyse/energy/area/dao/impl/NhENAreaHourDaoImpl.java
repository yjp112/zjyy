package com.supconit.nhgl.analyse.energy.area.dao.impl;


import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.energy.area.dao.NhENAreaHourDao;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaHour;


@Repository
public class NhENAreaHourDaoImpl extends AbstractBasicDaoImpl<NhENAreaHour,Long> implements NhENAreaHourDao {
	private static final String NAMESPACE=NhENAreaHour.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<NhENAreaHour> getAllSubEnergy(NhENAreaHour em){
		return selectList("getAllSubEnergy", em);
	}

	@Override
	public List<NhENAreaHour> getDayofSubEnergy(NhENAreaHour em) {
		return selectList("getDayofSubEnergy",em);
	}

	@Override
	public NhENAreaHour getSumDayofSubEnergy(NhENAreaHour em) {
		return selectOne("getSumDayofSubEnergy",em);
	}

	@Override
	public List<NhENAreaHour> getTwoDayofSubEnergy(NhENAreaHour em) {
		return selectList("getTwoDayofSubEnergy",em);
	}

	@Override
	public List<NhENAreaHour> getAllAreaEnergy(NhENAreaHour em) {
		return selectList("getAllAreaEnergy",em);
	}

	@Override
	public List<NhENAreaHour> getDayofAreaEnergy(NhENAreaHour em) {
		return selectList("getDayofAreaEnergy",em);
	}

	@Override
	public NhENAreaHour getTwoDayofAreaEnergy(NhENAreaHour em) {
		return selectOne("getTwoDayofAreaEnergy", em);
	}
}
