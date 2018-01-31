package com.supconit.nhgl.query.collect.energy.controller;

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

import com.supconit.common.utils.Constant;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.analyse.electric.area.entities.NhArea;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.energy.area.entities.NhENAreaMonth;
import com.supconit.nhgl.analyse.energy.area.service.NhENAreaMonthService;
import com.supconit.nhgl.analyse.energy.dept.entities.NhENDeptMonth;
import com.supconit.nhgl.analyse.energy.dept.service.NhENDeptMonthService;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.utils.GraphUtils;
@Controller
@RequestMapping("nhgl/query/collect/energy")
public class NhENMonthController  extends BaseControllerSupport{
	@Autowired
	private NhAreaService areaService;
	@Autowired
	private NhDeptService deptService;
	@Autowired
	private NhENAreaMonthService areaMonthService;
	@Autowired
	private NhENDeptMonthService deptMonthService;
	@Autowired
	private NhItemService itemService;
	
	//首页
	@RequestMapping("list")
	public String list(@ModelAttribute NhENAreaMonth area,@ModelAttribute NhENDeptMonth dept,ModelMap model,Integer nhType){
		//flag等于1表示显示部门信息
		model.put("flag", GraphUtils.DEPT);
		model.put("nhType", GraphUtils.ENERGY_TYPE);
		return "nhgl/query/collect/energy/energyMeterMonth_list";
	}
	//左边树
	@RequestMapping("treeTab")
	public String getTreeTab(@ModelAttribute NhENDeptMonth dept,@ModelAttribute NhENAreaMonth area,Integer flag,ModelMap model){
		model.put("flag", flag);
		if(flag == GraphUtils.DEPT){
			dept.setNhType(GraphUtils.ENERGY_TYPE);
			List<NhENDeptMonth> list = deptMonthService.getDeptList(dept);
			NhDept dp = new NhDept();
			dp.setpId(Long.parseLong(GraphUtils.TECHNIC_PID.toString()));
			dp.setNhType(Constant.NH_TYPE_Q);
			model.put("technicId", deptService.findRootId());
			model.put("list", list);
			return "nhgl/query/collect/energy/deptTree";
		}
		area.setNhType(GraphUtils.ENERGY_TYPE);
		model.put("technicId", areaService.findRootId());
		List<NhENAreaMonth> glist = areaMonthService.getAreaList(area);
		model.put("glist", glist);
		return "nhgl/query/collect/energy/areaTree";
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
	public Pageable<NhENAreaMonth> page(Pagination<NhENAreaMonth> pager,
			@ModelAttribute NhENAreaMonth condition, ModelMap model){
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
	public Pageable<NhENDeptMonth> deptlist(Pagination<NhENDeptMonth> pager,
			@ModelAttribute NhENDeptMonth condition,ModelMap model){
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
