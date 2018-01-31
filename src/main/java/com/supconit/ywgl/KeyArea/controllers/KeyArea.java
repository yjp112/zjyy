package com.supconit.ywgl.KeyArea.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.common.web.controllers.BaseControllerSupport;

@Controller
@RequestMapping("ywgl/KeyArea")
public class KeyArea extends BaseControllerSupport{
	
	@RequestMapping("go")
	public String go(ModelMap model) {
		return "ywgl2/KeyArea/list";
	}
	//停车楼
	@RequestMapping("tingchelou")
	public String tingCheLou(ModelMap model) {
		return "ywgl2/KeyArea/tingCheLou";
	}
	//园区
	@RequestMapping("yuanqu")
	public String yuanQu(ModelMap model) {
		return "ywgl2/KeyArea/yuanQu";
	}
	//试验室
	@RequestMapping("shiyanshi")
	public String shiYanShi(ModelMap model) {
		return "ywgl2/KeyArea/shiYanShi";
	}
}
