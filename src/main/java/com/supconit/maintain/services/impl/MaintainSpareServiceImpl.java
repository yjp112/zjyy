package com.supconit.maintain.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.maintain.daos.MaintainSpareDao;
import com.supconit.maintain.entities.MaintainSpare;
import com.supconit.maintain.services.MaintainSpareService;

@Service
public class MaintainSpareServiceImpl extends AbstractBaseBusinessService<MaintainSpare, Long> implements MaintainSpareService{

	@Autowired
	private MaintainSpareDao MaintainSpareDao;

	@Override
	public MaintainSpare getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(MaintainSpare entity) {
		
	}

	@Override
	public void update(MaintainSpare entity) {
		
	}

	@Override
	public void deleteById(Long id) {
		
	}
	
	@Override
	public List<MaintainSpare> selectMaintainSpareList(
			String maintainCode) {
		return MaintainSpareDao.selectMaintainSpareList(maintainCode);
	}

}
