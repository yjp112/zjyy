package com.supconit.inspection.daos;

import java.util.List;
import java.util.Map;

import com.supconit.common.daos.BaseDao;
import com.supconit.inspection.entities.InspectionSpare;

public interface InspectionSpareDao extends BaseDao<InspectionSpare, Long>{
	/**
	 * 查询任务耗材列表
	 */
	List<InspectionSpare> selectInspectionSpareList(String inspectionCode);
	/**
	 * 通过巡检单号删除
	 */
	void deleteByInspectionCode(String inspectionCode);
	/**
	 * 批量插入
	 */
	void batchInsert(Map<String,Object> param);
}
