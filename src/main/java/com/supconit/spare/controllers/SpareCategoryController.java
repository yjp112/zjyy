
package com.supconit.spare.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.entities.ScoMessage;
import com.supconit.spare.entities.SpareCategory;
import com.supconit.spare.services.SpareCategoryService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：SpareCategoryController.java
 * @创建日期：2013年7月11日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：备件类别管理
 */
@Controller
@RequestMapping("spare/spareCategory")
public class SpareCategoryController extends SpareBaseControllerSupport {

    
	@Autowired
	private SpareCategoryService spareCategoryService;
    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(@ModelAttribute SpareCategory spareCategory,
            ModelMap model) {
        //loadSpareCategoryTree(model);
    	List<SpareCategory> list = spareCategoryService.selectCategories();
        model.put("treeDatas", list);
        return "spare/spareCategory/spareCategory_list";
    }
    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<SpareCategory>  list(Pagination<SpareCategory> pager,@ModelAttribute SpareCategory spareCategory,
            ModelMap model) {
    	model.put("spareCategory", spareCategory);
		return spareCategoryService.findByCondition(pager, spareCategory);
		
	}

    /*
    save  spareCategory
    SpareCategory object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(SpareCategory spareCategory) {
        try {
			if(spareCategory.getId()==null){
			    copyCreateInfo(spareCategory);
			    spareCategoryService.insert(spareCategory); 
			}
			else{
			    copyUpdateInfo(spareCategory);    
			    spareCategoryService.update(spareCategory);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return ScoMessage.error(e.getMessage());
		}
		return ScoMessage.success("spare/spareCategory/go","操作成功。");
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		
		spareCategoryService.deleteByIds(ids);
		
		return ScoMessage.success("spare/spareCategory/go", "删除成功。");
	}   
    
    /**
	 * Edit SpareCategory
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			SpareCategory spareCategory = spareCategoryService.getById(id);
			if (null == spareCategory) {
				throw new IllegalArgumentException("Object does not exist");
			}
			SpareCategory parent=spareCategoryService.getById(spareCategory.getParentId());
			if(parent==null){
			    parent=getRootSpareCategory();
			}
			spareCategory.setParentName(parent.getCategoryName());
			model.put("spareCategory", spareCategory);			
		}		
		model.put("viewOnly", viewOnly);
		return "spare/spareCategory/spareCategory_edit";
	}
    @RequestMapping("lookup")
    public String lookup(String txtId,String txtName,String dialogName, Long id, ModelMap model) {
        
        if(null != id)
        {
             loadSpareCategoryTreeByFilter(model, id);
        } else
        {
            loadSpareCategoryTree(model);
        }
        model.put("txtId", txtId);  
        model.put("txtName", txtName);  
        model.put("dialogName", dialogName);
        return "spare/spareCategory/spareCategory_lookup";
    }   
}
