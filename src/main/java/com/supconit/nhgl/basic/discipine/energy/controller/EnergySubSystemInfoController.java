package com.supconit.nhgl.basic.discipine.energy.controller;

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
import com.supconit.nhgl.basic.discipine.energy.entities.EnergySubSystemInfo;
import com.supconit.nhgl.basic.discipine.energy.service.EnergySubSystemInfoService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller()
@RequestMapping("nhgl/basic/discipine/energy")
public class EnergySubSystemInfoController extends BaseControllerSupport{
	@Autowired
	private EnergySubSystemInfoService energySubSystemInfoService;
	@RequestMapping("list")
	public String list(ModelMap model) {
		List<EnergySubSystemInfo> list=energySubSystemInfoService.selecCategories();
		model.put("treeDatas", list);
		return "nhgl/basic/discipine/energy/energySubSystemInfo_list";
	}
	public EnergySubSystemInfo getRootSubSystemInfo(){
		EnergySubSystemInfo rootCategory=new EnergySubSystemInfo();
		rootCategory.setStandardCode("0");
		rootCategory.setParentId("-1");
		rootCategory.setName("分项");
		return rootCategory;
	}
	@RequestMapping("loadtree")
	@ResponseBody
	public List<EnergySubSystemInfo> reloadTree(){
		List<EnergySubSystemInfo> lstSub=energySubSystemInfoService.selecCategories();
		lstSub.add(getRootSubSystemInfo());
		return lstSub;
	}
	@ResponseBody
	@RequestMapping("page")
	public Pageable<EnergySubSystemInfo> page(Pagination<EnergySubSystemInfo> pager,
			@ModelAttribute EnergySubSystemInfo subSystemInfo, ModelMap model) {
		energySubSystemInfoService.findByCondition(pager, subSystemInfo);
		return pager;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly,
			@RequestParam(required = false) Long id,String parentId,String parentName,ModelMap model) {
		EnergySubSystemInfo energySubSystemInfo = null;
		Boolean ishasChild=false;
		if (null != id) {
			energySubSystemInfo = energySubSystemInfoService.findById(id);
			if (null == energySubSystemInfo) {
				throw new IllegalArgumentException("object dose not exist");
			}
			EnergySubSystemInfo parent=energySubSystemInfoService.findByStandardCode(energySubSystemInfo.getParentId());
			if(parent==null){
				parent=getRootSubSystemInfo();
			}
			List<EnergySubSystemInfo> lstSub=energySubSystemInfoService.findChildren(energySubSystemInfo.getStandardCode());
			ishasChild=lstSub.size()>0;
			energySubSystemInfo.setParentName(parent.getName());
			model.put("energySubSystemInfo", energySubSystemInfo);
		}
		model.put("parentId", parentId);
		model.put("parentName", parentName);
		model.put("viewOnly", viewOnly);
		model.put("ishasChild", ishasChild);
		return "nhgl/basic/discipine/energy/energySubSystemInfo_edit";
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(EnergySubSystemInfo energySubSystemInfo) {
		EnergySubSystemInfo energySubInfo=energySubSystemInfoService.findByStandardCode(energySubSystemInfo.getStandardCode());
		String parentId=energySubSystemInfo.getParentId();
		String name=energySubSystemInfo.getName();
		Long id=energySubSystemInfo.getId();
		if (energySubSystemInfo.getId() == null) {
			if(energySubSystemInfo.getStandardCode()!=null && !"".equals(energySubSystemInfo.getStandardCode())){
				if(energySubInfo!=null){
					return ScoMessage.error("["+energySubSystemInfo.getStandardCode()+"]该标准编码已存在！");
				}
			}
			energySubSystemInfoService.insert(energySubSystemInfo);
		} else {
			if(energySubSystemInfo.getStandardCode()!=null && !"".equals(energySubSystemInfo.getStandardCode())){
				if((energySubInfo!=null && !parentId.equals(energySubInfo.getParentId()))|| (energySubInfo!=null&& id!=energySubInfo.getId())){
					return ScoMessage.error("["+energySubSystemInfo.getStandardCode()+"]该标准编码已存在！");
				} 
			}
			energySubSystemInfoService.update(energySubSystemInfo);
		}
		return ScoMessage.success("basic/discipine/energy/list", "操作成功。");
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete(@RequestParam(required = false) Long id) {
		energySubSystemInfoService.deleteById(id);
		return ScoMessage.success("basic/discipine/energy/list", "删除成功。");
	}

}
