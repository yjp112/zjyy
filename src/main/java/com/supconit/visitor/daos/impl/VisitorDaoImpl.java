package com.supconit.visitor.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.visitor.daos.VisitorDao;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;

import hc.base.domains.Pageable;

@Repository
public class VisitorDaoImpl extends AbstractBaseDao<Visitor, Long>implements VisitorDao {
	private static final String NAMESPACE = Visitor.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public Pageable<Visitor> findByPages(Pageable<Visitor> pager, Visitor condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}

	@Override
	public void deleteByVisitorId(Long visitorId) {
		Map param = new HashMap(1);
		param.put("visitorId", visitorId);
		delete("deleteByVisitorId", param);
	}

	@Override
	public Visitor findByReservationId(Long reservationId) {
		return selectOne("findByReservationId", reservationId);
	}

	@Override
	public List<Visitor> autoCompletePersonList(String visitorName) {
		return selectList("autoCompletePersonList", visitorName);
	}

	@Override
	public List<Visitor> findByCardNo(String cardNo) {
		return selectList("findByCardNo", cardNo);
	}

}
