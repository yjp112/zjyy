package com.supconit.maintain.services.impl;

import hc.base.domains.Pageable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.maintain.daos.MaintainTaskContentDao;
import com.supconit.maintain.entities.MaintainTaskContent;
import com.supconit.maintain.services.MaintainTaskContentService;

@Service
public class MaintainTaskContentServiceImpl extends AbstractBaseBusinessService<MaintainTaskContent, Long> implements MaintainTaskContentService{

	@Autowired
	private MaintainTaskContentDao maintainTaskContentDao;

	@Override
	public MaintainTaskContent getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(MaintainTaskContent entity) {
		
	}

	@Override
	public void update(MaintainTaskContent entity) {
		maintainTaskContentDao.update(entity);
	}

	@Override
	public void deleteById(Long id) {
		
	}
	
	@Override
	public List<MaintainTaskContent> selectMaintainTaskContentList(
			String maintainCode) {
		return maintainTaskContentDao.selectMaintainTaskContentList(maintainCode);
	}

	@Override
	public Pageable<MaintainTaskContent> findByCondition(
			Pageable<MaintainTaskContent> pager, MaintainTaskContent condition) {
		maintainTaskContentDao.findByCondition(pager, condition);
		int num = 1;
		for (MaintainTaskContent maintainTaskContent : pager) {
			maintainTaskContent.setRowNum(num);
			num++;
		}
		return pager;
	}

}
