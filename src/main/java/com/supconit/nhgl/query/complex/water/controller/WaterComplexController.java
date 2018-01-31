package com.supconit.nhgl.query.complex.water.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngArea.service.NgAreaService;
import com.supconit.nhgl.query.complex.entities.Complex;
import com.supconit.nhgl.query.complex.service.ComplexService;
import com.supconit.nhgl.utils.GraphUtils;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.datetime.JDateTime;

 @Controller
@RequestMapping("nhgl/query/complex/water")
public class WaterComplexController extends BaseControllerSupport{

    
	@Autowired
	private ComplexService deviceService;
	@Autowired
	private NgAreaService ngAreaService;
	
    @RequestMapping("list")
	public String list(ModelMap model,Integer nhType) {
		List<NgArea> treeList = ngAreaService.findTree();
		//model.put("waterSubSystemInfoOptions",subSystemInfoOptions(""));
		model.put("treeList", treeList);
		JDateTime jdt = new JDateTime();
		Complex device=new Complex();
		device.setStartTime(GraphUtils.getDateString(jdt.getYear(), jdt.getMonth(), 1) 
				+ " 00:00:00");
		device.setEndTime(jdt.toString("YYYY-MM-DD hh:mm:ss"));
		model.put("device", device);
		model.put("nhType", nhType);
		return "nhgl/query/complex/water/collect_list";
	}
  
   /* private String subSystemInfoOptions(Object checkedValue){
    	StringBuffer optionsBuffer=new StringBuffer();
    	List<WaterSubSystemInfo> lstSub=waterSubSystemInfoService.findAll();
    	for(WaterSubSystemInfo sub:lstSub){
    		optionsBuffer.append("<option value='")
    			.append(sub.getCode()).append("'");
    		if(checkedValue!=null
    				&& String.valueOf(checkedValue).equals(sub.getName())){
    			optionsBuffer.append(" selected ");
    		}
    		optionsBuffer.append(">");
    		optionsBuffer.append(sub.getName());
    		optionsBuffer.append("</option>");
    	}
    	return optionsBuffer.toString();
    }*/
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping("page")
	public Pageable<Complex> page( Pagination<Complex> pager, @ModelAttribute Complex condition,
			ModelMap model) {
    	if(condition.getSubCode()!=null && condition.getSubCode().equals("0")){
    		condition.setSubCode("%");
    	}
    	if(condition.getLocationId()==null){
     		condition.setLocationId(0L); 
    	}
    	deviceService.findByWaterConditon(pager, condition);
		return pager;
	}
}
