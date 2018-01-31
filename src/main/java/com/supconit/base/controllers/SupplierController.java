
package com.supconit.base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Supplier;
import com.supconit.base.services.SupplierService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("base/supplier")
public class SupplierController extends BaseControllerSupport {

    
	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping("go")
	public String go(ModelMap model) {
		return "base/supplier/supplier_list";
	}	
    /*
    get "supplier" list
    */
	@ResponseBody
    @RequestMapping("list")
	public Pageable<Supplier> list(Pagination<Supplier> pager,Supplier supplier,
			ModelMap model) {
		
//		model.put("supplier", supplier);
//		model.put("pager", pager);
		
		return supplierService.findByCondition(pager, supplier);
	}


    /*
    save  supplier
    Supplier object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(Supplier supplier) {
				
         if(supplier.getId()==null){
            copyCreateInfo(supplier);
            supplierService.insert(supplier);	
        }
        else{
            copyUpdateInfo(supplier);    
            supplierService.update(supplier);
        }
            
		
         return ScoMessage.success("base/supplier/go","操作成功。");
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		
		supplierService.deleteByIds(ids);
		
		return ScoMessage.success("base/supplier/go", "删除成功。");
	}   
    
    /**
	 * Edit Supplier
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			Supplier supplier = supplierService.getById(id);
			if (null == supplier) {
				throw new IllegalArgumentException("Object does not exist");
			}
			
			model.put("supplier", supplier);			
		}		
		model.put("viewOnly", viewOnly);
		return "base/supplier/supplier_edit";
	}
    
    @RequestMapping("lookup/{txtId}/{txtName}")
    public String lookup(@PathVariable String txtId,@PathVariable String txtName,String flag, ModelMap map) {
        map.put("txtId",txtId);
        map.put("txtName",txtName);
        map.put("flag", flag);
        return "base/supplier/supplier_lookup";
    }    
}
