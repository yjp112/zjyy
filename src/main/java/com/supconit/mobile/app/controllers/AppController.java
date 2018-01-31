package com.supconit.mobile.app.controllers;

import com.alibaba.fastjson.JSON;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.mobile.app.entities.MobileUser;
import com.supconit.mobile.app.servers.MobileUserService;
import com.supconit.mobile.mobileJson.entities.ImagePath;
import com.supconit.mobile.mobileJson.entities.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 16/5/24.
 */
@Controller
@RequestMapping("mobile/app")
public class AppController extends BaseControllerSupport {

	//服务器URL地址
	@Value("${server_url}")
	private String serverUrl;

	private String server_url=serverUrl+"/jcds/mobile";

	@Autowired
	private MobileUserService mobileUserService;
	/**
	 * 设置手势密码
	 * @return
     */
	@ResponseBody
	@RequestMapping(value = "setNumberPsd", method = RequestMethod.POST)
	public JSON setNumberPsd(String numberPsd,String userId) {
		System.out.println("手势密码值："+numberPsd+"====用户"+userId);

		ResultJson resultJson=new ResultJson();
		Long resultCode=400l;
		String resultMsg="设置失败";
		if(numberPsd!=null&&!"".equals(numberPsd)&&userId!=null&&!"".equals(userId))
		{
			MobileUser mobileUser=new MobileUser();
			mobileUser.setId(Long.valueOf(userId));
			mobileUser.setSignPassword(numberPsd);
			this.mobileUserService.update(mobileUser);
			resultCode=200l;
			resultMsg="设置成功";
		}
		resultJson.setResultCode(resultCode);
		resultJson.setResultMsg(resultMsg);
		JSON returnJson= (JSON) JSON.toJSON(resultJson);
		return returnJson;
	}

