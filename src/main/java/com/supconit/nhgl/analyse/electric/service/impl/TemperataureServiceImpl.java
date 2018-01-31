package com.supconit.nhgl.analyse.electric.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.analyse.electric.dao.TemperataureDao;
import com.supconit.nhgl.analyse.electric.entities.Temperataure;
import com.supconit.nhgl.analyse.electric.service.TemperataureService;
@Service
public class TemperataureServiceImpl implements TemperataureService {
	@Autowired
	private TemperataureDao	dao;
	
	@Override
	public List<Temperataure> findByCon(Temperataure ssi) {
		return dao.findByCon(ssi);
	}

}
