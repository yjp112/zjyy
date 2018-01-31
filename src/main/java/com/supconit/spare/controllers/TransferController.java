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
import com.supconit.spare.entities.Stock;
import com.supconit.spare.entities.Transfer;
import com.supconit.spare.entities.TransferDetail;
import com.supconit.spare.services.SpareProcessService;
import com.supconit.spare.services.StockService;
import com.supconit.spare.services.TransferService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：TransferController.java
 * @创建日期：2013年7月11日
 * @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：备件调拨
 */
@Controller
@RequestMapping("spare/transfer")
public class TransferController extends SpareBaseControllerSupport {

    @Autowired
    private TransferService transferService;
    @Resource
    private StockService stockService;
    @Resource
    private SpareProcessService spareProcessService;
    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go( @ModelAttribute Transfer transfer,ModelMap model) {
        model.put("inWarehouseOptions",
                        warehouseSelectOptions(transfer.getInWarehouseId()));
        return "spare/transfer/transfer_list";
    }

    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<Transfer>  list(Pagination<Transfer> pager,@ModelAttribute Transfer transfer,
            ModelMap model) {
		return transferService.findByCondition(pager, transfer);
		
	}
    /*
     * save transfer Transfer object instance
     */
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ScoMessage save(Transfer transfer) {

        if (transfer.getId() == null) {
            copyCreateInfo(transfer);
            transferService.insert(transfer);
        } else {
            copyUpdateInfo(transfer);
            transferService.update(transfer);
        }
        return ScoMessage.success( "spare/transfer/go","操作成功。");
    }
    /*
     * MasterTable.Name,0)%>
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ScoMessage delete(@RequestParam("ids") Long[] ids) {
        transferService.deleteByIds(ids);
        return ScoMessage.success( ScoMessage.NEXT_REFRESH,"删除成功。");
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id, ModelMap model) {
        Transfer transfer = null;
        if (null != id) {
            transfer = transferService.findById(id);
            if (null == transfer) {
                throw new IllegalArgumentException("Object does not exist");
            }

        } else {
            transfer = new Transfer();
            transfer.setTransferCode(SerialNumberGenerator
                            .getSerialNumbersByDB(TableAndColumn.TRANSFER));
            transfer.setTransferPersonId(getCurrentPersonId());
            transfer.setTransferPersonName(getCurrentPersonName());
            transfer.setTransferTime(new Date());
        }
        model.put(PROCESS_INSTANCE_ID, transfer.getProcessInstanceId());//
        model.put("transfer", transfer);
        model.put("inWarehouseOptions",
                        warehouseSelectOptions(transfer.getInWarehouseId()));
        model.put("outWarehouseOptions",
                        warehouseSelectOptions(transfer.getOutWarehouseId()));
        model.put("viewOnly", viewOnly);
        return "spare/transfer/transfer_edit";
    }
    
    @RequestMapping("loadSpare")
    @ResponseBody
    public Map<String, List<TransferDetail>> loadSpare(@RequestParam("warehouseId") Long warehouseId,@RequestParam("spareIds[]") Long[] spareIds, ModelMap map) {
        Map<String, List<TransferDetail>> resultMap=new HashMap<String, List<TransferDetail>>();
        List<TransferDetail> notFoundDetails=new ArrayList<TransferDetail>();
        List<TransferDetail> foundDetails=new ArrayList<TransferDetail>();
        resultMap.put("notMatched", notFoundDetails);
        resultMap.put("matched", foundDetails);
        List<Long> spareIdList=Arrays.asList(spareIds);
        List<Stock> stocks = stockService.selectBySpareIds(warehouseId, spareIdList);
        if (spareIdList.size() != stocks.size()) {
            Transformer transformer = new BeanToPropertyValueTransformer("spareId");
            @SuppressWarnings("unchecked")
            Collection<Long> notFoundList = CollectionUtils.subtract(spareIdList,
                            CollectionUtils.collect(stocks, transformer));
            for (Long id : notFoundList) {
                TransferDetail detail = new TransferDetail();
                detail.setSpareId(id);
                notFoundDetails.add(detail);
            }
        }else{
            for (Stock stock : stocks) {
                TransferDetail detail = new TransferDetail();
                detail.setSpareId(stock.getSpareId());
                detail.setSpareCode(stock.getSpareCode());
                detail.setSpareName(stock.getSpareName());
                detail.setModel(stock.getModel());
                detail.setSpec(stock.getSpec());
                detail.setQuantity(stock.getAvailableQty());
                foundDetails.add(detail);
            }  
        }
        return resultMap;
    }
    @RequestMapping("audit")
    public String toAudit(@RequestParam(required = true) Long id, ModelMap model) {
        Transfer transfer = null;
        if (null != id) {
            transfer = transferService.findById(id);
            if (null == transfer) {
                throw new IllegalArgumentException("Object does not exist");
            }

        } else {
            transfer = new Transfer();
            transfer.setTransferCode(SerialNumberGenerator
                            .getSerialNumbersByDB(TableAndColumn.TRANSFER));
            transfer.setTransferPersonId(getCurrentPersonId());
            transfer.setTransferPersonName(getCurrentPersonName());
            transfer.setTransferTime(new Date());
        }
        model.put("transfer", transfer);
        model.put("inWarehouseOptions",
                        warehouseSelectOptions(transfer.getInWarehouseId()));
        model.put("outWarehouseOptions",
                        warehouseSelectOptions(transfer.getOutWarehouseId()));

        return "spare/transfer/transfer_audit";        
    }
    @ResponseBody
    @RequestMapping(value = "processDone", method = RequestMethod.POST)
    public ScoMessage processDone(@RequestParam("_bpm_ts_id")String transitionId,Transfer transfer) {
        try {
			if (transfer.getId() == null) {
			    copyCreateInfo(transfer);
			} else {
			    copyUpdateInfo(transfer);
			}
			spareProcessService.transferProcessDone(transitionId,transfer);
		} catch (Exception e) {
			//e.printStackTrace();
			return ScoMessage.error(e.getMessage());
		}
        return ScoMessage.success("spare/transfer/go", "操作成功。");
    }
    @ResponseBody
    @RequestMapping(value = "processSave", method = RequestMethod.POST)
    public ScoMessage processSave(Transfer transfer) {
        if (transfer.getId() == null) {
            copyCreateInfo(transfer);
        } else {
            copyUpdateInfo(transfer);
        }
        spareProcessService.transferProcessSave(transfer);
        return ScoMessage.success("spare/transfer/go", "操作成功。");
    }
    @ResponseBody
    @RequestMapping(value = "deleteProcessInstance", method = RequestMethod.POST)
    public ScoMessage deleteProcessInstance(@RequestParam("id") Long id) {

        spareProcessService.transferProcessInstanceDelete(id);
        return ScoMessage.success( "spare/transfer/go","删除成功。");
    }
}