	/**
	 * 验证手势密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkNumberPsd", method = RequestMethod.POST)
	public JSON checkNumberPsd(String numberPsd,String userId) {
		System.out.println("手势密码值："+numberPsd+"====用户"+userId);
		ResultJson resultJson=new ResultJson();
		Long resultCode=400l;
		String resultMsg="验证失败";
		if(userId!=null&&!"".equals(userId))
		{
			MobileUser mobileUser=this.mobileUserService.getById(Long.valueOf(userId));
			if(mobileUser!=null)
			{
				String signPassword=mobileUser.getSignPassword();
				if(signPassword!=null&&numberPsd!=null&&signPassword.equals(numberPsd))
				{
					resultCode=200l;
					resultMsg="验证通过";
				}
			}
		}
		resultJson.setResultCode(resultCode);
		resultJson.setResultMsg(resultMsg);
		JSON returnJson= (JSON) JSON.toJSON(resultJson);
		return returnJson;
	}

	/**
	 * 是否开启手势密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "useNumberPsd", method = RequestMethod.POST)
	public JSON useNumberPsd(Integer use,String userId) {
		System.out.println("是否使用手势密码："+use+"====用户"+userId);
		ResultJson resultJson=new ResultJson();
		Long resultCode=400l;
		String resultMsg="使用失败";
		if(userId!=null&&!"".equals(userId))
		{
			if(use!=null)
			{
				MobileUser mobileUser=new MobileUser();
				mobileUser.setId(Long.valueOf(userId));
				mobileUser.setIsOpenSignPassword(use);
				this.mobileUserService.update(mobileUser);
				resultCode=200l;
				resultMsg="使用成功";
			}
		}
		resultJson.setResultCode(resultCode);
		resultJson.setResultMsg(resultMsg);
		JSON returnJson= (JSON) JSON.toJSON(resultJson);
		return returnJson;
	}
	/**
	 * 轮播广告
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "slideList", method = RequestMethod.POST)
	public JSON slideList() {
		{
			ResultJson resultJson=new ResultJson();
			Long resultCode=200l;
			String resultMsg="操作成功";
			List<ImagePath> imagePaths=new ArrayList<ImagePath>();
			ImagePath imagePath1=new ImagePath();
			imagePath1.setPath(server_url+"/images/slide/pic1.png");
			imagePath1.setUrl("www.baidu.com");
			ImagePath imagePath2=new ImagePath();
			imagePath2.setPath(server_url+"/images/slide/pic2.png");
			imagePath2.setUrl("www.163.com");
			ImagePath imagePath3=new ImagePath();
			imagePath3.setPath(server_url+"/images/slide/pic3.png");
			imagePath3.setUrl("www.sina.com.cn");
			ImagePath imagePath4=new ImagePath();
			imagePath4.setPath(server_url+"/images/slide/pic4.png");
			imagePath4.setUrl("www.supconit.com");
			imagePaths.add(imagePath1);
			imagePaths.add(imagePath2);
			imagePaths.add(imagePath3);
			imagePaths.add(imagePath4);
			resultJson.setResultContent(imagePaths);
			resultJson.setResultCode(resultCode);
			resultJson.setResultMsg(resultMsg);
			JSON returnJson= (JSON) JSON.toJSON(resultJson);
			return returnJson;
		}
	}

	/**
	 * 轮播广告
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "rootMenus", method = RequestMethod.POST)
	public JSON rootMenus() {
		{
			ResultJson resultJson=new ResultJson();
			Long resultCode=200l;
			String resultMsg="操作成功";
			List<ImagePath> imagePaths=new ArrayList<ImagePath>();
			ImagePath imagePath1=new ImagePath();
			imagePath1.setTitle("我的流程");
			imagePath1.setUrl(server_url+"/todo/list");
			imagePath1.setPath(server_url+"/images/root/menu1.png");
			imagePath1.setFontColor("#000000");
			ImagePath imagePath2=new ImagePath();
			imagePath2.setTitle("考勤管理");
			imagePath2.setUrl(server_url+"/attendance/view");
			imagePath2.setPath(server_url+"/images/root/menu2.png");
			imagePath2.setFontColor("#000000");
			ImagePath imagePath3=new ImagePath();
			imagePath3.setTitle("访客管理");
			imagePath3.setUrl(server_url+"/visitor/list");
			imagePath3.setPath(server_url+"/images/root/menu3.png");
			imagePath3.setFontColor("#000000");
			ImagePath imagePath4=new ImagePath();
			imagePath4.setTitle("关于");
			imagePath4.setUrl(server_url+"/about/view");
			imagePath4.setPath(server_url+"/images/root/menu4.png");
			imagePath4.setFontColor("#000000");
			ImagePath imagePath5=new ImagePath();
			imagePath5.setTitle("我的流程");
			imagePath5.setUrl(server_url+"/todo/list");
			imagePath5.setPath(server_url+"/images/root/menu5.png");
			imagePath5.setFontColor("#000000");
			ImagePath imagePath6=new ImagePath();
			imagePath6.setTitle("考勤管理");
			imagePath6.setUrl(server_url+"/attendance/view");
			imagePath6.setPath(server_url+"/images/root/menu6.png");
			imagePath6.setFontColor("#000000");
			ImagePath imagePath7=new ImagePath();
			imagePath7.setTitle("访客管理");
			imagePath7.setUrl(server_url+"/visitor/list");
			imagePath7.setPath(server_url+"/images/root/menu7.png");
			imagePath7.setFontColor("#000000");
			ImagePath imagePath8=new ImagePath();
			imagePath8.setTitle("关于");
			imagePath8.setUrl(server_url+"/about/view");
			imagePath8.setPath(server_url+"/images/root/menu8.png");
			imagePath8.setFontColor("#000000");
			imagePaths.add(imagePath1);
			imagePaths.add(imagePath2);
			imagePaths.add(imagePath3);
			imagePaths.add(imagePath4);
			imagePaths.add(imagePath5);
			imagePaths.add(imagePath6);
			imagePaths.add(imagePath7);
			imagePaths.add(imagePath8);
			resultJson.setResultContent(imagePaths);
			resultJson.setResultCode(resultCode);
			resultJson.setResultMsg(resultMsg);
			JSON returnJson= (JSON) JSON.toJSON(resultJson);
			return returnJson;
		}
	}
}