package com.supconit.hikvision.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.hikvision.entities.AccessCount;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface AccessCountDao extends BaseDao<AccessCount, Long> {

	Pageable<AccessCount> findByPage(Pagination<AccessCount> pager, AccessCount condition);
	List<AccessCount> findByConditions(AccessCount condition);
	List<AccessCount> findAllByConditions(AccessCount condition);

	/** 手机端使用 **/
	List<AccessCount> searchMonthAttendances(AccessCount condition);
	List<AccessCount> searchUnusualMonthAttendances(AccessCount condition);
	AccessCount countMonthAttendances(AccessCount condition);
	AccessCount searchAttendance(AccessCount condition);
}
