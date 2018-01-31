
package com.supconit.nhgl.query.collectError.controllers;

import hc.base.domains.Pagination;

import java.util.List;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngArea.service.NgAreaService;
import com.supconit.nhgl.query.collectError.entities.CollectError;
import com.supconit.nhgl.query.collectError.services.CollectErrorService;
import com.supconit.nhgl.utils.GraphUtils;

@Controller
@RequestMapping("nhgl/query/collectError")
public class CollectErrorController  extends BaseControllerSupport{

    
	@Autowired
	private CollectErrorService collectErrorService;
	@Autowired
	private NgAreaService ngAreaService;
	
	@ModelAttribute("prepareJcrw")
	public CollectError prepareJcrw() {
		return new CollectError();
	}
	
    @RequestMapping("list")
	public String list(ModelMap model,Integer nhType) {
		List<NgArea> treeList = ngAreaService.findTree();
		//model.put("subSystemInfoOptions",subSystemInfoOptions(""));
		model.put("treeList", treeList);
		CollectError device=new CollectError();
		
		JDateTime jdt = new JDateTime();
		device.setStartTime(GraphUtils.getDateString(jdt.getYear(), jdt.getMonth(), 1) 
				+ " 00:00:00");
		device.setEndTime(jdt.toString("YYYY-MM-DD hh:mm:ss"));
		model.put("device", device);
		model.put("nhType", nhType);
		String url = "";
		switch (nhType) {
		case 1:
			url = "nhgl/query/collectError/collect_list";
			break;
		case 2:
			url = "nhgl/query/collectError/water/collect_list";
			break;
		case 3:
			url = "nhgl/query/collectError/gas/collect_list";
			break;
		case 4:
			url = "nhgl/query/collectError/energy/collect_list";
			break;
		default:
			url = "nhgl/query/collectError/collect_list";
			break;
		}
		return url;
	}
  
   /* private String subSystemInfoOptions(Object checkedValue){
    	StringBuffer optionsBuffer=new StringBuffer();
    	List<NhItem> lstSub=subSystemInfoService.findAll();          
    	for(NhItem sub:lstSub){
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
	public Pagination<CollectError> page( Pagination<CollectError> pager, CollectError condition,
			ModelMap model) {
    	if(condition.getLocationId()==null){
     		condition.setLocationId(0L); 
    	}
    	condition.setNhType(1);
    	collectErrorService.findByConditon(pager, condition);
		return pager;
	}
    
    @RequestMapping("edit")
	public String edit(ModelMap model,CollectError collectError) {
    	model.put("collectError", collectErrorService.findById(collectError.getId()).get(0));
		return "nhgl/query/collectError/edit";
	}
    
    
	@ResponseBody
	@RequestMapping(value = "save")
	public ScoMessage save(CollectError collectError) {
		copyUpdateInfo(collectError);
		collectErrorService.update(collectError);
		String url = "";
		switch (collectError.getNhType()) {
		case 1:
			url = "nhgl/query/collectError/collect_list";
			break;
		case 2:
			url = "nhgl/query/collectError/water/collect_list";
			break;
		case 3:
			url = "nhgl/query/collectError/gas/collect_list";
			break;
		case 4:
			url = "nhgl/query/collectError/energy/collect_list";
			break;
		default:
			url = "nhgl/query/collectError/collect_list";
			break;
		}
		return ScoMessage.success(url+"?nhType="+collectError.getNhType(),ScoMessage.DEFAULT_SUCCESS_MSG);
	}
}
