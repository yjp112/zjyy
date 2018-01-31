
package com.supconit.base.controllers;

import com.alibaba.fastjson.JSON;
import com.supconit.honeycomb.business.authorization.entities.Role;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.authorization.services.RoleService;
import com.supconit.honeycomb.util.StringPool;
import com.supconit.mobile.mobileJson.entities.PropertiesInit;
import com.supconit.mobile.mobileJson.entities.ResultJson;
import com.supconit.mobile.mobileJson.entities.LoginUser;
import hc.base.domains.AjaxMessage;
import hc.safety.authc.*;
import hc.safety.manager.SafetyManager;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/login")
public class UserLoginController {

	private transient static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

	@Value("${platform.login.open_verify_code:false}")
	private Boolean							openVerifyCode;
	@Value("${platform.login.verify_code_timeout:300}")
	private Integer							verifyCodeTimeout;
	
	public static final String ROLEADMINCODE = "ROLE_ADMIN";
	
	public static final String ROLEPTYGCODE = "PTYG";

	// private static final int VERIFY_CODE_TIMEOUT = 60 * 5; // 验证码五分钟过期
	@Autowired
	private SafetyManager	safetyManager;
	@Autowired
	private RoleService roleService;

	//安卓版本号
	@Value("${android_version}")
	private String androidVersion;

	//IOS版本号
	@Value("${ios_version}")
	private String iosVersion;

	//服务器版本号
	@Value("${server_version}")
	private String serverVersion;

	//服务器URL地址
	@Value("${server_url}")
	private String serverUrl;

//	@Autowired
//    protected SessionProvider sessionProvider;
//	@Autowired
//    protected AccountManager accountManager;
//	@Autowired
//    protected RoleResourceManager roleResourceManager;

	@RequestMapping(
			method = {RequestMethod.GET}
	)
	public String login(@RequestParam String isMobile,
						HttpServletRequest req){
		if (isMobile!=null&&"true".equals(isMobile))
		{
			return "mobile/login";
		}
		else
		{
			req.setAttribute("request_uri","/jcds");
			return "forward:/relogin";
		}
	}
	
