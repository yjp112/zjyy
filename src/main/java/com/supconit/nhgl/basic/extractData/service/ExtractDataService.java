package com.supconit.nhgl.basic.extractData.service;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.nhgl.basic.extractData.entities.ExtractData;

import hc.base.domains.Pageable;

public interface ExtractDataService extends BaseBusinessService<ExtractData, Long> {

	/**
	 * 分页查询
	 */
	Pageable<ExtractData> find(Pageable<ExtractData> pager, ExtractData condition);
}
