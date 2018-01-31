package com.supconit.nhgl.publicity;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.analyse.electric.area.entities.NhArea;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaMonth;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.electric.area.service.NhDAreaMonthService;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptMonth;
import com.supconit.nhgl.analyse.electric.dept.service.NhDDeptMonthService;
import com.supconit.nhgl.analyse.water.area.entities.NhSAreaMonth;
import com.supconit.nhgl.analyse.water.area.service.NhSAreaMonthService;
import com.supconit.nhgl.analyse.water.dept.entities.NhSDeptMonth;
import com.supconit.nhgl.analyse.water.dept.service.NhSDeptMonthService;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.utils.GraphUtils;

/**
 * 能耗告示
 * @author WangHaiBO
 *
 */
@Controller
@RequestMapping("nhgl/publicity")
public class NhgsController extends BaseControllerSupport{

	
	@Autowired
	private NhAreaService areaService;
	
	@Autowired
	private NhDDeptMonthService deptElectricService;
	
	@Autowired
	private NhDAreaMonthService areaElectricService;
	
	@Autowired
	private NhSAreaMonthService areaWaterService;
	
	@Autowired
	private NhSDeptMonthService deptWaterService;
	
	@Autowired
	private NhDeptService deptService;
	
	@RequestMapping("list")
	public String list(ModelMap model){
		
//		
//		model.addAttribute("sMonth", startMonth);
//		model.addAttribute("eMonth", endTime.toString("YYYY-MM"));
//		model.addAttribute("nowTime", nowTime);
		return "nhgl/publicity/nhgs";
	}
	/**
	 * 总能耗
	 * @param model
	 * @param startMonth
	 * @param endsMonth
	 * @param flag 表示查询的是几个月内的数据
	 * @param type 表示请求的是单位面积还是总能耗
	 * @return
	 */
	@RequestMapping("nhgl_all")
	public String getNhglAll(ModelMap model,String startMonth,String endsMonth,Integer flag){
		Date date = new Date();
		JDateTime time = new JDateTime(date);
		String nowTime = time.toString("YYYY-MM-DD hh:mm:ss");
		Integer month = Integer.parseInt(nowTime.substring(5,7));
		Integer start = month;
		Integer ends = month;
		JDateTime endTime = time;
		if(startMonth != null && !("").equals(startMonth) && endsMonth !=null && !("").equals(endsMonth) && flag == null){
			start = Integer.parseInt(startMonth.substring(5,7));
			ends = Integer.parseInt(endsMonth.substring(5,7));
		}
		//获取当前 年
		Integer year = Integer.parseInt(nowTime.substring(0,4));
		Integer startYear = year;
		Integer endYear = year;
		if(startMonth != null && endsMonth != null){
			startYear = Integer.parseInt(startMonth.substring(0, 4));
			endYear = Integer.parseInt(endsMonth.substring(0, 4));
		}
		//月份查询耗电
		if(flag != null){
			switch(flag){
				case 1 :{
					startYear=endTime.getYear();
					endYear=endTime.getYear();
					start = ends;
					break;
				}
				case 3 :{
					start = ends-2;
					break;
				}
				case 6 :{
					start = ends-5;
					break;
				}
				case 12 :{
					start = ends - 11;
					break;
				}
			}
			
		}
		if(start<=0){
			startYear=endYear-1;
			start =start +12;
		}else if(!startYear.equals(endYear) && flag!=null){
			startYear=endYear;
		}
		startMonth = GraphUtils.getDateString(startYear, start, null);
		endsMonth = GraphUtils.getDateString(endYear, ends, null);
		
		List<NhDAreaMonth> areaMonthList = null;
		List<NhDDeptMonth> deptMonthList = null;
		
		List<NhSAreaMonth> areaWaterMonthList = null;
		List<NhSDeptMonth> deptWaterMonthList = null;
		
		NhDAreaMonth areaEMonth = new NhDAreaMonth();
		NhDDeptMonth deptEMonth = new NhDDeptMonth();
		NhSAreaMonth areaWMonth = new NhSAreaMonth();
		NhSDeptMonth deptWMonth = new NhSDeptMonth();
		areaEMonth.setStart(startMonth);
		areaEMonth.setEnd(endsMonth);
		areaEMonth.setParentId(Integer.valueOf(areaService.findRootId().toString()));
		deptEMonth.setStart(startMonth);
		deptEMonth.setEnd(endsMonth);
		deptEMonth.setPid(Integer.valueOf(deptService.findRootId().toString()));
		areaMonthList = areaElectricService.getParentAreaMonthElectricty(areaEMonth);
		deptMonthList = deptElectricService.getParentDeptMonthElectricty(deptEMonth);
		
		areaWMonth.setStart(startMonth);
		areaWMonth.setEnd(endsMonth);
		areaWMonth.setParentId(Integer.valueOf(areaService.findRootId().toString()));
		deptWMonth.setStart(startMonth);
		deptWMonth.setEnd(endsMonth);
		deptWMonth.setPid(Integer.valueOf(deptService.findRootId().toString()));
		areaWaterMonthList = areaWaterService.getParentAreaMonthWater(areaWMonth);
		deptWaterMonthList = deptWaterService.getParentDeptMonthWater(deptWMonth);
		model.put("areaMonthList", areaMonthList);
		model.put("deptMonthList", deptMonthList);
		model.put("areaWaterMonthList", areaWaterMonthList);
		model.put("deptWaterMonthList", deptWaterMonthList);
		model.addAttribute("sMonth", startMonth);
		model.addAttribute("eMonth", endsMonth);
		model.put("minDate", GraphUtils.getDateString(time.getYear(), 1, null));
		return "nhgl/publicity/nhgs_all";
		
	}
	
