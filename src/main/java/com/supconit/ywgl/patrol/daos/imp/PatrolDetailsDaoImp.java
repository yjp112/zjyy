package com.supconit.ywgl.patrol.daos.imp;


import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.ywgl.patrol.daos.PatrolDetailsDao;
import com.supconit.ywgl.patrol.entities.PatrolDetails;
@Repository
public class PatrolDetailsDaoImp extends AbstractBaseDao<PatrolDetails,Long> implements PatrolDetailsDao{ 

	private static final String	NAMESPACE = PatrolDetails.class.getName();
	@Override
	protected String getNamespace(){  
		// TODO Auto-generated method stub
		return NAMESPACE;
	}
	@Override
	public long findDetailsCuont(PatrolDetails patrolDetails){
		// TODO Auto-generated method stub
		return selectOne("findDetailsCuont", patrolDetails); 
	}
	

}
