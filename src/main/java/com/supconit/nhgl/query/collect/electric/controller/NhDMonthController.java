package com.supconit.nhgl.query.collect.electric.controller;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.analyse.electric.area.entities.NhArea;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaMonth;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.electric.area.service.NhDAreaMonthService;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptMonth;
import com.supconit.nhgl.analyse.electric.dept.service.NhDDeptMonthService;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.utils.GraphUtils;
@Controller
@RequestMapping("nhgl/query/collect/electric")
public class NhDMonthController  extends BaseControllerSupport{
	@Autowired
	private NhAreaService areaService;
	@Autowired
	private NhDeptService deptService;
	@Autowired
	private NhDAreaMonthService areaMonthService;
	@Autowired
	private NhDDeptMonthService deptMonthService;
	@Autowired
	private NhItemService itemService;
	
	//首页
	@RequestMapping("list")
	public String list(@ModelAttribute NhDAreaMonth area,@ModelAttribute NhDDeptMonth dept,ModelMap model){
		//flag等于1表示显示部门信息
		model.put("flag", GraphUtils.DEPT);
		model.put("nhType", GraphUtils.ELECTRIC_TYPE);
		return "nhgl/query/collect/electric/electricMeterMonth_list";
	}
	//左边树
	@RequestMapping("treeTab")
	public String getTreeTab(@ModelAttribute NhDDeptMonth dept,@ModelAttribute NhDAreaMonth area,Integer flag,ModelMap model){
		model.put("flag", flag);
		if(flag == GraphUtils.DEPT){
			dept.setNhType(GraphUtils.ELECTRIC_TYPE);
			List<NhDDeptMonth> list = deptMonthService.getDeptList(dept);
			
			NhDept dp = new NhDept();
			dp.setpId(Long.valueOf(String.valueOf(deptService.findRootId()))); 
			dp.setNhType(GraphUtils.ELECTRIC_TYPE);
			
			model.put("technicId", deptService.findRootId());
			model.put("list", list);
			return "nhgl/query/collect/electric/deptTree";
		}
		model.put("technicId", areaService.findRootId());
		area.setNhType(GraphUtils.ELECTRIC_TYPE);
		List<NhDAreaMonth> glist = areaMonthService.getAreaList(area);
		model.put("glist", glist);
		return "nhgl/query/collect/electric/areaTree";
	}
	/**
	 * 获取区域的用电量
	 * @param pager
	 * @param condition
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("arealist")
	public Pageable<NhDAreaMonth> page(Pagination<NhDAreaMonth> pager,
			@ModelAttribute NhDAreaMonth condition, ModelMap model){
		List<NhArea> tree = areaService.getTreeById(condition.getAreaId());
		List<Integer> list = new ArrayList<Integer>();
		List<String> itemCodes = new ArrayList<String>();
		for(NhArea area : tree){
			list.add(area.getId().intValue());
			
		}
		condition.setAreaIdList(list);
		List<NhItem> itemList = new ArrayList<NhItem>();
		itemList = itemService.getTreeByItemCode(condition.getItemCode());
		for(NhItem item : itemList){
			itemCodes.add(item.getStandardCode());
			
		}
		condition.setItemCodeList(itemCodes);
		areaMonthService.findByCondition(pager, condition);
		return pager;
	}
	/**
	 * 获取部门的用电量
	 * @param pager
	 * @param condition
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deptlist")
	public Pageable<NhDDeptMonth> deptlist(Pagination<NhDDeptMonth> pager,
			@ModelAttribute NhDDeptMonth condition,ModelMap model){
		List<NhDept> tree = deptService.getTreeById(condition.getDeptId());
		List<Integer> list = new ArrayList<Integer>();
		List<String> itemCodes = new ArrayList<String>();
		for(NhDept dept : tree){
			list.add(dept.getId().intValue());
			
		}
		condition.setDeptIdList(list);
		List<NhItem> itemList = new ArrayList<NhItem>();
		itemList = itemService.getTreeByItemCode(condition.getItemCode());
		for(NhItem item : itemList){
			itemCodes.add(item.getStandardCode());
			
		}
		condition.setItemCodeList(itemCodes);
		deptMonthService.findByDeptCondition(pager, condition);
		return pager; 
	}
	
	
	
}
