package com.supconit.spare.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.ui.ModelMap;

import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.LabelValueBeanConverter;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.LabelValueBean;
import com.supconit.spare.entities.SpareCategory;
import com.supconit.spare.entities.Warehouse;
import com.supconit.spare.services.SpareCategoryService;
import com.supconit.spare.services.WarehouseService;


public class SpareBaseControllerSupport extends BaseControllerSupport {
    @Resource
    private SpareCategoryService spareCategoryService;
    @Resource
    protected WarehouseService warehouseService;

    protected void loadSpareCategoryTree(ModelMap model) {
        List<SpareCategory> list = spareCategoryService.selectCategories();
        list.add(getRootSpareCategory());
        model.put("treeDatas", list);
    }
    
    protected void loadSpareCategoryTreeByFilter(ModelMap model, Long id) { 
        List<SpareCategory> list = spareCategoryService.selectCategoriesByFilter(id);
        list.add(getRootSpareCategory());
        model.put("treeDatas", list);
    }

    protected SpareCategory getRootSpareCategory() {
        SpareCategory rootCategory = new SpareCategory();
        rootCategory.setId(0L);
        rootCategory.setParentId(-1L);
        rootCategory.setCategoryName("备件类别");
        return rootCategory;
    }

    protected String warehouseSelectOptions(Object checkedValue) {
        LabelValueBeanConverter<Warehouse> converter = new LabelValueBeanConverter<Warehouse>() {
            @Override
            public LabelValueBean toLabelValueBean(Warehouse t) {
                return new LabelValueBean(t.getWarehouseName(),
                                String.valueOf(t.getId()));
            }
        };
        List<Warehouse> warehouseList = warehouseService.finaAll();
        return htmlSelectOptions(warehouseList, converter,
                        checkedValue == null ? "" : checkedValue.toString());
    }
    protected Long getDefaultWarehouseId() {
        List<Warehouse> warehouseList = warehouseService.finaAll();
        return (warehouseList==null||warehouseList.size()<=0)?null:warehouseList.get(0).getId();
    }
    protected String billStatusSelectOptions(Object checkedValue) {
        return htmlSelectOptions(DictTypeEnum.BILLSTATUS,
                        checkedValue == null ? "" : checkedValue.toString());
    }
}
