package com.supconit.visitor.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.visitor.entities.TmpCard;
import hc.base.domains.Pageable;


public interface TmpCardService extends BaseBusinessService<TmpCard, Long> {
    
    /**
     * 分页查询
     */
    Pageable<TmpCard> findByPages(Pageable<TmpCard> pager, TmpCard condition);

    void save(TmpCard condition);
}
