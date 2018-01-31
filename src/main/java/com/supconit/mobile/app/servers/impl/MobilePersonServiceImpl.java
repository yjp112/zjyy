package com.supconit.mobile.app.servers.impl;

import com.supconit.mobile.app.daos.MobilePersonDao;
import com.supconit.mobile.app.entities.MobilePerson;
import com.supconit.mobile.app.servers.MobilePersonService;
import hc.orm.triggers.AbstractBusinessTriggerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class MobilePersonServiceImpl extends AbstractBusinessTriggerSupport<MobilePerson> implements MobilePersonService {

    @Autowired
    private MobilePersonDao mobilePersonDao;

    public MobilePersonServiceImpl() {
    }

    @Override
    protected Class<?> getDefaultEntityClass() {
        return MobilePerson.class;
    }

    @Override
    protected void doInsert(MobilePerson mobilePerson) {
        this.mobilePersonDao.insert(mobilePerson);
    }

    @Override
    protected void doUpdate(MobilePerson mobilePerson) {
        this.mobilePersonDao.update(mobilePerson);
    }

    @Override
    protected void doDelete(MobilePerson mobilePerson) {
        this.mobilePersonDao.delete(mobilePerson);
    }

    @Transactional(
            readOnly = true
    )
    public MobilePerson getById(Long id) {
        if(null != id && id.longValue() >= 1L) {
            MobilePerson person = (MobilePerson)this.mobilePersonDao.getById(id);
            return person;
        } else {
            return null;
        }
    }

    public void save(MobilePerson person) {
        Assert.notNull(person);
        if(null == person.getId()) {
            this.insert(person);
        } else {
            this.update(person);
        }
    }
}
