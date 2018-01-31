
package com.supconit.base.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Attachment;
import com.supconit.base.services.AttachmentService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.honeycomb.mvc.DwzMsg;

import hc.base.domains.Pageable;

@Controller
@RequestMapping("device/attachment")
public class AttachmentController extends BaseControllerSupport {

    
	@Autowired
	private AttachmentService attachmentService;
	@Value("${file.persistentsave}")
	private String savePath;

	@Value("${image.server.url}")
	private String serverPath;	
    /*
    get "attachment" list
    */
    @RequestMapping("list")
	public String list(Pageable<Attachment> page, Attachment attachment,
			ModelMap model) {
    	Pageable<Attachment> pager = attachmentService.findByCondition(page, attachment);
		
		model.put("attachment", attachment);
		model.put("pager", pager);
		
		return "device/attachment/attachment_list";
	}


    /*
    save  attachment
    Attachment object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public DwzMsg save(Attachment attachment) {
				
         if(attachment.getId()==null){
            copyCreateInfo(attachment);
            attachmentService.insert(attachment);	
        }
        else{
            copyUpdateInfo(attachment);    
            attachmentService.update(attachment);
        }
            
		DwzMsg msg = DwzMsg.success(DwzMsg.DEFAULT_SUCCESS_MSG);
		
		return msg;
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public DwzMsg delete( @RequestParam("ids[]") Long[] ids) {
		
		attachmentService.deleteByIds(ids);
		DwzMsg msg = DwzMsg.success(DwzMsg.DEFAULT_SUCCESS_MSG);
		
		return msg;
	}   
    
    /**
	 * Edit Attachment
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			Attachment attachment = attachmentService.getById(id);
			if (null == attachment) {
				throw new IllegalArgumentException("Object does not exist");
			}
			
			model.put("attachment", attachment);			
		}		
		
		return "device/attachment/attachment_edit";
	}
    @ResponseBody
	@RequestMapping("download")
    public void download(HttpServletRequest request,HttpServletResponse response,String path,String fileName){
	    try{ 
	       
	    	//path : 服务器上的保存文件名，fileName另存为文件名
//	    	fileName = URLEncoder.encode(fileName, "UTF-8");   
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
