
package com.supconit.spare.controllers;

import java.util.ArrayList;
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
import com.supconit.spare.entities.Warehouse;
import com.supconit.spare.services.WarehouseService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * @文 件 名：WarehouseController.java
 * @创建日期：2013年7月11日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：仓库设置
 */
@Controller
@RequestMapping("spare/warehouse")
public class WarehouseController extends SpareBaseControllerSupport {

    
	@Autowired
	private WarehouseService warehouseService;
		
	
	@RequestMapping(value="go",method=RequestMethod.GET)
	public String go() {
		return "spare/warehouse/warehouse_list";
	}
    /*
    get "warehouse" list
    */
	 @RequestMapping(value ="list", method = RequestMethod.POST)
	    @ResponseBody
		public Pageable<Warehouse>  list(Pagination<Warehouse> pager,@ModelAttribute Warehouse warehouse,
	            ModelMap model) {
		 	model.put("warehouse", warehouse);
			return warehouseService.findByCondition(pager, warehouse);
			
		}

    /*
    save  warehouse
    Warehouse object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(Warehouse warehouse) {
		 String message = checkToSave(warehouse);//验证code
         String messageKc = isAllowSave(warehouse);//其他
         if(("").equals(message) && ("").equals(messageKc)){
        	 if(warehouse.getId()==null){
                 copyCreateInfo(warehouse);
                 warehouseService.insert(warehouse);	
             }
             else{
                 copyUpdateInfo(warehouse);    
                 warehouseService.update(warehouse);
             }
        	 return ScoMessage.success("操作成功。");
         }else{
        	 if(!("").equals(message))
        		 return ScoMessage.error(message);
        	 else
        		 return ScoMessage.error(messageKc);
         }
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		
		warehouseService.deleteByIds(ids);
		return ScoMessage.success("删除成功。");
	}   
    
    /**
	 * Edit Warehouse
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			Warehouse warehouse = warehouseService.getById(id);
			if (null == warehouse) {
				throw new IllegalArgumentException("Object does not exist");
			}
			if( null!=warehouse.getUpperQty()){
				model.put("upperQty", warehouse.getUpperQty());
			}
			if( null!= warehouse.getLowerQty()){
				model.put("lowerQty", warehouse.getLowerQty());
			}
			if( null!=warehouse.getSafeQty()){
				model.put("safeQty", warehouse.getSafeQty());
			}
			model.put("warehouse", warehouse);			
		}		
		model.put("viewOnly", viewOnly);
		return "spare/warehouse/warehouse_edit";
	}
    private String isAllowSave(Warehouse warehouse)
    {
    	Integer upper=warehouse.getUpperQty();
    	Integer lower=warehouse.getLowerQty();
    	Integer safe=warehouse.getSafeQty();
        if(upper!=null&&safe!=null&&upper.compareTo(safe)<0){
            return "安全库存不能大于最高库存。";
        }
        if(upper!=null&&lower!=null&&upper.compareTo(lower)<0){
            return "最低库存不能大于最高库存。";
        }
        if(safe!=null&&lower!=null&&safe.compareTo(lower)<0){
            return "最低库存不能大于安全库存。";
        }
		return "";
        
    }
    
    private String checkToSave(Warehouse entity) {
		List<Warehouse> list = new ArrayList<Warehouse>();
		list = warehouseService.findByCode(entity.getWarehouseCode(),entity.getWarehouseName());
		
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				return "编码[" + entity.getWarehouseCode()+ "]或仓库名["+ entity.getWarehouseName() +"]已经被占用。";
			} else {
				// list.size()==1
				if (entity.getId() != null) {
					// update
					Warehouse old = list.get(0);
					if (entity.getId().longValue() == old.getId().longValue()) {
						// ok
					} else {
						return "编码["+ entity.getWarehouseCode() + "]或仓库名["+ entity.getWarehouseName() +"]已经被占用。";
					}
				} else {
					// insert
					return "编码["+ entity.getWarehouseCode() + "]或仓库名["+ entity.getWarehouseName() +"]已经被占用。";
				}

			}
		}
		return "";
	}
    
}
