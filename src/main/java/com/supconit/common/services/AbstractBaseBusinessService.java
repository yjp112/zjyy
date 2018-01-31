package com.supconit.common.services;

import java.io.Serializable;

import hc.base.domains.PK;
import hc.orm.AbstractBasicOrmService;

public abstract class AbstractBaseBusinessService<T extends PK<ID>, ID extends Serializable> extends AbstractBasicOrmService<T, ID> implements BaseBusinessService<T, ID>{

	public abstract void deleteById(ID id);
	@Override
	public void delete(T entity) {
		deleteById(entity.getId());
	}

}
