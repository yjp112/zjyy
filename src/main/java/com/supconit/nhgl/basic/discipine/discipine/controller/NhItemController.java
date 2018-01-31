package com.supconit.nhgl.basic.discipine.discipine.controller;


import java.util.List;

import jodd.util.StringUtil;

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
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller()
@RequestMapping("nhgl/basic/discipine")
public class NhItemController extends BaseControllerSupport{
	@Autowired
	public NhItemService subSystemInfoService;
	@RequestMapping("list")
	public String list(ModelMap model, Integer nhType) {
		model.put("nhType", nhType);
		return "nhgl/basic/discipine/discipine/subSystemInfo_list";
	}
	public NhItem getRootSubSystemInfo(){
		NhItem rootCategory=new NhItem();
		rootCategory.setStandardCode("0");
		rootCategory.setParentCode("-1");
		rootCategory.setId(1l); 
		rootCategory.setName("分项");
		return rootCategory;
	}
	@RequestMapping("loadtree")
	@ResponseBody
	public List<NhItem> reloadTree(Integer nhType){
		List<NhItem> lstSub=subSystemInfoService.selectCategories(nhType);
		lstSub.add(getRootSubSystemInfo());
		return lstSub;
	}
	@ResponseBody
	@RequestMapping("page")
	public Pageable<NhItem> page(Pagination<NhItem> pager,
			@ModelAttribute NhItem subSystemInfo, ModelMap model) {
		subSystemInfoService.findByCondition(pager, subSystemInfo);
		return pager;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly,
			@RequestParam(required = false) Long id,String parentId,String parentName,ModelMap model,Integer nhType) {
		NhItem subSystemInfo = null;
		Boolean ishasChild=false;
		if (null != id) {
			subSystemInfo = subSystemInfoService.findById(id);
			if (null == subSystemInfo) {
				throw new IllegalArgumentException("object dose not exist");
			}
			NhItem parent=subSystemInfoService.findByStandardCode(subSystemInfo.getParentCode(),nhType);
			if(parent==null){
				parent=getRootSubSystemInfo();
			}
			List<NhItem> lstSub=subSystemInfoService.findChildren(subSystemInfo.getStandardCode(),nhType);
			ishasChild=lstSub.size()>0;
			subSystemInfo.setParentName(parent.getName());
			model.put("subSystemInfo", subSystemInfo);
		}
		model.put("parentId", parentId);
		model.put("parentName", parentName);
		model.put("viewOnly", viewOnly);
		model.put("ishasChild", ishasChild);
		model.put("nhType", nhType);
		return "nhgl/basic/discipine/discipine/subSystemInfo_edit";
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(NhItem subSystemInfo) {
		NhItem subInfo=subSystemInfoService.findByStandardCode(subSystemInfo.getStandardCode(),subSystemInfo.getNhType());
		Long id=subSystemInfo.getId();
		//新增时，选择分项，parentCode为空
		if(StringUtil.isEmpty(subSystemInfo.getParentCode())){
			subSystemInfo.setParentCode("0"); 
		}
		if (id == null) {
			if(subInfo!=null){
				return ScoMessage.error("["+subSystemInfo.getStandardCode()+"]该标准编码已存在！");
			}
			subSystemInfoService.insert(subSystemInfo);
		} else {
			if(subInfo!=null){
				if(subInfo.getStandardCode().equals(subSystemInfo.getStandardCode())
						|| subInfo.getCode().equals(subSystemInfo.getCode())){
					if(subInfo!=null && subInfo.getId().longValue() != id.longValue()){
						if(subInfo.getStandardCode().equals(subSystemInfo.getStandardCode()))
							return ScoMessage.error("["+subSystemInfo.getStandardCode()+"]该标准编码已存在！");
					}
				}
			}
			
			subSystemInfoService.update(subSystemInfo);
		}
		return ScoMessage.success("basic/discipine/discipine/list", "操作成功。");
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete(@RequestParam(required = false) Long id) {
		subSystemInfoService.deleteById(id);
		return ScoMessage.success("basic/discipine/discipine/list", "删除成功。");
	}

}
