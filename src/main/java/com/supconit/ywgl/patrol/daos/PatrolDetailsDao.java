package com.supconit.ywgl.patrol.daos;


import com.supconit.common.daos.BaseDao;
import com.supconit.ywgl.patrol.entities.PatrolDetails;

public interface PatrolDetailsDao extends BaseDao<PatrolDetails, Long>{ 

	long findDetailsCuont(PatrolDetails patrolDetails);   

}
