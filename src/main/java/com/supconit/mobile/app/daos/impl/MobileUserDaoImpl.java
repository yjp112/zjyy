//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.supconit.mobile.app.daos.impl;

import com.supconit.mobile.app.daos.MobileUserDao;
import com.supconit.mobile.app.entities.MobileUser;
import hc.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class MobileUserDaoImpl extends AbstractBasicDaoImpl<MobileUser, Long> implements MobileUserDao {
	private static final String NAMESPACE = MobileUser.class.getName();

	public MobileUserDaoImpl() {
	}

	protected String getNamespace() {
		return NAMESPACE;
	}

	public MobileUser getByCondition(MobileUser condition) {
		return (MobileUser)this.selectOne("getByCondition", condition);
	}

	public MobileUser getById(Long id) {
		return this.getByCondition(new MobileUser(id));
	}
}
