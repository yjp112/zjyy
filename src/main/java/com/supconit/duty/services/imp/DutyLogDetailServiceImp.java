package com.supconit.duty.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.duty.daos.DutyLogDetailDao;
import com.supconit.duty.entities.DutyLogDetail;
import com.supconit.duty.services.DutyLogDetailService;
@Service
public class DutyLogDetailServiceImp extends AbstractBaseBusinessService<DutyLogDetail, Long> implements DutyLogDetailService{
	
	@Autowired
	private DutyLogDetailDao		dutyLogDetailDao;	
	
	@Override
	@Transactional(readOnly = true) 
	public DutyLogDetail getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DutyLogDetail dutyLogDetail = dutyLogDetailDao.getById(id);
		return dutyLogDetail;
	} 

	


	@Override
	public List<DutyLogDetail> findAll(Long id) {
		// TODO Auto-generated method stub
		return dutyLogDetailDao.findAll(id);
	}




	@Override
	public void insert(DutyLogDetail entity) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void update(DutyLogDetail dutyLogDetail) {
		dutyLogDetailDao.update(dutyLogDetail);
		
	}




	@Override
	public void deleteByIds(Long[] ids) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}



}
