package com.supconit.maintain.daos;

import hc.base.domains.Pageable;

import java.util.List;
import java.util.Map;

import com.supconit.common.daos.BaseDao;
import com.supconit.maintain.entities.MaintainTaskContent;

public interface MaintainTaskContentDao extends BaseDao<MaintainTaskContent, Long>{
	/**
	 * 查询任务内容列表
	 */
	List<MaintainTaskContent> selectMaintainTaskContentList(String maintainCode);
	/**
	 * 通过保养单号删除
	 */
	void deleteByMaintainCode(String maintainCode);
	/**
	 * 批量插入
	 */
	void batchInsert(Map<String,Object> param);
	/**
	 * 分页查询
	 */
	Pageable<MaintainTaskContent> findByCondition(Pageable<MaintainTaskContent> pager,MaintainTaskContent condition);
}
