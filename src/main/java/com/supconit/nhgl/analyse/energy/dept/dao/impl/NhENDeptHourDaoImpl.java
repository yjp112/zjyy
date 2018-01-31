package com.supconit.nhgl.analyse.energy.dept.dao.impl;


import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.energy.dept.dao.NhENDeptHourDao;
import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptHour;


@Repository
public class NhENDeptHourDaoImpl extends AbstractBasicDaoImpl<NhENDeptHour,Long> implements NhENDeptHourDao {
	private static final String NAMESPACE=NhENDeptHour.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<NhENDeptHour> getAllSubEnergy(NhENDeptHour em){
		return selectList("getAllSubEnergy", em);
	}

	@Override
	public List<NhENDeptHour> getDayofSubEnergy(NhENDeptHour em) {
		return selectList("getDayofSubEnergy",em);
	}


	@Override
	public NhENDeptHour getTwoDayofSubEnergy(NhENDeptHour em) {
		return selectOne("getTwoDayofSubEnergy",em);
	}

	@Override
	public List<NhENDeptHour> getDayofDeptEnergy(NhENDeptHour em) {
		return selectList("getDayofDeptEnergy",em);
	}
}
