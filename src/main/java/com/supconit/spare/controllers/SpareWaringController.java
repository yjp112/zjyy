package com.supconit.spare.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
 * @文 件 名：SpareController.java
 * @创建日期：2013年7月11日
 * @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：备件超存短缺预警、仓库预警、备件保质期预警
 */
@Controller
@RequestMapping("spare/waring")
public class SpareWaringController extends SpareBaseControllerSupport {

    @Resource
    private StockService stockService;

    @Resource
    private SpareCategoryService spareCategoryService;

    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(ModelMap model) {
        //loadSpareCategoryTree(model);
    	List<SpareCategory> list = spareCategoryService.selectCategories();
        model.put("treeDatas", list);
        return "spare/warning/warning_list";
    }
    /*
     * get "spare" list
     */
    @RequestMapping("stockWarning")
    @ResponseBody
	public Pageable<Stock>  stockWarning(Pagination<Stock> pager, Long spareCategoryId,
            ModelMap model) {
		return stockService.selectStockWaring(pager, spareCategoryId);
	}
    
    @RequestMapping("warehouseWaring")
    @ResponseBody
	public Pageable<Stock>  warehouseWaring(Pagination<Stock> pager, Stock stock,
            ModelMap model) {
		return stockService.selectWarehouseWaring(pager, stock);
	}
}
