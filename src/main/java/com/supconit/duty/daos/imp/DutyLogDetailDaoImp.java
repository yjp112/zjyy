package com.supconit.duty.daos.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.duty.daos.DutyLogDetailDao;
import com.supconit.duty.entities.DutyLogDetail;
@Repository
public class DutyLogDetailDaoImp extends AbstractBaseDao<DutyLogDetail,Long> implements DutyLogDetailDao{ 

	private static final String	NAMESPACE = DutyLogDetail.class.getName();
	@Override
	protected String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}


	@Override
	public void deleteByIds(Long[] ids) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<DutyLogDetail> findAll(Long id) {
		// TODO Auto-generated method stub
		return selectList("findAllById", id);
	}

}
