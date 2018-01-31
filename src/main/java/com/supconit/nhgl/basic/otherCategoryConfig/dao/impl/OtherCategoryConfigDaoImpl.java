package com.supconit.nhgl.basic.otherCategoryConfig.dao.impl;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.nhgl.basic.otherCategoryConfig.dao.OtherCategoryConfigDao;
import com.supconit.nhgl.basic.otherCategoryConfig.entities.OtherCategoryConfig;

@Repository
public class OtherCategoryConfigDaoImpl extends AbstractBaseDao<OtherCategoryConfig,Long> implements OtherCategoryConfigDao{
	private static final String NAME_SPACE=OtherCategoryConfig.class.getName();
	
	@Override
	public Pageable<OtherCategoryConfig> findByCondition(Pageable<OtherCategoryConfig> pager,
			OtherCategoryConfig condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}
	@Override
	protected String getNamespace() {
		return NAME_SPACE;
	}


	@Override
	public List<OtherCategoryConfig> findByCategoryIdAndRule(OtherCategoryConfig otherCategoryConfig) {
		return selectList("findByCategoryIdAndRule",otherCategoryConfig);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
	@Override
	public int deleteByCategoryId(Long categoryId,String nhtype) {
		Map map=new HashMap();
		map.put("categoryId", categoryId);
		map.put("nhType", nhtype);
		return update("deleteByCategoryId",map);
	}
	@Override
	public int countFindAll(OtherCategoryConfig otherCategoryConfig) {
		// TODO Auto-generated method stub
		return selectOne("countFindAll", otherCategoryConfig); 
	}
	@Override
	public OtherCategoryConfig getByCondition(OtherCategoryConfig otherCategoryConfig) {
		// TODO Auto-generated method stub
		return selectOne("getByCondition",otherCategoryConfig);
	}
	
}
