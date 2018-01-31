package com.supconit.nhgl.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.report.entities.ReportTemplate;
import com.supconit.nhgl.report.service.ReportTemplateService;
import com.supconit.nhgl.report.utils.VelocityUtil;

@RequestMapping("nhgl/report")
@Controller
public class ReportController extends BaseControllerSupport {
    @Autowired
    private ReportTemplateService templateService;

    @RequestMapping("index")
    public String index(){
        return "nhgl/report/index";
    }

    @RequestMapping("content")
    public String content(ReportTemplate template,ModelMap map){
        template.setCode("TEST_CODE_201503");
        template.setType(1);
        List<ReportTemplate> templateList = templateService.getTemplates(template);

        if(null!=templateList && templateList.size()>0) {
            map.put("content1", templateList.get(0).getContent());
            map.put("content2", templateList.get(1).getContent());
        }

        map.put("rt",template);

        return "nhgl/report/content";
    }

    @RequestMapping("save")
    @ResponseBody
    public String save(String[] contents,ReportTemplate template){
        templateService.save(contents,template);
        return "SUCCESS";
    }
    @RequestMapping("restore")
    @ResponseBody
    public String restore(ReportTemplate template){
        templateService.restoreTemplate(template);
        return "SUCCESS";
    }
    @RequestMapping("export")
    public void export(String[] contents,String pic,HttpServletResponse response){
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("content1",contents[0]);
        data.put("content2",contents[1]);
        data.put("pic",pic.split(",")[1]);

        String path = "/nhgl/report/export";
        String filename = "杭州市客运出租汽车运行月报";

        VelocityUtil.downloadWord(path, filename, data, response);
    }
}
