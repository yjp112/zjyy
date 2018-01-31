package com.supconit.nhgl.analyse.electric.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dao.ElectricMeterRealTimeDao;
import com.supconit.nhgl.analyse.electric.entities.ElectricMeterRealTime;
import com.supconit.nhgl.analyse.electric.entities.StatisticalPropDay;

import hc.orm.AbstractBasicDaoImpl;

@Repository
public class ElectricMeterRealTimeDaoImpl extends AbstractBasicDaoImpl<ElectricMeterRealTime,Long> implements ElectricMeterRealTimeDao {
	
	private static final String	NAMESPACE	= ElectricMeterRealTime.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<ElectricMeterRealTime> findByCon(ElectricMeterRealTime em) {
		return selectList("findByCon", em);
	}
	
	@Override
	public ElectricMeterRealTime findByBitNoMax(ElectricMeterRealTime ems){
		return selectOne("findByBitNoMax",ems);
	}

    @Override
    public StatisticalPropDay findMinCollectTime(){
        return selectOne("findMinCollectTimeaaa");
    }
}
