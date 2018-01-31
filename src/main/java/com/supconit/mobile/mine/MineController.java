package com.supconit.mobile.mine;

import com.supconit.base.domains.Organization;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.organization.entities.Person;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.honeycomb.util.SecurityUtils;
import com.supconit.mobile.app.entities.MobilePerson;
import com.supconit.mobile.app.servers.MobilePersonService;
import hc.base.domains.AjaxMessage;
import hc.safety.manager.SafetyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangwei on 16/6/6.
 */
@Controller
@RequestMapping("mobile/mine")
public class MineController extends BaseControllerSupport {

	@Autowired
	private PersonService personService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SafetyManager safetyManager;

	@Autowired
	private MobilePersonService mobilePersonService;

	/**
	 * 我的
	 */
	@RequestMapping(value = "mine", method = RequestMethod.GET)
	public String mine(ModelMap map) {
		long currentId=this.getCurrentPersonId();
		if(currentId!=0)
		{
			ExPerson exPerson=this.personService.getById(currentId);
//			exPerson.setHeadPic("www.iii.com");
//			this.personService.update(exPerson);
			List<Organization> orList = organizationService
					.getFullDeptNameByPersonId(currentId);
			String deptFullName = OrganizationUtils.joinFullDeptName(orList);
			if (deptFullName!=null&&!"".equals(deptFullName))
			{
				exPerson.setDeptName(deptFullName);
			}
			else
			{
				exPerson.setDeptName("无");
			}
			map.put("exPerson",exPerson);
		}
		return "mobile/mine/mine";
	}


	/**
	 * 二维码
	 */
	@RequestMapping(value = "qrcode", method = RequestMethod.GET)
	public String qrcode(ModelMap map) {
		return "mobile/mine/qrcode";
	}

	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getHeadPic")
	public String getHeadPic()
	{
		String headPic=null;
		long currentId=this.getCurrentPersonId();
		if(currentId!=0) {
			MobilePerson mobilePerson=this.mobilePersonService.getById(currentId);
			if(mobilePerson!=null)
			{
				headPic=mobilePerson.getHeadPic();
			}
		}
		return headPic;
	}

	/**
	 * 功能简介
	 */
	@RequestMapping(value = "sign", method = RequestMethod.GET)
	public String sign(ModelMap map) {
		return "mobile/mine/sign";
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "config", method = RequestMethod.GET)
	public String config(ModelMap map) {
		return "mobile/mine/config";
	}

	/**
	 * 关于
	 */
	@RequestMapping(value = "about", method = RequestMethod.GET)
	public String about(ModelMap map) {
		return "mobile/mine/about";
	}

	/**
	 * 新功能介绍
	 */
	@RequestMapping(value = "news", method = RequestMethod.GET)
	public String news(ModelMap map) {
		return "mobile/mine/news";
	}

	/**
	 * 更改密码
	 */
	@RequestMapping(value = "change", method = RequestMethod.GET)
	public String change(ModelMap map) {
		return "mobile/mine/change";
	}

	@ResponseBody
	@RequestMapping(value = "changePass", method = RequestMethod.POST)
	public AjaxMessage changePass(String oldPass, String newPass) {
		User user = (User) safetyManager.getCurrentUser();
		try {
			if (SecurityUtils.SHA1(oldPass).toLowerCase().trim()
					.equals(user.getPassword().trim())) {
				user.setPassword(SecurityUtils.SHA1(newPass).toLowerCase()
						.trim());
				userService.save(user);
			} else {
				return AjaxMessage.error("原始密码错误");
			}
		} catch (Exception e) {
			return AjaxMessage.error("未知异常");
		}
		return AjaxMessage.success("修改成功，重新登录生效");
	}

}