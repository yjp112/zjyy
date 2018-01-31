package com.supconit.alarm.daos;

import java.util.List;

import com.supconit.alarm.entities.AlarmAnalyse;
import com.supconit.common.daos.BaseDao;

public interface AlarmAnalyseDao extends BaseDao<AlarmAnalyse, Long>{
	/**
	 * 查询报警趋势
	 */
	List<AlarmAnalyse> yearReport(AlarmAnalyse alarm);
	/**
	 * 查询区域报警
	 */
	Long areaReport(AlarmAnalyse alarm);
	/**
	 * 查询部门报警
	 */
	Long deptReport(AlarmAnalyse alarm);
	/**
	 * 查询圆饼图
	 * type:1.报警趋势;2.区域报警;3.部门报警
	 */
	Long findPie(AlarmAnalyse alarm, int type);
}
