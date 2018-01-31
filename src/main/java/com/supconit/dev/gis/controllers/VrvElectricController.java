package com.supconit.dev.gis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.dev.gis.entities.VrvElectric;
import com.supconit.dev.gis.services.VrvElectricService;
import com.supconit.hl.common.controllers.BaseControllerSupport;


import hc.base.domains.Pageable;
import hc.base.domains.Pagination;;

@Controller
@RequestMapping("/dev/gis/vrv")
public class VrvElectricController extends BaseControllerSupport {
	
	@Autowired
	private VrvElectricService vrvElectricService;
	
	@ResponseBody
    @RequestMapping("list")
	public Pageable<VrvElectric> list(
			Pagination<VrvElectric> page,@ModelAttribute VrvElectric vrvElectric,
			ModelMap model) throws Exception {
		page.setPageSize(1000);
		Pageable<VrvElectric> pager = vrvElectricService.findByCondition(page, vrvElectric);
		model.put("pager", pager);
		return pager;
	}
	

}
