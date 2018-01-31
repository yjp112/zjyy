
package com.supconit.spare.controllers;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.entities.ScoMessage;
import com.supconit.spare.entities.Spare;
import com.supconit.spare.entities.SpareCategory;
import com.supconit.spare.services.SpareCategoryService;
import com.supconit.spare.services.SpareService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：SpareController.java
 * @创建日期：2013年7月11日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：备件信息
 */
@Controller
@RequestMapping("spare/spare")
public class SpareController extends SpareBaseControllerSupport {

    
	@Autowired
	private SpareService spareService;
	@Resource
	private SpareCategoryService spareCategoryService;
    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(@ModelAttribute SpareCategory spareCategory,
            ModelMap model) {
        loadSpareCategoryTree(model);
        return "spare/spare/spare_list";
    }
    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<Spare>  list(Pagination<Spare> pager,@ModelAttribute Spare spare,
            ModelMap model) {
		return spareService.findByCondition(pager, spare);
		
	}
    /*
    save  spare
    Spare object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(Spare spare) {
         try {
			if(spare.getId()==null){
			    copyCreateInfo(spare);
			    spareService.insert(spare);	
			}
			else{
			    copyUpdateInfo(spare);    
			    spareService.update(spare);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return ScoMessage.error(e.getMessage());
		}
		return ScoMessage.success("spare/spare/go","操作成功。");
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		
		spareService.deleteByIds(ids);
		return ScoMessage.success("spare/spare/go","删除成功。");
	}   
    
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
        if (null != id) {
            Spare spare = spareService.getById(id);
            if (null == spare) {
                throw new IllegalArgumentException("Object does not exist");
            }
            //格式化金额为两位小数
            if(null != spare.getPrice())
            {
                spare.setPrice(formatPrice(spare.getPrice()));
            }
            model.put("spare", spare);			
        }	
        if(viewOnly == null || " ".equals(viewOnly)){
        	viewOnly = false;
        }
        model.put("viewOnly", viewOnly); 
        return "spare/spare/spare_edit";
    }
    @RequestMapping("lookup")
    public String lookup(String txtId,String txtName,String flag,ModelMap model) {
        loadSpareCategoryTree(model);
        model.put("txtId", txtId);  
        model.put("txtName", txtName); 
        model.put("flag", flag);  
        return "spare/spare/spare_lookup";
    }     
    
    protected BigDecimal formatPrice(BigDecimal price)
    {
        
       return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    @RequestMapping("spareCategoryLookup")
    public String spareCategoryLookup(String txtId,String txtName,String dialogName, Long id, ModelMap model) {
        
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
        return "spare/spare/spareCategory_lookup";
    }
    
    @RequestMapping(value ="stockList", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<Spare>  stockList(Pagination<Spare> pager,@ModelAttribute Spare spare,
            ModelMap model) {
		return spareService.findStockByCondition(pager, spare);
		
	}
}
