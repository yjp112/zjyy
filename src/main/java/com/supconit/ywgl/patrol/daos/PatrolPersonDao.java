package com.supconit.ywgl.patrol.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.ywgl.patrol.entities.PatrolPerson;

public interface PatrolPersonDao  extends BaseDao<PatrolPerson, Long>{
			//查找所有的巡更人员
			List<PatrolPerson> findAll();
}
