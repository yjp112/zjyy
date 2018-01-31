package com.supconit.mobile.app.controllers;

import com.alibaba.fastjson.JSON;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.mobile.app.entities.MobilePerson;
import com.supconit.mobile.app.servers.MobilePersonService;
import com.supconit.mobile.app.servers.UploadService;
import com.supconit.mobile.mobileJson.entities.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by wangwei on 16/5/24.
 */
@Controller
@RequestMapping("mobile/upload")
public class UploadController extends BaseControllerSupport {

	//服务器URL地址
	@Value("${server_url}")
	private String serverUrl;

	private String server_url="/jcds/upload/";

	private String head_file="upload\\";
	@Autowired
	private UploadService uploadService;

	@Autowired
	private MobilePersonService mobilePersonService;

	@ResponseBody
	@RequestMapping(value = "uploadPic", method = RequestMethod.POST)
	public JSON uploadPic(String base64Img,HttpServletRequest request) {

		long persionId=getCurrentPersonId();
		ResultJson resultJson=new ResultJson();
		Long resultCode=200l;
		String resultMsg="上传成功";
		String url=null;
		//判断图片是否可用
		if(persionId!=0)
		{
			if(base64Img == null){
				resultCode=400l;
				resultMsg="上传失败";
			}
			else
			{
				String serverPath=request.getSession().getServletContext().getRealPath("/");
				url = uploadService.uploadImage(serverPath,base64Img,String.valueOf(persionId),"");
			}
			//上传图片
			if(url==null||url=="")
			{
				resultCode=400l;
				resultMsg="上传失败";
			}
			else
			{
				String resultContent=serverUrl+server_url+String.valueOf(persionId)+"/"+url;
				resultJson.setResultContent(resultContent);
			}
		}

		//返回resultContent
		resultJson.setResultMsg(resultMsg);
		resultJson.setResultCode(resultCode);
		JSON returnJson= (JSON) JSON.toJSON(resultJson);
		return returnJson;
	}

	@ResponseBody
	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	public JSON uploadImg(String base64Img,Long personId,HttpServletRequest request) {
		if(personId==null||personId==0l)
		{
			personId=getCurrentPersonId();
		}
		ResultJson resultJson=new ResultJson();
		Long resultCode=200l;
		String resultMsg="上传成功";
		String url=null;
		//判断图片是否可用
		if(personId!=0l)
		{
			MobilePerson mobilePerson=this.mobilePersonService.getById(personId);
			if(mobilePerson!=null)
			{
				String headPic=mobilePerson.getHeadPic();
				String headFile=mobilePerson.getHeadFile();
				String serverPath=request.getSession().getServletContext().getRealPath("/");
				if(headFile!=null&&!"".equals(headFile))
				{
					new File(serverPath+headFile).delete();
				}
				if(base64Img == null){
					resultCode=400l;
					resultMsg="上传失败";
				}
				else
				{
					url = uploadService.uploadImage(serverPath,base64Img,String.valueOf(personId),"headPic");
				}
				//上传图片
				if(url==null||url=="")
				{
					resultCode=400l;
					resultMsg="上传失败";
				}
				else
				{
					headFile=head_file+String.valueOf(personId)+"\\"+url;
					headPic=server_url+String.valueOf(personId)+"/"+url;
					mobilePerson.setHeadPic(headPic);
					mobilePerson.setHeadFile(headFile);
					this.mobilePersonService.update(mobilePerson);
					String resultContent=serverUrl+server_url+String.valueOf(personId)+"/"+url;
					resultJson.setResultContent(resultContent);
				}
			}
		}
		//返回resultContent
		resultJson.setResultMsg(resultMsg);
		resultJson.setResultCode(resultCode);
		JSON returnJson= (JSON) JSON.toJSON(resultJson);
		return returnJson;
	}
}