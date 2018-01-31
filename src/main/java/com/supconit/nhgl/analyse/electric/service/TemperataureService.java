package com.supconit.nhgl.analyse.electric.service;

import java.util.List;

import com.supconit.nhgl.analyse.electric.entities.Temperataure;

public interface TemperataureService {
	
	List<Temperataure> findByCon(Temperataure ssi);
}
