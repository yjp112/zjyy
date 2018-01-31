package com.supconit.common.web.controllers;

import java.io.ByteArrayInputStream;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.common.services.MenuExportService;
import com.supconit.common.utils.DateUtils;

import jodd.io.StreamUtil;

@Controller
@RequestMapping("menu")
public class MenuExportController {
	private static final transient Logger logger = LoggerFactory.getLogger(MenuExportController.class);

	private static final String encoding="UTF-8";
	@Resource
	private MenuExportService exportService;
	
	@RequestMapping(value = { "exportAll" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public void exportAll(HttpServletResponse response) throws Exception {

		//response.setContentType("application/octet-stream;charset="+encoding);
		//response.setContentType("application/x-download");
		String filename = DateUtils.format(new Date(),"yyyyMMddHHmmss") + "-menu.xml";
		response.setHeader("Content-Disposition", "attachment; filename=" +filename);
		ByteArrayInputStream bais = new ByteArrayInputStream(exportService.exportAllMenuForXML().getBytes());
		StreamUtil.copy(bais, response.getOutputStream());
		response.flushBuffer();
		bais.close();
	}

}