	/**
	 * 单位面积
	 * @param model
	 * @param startMonth
	 * @param endsMonth
	 * @param flag 表示查询的是几个月内的数据
	 * @param type 表示请求的是单位面积还是总能耗
	 * @return
	 */
	@RequestMapping("nhgl_area")
	public String getNhglArea(ModelMap model,String startMonth,String endsMonth,Integer flag){
		Date date = new Date();
		JDateTime time = new JDateTime(date);
		String nowTime = time.toString("YYYY-MM-DD hh:mm:ss");
		Integer month = Integer.parseInt(nowTime.substring(5,7));
		Integer start = month;
		Integer ends = month;
		JDateTime endTime = time;
		if(startMonth != null && !("").equals(startMonth) && endsMonth !=null && !("").equals(endsMonth) && flag == null){
			start = Integer.parseInt(startMonth.substring(5,7));
			ends = Integer.parseInt(endsMonth.substring(5,7));
		}
		//获取当前 年
		Integer year = Integer.parseInt(nowTime.substring(0,4));
		Integer startYear = year;
		Integer endYear = year;
		if(startMonth != null && endsMonth != null){
			startYear = Integer.parseInt(startMonth.substring(0, 4));
			endYear = Integer.parseInt(endsMonth.substring(0, 4));
		}
		//月份查询耗电
		if(flag != null){
			switch(flag){
				case 1 :{
					startYear=endTime.getYear();
					endYear=endTime.getYear();
					start = ends;
					break;
				}
				case 3 :{
					start = ends-2;
					break;
				}
				case 6 :{
					start = ends-5;
					break;
				}
				case 12 :{
					start = ends - 11;
					break;
				}
			}
			
		}
		if(start<=0){
			startYear=endYear-1;
			start =start +12;
		}
		startMonth = GraphUtils.getDateString(startYear, start, null);
		endsMonth = GraphUtils.getDateString(endYear, ends, null);
		
		List<NhDAreaMonth> areaMonthList = null;
		List<NhDDeptMonth> deptMonthList = null;
		
		List<NhSAreaMonth> areaWaterMonthList = null;
		List<NhSDeptMonth> deptWaterMonthList = null;
		
		NhDAreaMonth areaEMonth = new NhDAreaMonth();
		NhDDeptMonth deptEMonth = new NhDDeptMonth();
		NhSAreaMonth areaWMonth = new NhSAreaMonth();
		NhSDeptMonth deptWMonth = new NhSDeptMonth();
		areaEMonth.setStart(startMonth);
		areaEMonth.setEnd(endsMonth);
		areaEMonth.setParentId(Integer.valueOf(areaService.findRootId().toString()));
		areaEMonth.setNhType(GraphUtils.ELECTRIC_TYPE);
		deptEMonth.setStart(startMonth);
		deptEMonth.setEnd(endsMonth);
		deptEMonth.setPid(Integer.valueOf(deptService.findRootId().toString()));
		deptEMonth.setNhType(GraphUtils.ELECTRIC_TYPE);
		areaMonthList = areaElectricService.getUntilAreaElectricty(areaEMonth);
		deptMonthList = deptElectricService.getUntilDeptElectricty(deptEMonth);
		
		areaWMonth.setStart(startMonth);
		areaWMonth.setEnd(endsMonth);
		areaWMonth.setParentId(Integer.valueOf(areaService.findRootId().toString()));
		areaWMonth.setNhType(GraphUtils.WATER_TYPE);
		deptWMonth.setStart(startMonth);
		deptWMonth.setEnd(endsMonth);
		deptWMonth.setPid(Integer.valueOf(deptService.findRootId().toString()));
		deptWMonth.setNhType(GraphUtils.WATER_TYPE);
		areaWaterMonthList = areaWaterService.getUntilAreaWater(areaWMonth);
		deptWaterMonthList = deptWaterService.getUntilDeptWater(deptWMonth);
		model.put("areaMonthList", areaMonthList);
		model.put("deptMonthList", deptMonthList);
		model.put("areaWaterMonthList", areaWaterMonthList);
		model.put("deptWaterMonthList", deptWaterMonthList);
		model.addAttribute("sMonth", startMonth);
		model.addAttribute("eMonth", endsMonth);
		model.put("minDate", GraphUtils.getDateString(time.getYear(), 1, null));
		return "nhgl/publicity/nhgs_area";
		
	}
	
