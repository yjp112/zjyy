package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.Document;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface DocumentDao extends BaseDao<Document, Long>{

	int deleteByIds(Long[] ids);

	Pageable<Document> findByPage(Pagination<Document> pager, Document document);

	long countByDocNo(String docNo);

	List<Document> findByDocNo(String docNo);
	/**
	 * 查询设备文档的设计、招标、实施、验收四个阶段
	 */
	List<Document> getByDeviceId(Long deviceId);
}
