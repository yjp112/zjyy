package com.supconit.nhgl.basic.otherCategoryConfig.dao;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.nhgl.basic.otherCategoryConfig.entities.OtherCategoryConfig;


public interface OtherCategoryConfigDao extends BaseDao<OtherCategoryConfig, Long>{
	Pageable<OtherCategoryConfig> findByCondition(Pageable<OtherCategoryConfig> pager, OtherCategoryConfig condition);
	public int deleteByIds(Long[] ids);
	public int deleteByCategoryId(Long categoryId,String nhtype);
	int countFindAll(OtherCategoryConfig otherCategoryConfig);
	OtherCategoryConfig getByCondition(OtherCategoryConfig otherCategoryConfig);
	List<OtherCategoryConfig> findByCategoryIdAndRule(OtherCategoryConfig otherCategoryConfig);  
}
