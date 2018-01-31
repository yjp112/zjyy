package com.supconit.nhgl.analyse.electric.dept.dao.impl;


import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dept.dao.NhDDeptHourDao;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptHour;

@Repository
public class NhDDeptHourDaoImpl extends AbstractBasicDaoImpl<NhDDeptHour,Long> implements NhDDeptHourDao {
	private static final String NAMESPACE=NhDDeptHour.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<NhDDeptHour> getAllSubElectricty(NhDDeptHour em){
		return selectList("getAllSubElectricty", em);
	}

	@Override
	public List<NhDDeptHour> getDayofSubElectricty(NhDDeptHour em) {
		return selectList("getDayofSubElectricty",em);
	}


	@Override
	public NhDDeptHour getTwoDayofSubElectricty(NhDDeptHour em) {
		return selectOne("getTwoDayofSubElectricty",em);
	}

	@Override
	public List<NhDDeptHour> getDayofDeptElectricty(NhDDeptHour em) {
		return selectList("getDayofDeptElectricty",em);
	}

	@Override
	public List<NhDDeptHour> getSubInfoDetailByDept(NhDDeptHour elecDept) {
		return selectList("getSubInfoDetailByDept", elecDept);
	}
}
