package com.supconit.inspection.daos;

import hc.base.domains.Pageable;

import java.util.List;
import java.util.Map;

import com.supconit.common.daos.BaseDao;
import com.supconit.inspection.entities.InspectionTaskContent;

public interface InspectionTaskContentDao extends BaseDao<InspectionTaskContent, Long>{
	/**
	 * 查询任务内容列表
	 */
	List<InspectionTaskContent> selectInspectionTaskContentList(String inspectionCode);
	/**
	 * 通过巡检单号删除
	 */
	void deleteByInspectionCode(String inspectionCode);
	/**
	 * 批量插入
	 */
	void batchInsert(Map<String,Object> param);
	/**
	 * 分页查询
	 */
	Pageable<InspectionTaskContent> findByCondition(Pageable<InspectionTaskContent> pager,InspectionTaskContent condition);
}
