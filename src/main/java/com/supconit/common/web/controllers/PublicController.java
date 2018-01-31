package com.supconit.common.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jodd.util.Base64;
import jodd.util.StringUtil;

@Controller("platform_business_public_controller")
@RequestMapping({ "/platform/public" })
public class PublicController {
	@RequestMapping({ "unauthorized" })
	public String unauthorized() {
		return "redirect:/platform/login";
	}

	@RequestMapping({ "forbidden" })
	public String forbidden() {
		return "/platform/public/forbidden";
	}

	@RequestMapping({ "notfound" })
	public String notfound() {
		return "/platform/public/notfound";
	}

	@RequestMapping({ "redirect" })
	public String redirect(
			@RequestParam(required = false, defaultValue = "true") Boolean success,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String message,
			@RequestParam(required = false, defaultValue = "3") Long stopwatch,
			@RequestParam(required = false) String next,
			@RequestParam(required = false) String nextUrl,
			@RequestParam(required = false, defaultValue = "true") Boolean close,
			@RequestParam(required = false) String callback,
			@RequestParam(value = "his", required = false, defaultValue = "true") Boolean historyGo,
			ModelMap model) {
		model.put("success", success);
		if (StringUtil.isNotBlank(message))
			model.put("message", Base64.decodeToString(message));
		if (StringUtil.isNotBlank(title))
			model.put("title", Base64.decodeToString(title));
		if (StringUtil.isNotBlank(next))
			model.put("next", Base64.decodeToString(next));
		model.put("nextUrl", nextUrl);
		model.put("close", close);
		model.put("callback", callback);
		model.put("historyGo", historyGo);
		model.put("stopwatch", stopwatch);
		return "platform/public/redirect";
	}
}