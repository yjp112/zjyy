package com.supconit.spare.controllers;

import java.util.List;

import javax.annotation.Resource;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.spare.entities.StockBack;
import com.supconit.spare.entities.Warehouse;
import com.supconit.spare.services.SpareProcessService;
import com.supconit.spare.services.StockBackService;

/**
 * 备件归还控制类
 * @author yuhuan
 * @日期 2016/01
 */
@Controller
@RequestMapping("spare/stockBack")
public class StockBackController extends SpareBaseControllerSupport {
	@Autowired
	private StockBackService stockBackService;
	@Resource
    private SpareProcessService spareProcessService;
	
    @RequestMapping(value="go",method=RequestMethod.GET)
    public String go(ModelMap model) {
        return "spare/stockBack/stockBack_list";
    }

    @RequestMapping(value ="list", method = RequestMethod.POST)
    @ResponseBody
	public Pageable<StockBack>  list(Pagination<StockBack> pager,@ModelAttribute StockBack stockBack,
            ModelMap model) {
		return stockBackService.findByCondition(pager, stockBack);
	}
    
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id, ModelMap model) {
    	StockBack stockBack = null;
    	if (null != id) {
    		stockBack = stockBackService.getById(id);
            if (null == stockBack) {
                throw new IllegalArgumentException("Object does not exist");
            }
        } else {
        	stockBack = new StockBack();
        	stockBack.setStockBackCode(SerialNumberGenerator
                    .getSerialNumbersByDB(TableAndColumn.STOCK_BACK));
        }
    	model.put(PROCESS_INSTANCE_ID, stockBack.getProcessInstanceId());
    	model.put("stockBack", stockBack);
    	List<Warehouse> warehouseList = warehouseService.finaAll();
    	List<EnumDetail> backTypeList = DictUtils.getDictList(DictTypeEnum.STOCK_BACK_SOURCE);
    	model.put("warehouseList",warehouseList);
        model.put("backTypeList",backTypeList);
        model.put("viewOnly", viewOnly);
    	return "spare/stockBack/stockBack_edit";
    }
    
    @RequestMapping(value = "audit", method = RequestMethod.GET)
    public String audit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id, 
    		@RequestParam(required = false)Boolean hisView,ModelMap model) {
    	StockBack stockBack = null;
    	if (null != id) {
    		stockBack = stockBackService.getById(id);
    		if (null == stockBack) {
    			throw new IllegalArgumentException("Object does not exist");
    		}
    		if(stockBack.getStatus()==0){//草稿状态
    			viewOnly = false;
    		}
    	}
    	model.put(PROCESS_INSTANCE_ID, stockBack.getProcessInstanceId());
    	model.put("stockBack", stockBack);
    	List<Warehouse> warehouseList = warehouseService.finaAll();
    	List<EnumDetail> backTypeList = DictUtils.getDictList(DictTypeEnum.STOCK_BACK_SOURCE);
    	model.put("warehouseList",warehouseList);
    	model.put("backTypeList",backTypeList);
    	model.put("viewOnly", true);
    	if(hisView==null? false:hisView){
    		model.put("todo", "hisList");
    	}else{
    		model.put("todo", "list");
    	}
    	return "spare/stockBack/stockBack_audit";
    }
    
    @ResponseBody
    @RequestMapping(value = "processDone", method = RequestMethod.POST)
    public ScoMessage processDone(@RequestParam("_bpm_ts_id")String transitionId,StockBack stockBack) {
    	String url = "";
        try {
			if (stockBack.getId() == null) {
			    copyCreateInfo(stockBack);
			    url = "spare/stockBack/go";
			} else {
			    copyUpdateInfo(stockBack);
			    url = "workspace/todo/list";
			}
			spareProcessService.stockBackProcessDone(transitionId,stockBack);
		} 
        catch (Exception e) {
			return ScoMessage.error(e.getMessage());
		}
        return ScoMessage.success(url, "操作成功。");
    }
    
    @RequestMapping("lookup")
    public String lookup(String txtId,String txtName,String flag,ModelMap model) {
        loadSpareCategoryTree(model);
        model.put("txtId", txtId);  
        model.put("txtName", txtName); 
        model.put("flag", flag);  
        return "spare/stockBack/spare_lookup";
    }     
}
