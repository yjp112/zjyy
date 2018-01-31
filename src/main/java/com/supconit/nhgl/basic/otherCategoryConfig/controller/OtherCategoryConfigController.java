package com.supconit.nhgl.basic.otherCategoryConfig.controller;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.Constant;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.otherCategory.entity.OtherCategory;
import com.supconit.nhgl.basic.otherCategory.servive.OtherCategoryService;
import com.supconit.nhgl.basic.otherCategoryConfig.entities.OtherCategoryConfig;
import com.supconit.nhgl.basic.otherCategoryConfig.service.OtherCategoryConfigService;
@Controller
@RequestMapping("/nhgl/basic/otherCategoryConfig")
public class OtherCategoryConfigController extends BaseControllerSupport{
	@Autowired
	private OtherCategoryConfigService otherCategoryConfigService;
	@Autowired
	private OtherCategoryService otherCategoryService;
	
	@RequestMapping("list")
	public String list(ModelMap model,Integer nhType){
		List<OtherCategory> treeList=otherCategoryService.findAll();
		model.put("treeList", treeList);
		model.put("nhType", nhType);
		return "nhgl/basic/otherCategoryConfig/list";
	}
	
	@ResponseBody
	@RequestMapping("pager")
	public Pageable<OtherCategoryConfig> pager(Pagination<OtherCategoryConfig> pager, OtherCategoryConfig condition){
		if(condition.getCategoryId()==null){
	 		condition.setCategoryId(0l);
		}
		otherCategoryConfigService.findByCondition(pager, condition);
		return pager;
	}
	
	@RequestMapping("edit")
	public String edit(@RequestParam(required = false) Boolean viewOnly,ModelMap model,OtherCategoryConfig condition){
		String devName="";
		if(condition.getId()!=null){   //修改
			OtherCategoryConfig otherCategoryConfig=otherCategoryConfigService.getByCondition(condition);
			//区域叠加区域
			otherCategoryConfig.setSubLeftOtherCategoryConfigList(otherCategoryConfigService.findByCategoryIdAndRule(condition,Constant.RULE_FLAG_PLUS));
			//区域被减区
			otherCategoryConfig.setSubRightOtherCategoryConfigList(otherCategoryConfigService.findByCategoryIdAndRule(condition,Constant.RULE_FLAG_DECREASE));
			model.put("otherCategoryConfig", otherCategoryConfig);
		}else{
			model.put("otherCategoryConfig", condition);
		}
		model.put("viewOnly", viewOnly);
		if(condition.getNhType().equals(1)){
			devName="电表";
		}else if(condition.getNhType().equals(2)){
			devName="水表";
		}else if(condition.getNhType().equals(3)){
			devName="蒸汽表";
		}else{
			devName="能量表";
		}
		model.put("devName", devName);
		return "nhgl/basic/otherCategoryConfig/edit";
	}
	
	@ResponseBody
	@RequestMapping("save")
	public ScoMessage save(OtherCategoryConfig otherCategoryConfig){
		if(otherCategoryConfig.getId()==null){//添加
			otherCategoryConfigService.insert(otherCategoryConfig);
		}else{//修改
			otherCategoryConfigService.update(otherCategoryConfig); 
		}
		return ScoMessage.success("nhgl/basic/otherCategoryConfig/list?nhType="+otherCategoryConfig.getNhType(), "操作成功。");
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete(OtherCategoryConfig otherCategoryConfig){
		otherCategoryConfigService.deleteByCategoryId(otherCategoryConfig.getCategoryId(),otherCategoryConfig.getNhType().toString());
		return ScoMessage.success("nhgl/basic/otherCategoryConfig/list?nhType="+otherCategoryConfig.getNhType(), "删除成功。");
	}
	@ResponseBody
	@RequestMapping(value = "check", method = RequestMethod.POST)
	public boolean check(OtherCategoryConfig otherCategoryConfig){
		boolean state=false;
		int count=otherCategoryConfigService.countFindAll(otherCategoryConfig);
		if(count>0)
			state=true;
		return state;
	}
}
