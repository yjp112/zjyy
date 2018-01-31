package com.supconit.mobile.app.servers.impl;

import com.supconit.mobile.app.daos.MobileUserDao;
import com.supconit.mobile.app.entities.MobileUser;
import com.supconit.mobile.app.servers.MobileUserService;
import hc.orm.triggers.AbstractBusinessTriggerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class MobileUserServiceImpl extends AbstractBusinessTriggerSupport<MobileUser> implements MobileUserService {

    @Autowired
    private MobileUserDao mobileUserDao;

    public MobileUserServiceImpl() {
    }

    @Override
    protected Class<?> getDefaultEntityClass() {
        return MobileUser.class;
    }

    @Override
    protected void doInsert(MobileUser mobileUser) {
        this.mobileUserDao.insert(mobileUser);
    }

    @Override
    protected void doUpdate(MobileUser mobileUser) {
        this.mobileUserDao.update(mobileUser);
    }

    @Override
    protected void doDelete(MobileUser mobileUser) {
        this.mobileUserDao.delete(mobileUser);
    }

    @Transactional(
            readOnly = true
    )
    public MobileUser getById(Long id) {
        if(null != id && id.longValue() >= 1L) {
            String key = String.valueOf(id);
            MobileUser user = (MobileUser)this.mobileUserDao.getById(id);
            return user;
        } else {
            return null;
        }
    }

    public void save(MobileUser user) {
        Assert.notNull(user);
        if(null == user.getId()) {
            this.insert(user);
        } else {
            this.update(user);
        }
    }

}
