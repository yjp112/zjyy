
package com.supconit.spare.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.spare.entities.PriceChange;
import com.supconit.spare.entities.SpareCategory;
import com.supconit.spare.services.PriceChangeService;
import com.supconit.spare.services.SpareCategoryService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：PriceChangeController.java
 * @创建日期：2013年7月11日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：调价管理
 */
@Controller
@RequestMapping("spare/priceChange")
public class PriceChangeController extends SpareBaseControllerSupport {

    
	@Autowired
	private PriceChangeService priceChangeService;
	@Autowired
	private SpareCategoryService spareCategoryService;
    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(@ModelAttribute PriceChange priceChange,
            ModelMap model) {
        //loadSpareCategoryTree(model);
    	List<SpareCategory> list = spareCategoryService.selectCategories();
        model.put("treeDatas", list);
        return "spare/priceChange/priceChange_list";
    }
    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<PriceChange>  list(Pagination<PriceChange> pager,@ModelAttribute PriceChange priceChange,
            ModelMap model) {
		return priceChangeService.findByCondition(pager, priceChange);
	}
    /*
    save  priceChange
    PriceChange object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(PriceChange priceChange) {
				
         if(priceChange.getId()==null){
            copyCreateInfo(priceChange);
            priceChangeService.insert(priceChange);	
        }
        else{
            copyUpdateInfo(priceChange);    
            priceChangeService.update(priceChange);
        }
            
         return ScoMessage.success("spare/priceChange/go","操作成功。");
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		
		priceChangeService.deleteByIds(ids);
		
		return ScoMessage.success("spare/priceChange/go", "删除成功。");
	}   
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
        PriceChange priceChange=null;
        if (null != id) {
            priceChange = priceChangeService.getById(id);
            //保存价格为2为小数
            if(null != priceChange.getOldPrice())
            {
                priceChange.setOldPrice(formatPrice(priceChange.getOldPrice()));
            }
            if(null != priceChange.getNewPrice())
            {
                priceChange.setNewPrice(formatPrice(priceChange.getNewPrice()));
            }
            
            
            if (null == priceChange) {
                throw new IllegalArgumentException("Object does not exist");
            }
            
        }else {
            priceChange=new PriceChange();
            String ss = SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.PRICE_CHANGE);
            
            priceChange.setBillNo(ss);
            priceChange.setChangePersonId(getCurrentPersonId());
            priceChange.setChangePersonName(getCurrentPersonName());
            priceChange.setChangeDate(new Date());
        }		
        model.put("priceChange", priceChange);			
        model.put("viewOnly", viewOnly);
        return "spare/priceChange/priceChange_edit";
    }
    
    protected BigDecimal formatPrice(BigDecimal price)
    {
        
       return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
}
