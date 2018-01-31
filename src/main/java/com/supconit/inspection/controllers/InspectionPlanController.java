package com.supconit.inspection.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.text.SimpleDateFormat;
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
import com.supconit.inspection.entities.InspectionPlan;
import com.supconit.inspection.services.InspectionPlanService;

/**
 * 巡检计划控制类
 * @author yuhuan
 * @日期 2015/09
 */
@Controller
@RequestMapping("inspection/plan")
public class InspectionPlanController extends BaseControllerSupport {
	@Autowired
	private InspectionPlanService inspectionPlanService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	
	private static final SimpleDateFormat sdf_month = new SimpleDateFormat("yyyy-MM");
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list/{type}",method=RequestMethod.GET)
	public String list(@PathVariable String type,ModelMap model) {
		List<DeviceCategory> deviceCategoryList = deviceCategoryService.findAll();
        model.addAttribute("deviceCategoryList", deviceCategoryList);//设备类别树
        List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);//地理区域树
		model.put("planDate", sdf_month.format(new Date()));
        if(type.equals("1")){
        	return "inspection/plan/week_list";
        }else{
        	return "inspection/plan/month_list";
        }
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list/{type}",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<InspectionPlan> dolist(@PathVariable String type,InspectionPlan condition,
			Pagination<InspectionPlan> pager, ModelMap model) {
		String[] planDates = condition.getPlanDates().split("-");
		condition.setYear(Integer.parseInt(planDates[0]));
		condition.setMonth(Integer.parseInt(planDates[1]));
		if(type.equals("1")){//周
			inspectionPlanService.findByCondition(pager,condition);
		}else{//月
			inspectionPlanService.findByConditionMonth(pager,condition);
		}
		return pager;
	}

}
