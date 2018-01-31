package com.supconit.spare.controllers;

import java.util.Date;

import javax.annotation.Resource;

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
import com.supconit.spare.entities.StockIn;
import com.supconit.spare.services.SpareProcessService;
import com.supconit.spare.services.StockInService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：StockInController.java
 * @创建日期：2013年7月11日
 * @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：备件入库
 */
@Controller
@RequestMapping("spare/stockIn")
public class StockInController extends SpareBaseControllerSupport {

    @Autowired
    private StockInService stockInService;
    @Resource
    private SpareProcessService spareProcessService;
    
    /*
     * get "stockIn" list
     */
    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(ModelMap model) {
        return "spare/stockIn/stockIn_list";
    }

    /*
     * get "stockIn" list
     */
    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<StockIn>  list(Pagination<StockIn> pager,@ModelAttribute StockIn stockIn,
            ModelMap model) {
    	
		return stockInService.findByCondition(pager, stockIn);
		
	}
    /*
     * save stockIn StockIn object instance
     */
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ScoMessage save(StockIn stockIn) {

    	try {
			if (stockIn.getId() == null) {
			    copyCreateInfo(stockIn);
			    stockInService.insert(stockIn);
			} else {
			    copyUpdateInfo(stockIn);
			    stockInService.update(stockIn);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return ScoMessage.error(e.getMessage());
		}
        return ScoMessage.success("spare/stockIn/go", "操作成功。");
   
     }

    /*
     * MasterTable.Name,0)%>
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ScoMessage delete(@RequestParam("ids") Long[] ids) {

        stockInService.deleteByIds(ids);
        return ScoMessage.success(ScoMessage.NEXT_REFRESH, "删除成功。");
    }


    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id, ModelMap model) {
    	StockIn stockIn = null;
        if (null != id) {
            stockIn = stockInService.findById(id);
            if (null == stockIn) {
                throw new IllegalArgumentException("Object does not exist");
            }

        } else {
            stockIn = new StockIn();
            stockIn.setStockInCode(SerialNumberGenerator
                            .getSerialNumbersByDB(TableAndColumn.STOCK_IN));
            stockIn.setInTime(new Date());
            stockIn.setCheckerId(getCurrentPersonId());
            stockIn.setCheckerName(getCurrentPersonName());
        }
        model.put(PROCESS_INSTANCE_ID, stockIn.getProcessInstanceId());
        model.put("stockIn", stockIn);
        model.put("inWarehouseOptions",
                        warehouseSelectOptions(stockIn.getWarehouseId()));
        model.put("inTypeSelectOptions",
                        htmlSelectOptions(DictTypeEnum.STOCK_IN_SOURCE,
                                        stockIn.getInType()));
        model.put("viewOnly", viewOnly);
        return "spare/stockIn/stockIn_edit";
  
    }

    @RequestMapping("audit")
    public String toAudit(@RequestParam(required = true) Long id, ModelMap model) {
        StockIn stockIn = null;
        if (null != id) {
            stockIn = stockInService.findById(id);
            if (null == stockIn) {
                throw new IllegalArgumentException("Object does not exist");
            }

        } else {
            stockIn = new StockIn();
            stockIn.setStockInCode(SerialNumberGenerator
                            .getSerialNumbersByDB(TableAndColumn.STOCK_IN));
            stockIn.setInTime(new Date());
            stockIn.setCheckerId(getCurrentPersonId());
            stockIn.setCheckerName(getCurrentPersonName());
        }
        model.put("stockIn", stockIn);
        model.put("inWarehouseOptions",
                        warehouseSelectOptions(stockIn.getWarehouseId()));
        model.put("inTypeSelectOptions",
                        htmlSelectOptions(DictTypeEnum.STOCK_IN_SOURCE,
                                        stockIn.getInType()));
        return "spare/stockIn/stockIn_audit";
    }
    @ResponseBody
    @RequestMapping(value = "processDone", method = RequestMethod.POST)
    public ScoMessage processDone(@RequestParam("_bpm_ts_id")String transitionId,StockIn stockIn) {
        try {
			if (stockIn.getId() == null) {
			    copyCreateInfo(stockIn);
			} else {
			    copyUpdateInfo(stockIn);
			}
			spareProcessService.stockInProcessDone(transitionId,stockIn);
		} 
        catch (Exception e) {
			//e.printStackTrace();
			return ScoMessage.error(e.getMessage());
		}
        return ScoMessage.success("spare/stockIn/go", "操作成功。");
    }
    @ResponseBody
    @RequestMapping(value = "processSave", method = RequestMethod.POST)
    public ScoMessage processSave(StockIn stockIn) {
        if (stockIn.getId() == null) {
            copyCreateInfo(stockIn);
        } else {
            copyUpdateInfo(stockIn);
        }
        spareProcessService.stockInProcessSave(stockIn);
        return ScoMessage.success("spare/stockIn/go","操作成功。");
    }
    @ResponseBody
    @RequestMapping(value = "deleteProcessInstance", method = RequestMethod.POST)
    public ScoMessage deleteProcessInstance(@RequestParam("id") Long id) {

        spareProcessService.stockInProcessInstanceDelete(id);
        return ScoMessage.success("spare/stockIn/go","删除成功。");
    }
}
