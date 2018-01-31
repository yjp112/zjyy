//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.supconit.mobile.app.daos;

import com.supconit.mobile.app.entities.MobileUser;
import hc.orm.BasicDao;

public interface MobileUserDao extends BasicDao<MobileUser, Long> {

	MobileUser getByCondition(MobileUser var1);

}
