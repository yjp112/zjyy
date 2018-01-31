package com.supconit.ywgl.patrol.daos.imp;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.ywgl.patrol.daos.PatrolPlanDao;
import com.supconit.ywgl.patrol.entities.PatrolPlan;
@Repository 
public class PatrolPlanDaoImp extends AbstractBaseDao<PatrolPlan,Long>  implements PatrolPlanDao{

	private static final String	NAMESPACE = PatrolPlan.class.getName();
	@Override
	protected String getNamespace() { 
		// TODO Auto-generated method stub 
		return NAMESPACE;
	}
	@Override
	public long findPlanCuont(PatrolPlan patrolPlan) {
		// TODO Auto-generated method stub
		return selectOne("findPlanCuont", patrolPlan); 
	}
}
