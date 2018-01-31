package com.supconit.montrol.dao.impl;

import com.supconit.montrol.dao.DeviceTagDao;
import com.supconit.montrol.entity.DeviceTag;
import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceTagDaoImpl extends AbstractBasicDaoImpl<DeviceTag, Long> implements DeviceTagDao {

	private static final String NAMESPACE	= DeviceTag.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


    @Override
    public Pageable<DeviceTag> findByPager(Pageable<DeviceTag> pager, DeviceTag condition) {
        return findByPager(pager, "selectPager", "countPager", condition);
    }

    @Override
    public DeviceTag findByName(DeviceTag condition) {
        return selectOne("getByName",condition);
    }
}
