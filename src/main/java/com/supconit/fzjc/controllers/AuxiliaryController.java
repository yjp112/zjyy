package com.supconit.fzjc.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.common.web.controllers.BaseControllerSupport;
@Controller
@RequestMapping("auxiliary")
public class AuxiliaryController extends BaseControllerSupport{
	@RequestMapping("go")
	public String list(ModelMap model) {
		
		return "fzjc/fzjc_list";
	}
}
