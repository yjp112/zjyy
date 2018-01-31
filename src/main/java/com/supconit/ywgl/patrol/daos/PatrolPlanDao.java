package com.supconit.ywgl.patrol.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.ywgl.patrol.entities.PatrolPlan;

public interface PatrolPlanDao extends BaseDao<PatrolPlan, Long>{
	//获取计划巡更次数
	long findPlanCuont(PatrolPlan patrolPlan);
}
