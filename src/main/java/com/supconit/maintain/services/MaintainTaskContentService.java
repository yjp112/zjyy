package com.supconit.maintain.services;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.maintain.entities.MaintainTaskContent;

public interface MaintainTaskContentService extends BaseBusinessService<MaintainTaskContent, Long>{
	/**
	 * 查询任务内容列表
	 */
	List<MaintainTaskContent> selectMaintainTaskContentList(String maintainCode);
	/**
	 * 分页查询
	 */
	Pageable<MaintainTaskContent> findByCondition(Pageable<MaintainTaskContent> pager,MaintainTaskContent condition);
}
