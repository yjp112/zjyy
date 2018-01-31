package com.supconit.alarm.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.alarm.entities.AlarmAnalyse;
import com.supconit.alarm.services.AlarmAnalyseService;
import com.supconit.common.web.controllers.BaseControllerSupport;

/**
 * 报警趋势分析控制类
 * @author yuhuan
 * @日期 2015/12
 */
@Controller
@RequestMapping("alarm/alarmAnalyse")
public class AlarmAnalyseController extends BaseControllerSupport {
	@Autowired
	private AlarmAnalyseService alarmAnalyseService;
	
	private static final SimpleDateFormat sdf_month = new SimpleDateFormat("yyyy-MM");
	
	/**
	 * 跳转到列表
	 * type:1.报警趋势;2.区域报警;3.部门报警
	 */
	@RequestMapping("list/{type}")
    public String page(@PathVariable(value = "type") int type, ModelMap model) {
        String path = "";
        String month = "";
        switch (type) {
        case 1:
        	Calendar cal = Calendar.getInstance();
        	int year=cal.get(Calendar.YEAR);
        	model.put("alarmYear", year);
            path = "alarm/alarmAnalyse/trend";
            break;
        case 2:
        	month = sdf_month.format(new Date());
            model.put("startMonth", month);
            model.put("endMonth", month);
            path = "alarm/alarmAnalyse/area";
            break;
        case 3:
            month = sdf_month.format(new Date());
            model.put("startMonth", month);
            model.put("endMonth", month);
            path = "alarm/alarmAnalyse/dept";
            break;
        default:
        	Calendar cals = Calendar.getInstance();
        	int years=cals.get(Calendar.YEAR);
        	model.put("alarmYear", years);
            path = "alarm/alarmAnalyse/trend";
            break;
        }
        model.put("type", type);
        return path;
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<AlarmAnalyse>  list(@ModelAttribute AlarmAnalyse alarm, int type,
            ModelMap model) {
		List<AlarmAnalyse> list = alarmAnalyseService.findList(alarm, type,DEFAULT_DEPARTMENTID);
		Pageable<AlarmAnalyse> pager = new Pagination<AlarmAnalyse>(list);
        pager.setPageNo(1);
        pager.setPageSize(Integer.MAX_VALUE);
        pager.setTotal(list.size());
		return pager;
	}
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="pie",method=RequestMethod.POST)
	@ResponseBody
	public Pageable<AlarmAnalyse>  pie(@ModelAttribute AlarmAnalyse alarm, int type,
			ModelMap model) {
		List<AlarmAnalyse> list = alarmAnalyseService.findPie(alarm, type);
		Pageable<AlarmAnalyse> pager = new Pagination<AlarmAnalyse>(list);
		pager.setPageNo(1);
		pager.setPageSize(Integer.MAX_VALUE);
		pager.setTotal(list.size());
		return pager;
	}
}
