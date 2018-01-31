package com.supconit.nhgl.analyse.electric.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.analyse.electric.dao.StatisticalPropDayDao;
import com.supconit.nhgl.analyse.electric.entities.StatisticalPropDay;

import hc.orm.AbstractBasicDaoImpl;

/**
 * 时间维度dao
 * @author 高文龙
 */
@Repository
public class StatisticalPropDayDaoImpl extends AbstractBasicDaoImpl<StatisticalPropDay, Long> implements StatisticalPropDayDao {
	private static final String NAMESPACE = StatisticalPropDay.class.getName();
	@Override
	protected String getNamespace(){
		return NAMESPACE;
	}
	
	/**
	 * @方法名: findByDate
	 * @创建日期: 2014-5-23
	 * @开发人员:高文龙
	 * @描述:根据时间间隔拿到周数
	 */
	@Override
	public List<StatisticalPropDay> findByDate(StatisticalPropDay sta) {
		return selectList("findByCon", sta);
	}
	
	
	@Override
	public List<StatisticalPropDay> findByDay(StatisticalPropDay sta) {
		return selectList("findByDay", sta);
	}


	@Override
	public void insertSpd(List<StatisticalPropDay> stalist) {
		insert("insertSpd", stalist);
	}


	@Override
	public List<StatisticalPropDay> findByHoildayLast(StatisticalPropDay sta) {
		return selectList("findByHoildayLast",sta);
	}
	
	@Override
	public StatisticalPropDay getMaxDate() {
		return selectOne("getMaxDateSta");
	}
	
	@Override
	public StatisticalPropDay getWeekTime(StatisticalPropDay sta) {
		return selectOne("getWeekTime",sta);
	}

}
