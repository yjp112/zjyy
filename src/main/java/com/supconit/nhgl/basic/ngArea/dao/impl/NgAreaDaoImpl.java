package com.supconit.nhgl.basic.ngArea.dao.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.nhgl.basic.ngArea.dao.NgAreaDao;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;

@Repository
public class NgAreaDaoImpl  extends AbstractBaseDao<NgArea, Long> implements NgAreaDao {

	@Override
	protected String getNamespace() {
		return NgArea.class.getName();
	}
	
	@Override
	public Pageable<NgArea> findByPage(Pageable<NgArea> pager,NgArea condition){
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public List<NgArea> findAll() {
		return selectList("findAll");
	}

	

	@Override
	public List<NgArea> findById(Long id) {
		return selectList("findById",id);
	}
	
	
	
	

	@Override
	public List<NgArea> findByCode(String code) {
		return selectList("findByCode",code);
	}

	

	@Override
	public List<NgArea> findByName(NgArea area) {
		// TODO Auto-generated method stub
		return selectList("findByName",area);
	}
	
	@Override
	public List<NgArea> findByRoot(Long id) {
		return selectList("findByRoot",id);
	}

	@Override
	public List<NgArea> findByConfig(Long nhType) {
		// TODO Auto-generated method stub
		return selectList("findByConfig",nhType);
	}

	@Override
	public List<NgArea> findAllExceptionRoot() {
		return selectList("findAllExceptionRoot");
	}

	@Override
	public List<NgArea> findAllDArea() {
		return selectList("findAllDArea");
	}

	@Override
	public List<NgArea> findAllSArea() {
		return selectList("findAllSArea");
	}
}
