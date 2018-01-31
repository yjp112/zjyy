package com.supconit.nhgl.analyse.electric.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dao.TemperataureDao;
import com.supconit.nhgl.analyse.electric.entities.Temperataure;

import hc.orm.AbstractBasicDaoImpl;
@Repository
public class TemperataureDaoImpl extends AbstractBasicDaoImpl<Temperataure, Long> implements TemperataureDao {

	private static final String NAMESPACE = Temperataure.class.getName();
	
	@Override
	public List<Temperataure> findByCon(Temperataure ssi) {
		return selectList("findByCon", ssi);
	}

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
}
