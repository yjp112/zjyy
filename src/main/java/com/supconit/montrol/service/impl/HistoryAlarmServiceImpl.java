package com.supconit.montrol.service.impl;



import com.supconit.montrol.dao.IHistoryAlarmDao;
import com.supconit.montrol.entity.MHistoryAlarm;
import com.supconit.montrol.service.IHistoryAlarmService;
import hc.base.domains.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class HistoryAlarmServiceImpl extends hc.orm.AbstractBasicOrmService<MHistoryAlarm, Long>  implements IHistoryAlarmService {
	@Autowired
	private IHistoryAlarmDao historyAlarmDao;

    @Override
    @Transactional(readOnly = true)
    public Pageable<MHistoryAlarm> find(Pageable<MHistoryAlarm> pager, MHistoryAlarm condition) {
        return historyAlarmDao.findByPager(pager,condition);
    }

    @Override
    @Transactional(readOnly = true)
    public MHistoryAlarm findFirstAlarm() {
        return historyAlarmDao.findFirstAlarm();
    }

    @Override
    @Transactional
    public void displayAlarm() {
        historyAlarmDao.displayAlarm();
    }

    @Override
    @Transactional(readOnly = true)
    public MHistoryAlarm getById(Long aLong) {
        return historyAlarmDao.getById(aLong);
    }

    @Override
    @Transactional
    public void insert(MHistoryAlarm entity) {
        historyAlarmDao.insert(entity);
    }

    @Override
    @Transactional
    public void update(MHistoryAlarm entity) {
        historyAlarmDao.update(entity);
    }

    @Override
    @Transactional
    public void delete(MHistoryAlarm entity) {
        historyAlarmDao.delete(entity);
    }

	@Override
	public MHistoryAlarm getByDeviceId(Long deviceId) {
		return historyAlarmDao.getByDeviceId(deviceId);
	}
}