	protected AjaxMessage check(String username, String password, HttpServletRequest request) {
		if (StringUtil.isBlank(username))
			return AjaxMessage.error("用户名不能为空");
		if (StringUtil.isBlank(password))
			return AjaxMessage.error("密码不能为空");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			safetyManager.login(token);
			return AjaxMessage.success("").setMessage("登录成功");
		} catch (UnknownAccountException e) {
			return AjaxMessage.error("用户不存在");
		} catch (IncorrectCredentialsException e) {
			return AjaxMessage.error("密码错误");
		} catch (LockedAccountException e) {
			return AjaxMessage.error("用户被锁定");
		} catch (ExcessiveAttemptsException e) {
			return AjaxMessage.error("登录尝试失败超限");
		} catch (AuthenticationException e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error("登录失败");
		}
	}
	
	@ResponseBody
	@RequestMapping(value ="login")
	public AjaxMessage userCheck(String username, String password, HttpServletRequest request) {
		if (StringUtil.isBlank(username))
			return AjaxMessage.error("用户名不能为空");
		if (StringUtil.isBlank(password))
			return AjaxMessage.error("密码不能为空");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			safetyManager.login(token);
			User user = (User) this.safetyManager.getCurrentUser();
			List<Role> roleList = roleService.findAssigned(user.getId());
			boolean flag = false;
			for (Role role : roleList) {// 判断该登录用户是否包含管理员角色
				if (ROLEPTYGCODE.equals(role.getCode())) {
						flag = true;
						break;
				}
			}
			if(flag){
				return AjaxMessage.success("/index/ROOT_YGZZ").setMessage("登录成功");
			}else{
				return AjaxMessage.success("").setMessage("登录成功");			
			}
		} catch (UnknownAccountException e) {
			return AjaxMessage.error("用户不存在");
		} catch (IncorrectCredentialsException e) {
			return AjaxMessage.error("密码错误");
		} catch (LockedAccountException e) {
			return AjaxMessage.error("用户被锁定");
		} catch (ExcessiveAttemptsException e) {
			return AjaxMessage.error("登录尝试失败超限");
		} catch (AuthenticationException e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error("登录失败");
		}
	}

	@ResponseBody
	@RequestMapping(value ="mobile")
	public AjaxMessage mobileCheck(String username, String password, HttpServletRequest request) {
		if (StringUtil.isBlank(username))
			return AjaxMessage.error("用户名不能为空");
		if (StringUtil.isBlank(password))
			return AjaxMessage.error("密码不能为空");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			safetyManager.login(token);
			return AjaxMessage.success().setMessage("登录成功");
		} catch (UnknownAccountException e) {
			return AjaxMessage.error("用户不存在");
		} catch (IncorrectCredentialsException e) {
			return AjaxMessage.error("密码错误");
		} catch (LockedAccountException e) {
			return AjaxMessage.error("用户被锁定");
		} catch (ExcessiveAttemptsException e) {
			return AjaxMessage.error("登录尝试失败超限");
		} catch (AuthenticationException e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error("登录失败");
		}
	}

	@ResponseBody
	@RequestMapping(value ="android" ,method = RequestMethod.POST)
	public JSON androidCheck(String username, String password, HttpServletRequest request) {
		Long resultCode=400l;
		String resultMsg="";
		ResultJson resultJson=new ResultJson();
		if (StringUtil.isBlank(username))
		{
			resultMsg="用户名不能为空";
			resultJson.setResultCode(resultCode);
			resultJson.setResultMsg(resultMsg);
			JSON returnJson= (JSON) JSON.toJSON(resultJson);
			return returnJson;
		}
		else if (StringUtil.isBlank(password))
		{
			resultMsg="密码不能为空";
			resultJson.setResultCode(resultCode);
			resultJson.setResultMsg(resultMsg);
			JSON returnJson= (JSON) JSON.toJSON(resultJson);
			return returnJson;
		}
		else if("admin".equals(username)||"ADMIN".equals(username))
		{
			resultMsg="用户不存在";
			resultJson.setResultCode(resultCode);
			resultJson.setResultMsg(resultMsg);
			JSON returnJson= (JSON) JSON.toJSON(resultJson);
			return returnJson;
		}
		else
		{
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			try {
				safetyManager.login(token);
				resultCode=200l;
				resultMsg="登录成功";
				User user = (User) this.safetyManager.getCurrentUser();
				Long userId=user.getId();
				Long personId=user.getPersonId();
				String tokenString="1111-1111-1111";
				LoginUser loginUser=new LoginUser();
				loginUser.setUserId(userId);
				loginUser.setToken(tokenString);
				loginUser.setUseNumberPsd(false);
				loginUser.setPersonId(personId);
				resultJson.setResultContent(loginUser);
			} catch (UnknownAccountException e) {
				resultMsg="用户不存在";
			} catch (IncorrectCredentialsException e) {
				resultMsg="密码错误";
			} catch (LockedAccountException e) {
				resultMsg="用户被锁定";
			} catch (ExcessiveAttemptsException e) {
				resultMsg="登录尝试失败超限";
			} catch (AuthenticationException e) {
				resultMsg="登录失败";
			}finally {
				resultJson.setResultCode(resultCode);
				resultJson.setResultMsg(resultMsg);
				JSON returnJson= (JSON) JSON.toJSON(resultJson);
				return returnJson;
			}
		}

	}

	@ResponseBody
	@RequestMapping(value ="loginCheck")
	public JSON loginCheck(String username, String password, HttpServletRequest request) {
		Long resultCode=400l;
		String resultMsg="";
		ResultJson resultJson=new ResultJson();
		if (StringUtil.isBlank(username))
		{
			resultMsg="用户名不能为空";
		}
		if (StringUtil.isBlank(password))
		{
			resultMsg="密码不能为空";
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			safetyManager.login(token);
			resultCode=200l;
			resultMsg="登录成功";
		} catch (UnknownAccountException e) {
			resultMsg="用户不存在";
		} catch (IncorrectCredentialsException e) {
			resultMsg="密码错误";
		} catch (LockedAccountException e) {
			resultMsg="用户被锁定";
		} catch (ExcessiveAttemptsException e) {
			resultMsg="登录尝试失败超限";
		} catch (AuthenticationException e) {
			resultMsg="登录失败";
		}finally {
			resultJson.setResultCode(resultCode);
			resultJson.setResultMsg(resultMsg);
			JSON returnJson= (JSON) JSON.toJSON(resultJson);
			return returnJson;
		}
	}

	@ResponseBody
	@RequestMapping(value ="propertiesInit")
	public JSON propertiesInit() {
		System.out.println("=============开始访问配置文件========");
		ResultJson resultJson=new ResultJson();
		Long resultCode=200l;
		String resultMsg="登录成功";
		resultJson.setResultCode(resultCode);
		resultJson.setResultMsg(resultMsg);
		PropertiesInit propertiesInit=new PropertiesInit();
		/**主页前是否需要登录 **/
		Boolean needLoginBeforeIndex=true;

		String setKeyPageBackground=serverUrl+"/jcds/mobile/images/icon/icon-c-back.png";//返回图标

		/**登录背景地址 **/
		String loginPageBackground="";

		String headerFontColor="#c7eafd";
		int styleIndex=4;//当前主页采取样式编号
		String indexUrl=serverUrl+"/jcds/login/androidIndex";
		String headerBg="#0c83c7";//头部颜色
		boolean isGuidePages=false;
		String cover=serverUrl+"/jcds/mobile/images/ui-login-bg.jpg";

		propertiesInit.setNeedLoginBeforeIndex(needLoginBeforeIndex);
		propertiesInit.setSetKeyPageBackground(setKeyPageBackground);
		propertiesInit.setLoginPageBackground(loginPageBackground);
		propertiesInit.setHeaderFontColor(headerFontColor);
		propertiesInit.setStyleIndex(styleIndex);
		propertiesInit.setIndexUrl(indexUrl);
		propertiesInit.setHeaderBg(headerBg);
		propertiesInit.setIsGuidePages(isGuidePages);
		propertiesInit.setCover(cover);
		propertiesInit.setIosVersion(iosVersion);
		propertiesInit.setAndroidVersion(androidVersion);
		propertiesInit.setServerVersion(serverVersion);

		resultJson.setResultContent(propertiesInit);

		JSON returnJson= (JSON) JSON.toJSON(resultJson);
		System.out.println("=============结束访问配置文件========");
		return returnJson;
	}

	@RequestMapping(value = "km",method = RequestMethod.GET)
	public void login(String username, String password,String callback,HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding(StringPool.UTF_8);
		AjaxMessage message = check(username, password, request);
//		if ("json".equals(rtype)) {
//			response.setContentType("application/json");
//			response.getWriter().write(JSON.toJSONString(message));
//		} else if ("jsonp".equals(rtype)) {
//			response.setContentType("application/json");
//			response.getWriter().write(callback + "(" + JSON.toJSONString(message) + ")");
//		} else if ("iframe".equals(rtype)) {
//			if (AjaxMessage.STATUS_SUCCESS.equals(message.getStatus())) {
//				response.getWriter().write("<script>top.location = '" + callback + "'</script>");
//			} else {
//				response.getWriter().write("<script>alert('" + string2Unicode(message.getMessage()) + "')</script>");
//			}
//		} else {
		if (AjaxMessage.STATUS_SUCCESS.equals(message.getStatus())) {
			if (StringUtil.isNotBlank(callback)) {
				response.sendRedirect(callback);
				return;
			} else {
				response.sendRedirect("/jcds/index/ROOT_YGZZ");
				return;
			}
		} else {
			response.getWriter().write("<script>alert('" + string2Unicode(message.getMessage()) + "');history.go(-1);</script>");
		}

//		}
		response.getWriter().flush();
		response.getWriter().close();
	}

	@RequestMapping(value = "androidIndex",method = RequestMethod.GET)
	public void androidIndex(String username, String password,HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setCharacterEncoding(StringPool.UTF_8);
		AjaxMessage message = check(username, password, request);
		if (AjaxMessage.STATUS_SUCCESS.equals(message.getStatus())) {
			response.sendRedirect("/jcds/mobile/todo/list");
		}
	}

	private static String string2Unicode(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			String tmpStr = Integer.toHexString(str.charAt(i));
			if (tmpStr.length() < 4) {
				sb.append("\\u00");
			} else {
				sb.append("\\u");
			}
			sb.append(tmpStr);
		}
		return sb.toString();
	}
}
