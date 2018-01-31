package com.supconit.mobile.index.controllers;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.hikvision.entities.AccessCount;
import com.supconit.hikvision.services.AccessCountService;
import hc.mvc.annotations.FormBean;
import hc.safety.manager.SafetyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangwei on 16/5/5.
 */

@Controller
@RequestMapping("mobile/index")
public class IndexController extends BaseControllerSupport {

	@Autowired
	private SafetyManager safetyManager;
	/**
	 * 重写注销 原注销请求：/platform/logout
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		this.safetyManager.logout();
		return "mobile/login";
	}
}