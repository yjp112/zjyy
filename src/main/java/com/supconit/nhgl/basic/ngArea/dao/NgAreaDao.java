package com.supconit.nhgl.basic.ngArea.dao;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;

public interface NgAreaDao extends BaseDao<NgArea, Long>{
	//查询分页
			Pageable<NgArea> findByPage(Pageable<NgArea> pager,NgArea condition);
			
			List<NgArea> findAll();

			

			List<NgArea> findById(Long id);
		 

			List<NgArea> findByCode(String code);
			
			List<NgArea> findByName(NgArea area);
 
			List<NgArea> findByRoot(Long id);

			List<NgArea> findByConfig(Long nhType);

			List<NgArea> findAllExceptionRoot();

			List<NgArea> findAllDArea();

			List<NgArea> findAllSArea();  

			

			
}
