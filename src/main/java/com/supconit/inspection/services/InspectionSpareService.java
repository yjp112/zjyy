package com.supconit.inspection.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.inspection.entities.InspectionSpare;

public interface InspectionSpareService extends BaseBusinessService<InspectionSpare, Long>{
	/**
	 * 查询任务耗材列表
	 */
	List<InspectionSpare> selectInspectionSpareList(String inspectionCode);
}
