package com.supconit.common.services;

import java.io.Serializable;

import hc.base.domains.PK;
import hc.orm.BasicOrmService;

public interface BaseBusinessService<T extends PK<ID>, ID extends Serializable> extends BasicOrmService<T, ID> {

	public void deleteById(ID id);

}
