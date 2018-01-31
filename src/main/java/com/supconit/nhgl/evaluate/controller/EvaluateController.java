package com.supconit.nhgl.evaluate.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.nhgl.evaluate.entities.MonitorObjScore;
import com.supconit.nhgl.evaluate.service.EvaluateInfoService;

/**
 * Created by zhangzhaoyun on 14-6-23.
 */
@Controller
@RequestMapping("nhgl/evaluate")
public class EvaluateController {
    @Autowired
    private EvaluateInfoService evaluateInfoService;


    @RequestMapping(value = "show", method = RequestMethod.GET)
    public String show() {
        //Map map = evaluateInfoService.showEvaluateResult();
        //System.out.println(map);
        return "nhgl/evaluate/show";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map doEvaluate() {
    	
        return evaluateInfoService.showEvaluateResult();

    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public List<MonitorObjScore> showDetail(@RequestParam(required = true) String taskCode, @RequestParam(required = true) String date) {
        return evaluateInfoService.showEvaluateDetail(taskCode, date);
    }
}
