package com.supconit.hikvision.services.impl;

import com.supconit.hikvision.daos.AccessCountDao;
import com.supconit.hikvision.entities.AccessCount;
import com.supconit.hikvision.services.AccessCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccessCountServiceImpl implements AccessCountService {
	
	@Autowired
	private AccessCountDao accessCountDao;

	/** 手机端使用 **/
	@Override
	public List<AccessCount> searchMonthAttendances(AccessCount condition) {
		return this.accessCountDao.searchMonthAttendances(condition);
	}

	@Override
	public List<AccessCount> searchUnusualMonthAttendances(AccessCount condition) {
		return this.accessCountDao.searchUnusualMonthAttendances(condition);
	}

	@Override
	public AccessCount countMonthAttendances(AccessCount condition) {
		return this.accessCountDao.countMonthAttendances(condition);
	}

	@Override
	public AccessCount searchAttendance(AccessCount condition) {
		return this.accessCountDao.searchAttendance(condition);
	}
}
