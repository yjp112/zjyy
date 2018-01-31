package com.supconit.alarm.services;

import java.util.List;

import com.supconit.alarm.entities.AlarmAnalyse;
import com.supconit.common.services.BaseBusinessService;

public interface AlarmAnalyseService extends BaseBusinessService<AlarmAnalyse, Long> {
	/**
	 * 查询
	 * type:1.报警趋势;2.区域报警;3.部门报警
	 */
	List<AlarmAnalyse> findList(AlarmAnalyse alarm, int type,Long default_departmentId);
	/**
	 * 查询
	 * type:1.报警趋势;2.区域报警;3.部门报警
	 */
	List<AlarmAnalyse> findPie(AlarmAnalyse alarm, int type);
}
