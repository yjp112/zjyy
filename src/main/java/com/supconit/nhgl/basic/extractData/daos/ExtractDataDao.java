package com.supconit.nhgl.basic.extractData.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.nhgl.basic.extractData.entities.ExtractData;

import hc.base.domains.Pageable;

public interface ExtractDataDao extends BaseDao<ExtractData, Long> {

	/**
	 * 分页查询
	 */
	Pageable<ExtractData> findByPager(Pageable<ExtractData> pager, ExtractData condition);
}
