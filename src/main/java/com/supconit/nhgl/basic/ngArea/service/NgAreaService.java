package com.supconit.nhgl.basic.ngArea.service;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.base.entities.GeoArea;
import com.supconit.common.services.BaseBusinessService;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;


public interface NgAreaService extends BaseBusinessService<NgArea, Long>{
	
	//分页查询
			Pageable<NgArea> findByPage(Pageable pager,NgArea NgArea);

			List<NgArea> findTree();

			List<NgArea> findById(Long id);

			void removeNgArea(Long[] ids);

			List<NgArea> findByConfig(Long nhType);

			List<NgArea> findAll();

			List<NgArea> findAllDArea();

			List<NgArea> findAllSArea();    
			
}
