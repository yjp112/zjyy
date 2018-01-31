package com.supconit.inspection.services;

import hc.base.domains.Pageable;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.inspection.entities.InspectionTaskContent;

public interface InspectionTaskContentService extends BaseBusinessService<InspectionTaskContent, Long>{
	/**
	 * 查询任务内容列表
	 */
	List<InspectionTaskContent> selectInspectionTaskContentList(String inspectionCode);
	/**
	 * 分页查询
	 */
	Pageable<InspectionTaskContent> findByCondition(Pageable<InspectionTaskContent> pager,InspectionTaskContent condition);
}
