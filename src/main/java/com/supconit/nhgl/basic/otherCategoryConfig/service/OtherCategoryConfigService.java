package com.supconit.nhgl.basic.otherCategoryConfig.service;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.nhgl.basic.otherCategoryConfig.entities.OtherCategoryConfig;


public interface OtherCategoryConfigService extends BaseBusinessService<OtherCategoryConfig,Long>{
	Pageable<OtherCategoryConfig> findByCondition(Pageable<OtherCategoryConfig> pager, OtherCategoryConfig condition);
	void deleteByCategoryId(Long categoryId,String nhtype);
	int countFindAll(OtherCategoryConfig otherCategoryConfig);
	OtherCategoryConfig getByCondition(OtherCategoryConfig otherCategoryConfig);
	List<OtherCategoryConfig> findByCategoryIdAndRule(OtherCategoryConfig condition, Integer ruleFlagPlus);    
}
