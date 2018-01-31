package com.supconit.montrol.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.supconit.montrol.dao.IHistoryAlarmDao;
import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

import com.supconit.montrol.entity.MHistoryAlarm;
@Repository
public class HistoryAlarmDaoImpl extends AbstractBasicDaoImpl<MHistoryAlarm, Long> implements IHistoryAlarmDao {
	private static final String	NAMESPACE	= MHistoryAlarm.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

    @Override
    public Pageable<MHistoryAlarm> findByPager(Pageable<MHistoryAlarm> pager, MHistoryAlarm condition) {
        return findByPager(pager, "selectPager", "countPager", condition);
    }

    @Override
	public int disappearAlarmById(long id){
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("disappearTime", new Date().toLocaleString());
		pars.put("id", String.valueOf(id));
		return update("disappearAlarmById", pars);
	}

	@Override
	public MHistoryAlarm findFirstAlarm() {
		return selectOne("findFirstAlarm");
	}

    @Override
    public void displayAlarm() {
        update("displayAlarm");
    }

	@Override
	public MHistoryAlarm getByDeviceId(Long deviceId) {
		Map param = new HashMap<String, Long>();
		param.put("deviceId", deviceId);
		return selectOne("getByDeviceId", param);
	}

}
