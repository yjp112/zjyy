package com.supconit.mobile.bpm.controllers;

import com.alibaba.fastjson.JSON;
import hc.bpm.domains.ProcessParameter;
import hc.bpm.services.ProcessService;
import jodd.util.StringUtil;
import jodd.util.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


@Controller
@RequestMapping({"/mobile/bpm"})
public class JumpController {

    private static final transient Logger logger = LoggerFactory.getLogger(JumpController.class);
    @Autowired
    private ProcessService processService;

    public JumpController() {
    }

    @RequestMapping({"start"})
    public String start(@RequestParam(
            value = "_bpm_process_key",
            required = false
    ) String processKey, @RequestParam(
            value = "_bpm_version",
            required = false
    ) Integer processVersion, @RequestParam(
            value = "_bpm_extra_params",
            required = false
    ) String extraParams, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        ProcessParameter parameter = new ProcessParameter(processKey, processVersion);
        parameter.setExtraParams(extraParams);
        return this.handleJump(extraParams, request, response, model, parameter);
    }

    @RequestMapping({"task"})
    public String task(@RequestParam(
            value = "_tid",
            required = false
    ) Long userTaskId, @RequestParam(
            value = "_bpm_extra_params",
            required = false
    ) String extraParams, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        ProcessParameter parameter = new ProcessParameter(userTaskId);
        parameter.setExtraParams(extraParams);
        return this.handleJump(extraParams, request, response, model, parameter);
    }

    private String handleJump(String extraParams, HttpServletRequest request, HttpServletResponse response, ModelMap model, ProcessParameter parameter) {
        try {
            String e = this.processService.getFormUrl(parameter);
            if (e.indexOf("/repair/newTask")>-1)
            {
                e=e.replaceAll("/repair/newTask","/mobile/repair");
            }
            if(StringUtil.isBlank(e)) {
                model.put("error_msg", "流程有误！");
                return "mobile/error/error";
            } else {
                if(!e.startsWith("http://")) {
                    e = request.getContextPath() + e;
                }
                if(e.indexOf("?") > 0) {
                    e = e + "&_t=";
                } else {
                    e = e + "?_t=";
                }
                e = e + System.currentTimeMillis();
                if(StringUtil.isNotBlank(extraParams)) {
                    e = e + this.extratParams(URLDecoder.decode(extraParams, "UTF-8"));
                }
                response.sendRedirect(e);
                return null;
            }
        } catch (Exception var7) {
            model.put("error_msg", "流程有误！");
            return "mobile/error/error";
        }
    }

    private String extratParams(String extraParams) {
        Map map = (Map)JSON.parse(extraParams);
        if(CollectionUtils.isEmpty(map)) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            Iterator var5 = map.entrySet().iterator();
            while(var5.hasNext()) {
                Entry entry = (Entry)var5.next();
                builder.append("&").append((String)entry.getKey()).append("=").append(entry.getValue());
            }
            return builder.toString();
        }
    }
}
