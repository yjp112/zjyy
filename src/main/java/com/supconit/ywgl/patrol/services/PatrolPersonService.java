package com.supconit.ywgl.patrol.services;

import java.util.List;

import com.supconit.ywgl.patrol.entities.PatrolPerson;


public interface PatrolPersonService {
		//查找所有的巡更人员
		List<PatrolPerson> findAll();
}
