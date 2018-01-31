package com.supconit.spare.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.spare.entities.Inventory;
import com.supconit.spare.entities.InventoryDetail;
import com.supconit.spare.entities.Stock;
import com.supconit.spare.services.InventoryService;
import com.supconit.spare.services.SpareProcessService;
import com.supconit.spare.services.StockService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：InventoryController.java
 * @创建日期：2013年7月11日
 * @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：备件盘点
 */
@Controller
@RequestMapping("spare/inventory")
public class InventoryController extends SpareBaseControllerSupport {

    @Autowired
    private InventoryService inventoryService;
    @Resource
    private StockService stockService;
    @Resource
    private SpareProcessService spareProcessService;

    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(@ModelAttribute Inventory inventory, ModelMap model) {
        model.put("warehouseOptions",
                        warehouseSelectOptions(inventory.getWarehouseId()));
        return "spare/inventory/inventory_list";
    }

   
    /**
	 * 列表查询
	 */
    @SuppressWarnings("deprecation")
    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<Inventory>  list(Pagination<Inventory> pager,@ModelAttribute Inventory inventory,
            ModelMap model) {
		return inventoryService.findByCondition(pager, inventory);
	}
    /*
     * save inventory Inventory object instance
     */
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ScoMessage save(Inventory inventory) {

        if (inventory.getId() == null) {
            copyCreateInfo(inventory);
            inventoryService.insert(inventory);
        } else {
            copyUpdateInfo(inventory);
            inventoryService.update(inventory);
        }
        return ScoMessage.success( "spare/inventory/go","操作成功。");
    }
    /*
     * MasterTable.Name,0)%>
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ScoMessage delete(@RequestParam("ids") Long[] ids) {
        inventoryService.deleteByIds(ids);
        return ScoMessage.success( ScoMessage.NEXT_REFRESH,"删除成功。");
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id, ModelMap model) {
        Inventory inventory = null;
        if (null != id) {
            inventory = inventoryService.findById(id);
            if (null == inventory) {
                throw new IllegalArgumentException("Object does not exist");
            }

        } else {
            inventory = new Inventory();
            inventory.setInventoryCode(SerialNumberGenerator
                            .getSerialNumbersByDB(TableAndColumn.INVENTORY));
            inventory.setInventoryPersonId(getCurrentPersonId());
            inventory.setInventoryPersonName(getCurrentPersonName());
            inventory.setInventoryTime(new Date());
            List<Stock> stocks=stockService.selectByWarehouseId(getDefaultWarehouseId());
            List<InventoryDetail> details=new ArrayList<InventoryDetail>();
            for (Stock stock : stocks) {
                InventoryDetail detail=new InventoryDetail();
                detail.setSpareId(stock.getSpareId());
                detail.setSpareCode(stock.getSpareCode());
                detail.setSpareName(stock.getSpareName());
                detail.setAccountQty(stock.getQty());
                details.add(detail);
            }
            inventory.setInventoryDetailList(details);
        }
        model.put(PROCESS_INSTANCE_ID,inventory.getProcessInstanceId());//
        model.put("inventory", inventory);
        model.put("warehouseOptions",
                        warehouseSelectOptions(inventory.getWarehouseId()));
        model.put("viewOnly", viewOnly);
        return "spare/inventory/inventory_edit";
    }
    @RequestMapping("loadSpare/{warehouseId}")
    @ResponseBody
    public List<InventoryDetail> loadSpare(@PathVariable Long warehouseId, ModelMap map) {
        List<InventoryDetail> details=new ArrayList<InventoryDetail>();
        List<Stock> stocks=stockService.selectByWarehouseId(warehouseId);
        for (Stock stock : stocks) {
            InventoryDetail detail=new InventoryDetail();
            detail.setSpareId(stock.getSpareId());
            detail.setSpareCode(stock.getSpareCode());
            detail.setSpareName(stock.getSpareName());
            detail.setAccountQty(stock.getQty());
            details.add(detail);
        }
        return details;
    }
    @RequestMapping(value = "audit", method = RequestMethod.GET)
    public String toAudit(@RequestParam(required = false) Long id, ModelMap model) {
        Inventory inventory = null;
        if (null != id) {
            inventory = inventoryService.findById(id);
            if (null == inventory) {
                throw new IllegalArgumentException("Object does not exist");
            }

        } else {
            inventory = new Inventory();
            inventory.setInventoryCode(SerialNumberGenerator
                            .getSerialNumbersByDB(TableAndColumn.INVENTORY));
            inventory.setInventoryPersonId(getCurrentPersonId());
            inventory.setInventoryPersonName(getCurrentPersonName());
            inventory.setInventoryTime(new Date());
            List<Stock> stocks=stockService.selectByWarehouseId(getDefaultWarehouseId());
            List<InventoryDetail> details=new ArrayList<InventoryDetail>();
            for (Stock stock : stocks) {
                InventoryDetail detail=new InventoryDetail();
                detail.setSpareId(stock.getSpareId());
                detail.setSpareCode(stock.getSpareCode());
                detail.setSpareName(stock.getSpareName());
                detail.setAccountQty(stock.getQty());
                details.add(detail);
            }
            inventory.setInventoryDetailList(details);
        }
        model.put("inventory", inventory);
        model.put("warehouseOptions",
                        warehouseSelectOptions(inventory.getWarehouseId()));

        return "spare/inventory/inventory_audit";
    }
    @SuppressWarnings("deprecation")
	@ResponseBody
    @RequestMapping(value = "processDone", method = RequestMethod.POST)
    public ScoMessage processDone(@RequestParam("_bpm_ts_id")String transitionId,Inventory inventory) {
        try {
			if (inventory.getId() == null) {
			    copyCreateInfo(inventory);
			} else {
			    copyUpdateInfo(inventory);
			}
			spareProcessService.invertoryProcessDone(transitionId,inventory);
		} catch (Exception e) {
			//e.printStackTrace();
			return ScoMessage.error(e.getMessage());
		}
        return ScoMessage.success( "spare/inventory/go","操作成功。");
    }
    @ResponseBody
    @RequestMapping(value = "processSave", method = RequestMethod.POST)
    public ScoMessage processDone(Inventory inventory) {
        if (inventory.getId() == null) {
            copyCreateInfo(inventory);
        } else {
            copyUpdateInfo(inventory);
        }
        spareProcessService.invertoryProcessSave(inventory);
        return ScoMessage.success("spare/inventory/go",  "操作成功。");
    }

    @ResponseBody
    @RequestMapping(value = "deleteProcessInstance", method = RequestMethod.POST)
    public ScoMessage deleteProcessInstance(@RequestParam("id") Long id) {

        spareProcessService.invertoryProcessInstanceDelete(id);
        return ScoMessage.success("spare/inventory/go","删除成功。");
    }
}
