package com.supconit.common.daos;

import java.io.Serializable;

import hc.base.domains.PK;
import hc.orm.BasicDao;

public interface BaseDao<T extends PK<ID>, ID extends Serializable> extends BasicDao<T, ID> {

	public int deleteById(ID id);
}
