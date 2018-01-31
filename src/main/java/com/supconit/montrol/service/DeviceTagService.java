package com.supconit.montrol.service;

import com.supconit.montrol.entity.DeviceTag;
import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface DeviceTagService extends BasicOrmService<DeviceTag, Long> {
    Pageable<DeviceTag> find(Pageable<DeviceTag> pager, DeviceTag condition);

    DeviceTag findByName(DeviceTag condition);
}
