package com.supconit.visitor.services.impl;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.StringUtil;
import com.supconit.common.utils.UtilTool;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.visitor.daos.TmpCardDao;
import com.supconit.visitor.daos.VisitorDao;
import com.supconit.visitor.daos.VisitorReservationDao;
import com.supconit.visitor.entities.TmpCard;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;
import com.supconit.visitor.services.TmpCardService;
import com.supconit.visitor.services.VisitorReservationService;
import hc.base.domains.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TmpCardServiceImpl extends AbstractBaseBusinessService<TmpCard, Long>
        implements TmpCardService {

    @Autowired
    private TmpCardDao tmpCardDao;


    @Override
    public Pageable<TmpCard> findByPages(Pageable<TmpCard> pager, TmpCard condition) {
        pager = tmpCardDao.findByPages(pager, condition);
        return pager;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public TmpCard getById(Long aLong) {
        return null;
    }

    @Override
    public void insert(TmpCard tmpCard) {

    }

    @Override
    public void update(TmpCard tmpCard) {

    }

    @Override
    public void save(TmpCard tmpCard){
        tmpCardDao.save(tmpCard);
    }
}
