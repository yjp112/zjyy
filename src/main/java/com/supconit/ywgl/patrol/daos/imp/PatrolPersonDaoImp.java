package com.supconit.ywgl.patrol.daos.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.ywgl.patrol.daos.PatrolPersonDao;
import com.supconit.ywgl.patrol.entities.PatrolPerson;
@Repository
public class PatrolPersonDaoImp extends AbstractBaseDao<PatrolPerson,Long>  implements PatrolPersonDao{

	private static final String	NAMESPACE = PatrolPerson.class.getName();
	@Override
	protected String getNamespace() { 
		// TODO Auto-generated method stub
		return NAMESPACE;
	}
	@Override
	public List<PatrolPerson> findAll() {
		// TODO Auto-generated method stub
		return selectList("findAll"); 
	}
}
