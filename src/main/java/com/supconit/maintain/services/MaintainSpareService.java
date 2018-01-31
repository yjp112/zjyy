package com.supconit.maintain.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.maintain.entities.MaintainSpare;

public interface MaintainSpareService extends BaseBusinessService<MaintainSpare, Long>{
	/**
	 * 查询任务耗材列表
	 */
	List<MaintainSpare> selectMaintainSpareList(String maintainCode);
}
