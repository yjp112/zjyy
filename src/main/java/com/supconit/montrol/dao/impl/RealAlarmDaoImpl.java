package com.supconit.montrol.dao.impl;

import java.util.List;

import com.supconit.montrol.dao.IRealAlarmDao;
import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

import com.supconit.montrol.entity.MRealAlarm;
@Repository
public class RealAlarmDaoImpl extends AbstractBasicDaoImpl<MRealAlarm, Long> implements IRealAlarmDao {
	private static final String	NAMESPACE	= MRealAlarm.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

    @Override
    public Pageable<MRealAlarm> findByPager(Pageable<MRealAlarm> pager, MRealAlarm condition) {
        return findByPager(pager, "selectPager", "countPager", condition);
    }

    @Override
	public MRealAlarm findNewAlarm() {
		return selectOne("findNewAlarm");
	}

	@Override
	public long countRealAlarm() {
		MRealAlarm realAlarm = selectOne("countRealAlarm");
		if(realAlarm != null){
			return realAlarm.getId();
		}
		return 0;
	}

	@Override
	public List<MRealAlarm> findFirstFifthAlarm() {
		return selectList("findFirstFifthAlarm");
	}

    @Override
    public void displayAlarm() {
        delete("displayAlarm");
    }

}
