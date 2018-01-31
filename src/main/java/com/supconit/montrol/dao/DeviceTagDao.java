package com.supconit.montrol.dao;


import com.supconit.montrol.entity.DeviceTag;
import hc.base.domains.Pageable;

public interface DeviceTagDao extends hc.orm.BasicDao<DeviceTag, Long>{
    public Pageable<DeviceTag> findByPager(Pageable<DeviceTag> pager, DeviceTag condition);

   public  DeviceTag findByName(DeviceTag condition);
}
