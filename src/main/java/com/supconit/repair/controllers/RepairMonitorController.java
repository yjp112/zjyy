package com.supconit.repair.controllers;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.excel.ExcelExport;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.repair.entities.Repair;
import com.supconit.repair.services.RepairService;

/**
 * 维修监控中心控制类
 * @author yuhuan
 * @日期 2015/08
 */
@Controller
@RequestMapping("repair/monitor")
public class RepairMonitorController extends BaseControllerSupport{
	@Autowired
	private RepairService repairService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	
	private static List<Repair> list = new ArrayList<Repair>();
	
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		List<DeviceCategory> deviceCategoryList = deviceCategoryService.findAll();
        model.addAttribute("deviceCategoryList", deviceCategoryList);//设备类别树
        List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);//地理区域树
        model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE)); 
        model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY)); 
		return "repair/monitor/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<Repair> dolist(Repair condition,
			@RequestParam(required = false) String treeId1,
			@RequestParam(required = false) String treeId2,
			Pagination<Repair> pager,
            ModelMap model) {
		if("0".equals(treeId1)) treeId1 = null;
		if("0".equals(treeId2)) treeId2 = null;
		//类别
		if(StringUtils.isNotEmpty(treeId1)) condition.setCategoryId(Long.parseLong(treeId1));
		//区域
		if(StringUtils.isNotEmpty(treeId2)) condition.setAreaId(Long.parseLong(treeId2));
		Pageable<Repair> pager2 = repairService.findRepairMonitorPages(pager, condition);
		list.clear();
	    list.addAll(pager2);
		return pager2;
	}
	
	@RequestMapping("excelExport")
    public void excelExport(HttpServletResponse response) {
        String title = "监控中心维修数据记录";
        OutputStream out = null;
        try {
            response.setHeader("Content-Disposition", "attachment; filename="+ new String((title+".xls").getBytes("GB2312"), "iso8859-1"));
            response.setContentType("application/msexcel;charset=UTF-8");
            out = response.getOutputStream();
            ExcelExport<Repair> ex = new ExcelExport<Repair>();
            ex.exportExcel(title,list,out);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(null != out) out.close();
            }catch(Exception e){

            }
        }
    }
}
