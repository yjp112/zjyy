package com.supconit.nhgl.analyse.electric.dao;

import java.util.List;

import com.supconit.nhgl.analyse.electric.entities.ElectricMeterRealTime;
import com.supconit.nhgl.analyse.electric.entities.StatisticalPropDay;

import hc.orm.BasicDao;

/**
 * 
 * @author gaowenlong
 */
public interface ElectricMeterRealTimeDao extends BasicDao<ElectricMeterRealTime, Long>{

	/**
	  * @方法名: findByCon
	  * @创建日期:2014-5-27
	  * @开发人员:高文龙
	  * @参数:@param em
	  * @参数:@return
	  * @返回值:List<ElectricMeterRealTime>
	  * @描述:查询
	 */
	List<ElectricMeterRealTime> findByCon(ElectricMeterRealTime em);

	ElectricMeterRealTime findByBitNoMax(ElectricMeterRealTime ems);

	StatisticalPropDay findMinCollectTime();
}
