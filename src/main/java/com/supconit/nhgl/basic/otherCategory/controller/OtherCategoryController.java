package com.supconit.nhgl.basic.otherCategory.controller;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.otherCategory.entity.OtherCategory;
import com.supconit.nhgl.basic.otherCategory.servive.OtherCategoryService;

@Controller
@RequestMapping("/nhgl/basic/otherCategory")
public class OtherCategoryController extends BaseControllerSupport {
	
	@Autowired
	private OtherCategoryService otherCategoryService;

	@RequestMapping(value="list",method=RequestMethod.GET)
	public String go(ModelMap model) {
		List<OtherCategory> treeList = null;
		treeList = otherCategoryService.findTree();
		model.put("treeList", treeList);
		return "nhgl/basic/otherCategory/list";
	}

	@RequestMapping(value="list",method=RequestMethod.POST)
	@ResponseBody
	public Pageable<OtherCategory> list(@ModelAttribute OtherCategory otherCategory,
			@RequestParam(required = false) String treeId,
			Pagination<OtherCategory> pager, ModelMap model) {
		if (StringUtil.isNotBlank(treeId)) {// 为了区别树上面的参数parentId和查询表单里面的parentId
			otherCategory.setId(Long.parseLong(treeId));
		}
		Pageable<OtherCategory> dd = otherCategoryService.findByPage(pager, otherCategory);
		return dd;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,ModelMap model, @RequestParam(required = false) Long id) {
    	//修改
		if (null != id) {
			OtherCategory otherCategory = otherCategoryService.getById(id);	 
			if (StringUtil.isBlank(otherCategory.getParentName())){
				otherCategory.setParentName("根目录");
				//otherCategory.setParentId(0L); 
			}
			model.put("otherCategory", otherCategory);
		}
		List<EnumDetail> typeList = DictUtils.getDictList(DictTypeEnum.NH_TYPE);
		model.put("typeList", typeList);
		model.put("viewOnly", viewOnly);
		return "nhgl/basic/otherCategory/edit";
	}
	
    /**
	 * 判断编码是否重名  
	 * @throws HoncombException
	 */
	@ResponseBody
	@RequestMapping("checkAreaCode")
	public String checkAreaCode(String code) throws Exception{

		List<OtherCategory> list=otherCategoryService.findByCode(code);	
		
		String responseText;
		if(list.size()==0){
			responseText="usable";
		}
		else{
			responseText="unusable";
		}
		
		return responseText;
	}
	
	/**
	 * 区域保存/修改
	 * @return
	 * @throws HoncombException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(OtherCategory otherCategory) {
		if (otherCategory.getId() == null) {
			copyCreateInfo(otherCategory);
			otherCategoryService.insert(otherCategory);
		} else {
			copyUpdateInfo(otherCategory);
			otherCategoryService.update(otherCategory);
		}
		return ScoMessage.success("nhgl/basic/otherCategory/list","操作成功。");
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		int a=0;
		otherCategoryService.removeOtherCategory(ids);
		
		return ScoMessage.success("nhgl/basic/otherCategory/list", "删除成功。");
	} 
	
	@ResponseBody
	@RequestMapping("findFloorById")
	public Map<String,List<OtherCategory>> findFloorById(
			@RequestParam(required = false) Long buildingId) {
		if(null!=buildingId){
			List<OtherCategory> listOtherCategorys = otherCategoryService.findFloorById(buildingId);
			Map<String,List<OtherCategory>> map = new HashMap<String,List<OtherCategory>>();
			map.put("listOtherCategorys", listOtherCategorys);
			return map;
		}else
			return null;
	}
	
	@ResponseBody
	@RequestMapping("findOtherCategoryByCode")
	public Map<String,OtherCategory> findOtherCategoryByCode(
			@RequestParam(required = false) String code) {
		if(null!=code&&!("").equals(code)){
			List<OtherCategory> list=otherCategoryService.findByCode(code);
			Map<String,OtherCategory> map = new HashMap<String,OtherCategory>();
			map.put("otherCategory", list.get(0));
			return map;
		}else
			return null;
	}
	
	@RequestMapping("lookup")
    public String lookup(String txtId,String txtName,String dialogId,ModelMap model) {
		List<OtherCategory> treeList = null;
		treeList = otherCategoryService.findTree();
		model.put("treeList", treeList);
        model.put("txtId", txtId);  
        model.put("txtName", txtName);  
        model.put("dialogId", dialogId);
        return "nhgl/basic/otherCategory/otherCategory_lookup";
    }
	
	

}
