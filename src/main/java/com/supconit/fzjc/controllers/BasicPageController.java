package com.supconit.fzjc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.honeycomb.business.authorization.entities.Operate;
import com.supconit.honeycomb.business.authorization.services.OperateService;


@Controller("fzjc")
@RequestMapping("fzjc/nhgl/chart")
public class BasicPageController extends BaseControllerSupport{
    @Autowired
    private OperateService operateService;

    @RequestMapping(value = "/render",method = RequestMethod.GET)
    public String render(@RequestParam(required = true)Long menuId,ModelMap map){

        List<Operate> operators =  operateService.findByMenuIdAndUserId(menuId,getCurrentUser().getId());

        map.put("operators",operators);
        return "fzjc/basic_page";
    }
}
