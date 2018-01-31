package com.supconit.ywgl.patrol.controllers;

import java.util.Date;

import hc.base.domains.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.DateUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.ywgl.patrol.entities.PatrolStatistic;
import com.supconit.ywgl.patrol.services.PatrolStatisticService;

@Controller
@RequestMapping("ywgl/patrol/statistic")
public class PatrolStatisticController extends BaseControllerSupport{
	@Autowired
	private PatrolStatisticService patrolStatisticService;
	
	
	
	@RequestMapping("go")
	public String go(ModelMap model) {
		return "ywgl2/patrol/statistic/statistic_list";
	}
	
	@ResponseBody
    @RequestMapping("list")
	public PatrolStatistic list(
			Pagination<PatrolStatistic> page, @ModelAttribute PatrolStatistic patrolStatistic,
			ModelMap model){
		if(patrolStatistic.getStatisticStarTime()==null){ //开始时间默认为当前月的开始时间
			patrolStatistic.setStatisticStarTime(DateUtils.getMonthBegain());
		}else{
			patrolStatistic.setStatisticStarTime(patrolStatistic.getStatisticStarTime());
		}
		if(patrolStatistic.getStatisticEndTime()==null){ //结束时间默认为当前时间
			patrolStatistic.setStatisticEndTime(new Date()); 
		}else{
			patrolStatistic.setStatisticEndTime(patrolStatistic.getStatisticEndTime());
		}
		PatrolStatistic patrolStatistics=patrolStatisticService.initStatistic(patrolStatistic);
		return patrolStatistics;
		
	}
	
}
