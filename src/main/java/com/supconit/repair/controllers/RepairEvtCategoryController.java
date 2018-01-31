package com.supconit.repair.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.StringUtil;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.repair.entities.RepairEvtCategory;
import com.supconit.repair.services.RepairEvtCategoryService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("repair/repairEvtCategory")
public class RepairEvtCategoryController extends BaseControllerSupport {

    
	@Autowired
	private RepairEvtCategoryService repairEvtCategoryService;
	
    /*
    get "repairEvtCategory" list
    */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		List<RepairEvtCategory> treeList = repairEvtCategoryService.findAll();
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.EMERGENCY));
		model.put("treeList", treeList);		
		return "repair/appeal/list";
	}

    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.POST)
	public Pageable<RepairEvtCategory> page(Pagination<RepairEvtCategory> pager, 
			@ModelAttribute RepairEvtCategory repairEvtCategory,
			ModelMap model) {
		return repairEvtCategoryService.findByCondition(pager, repairEvtCategory);
	}

    /*
    save  repairEvtCategory
    repairEvtCategory object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(RepairEvtCategory repairEvtCategory) {

		if(repairEvtCategory.getParentId()==null){
			 repairEvtCategory.setParentId(0L);
		 }
         if(repairEvtCategory.getId()==null){
            copyCreateInfo(repairEvtCategory);
            repairEvtCategoryService.insert(repairEvtCategory);	
        }
        else{
            copyUpdateInfo(repairEvtCategory);    
            repairEvtCategoryService.update(repairEvtCategory);	
        }
		return ScoMessage.success("repair/repairEvtCategory/list",ScoMessage.SAVE_SUCCESS_MSG);
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {		
		repairEvtCategoryService.deleteByIds(ids);	
		return ScoMessage.success("repair/repairEvtCategory/list",ScoMessage.DELETE_SUCCESS_MSG);
	}   
    
    /**
	 * Edit RepairEvtCategory
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model,String showFlag,String viewOnly) {
    	
    	List<RepairEvtCategory> lstProperty = new ArrayList<RepairEvtCategory>();
    	//List<RepairEvtCategory> lstPropertyAll = repairEvtCategoryService.findList("findAll",null);
		if (null != id) {
			Map<String, Long> slqMap = new HashMap<String, Long>();
	    	slqMap.put("id",id);
	    	//lstProperty = repairEvtCategoryService.findList("findByCategoryId",slqMap);
	    	RepairEvtCategory repairEvtCategory = repairEvtCategoryService.getById(id);
			if (null == repairEvtCategory) {
				throw new IllegalArgumentException("Object does not exist");
			}
			if(UtilTool.isEmpty(repairEvtCategory.getParentName())){
				repairEvtCategory.setParentName("维修类别簇");
			}
			model.put("repairEvtCategory", repairEvtCategory);
		}	
		model.put("lstProperty", lstProperty);
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.EMERGENCY));
		//model.put("lstPropertyAll", lstPropertyAll);
		model.put("showFlag", showFlag);
		model.put("viewOnly", viewOnly);
		return "repair/appeal/edit";
	}
    
    
	@RequestMapping(value = "repair", method = RequestMethod.GET)
	public String repairevt(ModelMap model, String txtId, String txtName) {
    	List<RepairEvtCategory> treeList = repairEvtCategoryService.findAll();
		model.addAttribute("treeList", treeList);
		model.put("txtId",txtId);
		model.put("txtName","categoryName");
		return "repair/appeal/choose_repair";
	}
	
	
	@RequestMapping("lookup")
    public String lookup(String txtId,String txtName,String dialogId,ModelMap model) {
		List<RepairEvtCategory> treeList = null;
		treeList = repairEvtCategoryService.findAll();
	 	model.put("treeList", treeList);
        model.put("txtId", txtId);  
        model.put("txtName", txtName);  
        model.put("dialogId", dialogId);
        return "repair/category/category_lookup";
    }

}
