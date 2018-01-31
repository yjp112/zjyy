package com.supconit.maintain.daos;

import java.util.List;
import java.util.Map;

import com.supconit.common.daos.BaseDao;
import com.supconit.maintain.entities.MaintainSpare;

public interface MaintainSpareDao extends BaseDao<MaintainSpare, Long>{
	/**
	 * 查询任务耗材列表
	 */
	List<MaintainSpare> selectMaintainSpareList(String maintainCode);
	/**
	 * 通过保养单号删除
	 */
	void deleteByMaintainCode(String maintainCode);
	/**
	 * 批量插入
	 */
	void batchInsert(Map<String,Object> param);
}
