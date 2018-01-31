package com.supconit.mobile.about.controllers;

import com.supconit.common.web.controllers.BaseControllerSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangwei on 16/5/19.
 */
@Controller
@RequestMapping("mobile/about")
public class AboutController extends BaseControllerSupport {

	/**
	 * 关于
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(ModelMap map) {
		return "mobile/about/view";
	}

}