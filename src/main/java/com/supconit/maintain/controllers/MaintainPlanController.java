package com.supconit.maintain.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.maintain.entities.MaintainPlan;
import com.supconit.maintain.services.MaintainPlanService;

/**
 * 保养计划控制类
 * @author yuhuan
 * @日期 2015/09
 */
@Controller
@RequestMapping("maintain/plan")
public class MaintainPlanController extends BaseControllerSupport {
	@Autowired
	private MaintainPlanService maintainPlanService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	
	private static final SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
	private static final SimpleDateFormat sdf_month = new SimpleDateFormat("yyyy-MM");
	private static final SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list/{type}",method=RequestMethod.GET)
	public String list(@PathVariable String type,ModelMap model) {
		List<DeviceCategory> deviceCategoryList = deviceCategoryService.findAll();
        model.addAttribute("deviceCategoryList", deviceCategoryList);//设备类别树
        List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);//地理区域树
        if(type.equals("1")){
        	model.put("planDate", sdf_month.format(new Date()));
        	return "maintain/plan/month_list";
        }else{
        	model.put("planDate", sdf_year.format(new Date()));
        	return "maintain/plan/year_list";
        }
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list/{type}",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<MaintainPlan> dolist(@PathVariable String type,MaintainPlan condition,
			Pagination<MaintainPlan> pager, ModelMap model) {
		Calendar cal = Calendar.getInstance();
		Date start;
		if(type.equals("1")){//月
			try {
				start = sdf_day.parse(condition.getPlanDates() + "-01");
				condition.setBeginTime(start);
				cal.setTime(start);
				cal.add(Calendar.MONTH, 1);
				condition.setEndTime(cal.getTime());
				maintainPlanService.findByCondition(pager,condition);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{//年
			maintainPlanService.findByConditionYear(pager,condition);
		}
		return pager;
	}

}
