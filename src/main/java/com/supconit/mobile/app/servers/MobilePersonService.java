package com.supconit.mobile.app.servers;

import com.supconit.mobile.app.entities.MobilePerson;
import hc.orm.BasicOrmService;

public interface MobilePersonService extends BasicOrmService<MobilePerson, Long> {

	MobilePerson getById(Long id);

	void save(MobilePerson mobilePerson);
}
