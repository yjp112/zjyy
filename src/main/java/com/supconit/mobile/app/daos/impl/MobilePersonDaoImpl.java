package com.supconit.mobile.app.daos.impl;

import com.supconit.mobile.app.daos.MobilePersonDao;
import com.supconit.mobile.app.entities.MobilePerson;
import hc.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class MobilePersonDaoImpl extends AbstractBasicDaoImpl<MobilePerson, Long> implements MobilePersonDao {
	private static final String NAMESPACE = MobilePerson.class.getName();

	public MobilePersonDaoImpl() {
	}

	protected String getNamespace() {
		return NAMESPACE;
	}

	public MobilePerson getByCondition(MobilePerson condition) {
		return (MobilePerson)this.selectOne("getByCondition", condition);
	}

	public MobilePerson getById(Long id) {
		return this.getByCondition(new MobilePerson(id));
	}
}
