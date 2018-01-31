package com.supconit.common.daos;

import java.io.Serializable;

import hc.base.domains.PK;
import hc.orm.AbstractBasicDaoImpl;

public abstract class AbstractBaseDao<T extends PK<ID>, ID extends Serializable> extends AbstractBasicDaoImpl<T, ID> implements BaseDao<T, ID> {

 	@Override
 	public int deleteById(ID id) {
 		return delete(STATEMENT_DELETE_BY_ID,id);
 	}
}
