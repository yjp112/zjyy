package com.supconit.spare.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.spare.entities.SpareCategory;
import com.supconit.spare.entities.Stock;
import com.supconit.spare.services.SpareCategoryService;
import com.supconit.spare.services.StockService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：StockController.java
 * @创建日期：2013年7月11日
 * @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：库存查询
 */
@Controller
@RequestMapping("spare/stock")
public class StockController extends SpareBaseControllerSupport {

    @Autowired
    private StockService stockService;
    @Resource
    private SpareCategoryService spareCategoryService;

    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(ModelMap model) {
        //loadSpareCategoryTree(model);
    	List<SpareCategory> list = spareCategoryService.selectCategories();
        model.put("treeDatas", list);
        model.put("warehouseOptions",warehouseSelectOptions(""));
        return "spare/stock/stock_list";
    }

    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<Stock>  list(Pagination<Stock> pager,@ModelAttribute Stock stock,
            ModelMap model) {
		return stockService.findByCondition(pager, stock);
		
	}
    @RequestMapping("lookup")
    public String lookup(@ModelAttribute Stock stock,String txtName,ModelMap model) {
        loadSpareCategoryTree(model);
        model.put("warehouseOptions",warehouseSelectOptions(stock.getWarehouseId()));
        model.put("txtName", txtName);
        return "spare/stock/stock_lookup";
    }    
}
