package com.supconit.nhgl.basic.discipine.gas.controller;

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
import com.supconit.nhgl.basic.discipine.gas.entities.GasSubSystemInfo;
import com.supconit.nhgl.basic.discipine.gas.service.GasSubSystemInfoService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller()
@RequestMapping("nhgl/basic/discipine/gas")
public class GasSubSystemInfoController extends BaseControllerSupport{
	@Autowired
	private GasSubSystemInfoService gasSubSystemInfoService;
	@RequestMapping("list")
	public String list(ModelMap model) {
		List<GasSubSystemInfo> list=gasSubSystemInfoService.selecCategories();
		model.put("treeDatas", list);
		return "nhgl/basic/discipine/gas/gasSubSystemInfo_list";
	}
	public GasSubSystemInfo getRootSubSystemInfo(){
		GasSubSystemInfo rootCategory=new GasSubSystemInfo();
		rootCategory.setStandardCode("0");
		rootCategory.setParentId("-1");
		rootCategory.setName("分项");
		return rootCategory;
	}
	@RequestMapping("loadtree")
	@ResponseBody
	public List<GasSubSystemInfo> reloadTree(){
		List<GasSubSystemInfo> lstSub=gasSubSystemInfoService.selecCategories();
		lstSub.add(getRootSubSystemInfo());
		return lstSub;
	}
	@ResponseBody
	@RequestMapping("page")
	public Pageable<GasSubSystemInfo> page(Pagination<GasSubSystemInfo> pager,
			@ModelAttribute GasSubSystemInfo subSystemInfo, ModelMap model) {
		gasSubSystemInfoService.findByCondition(pager, subSystemInfo);
		return pager;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly,
			@RequestParam(required = false) Long id,String parentId,String parentName,ModelMap model) {
		GasSubSystemInfo gasSubSystemInfo = null;
		Boolean ishasChild=false;
		if (null != id) {
			gasSubSystemInfo = gasSubSystemInfoService.findById(id);
			if (null == gasSubSystemInfo) {
				throw new IllegalArgumentException("object dose not exist");
			}
			GasSubSystemInfo parent=gasSubSystemInfoService.findByStandardCode(gasSubSystemInfo.getParentId());
			if(parent==null){
				parent=getRootSubSystemInfo();
			}
			List<GasSubSystemInfo> lstSub=gasSubSystemInfoService.findChildren(gasSubSystemInfo.getStandardCode());
			ishasChild=lstSub.size()>0;
			gasSubSystemInfo.setParentName(parent.getName());
			model.put("gasSubSystemInfo", gasSubSystemInfo);
		}
		model.put("parentId", parentId);
		model.put("parentName", parentName);
		model.put("viewOnly", viewOnly);
		model.put("ishasChild", ishasChild);
		return "nhgl/basic/discipine/gas/gasSubSystemInfo_edit";
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(GasSubSystemInfo gasSubSystemInfo) {
		GasSubSystemInfo gasSubInfo=gasSubSystemInfoService.findByStandardCode(gasSubSystemInfo.getStandardCode());
		String parentId=gasSubSystemInfo.getParentId();
		String name=gasSubSystemInfo.getName();
		Long id=gasSubSystemInfo.getId();
		if (gasSubSystemInfo.getId() == null) {
			if(gasSubSystemInfo.getStandardCode()!=null && !"".equals(gasSubSystemInfo.getStandardCode())){
				if(gasSubInfo!=null){
					return ScoMessage.error("["+gasSubSystemInfo.getStandardCode()+"]该标准编码已存在！");
				}
			}
			gasSubSystemInfoService.insert(gasSubSystemInfo);
		} else {
			if(gasSubSystemInfo.getStandardCode()!=null && !"".equals(gasSubSystemInfo.getStandardCode())){
				if((gasSubInfo!=null && !parentId.equals(gasSubInfo.getParentId()))|| (gasSubInfo!=null&& id!=gasSubInfo.getId())){
					return ScoMessage.error("["+gasSubSystemInfo.getStandardCode()+"]该标准编码已存在！");
				} 
			}
			gasSubSystemInfoService.update(gasSubSystemInfo);
		}
		return ScoMessage.success("basic/discipine/gas/list", "操作成功。");
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete(@RequestParam(required = false) Long id) {
		gasSubSystemInfoService.deleteById(id);
		return ScoMessage.success("basic/discipine/gas/list", "删除成功。");
	}

}
