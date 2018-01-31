package com.supconit.mobile.app.daos;

import com.supconit.mobile.app.entities.MobilePerson;
import hc.orm.BasicDao;

public interface MobilePersonDao extends BasicDao<MobilePerson, Long> {

	MobilePerson getByCondition(MobilePerson var1);

}
