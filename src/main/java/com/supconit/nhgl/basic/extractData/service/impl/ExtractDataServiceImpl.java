package com.supconit.nhgl.basic.extractData.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hc.base.domains.Pageable;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.nhgl.basic.extractData.daos.ExtractDataDao;
import com.supconit.nhgl.basic.extractData.entities.ExtractData;
import com.supconit.nhgl.basic.extractData.service.ExtractDataService;


@Service
public class ExtractDataServiceImpl extends AbstractBaseBusinessService<ExtractData, Long> implements ExtractDataService{

	@Autowired
	private ExtractDataDao extractDataDao;
	@Override
	public ExtractData getById(Long id) {
		return extractDataDao.getById(id);
	}

	@Override
	public void insert(ExtractData entity) {
	}



	@Override
	public void update(ExtractData entity) {
	}



	@Override
	public void deleteById(Long id) {
	}

	@Override
	public Pageable<ExtractData> find(Pageable<ExtractData> pager,
			ExtractData condition) {
		return extractDataDao.findByPager(pager, condition);
	}



}
