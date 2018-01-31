package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DocumentDao;
import com.supconit.base.entities.Document;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Repository
public class DocumentDaoImpl extends AbstractBaseDao<Document, Long> implements DocumentDao {
	private static final String	NAMESPACE	= Document.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}

	@Override
	public Pageable<Document> findByPage(Pagination<Document> pager,
			Document document) {
		return findByPager(pager, "selectPager", "countPager", document);
	}

	@Override
	public long countByDocNo(String docNo) {
		Document condition=new Document();
	        condition.setDocNo(docNo);
	        return getSqlSession().selectOne(Document.class.getName()+".countByDocNo", condition);
	}

	@Override
	public List<Document> findByDocNo(String docNo) {
		return selectList("findByDocNo",docNo);
	}

	@Override
	public List<Document> getByDeviceId(Long deviceId) {
		Map param = new HashMap<String, Long>();
		param.put("deviceId", deviceId);
		return selectList("getByDeviceId", param);
	}

}
