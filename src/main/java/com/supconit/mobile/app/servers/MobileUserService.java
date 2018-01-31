package com.supconit.mobile.app.servers;

import com.supconit.mobile.app.entities.MobileUser;
import hc.orm.BasicOrmService;

public interface MobileUserService extends BasicOrmService<MobileUser, Long> {

	MobileUser getById(Long id);

	void save(MobileUser mobileUser);
}
