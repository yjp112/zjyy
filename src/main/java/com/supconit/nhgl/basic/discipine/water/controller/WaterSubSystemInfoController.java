package com.supconit.nhgl.basic.discipine.water.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.discipine.water.entities.WaterSubSystemInfo;
import com.supconit.nhgl.basic.discipine.water.service.WaterSubSystemInfoService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller()
@RequestMapping("nhgl/basic/discipine/water")
public class WaterSubSystemInfoController extends BaseControllerSupport{
	@Autowired
	public WaterSubSystemInfoService waterSubSystemInfoService;
	@RequestMapping("list")
	public String list(ModelMap model) {
		List<WaterSubSystemInfo> list=waterSubSystemInfoService.selectCategories();
		model.put("treeDatas", list);
		return "nhgl/basic/discipine/water/waterSubSystemInfo_list";
	}
	public WaterSubSystemInfo getRootSubSystemInfo(){
		WaterSubSystemInfo rootCategory=new WaterSubSystemInfo();
		rootCategory.setStandardCode("0");
		rootCategory.setParentId("-1");
		rootCategory.setName("分项");
		return rootCategory;
	}
	@RequestMapping("loadtree")
	@ResponseBody
	public List<WaterSubSystemInfo> reloadTree(){
		List<WaterSubSystemInfo> lstSub=waterSubSystemInfoService.selectCategories();
		lstSub.add(getRootSubSystemInfo());
		return lstSub;
	}
	@ResponseBody
	@RequestMapping("page")
	public Pageable<WaterSubSystemInfo> page(Pagination<WaterSubSystemInfo> pager,
			@ModelAttribute WaterSubSystemInfo subSystemInfo, ModelMap model) {
		waterSubSystemInfoService.findByCondition(pager, subSystemInfo);
		return pager;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly,
			@RequestParam(required = false) Long id,String parentId,String parentName,ModelMap model) {
		WaterSubSystemInfo waterSubSystemInfo = null;
		Boolean ishasChild=false;
		if (null != id) {
			waterSubSystemInfo = waterSubSystemInfoService.findById(id);
			if (null == waterSubSystemInfo) {
				throw new IllegalArgumentException("object dose not exist");
			}
			WaterSubSystemInfo parent=waterSubSystemInfoService.findByStandardCode(waterSubSystemInfo.getParentId());
			if(parent==null){
				parent=getRootSubSystemInfo();
			}
			List<WaterSubSystemInfo> lstSub=waterSubSystemInfoService.findChildren(waterSubSystemInfo.getStandardCode());
			ishasChild=lstSub.size()>0;
			waterSubSystemInfo.setParentName(parent.getName());
			model.put("waterSubSystemInfo", waterSubSystemInfo);
		}
		model.put("parentId", parentId);
		model.put("parentName", parentName);
		model.put("viewOnly", viewOnly);
		model.put("ishasChild", ishasChild);
		return "nhgl/basic/discipine/water/waterSubSystemInfo_edit";
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(WaterSubSystemInfo waterSubSystemInfo) {
		WaterSubSystemInfo waterSubInfo=waterSubSystemInfoService.findByStandardCode(waterSubSystemInfo.getStandardCode());
		String parentId=waterSubSystemInfo.getParentId();
		String name=waterSubSystemInfo.getName();
		Long id=waterSubSystemInfo.getId();
		if (waterSubSystemInfo.getId() == null) {
			if(waterSubSystemInfo.getStandardCode()!=null && !"".equals(waterSubSystemInfo.getStandardCode())){
				if(waterSubInfo!=null){
					return ScoMessage.error("["+waterSubSystemInfo.getStandardCode()+"]该标准编码已存在！");
				}
			}
			waterSubSystemInfoService.insert(waterSubSystemInfo);
		} else {
			if(waterSubSystemInfo.getStandardCode()!=null && !"".equals(waterSubSystemInfo.getStandardCode())){
				if((waterSubInfo!=null && !parentId.equals(waterSubInfo.getParentId()))|| (waterSubInfo!=null&& id!=waterSubInfo.getId())){
					return ScoMessage.error("["+waterSubSystemInfo.getStandardCode()+"]该标准编码已存在！");
				} 
			}
			waterSubSystemInfoService.update(waterSubSystemInfo);
		}
		return ScoMessage.success("basic/discipine/water/list", "操作成功。");
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete(@RequestParam(required = false) Long id) {
		waterSubSystemInfoService.deleteById(id);
		return ScoMessage.success("basic/discipine/water/list", "删除成功。");
	}

}
