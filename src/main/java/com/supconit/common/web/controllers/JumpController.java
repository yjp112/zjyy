 package com.supconit.common.web.controllers;
 
 import com.alibaba.fastjson.JSON;
 import hc.bpm.domains.ProcessParameter;
 import hc.bpm.services.ProcessService;
 import java.util.Map;
 import java.util.Map.Entry;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
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
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Controller("bpmJumpController")
 @RequestMapping({"/bpm"})
 public class JumpController
 {
   private static final String RESULT_ERROR = "platform/bpm/error";
   private static final transient Logger logger = LoggerFactory.getLogger(JumpController.class);
   
   @Autowired
   private ProcessService processService;
   
 
   public JumpController() {}
   
   @RequestMapping({"start"})
   public String start(@RequestParam(value="_bpm_process_key", required=false) String processKey, @RequestParam(value="_bpm_version", required=false) Integer processVersion, @RequestParam(value="_bpm_extra_params", required=false) String extraParams, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     ProcessParameter parameter = new ProcessParameter(processKey, processVersion);
     parameter.setExtraParams(extraParams);
     return handleJump(extraParams, request, response, model, parameter);
   }
   
 
   @RequestMapping({"task"})
   public String task(@RequestParam(value="_tid", required=false) Long userTaskId, @RequestParam(value="_bpm_extra_params", required=false) String extraParams, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     ProcessParameter parameter = new ProcessParameter(userTaskId);
     parameter.setExtraParams(extraParams);
     return handleJump(extraParams, request, response, model, parameter);
   }
   
   private String handleJump(String extraParams, HttpServletRequest request, HttpServletResponse response, ModelMap model, ProcessParameter parameter) {
     try {
       String url = this.processService.getFormUrl(parameter);
       if (StringUtil.isBlank(url)) {
         model.put("error_msg", "FORM URL UNSETTED ?");
         return "platform/bpm/error";
       }
       if (!url.startsWith("http://")) {
         url = request.getContextPath() + url;
       }
       
       if (url.indexOf("?") > 0) {
         url = url + "&_t=";
       } else {
         url = url + "?_t=";
       }
       url = url + System.currentTimeMillis();
       if (StringUtil.isNotBlank(extraParams)) {
         url = url + extratParams(URLDecoder.decode(extraParams, "UTF-8"));
       }
//       response.sendRedirect(url);
//       return null;
       if(url.startsWith(request.getContextPath())){
    	   url=url.replaceFirst(request.getContextPath(), "");
       }
       return "forward:"+url;
     } catch (Exception e) {
       model.put("error_msg", e.getMessage());
       logger.error(e.getMessage(), e); }
     return "platform/bpm/error";
   }
   
 
   private String extratParams(String extraParams)
   {
     Map<String, Object> map = (Map)JSON.parse(extraParams);
     if (CollectionUtils.isEmpty(map)) return "";
     StringBuilder builder = new StringBuilder();
     for (Map.Entry<String, Object> entry : map.entrySet()) {
       builder.append("&").append((String)entry.getKey()).append("=").append(entry.getValue());
     }
     return builder.toString();
   }
 }
