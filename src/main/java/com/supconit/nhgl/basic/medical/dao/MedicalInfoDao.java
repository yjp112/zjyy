package com.supconit.nhgl.basic.medical.dao;

import java.util.List;

import com.supconit.nhgl.basic.medical.entities.MedicalInfo;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;



public interface MedicalInfoDao extends BasicDao<MedicalInfo,Long>{
	Pageable<MedicalInfo> findByCondition(Pageable<MedicalInfo> pager, MedicalInfo condition);

	List<MedicalInfo> findByMonthKey(MedicalInfo minfo);

	int deleteByIds(Long[] ids);
}
