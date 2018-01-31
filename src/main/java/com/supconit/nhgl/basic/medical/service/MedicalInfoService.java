package com.supconit.nhgl.basic.medical.service;

import java.util.List;

import com.supconit.nhgl.basic.medical.entities.MedicalInfo;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;




public interface MedicalInfoService extends BasicOrmService<MedicalInfo,Long>{
	Pageable<MedicalInfo> findByCondition(Pageable<MedicalInfo> pager,MedicalInfo condition);
//	String buildTree(List<SubSystemInfo> slist);

	List<MedicalInfo> findByMonthKey(MedicalInfo minfo);
	void deleteByIds(Long[] ids);
}
