package com.supconit.nhgl.basic.medical.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.medical.dao.MedicalInfoDao;
import com.supconit.nhgl.basic.medical.entities.MedicalInfo;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class MedicalInfoDaoImpl extends AbstractBasicDaoImpl<MedicalInfo,Long> implements MedicalInfoDao {
	private static final String NAMESPACE=MedicalInfo.class.getName();
	
	@Override
	public Pageable<MedicalInfo> findByCondition(
			Pageable<MedicalInfo> pager, MedicalInfo condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public List<MedicalInfo> findByMonthKey(MedicalInfo minfo) {
		return selectList("findByMonthKey", minfo);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
}