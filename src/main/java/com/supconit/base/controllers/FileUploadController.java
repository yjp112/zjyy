package com.supconit.base.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.honeycomb.mvc.utils.UploadUtils;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadRenamePolicy;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadedFile;

import hc.base.domains.AjaxMessage;


/**
 * @文 件 名：FileUploadController.java
 * @创建日期：2012-10-11
 * @版 权：Copyrigth(c)2012
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：杨明
 * @版 本: v1.0
 * @描 述：文件上传控制类
 */
@Controller
@RequestMapping(value = "/fileupload")
public class FileUploadController {

	private static final Logger logger = LoggerFactory
			.getLogger(FileUploadController.class);

	@Value("${file.tmpsave}")
	private String tmpPath;
	@Value("${file.persistentsave}")
	private String savePath;
	
	@RequestMapping(value = "fileup",method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long startTime = System.currentTimeMillis();

		logger.debug("开始文件上传");
		logger.debug("文件临时存放目录：{}", tmpPath);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result = "";
		try {
			MultiValueMap<String, UploadedFile> fileMap = UploadUtils.uploadFiles(request, tmpPath, Integer.MAX_VALUE,
					"UTF-8", new UploadRenamePolicy() {
						@Override
						public String rename(String s, String s1) {
							String postfix = s1.substring(s1.indexOf(".") + 1, s1.length());
							return UUID.randomUUID().toString() + "." + postfix;
						}
					}, null);

			List<UploadedFile> file = fileMap.get("file");
			DecimalFormat df2 = new DecimalFormat("0.00");

			if (file != null) {
				UploadedFile saveFile = file.get(0);
			    result = "{ \"flag\": \"1\"," 
						+ "\"orignalname\" : \""+ saveFile.getSystemFileName() + "\","
						+ "\"filePath\" : \""+ tmpPath+saveFile.getFile().getName() + "\","
						+ "\"filename\" : \""+ saveFile.getFile().getName() + "\","
						+ "\"postfix\" : \""+ saveFile.getFile().getName().substring(saveFile.getFile().getName().lastIndexOf(".")+1, saveFile.getFile().getName().length()) + "\","
						+ "\"filesize\" : \""+ df2.format((double)saveFile.getFile().length()/1024) + "\"}";
			}
			out.print(result);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"flag\":\"0\"}";
			out.print(result);
			out.close();
		}
		logger.debug("文件上传结束");
		long endTime = System.currentTimeMillis();
		logger.info("startTime=" + startTime);
		logger.info("endTime=" + endTime);
		logger.info("耗时(秒):" + (endTime - startTime) / 1000);
		return null;
	}
	
	
	  @ResponseBody
		@RequestMapping("download")
	    public void download(HttpServletRequest request,HttpServletResponse response,String path,String fileName){
		    try{ 
		    	//path : 服务器上的保存文件名，fileName另存为文件名
		    	fileName = URLEncoder.encode(fileName, "UTF-8");   
		    	//fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
		        if(!savePath.endsWith("/")){path="/"+path;}
		        InputStream inStream = new FileInputStream(savePath+path);// 文件的存放路径    
		        response.reset();     
		        response.setContentType("application/x-download");     // 设置输出格式   
		        response.setHeader("Content-Disposition", "attachment;filename =" + fileName);    
		        OutputStream out = null;
				byte[] b = new byte[1024]; 
				int len;     
				try{       
					  out = response.getOutputStream();                      
					  while ((len = inStream.read(b)) != -1){                 
						  out.write(b, 0, len);          
					  }          
					  out.flush();       
				}catch(IOException e){
					  e.printStackTrace();    
				}finally{
					try {
						if(out!=null){
							out.close();         
						}
						if(inStream!=null){
							inStream.close();     
						}
					} catch (IOException e) {
						e.printStackTrace();

					}
				} 
			}catch(Exception e){
				 e.printStackTrace();    
			}
	    }
}