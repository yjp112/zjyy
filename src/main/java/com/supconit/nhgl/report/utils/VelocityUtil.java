package com.supconit.nhgl.report.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import com.supconit.honeycomb.base.context.SpringContextHolder;

import jodd.util.StringUtil;

/**
 * @author: hhy
 * @date: 2014-8-14
 * @description: velocity工具类
 */
public class VelocityUtil {

    /**
     * @author: hhy
     * @date: 2014-08-14
     * @description: word文件下载
     */
    public static void downloadWord(String path, String filename, Map<String, ?> data, HttpServletResponse response) {

        if (StringUtil.isEmpty(path)) {
            return;
        }

        Context context = new VelocityContext();
        Template template = initVelocityTemplate(path, data, context);

        try {
            //设置下载文件名
          //  String name = URLEncoder.encode(filename, "UTF-8");
        	String name = new String(filename.getBytes("utf-8"),"iso8859-1");
            response.setHeader("Content-Disposition", "attachment; filename="+name+ ".doc");
            response.setContentType("application/msword;utf-8");
            template.merge(context, response.getWriter());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (ParseErrorException e) {
            e.printStackTrace();
        } catch (MethodInvocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: hhy
     * @date:  2014-08-14
     * @description: excel文件下载
     */
    public static void downloadExcel(String path, String fileName, Map<String, ?> data, HttpServletResponse response) {

        if (StringUtil.isEmpty(path)) {
            return;
        }

        Context context = new VelocityContext();
        Template template = initVelocityTemplate(path, data, context);

        try {
            // 设置下载文件名
            String Name = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename="+new String(Name.getBytes("UTF-8"),"ISO8859-1")+ ".xls");
            response.setContentType("application/vnd.ms-excel");
            template.merge(context, response.getWriter());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (ParseErrorException e) {
            e.printStackTrace();
        } catch (MethodInvocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Template initVelocityTemplate(String path, Map<String, ?> data, Context context) {

        VelocityEngineFactoryBean factory = SpringContextHolder.getBean(VelocityEngineFactoryBean.class);
        VelocityEngine velocityEngine = factory.getObject();
        Template template = velocityEngine.getTemplate(path + ".vm", "utf-8");
        for (Entry<String, ?> entry : data.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }

        return template;
    }

}
