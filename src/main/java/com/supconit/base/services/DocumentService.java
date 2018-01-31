package com.supconit.base.services;

import java.util.List;

import com.supconit.base.entities.Document;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface DocumentService extends BaseBusinessService<Document, Long>{

	Pageable<Document> findByCondition(Pagination<Document> pager,
			Document document);

	void deleteByIds(Long[] ids);
	/**
	 * 查询设备文档的设计、招标、实施、验收四个阶段
	 */
	List<Document> getByDeviceId(Long deviceId);
}
