package com.supconit.spare.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.domains.Organization;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.spare.entities.Stock;
import com.supconit.spare.entities.StockOut;
import com.supconit.spare.entities.StockOutDetail;
import com.supconit.spare.services.SpareProcessService;
import com.supconit.spare.services.StockOutService;
import com.supconit.spare.services.StockService;
import com.supconit.spare.services.WarehouseService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：StockOutController.java
 * @创建日期：2013年7月11日
 * @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：备件出库
 */
@Controller
@RequestMapping("spare/stockOut")
public class StockOutController extends SpareBaseControllerSupport {

    @Autowired
    private StockOutService stockOutService;
    @Resource
    private StockService stockService;
    @Resource
    private WarehouseService warehouseService;
    @Resource
    private SpareProcessService spareProcessService;
  
    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(ModelMap model) {
        model.put("billStatusOptions", billStatusSelectOptions(""));
        return "spare/stockOut/stockOut_list";
    }

    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<StockOut>  list(Pagination<StockOut> pager,@ModelAttribute StockOut stockOut,
            ModelMap model) {
    	
		return stockOutService.findByCondition(pager, stockOut);
		
	}
    /*
     * save stockOut StockOut object instance
     */
    @SuppressWarnings("deprecation")
	@ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ScoMessage save(StockOut stockOut) {

        if (stockOut.getId() == null) {
            copyCreateInfo(stockOut);
            stockOutService.insert(stockOut);
        } else {
            copyUpdateInfo(stockOut);
            stockOutService.update(stockOut);
        }
        return ScoMessage.success("spare/stockOut/go","操作成功。");
        
     }

    /*
     * MasterTable.Name,0)%>
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ScoMessage delete(@RequestParam("ids") Long[] ids) {

        stockOutService.deleteByIds(ids);
        return ScoMessage.success( ScoMessage.NEXT_REFRESH,"删除成功。");
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id, ModelMap model) {
        StockOut stockOut = null;
        if (null != id) {
            stockOut = stockOutService.findById(id);
            if (null == stockOut) {
                throw new IllegalArgumentException("Object does not exist");
            }
        } else {
            stockOut = new StockOut();
            stockOut.setStockOutCode(SerialNumberGenerator
                            .getSerialNumbersByDB(TableAndColumn.STOCK_OUT));
            stockOut.setOutPersonId(getCurrentPersonId());
            stockOut.setOutPersonName(getCurrentPersonName());
            stockOut.setManagerName(warehouseService.getById(
                            getDefaultWarehouseId()).getManagerName());
            stockOut.setOutTime(new Date());
        }
        model.put(PROCESS_INSTANCE_ID, stockOut.getProcessInstanceId());//
        model.put("stockOut", stockOut);
        model.put("warehouseOptions",
                        warehouseSelectOptions(stockOut.getWarehouseId()));
        model.put("outTypeSelectOptions",
                        htmlSelectOptions(DictTypeEnum.STOCK_OUT_USETYPE,
                                        stockOut.getOutType()));
        model.put("viewOnly", viewOnly);
        return "spare/stockOut/stockOut_edit";
    }

    @RequestMapping("loadSpare")
    @ResponseBody
    public Map<String, Object> loadSpare(
                    @RequestParam("warehouseId") Long warehouseId,
                    @RequestParam(required = false, value = "spareIds[]") Long[] spareIds,
                    ModelMap map) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<StockOutDetail> notFoundDetails = new ArrayList<StockOutDetail>();
        List<StockOutDetail> foundDetails = new ArrayList<StockOutDetail>();
        resultMap.put("notMatched", notFoundDetails);
        resultMap.put("matched", foundDetails);
        resultMap.put("managerName", warehouseService.getById(warehouseId)
                        .getManagerName());
        if (spareIds == null || spareIds.length <= 0) {
            return resultMap;
        }
        List<Long> spareIdList = Arrays.asList(spareIds);
        List<Stock> stocks = stockService.selectBySpareIds(warehouseId,
                        spareIdList);
        if (spareIdList.size() != stocks.size()) {
            Transformer transformer = new BeanToPropertyValueTransformer(
                            "spareId");
            @SuppressWarnings("unchecked")
            Collection<Long> notFoundList = CollectionUtils.subtract(
                            spareIdList,
                            CollectionUtils.collect(stocks, transformer));
            for (Long id : notFoundList) {
                StockOutDetail detail = new StockOutDetail();
                detail.setSpareId(id);
                notFoundDetails.add(detail);
            }
        } else {
            for (Stock stock : stocks) {
                StockOutDetail detail = new StockOutDetail();
                detail.setSpareId(stock.getSpareId());
                detail.setSpareCode(stock.getSpareCode());
                detail.setSpareName(stock.getSpareName());
                detail.setModel(stock.getModel());
                detail.setSpec(stock.getSpec());
                detail.setAvailableQty(stock.getAvailableQty());
                foundDetails.add(detail);
            }
        }
        return resultMap;
    }

    @RequestMapping("audit")
    public String toAudit(@RequestParam(required = true) Long id, ModelMap model) {
        StockOut stockOut = null;
        if (null != id) {
            stockOut = stockOutService.findById(id);
            if (null == stockOut) {
                throw new IllegalArgumentException("Object does not exist");
            }
        } else {
            stockOut = new StockOut();
            stockOut.setStockOutCode(SerialNumberGenerator
                            .getSerialNumbersByDB(TableAndColumn.STOCK_OUT));
            stockOut.setOutPersonId(getCurrentPersonId());
            stockOut.setOutPersonName(getCurrentPersonName());
            stockOut.setManagerName(warehouseService.getById(
                            getDefaultWarehouseId()).getManagerName());
            stockOut.setOutTime(new Date());
        }
        model.put("stockOut", stockOut);
        model.put("warehouseOptions",
                        warehouseSelectOptions(stockOut.getWarehouseId()));
        model.put("outTypeSelectOptions",
                        htmlSelectOptions(DictTypeEnum.STOCK_OUT_USETYPE,
                                        stockOut.getOutType()));
        return "spare/stockOut/stockOut_audit";

    }
    @ResponseBody
    @RequestMapping(value = "processDone", method = RequestMethod.POST)
    public ScoMessage processDone(@RequestParam("_bpm_ts_id")String transitionId,StockOut stockOut) {
        try {
			if (stockOut.getId() == null) {
			    copyCreateInfo(stockOut);
			} else {
			    copyUpdateInfo(stockOut);
			}
			spareProcessService.stockOutProcessDone(transitionId,stockOut);
		} catch (Exception e) {
			//e.printStackTrace();
			return ScoMessage.error(e.getMessage());
		}
        return ScoMessage.success("spare/stockOut/go","操作成功。");
    }    
    @ResponseBody
    @RequestMapping(value = "processSave", method = RequestMethod.POST)
    public ScoMessage processSave(StockOut stockOut) {
        if (stockOut.getId() == null) {
            copyCreateInfo(stockOut);
        } else {
            copyUpdateInfo(stockOut);
        }
        spareProcessService.stockOutProcessSave(stockOut);
        return ScoMessage.success("spare/stockOut/go", "操作成功。");
    }    
    @ResponseBody
    @RequestMapping(value = "deleteProcessInstance", method = RequestMethod.POST)
    public ScoMessage deleteProcessInstance(@RequestParam("id") Long id) {

        spareProcessService.stockOutProcessInstanceDelete(id);
        return ScoMessage.success( "spare/stockOut/go","删除成功。");
    }
}
