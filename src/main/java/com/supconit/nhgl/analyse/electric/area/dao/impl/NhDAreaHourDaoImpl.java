package com.supconit.nhgl.analyse.electric.area.dao.impl;


import hc.orm.AbstractBasicDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.area.dao.NhDAreaHourDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaHour;


@Repository
public class NhDAreaHourDaoImpl extends AbstractBasicDaoImpl<NhDAreaHour,Long> implements NhDAreaHourDao {
	private static final String NAMESPACE=NhDAreaHour.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	@Override
	public List<NhDAreaHour> getAllSubElectricty(NhDAreaHour em){
		return selectList("getAllSubElectricty", em);
	}

	@Override
	public List<NhDAreaHour> getDayofSubElectricty(NhDAreaHour em) {
		return selectList("getDayofSubElectricty",em);
	}

	@Override
	public NhDAreaHour getSumDayofSubElectricty(NhDAreaHour em) {
		return selectOne("getSumDayofSubElectricty",em);
	}

	@Override
	public NhDAreaHour getTwoDayofSubElectricty(NhDAreaHour em) {
		return selectOne("getTwoDayofSubElectricty",em);
	}
	@Override
	public List<NhDAreaHour> getAllAreaElectricty(NhDAreaHour em){
		return selectList("getAllAreaElectricty", em);
	}

	@Override
	public List<NhDAreaHour> getDayofAreaElectricty(NhDAreaHour em) {
		return selectList("getDayofAreaElectricty",em);
	}

	@Override
	public NhDAreaHour getTwoDayofAreaElectricty(NhDAreaHour em) {
		return selectOne("getTwoDayofAreaElectricty",em);
	}

	@Override
	public List<NhDAreaHour> getSubInfoDetailByArea(NhDAreaHour elec) {
		return selectList("getSubInfoDetailByArea", elec);
	}

	@Override
	public List<NhDAreaHour> getAreaDetail(NhDAreaHour elec) {
		return selectList("getAreaDetail", elec);
	}

	@Override
	public List<NhDAreaHour> getDayElectric(NhDAreaHour ectm) {
		return selectList("getDayElectric", ectm);
	}
}
