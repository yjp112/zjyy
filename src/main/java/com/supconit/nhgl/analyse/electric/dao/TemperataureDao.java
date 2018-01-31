package com.supconit.nhgl.analyse.electric.dao;

import java.util.List;

import com.supconit.nhgl.analyse.electric.entities.Temperataure;

import hc.orm.BasicDao;

public interface TemperataureDao extends BasicDao<Temperataure, Long>{
	
	List<Temperataure> findByCon(Temperataure ssi);
}
