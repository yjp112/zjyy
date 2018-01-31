package com.supconit.hikvision.services;

import java.util.List;

import com.supconit.base.entities.EnumDetail;
import com.supconit.hikvision.entities.AccessCount;
import com.supconit.hikvision.entities.AcsHisEvent;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface AcsHisEventService  {
	/**
	 * 分页查询
	 */
	Pageable<AcsHisEvent> findByPage(Pageable<AcsHisEvent> pager,AcsHisEvent condition);

	

	Pageable<AccessCount> findAccessCountByPage(Pagination<AccessCount> pager, AccessCount condition);



	List<EnumDetail> getComeStatusList();



	List<EnumDetail> getOffStatusList();


	/**
	 * 查询所有指定条件的进出记录
	 * @param condition
	 * @return
	 */
	List<AccessCount> findAccessCount(AccessCount condition);
	
}
