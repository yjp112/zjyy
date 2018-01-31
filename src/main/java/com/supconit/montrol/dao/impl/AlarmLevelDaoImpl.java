package com.supconit.montrol.dao.impl;

import java.util.List;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;
import com.supconit.montrol.dao.IAlarmLevelDao;
import com.supconit.montrol.entity.MAlarmLevel;
@Repository
public class AlarmLevelDaoImpl extends AbstractBasicDaoImpl<MAlarmLevel, Long> implements IAlarmLevelDao{

	private static final String	NAMESPACE	= MAlarmLevel.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


    @Override
    public Pageable<MAlarmLevel> findByPager(Pageable<MAlarmLevel> pager, MAlarmLevel condition) {
        return findByPager(pager, "selectPager", "countPager", condition);
    }

    @Override
	public MAlarmLevel findLevelByName(String name) {
		return selectOne("findLevelByName",name);
	}

    @Override
    public List<MAlarmLevel> findList() {
        return selectList("findAllLevels");
    }

    @Override
    public Long getCount(MAlarmLevel condition) {
        MAlarmLevel mAlarmLevel = selectOne("findLevelByCondition",condition);
        if(mAlarmLevel==null||mAlarmLevel.getId()==null||"".equals(mAlarmLevel.getId())){
            return new Long(0);
        }else{
            return new Long(1);
        }
    }
}
