package com.supconit.visitor.daos.impl;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.visitor.daos.TmpCardDao;
import com.supconit.visitor.entities.TmpCard;
import hc.base.domains.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public class TmpCardDaoImpl extends AbstractBaseDao<TmpCard, Long>implements TmpCardDao {
    private static final String NAMESPACE = TmpCard.class.getName();

    @Override
    protected String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public Pageable<TmpCard> findByPages(Pageable<TmpCard> pager, TmpCard condition) {
        return findByPager(pager, "findByConditions", "countByConditions", condition);
    }

    @Override
    public void save(TmpCard condition) {
        this.insert(condition);
    }
}
