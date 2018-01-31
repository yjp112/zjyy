package com.supconit.visitor.daos;

import com.supconit.common.daos.BaseDao;
import com.supconit.visitor.entities.TmpCard;
import hc.base.domains.Pageable;


public interface TmpCardDao extends BaseDao<TmpCard, Long>{
	Pageable<TmpCard> findByPages(Pageable<TmpCard> pager, TmpCard condition);

    void save(TmpCard condition);
}