	/**
	 * 获取总能耗的子级部门或区域的页面
	 * @param model
	 * @param parentId
	 * @param attach
	 * @param nhType
	 * @param type 表示请求的是单位面积还是总能耗
	 * @return
	 */
	@RequestMapping("getSubAll")
	public String getSubAll(ModelMap model,Long parentId,String attach,Integer nhType,String startMonth,String endMonth){
		model.addAttribute("attach", attach);
		model.addAttribute("nhType", nhType);
		model.put("parentId", parentId);
		model.put("startMonth", startMonth);
		model.put("endMonth", endMonth);
		return "nhgl/publicity/sub_list";
	}
	
	/**
	 * 获取子级区域或部门的面积
	 * @param model
	 * @param parentId
	 * @param attach
	 * @param nhType
	 * @return
	 */
	@RequestMapping("getSubArea")
	public String getSubArea(ModelMap model,Long parentId,String attach,String startMonth,String endMonth,Integer nhType){
		model.addAttribute("attach", attach);
		model.put("parentId", parentId);
		model.put("startMonth", startMonth);
		model.put("endMonth", endMonth);
		model.put("nhType", nhType);
		return "nhgl/publicity/subArea_list";
	}
	
	/**
	 * 获取区域用电量数据,及单位面积耗能
	 * @param pager
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getElectricASubAllList")
	public Pageable<NhDAreaMonth> getElectricASubAllList(Pagination<NhDAreaMonth> pager,Long parentId, String startMonth, String endMonth){
		
		NhDAreaMonth condition = new NhDAreaMonth();
		List<NhArea> list = areaService.getTreeById(parentId.intValue());
		List<Integer> ids = new ArrayList<Integer>();
		for(NhArea na : list){
			ids.add(na.getId().intValue());
		}
		condition.setAreaIdList(ids);
		condition.setStart(startMonth);
		condition.setEnd(endMonth);
		condition.setNhType(GraphUtils.ELECTRIC_TYPE);
		try{
			areaElectricService.findByConditionForNhgs(pager, condition);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pager; 
		
	}
	/**
	 * 获取部门用电量的数据,及单位面积耗能
	 * @param pager
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getElectricDSubAllList")
	public Pageable<NhDDeptMonth> getElectricDSubAllList(Pagination<NhDDeptMonth> pager,Long parentId, String startMonth, String endMonth){
		
		NhDDeptMonth condition = new NhDDeptMonth();
		List<NhDept> list = deptService.getTreeById(parentId.intValue());
		List<Integer> ids = new ArrayList<Integer>();
		for(NhDept na : list){
			ids.add(na.getId().intValue());
		}
		condition.setDeptIdList(ids);
		condition.setStart(startMonth);
		condition.setEnd(endMonth);
		condition.setNhType(GraphUtils.ELECTRIC_TYPE);
		try{
			deptElectricService.findByDeptConditionForNhgs(pager, condition);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pager; 
		
	}
	/**
	 * 获取部门用水量的数据,及单位面积耗水
	 * @param pager
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getWaterDSubAllList")
	public Pageable<NhSDeptMonth> getWaterDSubAllList(Pagination<NhSDeptMonth> pager,Long parentId, String startMonth, String endMonth){
		
		NhSDeptMonth condition = new NhSDeptMonth();
		List<NhDept> list = deptService.getTreeById(parentId.intValue());
		List<Integer> ids = new ArrayList<Integer>();
		for(NhDept na : list){
			ids.add(na.getId().intValue());
		}
		condition.setDeptIdList(ids);
		condition.setStart(startMonth);
		condition.setEnd(endMonth);
		condition.setNhType(GraphUtils.WATER_TYPE);
		try{
			deptWaterService.findByDeptConditionForNhgs(pager, condition);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pager; 
		
	}
	/**
	 * 获取区域用水量的数据,及单位面积耗水
	 * @param pager
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getWaterASubAllList")
	public Pageable<NhSAreaMonth> getWaterASubAllList(Pagination<NhSAreaMonth> pager,Long parentId, String startMonth, String endMonth){
		
		NhSAreaMonth condition = new NhSAreaMonth();
		List<NhArea> list = areaService.getTreeById(parentId.intValue());
		List<Integer> ids = new ArrayList<Integer>();
		for(NhArea na : list){
			ids.add(na.getId().intValue());
		}
		condition.setAreaIdList(ids);
		condition.setStart(startMonth);
		condition.setEnd(endMonth);
		condition.setNhType(GraphUtils.WATER_TYPE);
		try{
			areaWaterService.findByConditionForNhgs(pager, condition);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pager; 
		
	}
}
