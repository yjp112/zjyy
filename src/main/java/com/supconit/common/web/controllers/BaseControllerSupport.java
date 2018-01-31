package com.supconit.common.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.supconit.base.entities.EnumDetail;
import com.supconit.common.daos.MenuOperateDao;
import com.supconit.common.domains.MenuOperate;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.LabelValueBeanConverter;
import com.supconit.common.utils.datasource.DynamicDataSource;
import com.supconit.common.utils.ireport.JasperReportUtil;
import com.supconit.common.utils.ireport.JasperUtils;
import com.supconit.common.web.entities.AuditExtend;
import com.supconit.common.web.entities.LabelValueBean;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.honeycomb.business.authorization.entities.Menu;
import com.supconit.honeycomb.business.authorization.entities.Role;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.authorization.services.MenuService;
import com.supconit.honeycomb.business.authorization.services.RoleService;
import com.supconit.honeycomb.business.authorization.services.UserService;
import com.supconit.honeycomb.business.organization.entities.Person;
import com.supconit.honeycomb.business.organization.services.PersonService;

import hc.base.domains.AjaxMessage;
import hc.safety.manager.SafetyManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/**
 * @文 件 名：BaseControllerSupport.java @创建日期：2013年7月5日 @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：
 */
@Scope("prototype")
@Controller
@RequestMapping("/base")
public class BaseControllerSupport {

	/**
	 * 字段名称 serialVersionUID 字段类型 long 字段描述
	 */
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = LoggerFactory.getLogger(BaseControllerSupport.class);
	protected static final String PROCESS_INSTANCE_ID = "_PROCESS_INSTANCE_ID";
	@Resource
	public UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private PersonService personService;
	@Resource
	private SafetyManager safetyManager;
	@Resource
	private MenuService		menuService;;
	@Resource
	private MenuOperateDao	menuOperateDao;
	/**
	 * 默认部门ID HO_DEPARTMENT表中根部门ID
	 */
	public static final long DEFAULT_DEPARTMENTID = 10001l;
	/**
	 * 系统管理员角色标识 code:ROLE_ADMIN
	 */
	public static final String ROLEADMINCODE = "ROLE_ADMIN";
	
	public static final String ROLEVISITORCODE = "VISITOR"; //前台角色

	@RequestMapping(value = "process/chart", method = RequestMethod.GET)
	public String processDialog(@RequestParam(required = true) String processInstanceId, ModelMap map) {
		map.put("processInstanceId", processInstanceId);
		return "/bpm/bpm_chart";
	}

	/**
	 * 
	 * @方法名称:getCurrentUser
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述:获取当前用户对象
	 * @return User
	 */
	protected User getCurrentUser() {
		// SpringContextHolder
		// return userService.getCurrentUser();
		return (User) this.safetyManager.getCurrentUser();
	}

	/**
	 * @方法名称:getCurrentUserName
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述: 获取当前用户姓名
	 * @return String
	 */
	protected String getCurrentUserName() {
		return getCurrentUser().getUsername();
	}

	/**
	 * @方法名称:getCurrentPerson
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述: 获取当前person对象
	 * @return Person
	 */
	protected Person getCurrentPerson() {
		long personId = getCurrentUser().getPersonId();
		return personService.getById(personId);
	}

	/**
	 * @方法名称:getCurrentPersonId
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述: 获取当前personId
	 * @return Long
	 */
	protected Long getCurrentPersonId() {
		return getCurrentUser().getPersonId();
	}

	/**
	 * @方法名称:getCurrentPersonName
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述: 获取当前personName
	 * @return String
	 */
	protected String getCurrentPersonName() {
		long personId = getCurrentUser().getPersonId();
		return personService.getById(personId).getName();
	}

	/**
	 * 判断personId是否有对应的user
	 * 
	 * @param personId
	 * @return
	 */
	protected boolean hasUser(Long personId) {
		Person person = personService.getById(personId);
		Set<String> set = new HashSet<String>();
		set.add(person.getCode());
		List<String> userNameList = userService.findUsernamesByPersonCodes(set);
		if (null == userNameList || userNameList.size() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断personCode是否有对应的user
	 * 
	 * @param personCode
	 * @return
	 */
	protected boolean hasUser(String personCode) {
		// Person person = personService.getById(personId);
		Set<String> set = new HashSet<String>();
		set.add(personCode);
		List<String> userNameList = userService.findUsernamesByPersonCodes(set);
		if (null == userNameList || userNameList.size() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 当前用户是否包含管理员角色
	 * 
	 * @return 是：true;否：false
	 */
	protected boolean hasAdminRole() {
		User user = getCurrentUser();
		List<Role> roleList = roleService.findAssigned(user.getId());
		boolean flag = false;
		for (Role role : roleList) {// 判断该登录用户是否包含管理员角色
			if (ROLEADMINCODE.equals(role.getCode())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 当前用户是否包含管理员角色
	 * 
	 * @return 是：true;否：false
	 */
	protected boolean hasVisitorRole() {
		User user = getCurrentUser();
		List<Role> roleList = roleService.findAssigned(user.getId());
		boolean flag = false;
		for (Role role : roleList) {// 判断该登录用户是否包含前台角色
			if (ROLEVISITORCODE.equals(role.getCode())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * @方法名称:copyCreateInfo
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述:拷贝创建人信息到目标实体类
	 * @param audit
	 *            void
	 */
	protected void copyCreateInfo(AuditExtend audit) {
		audit.setCreateDate(new Date());
		audit.setCreateId(getCurrentPersonId());
		audit.setCreator(getCurrentPersonName());
	}

	/**
	 * @方法名称:copyUpdateInfo
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述: 拷贝更新人信息到目标实体类
	 * @param audit
	 *            void
	 */
	protected void copyUpdateInfo(AuditExtend audit) {
		audit.setUpdateDate(new Date());
		audit.setUpdateId(getCurrentPersonId());
		audit.setUpdator(getCurrentPersonName());
	}

	protected Map<String, Object> buildAjaxResult(boolean isSuccess, String msg, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", isSuccess);
		map.put("msg", msg);
		map.put("data", data);
		return map;
	}

	/**
	 * @方法名称:htmlSelectOptions
	 * @作 者:丁阳光
	 * @创建日期:2013年7月11日
	 * @方法描述: 生成html 的option 选项
	 * @param typeEnum
	 * @param checkedValue
	 * @return String
	 */
	protected String htmlSelectOptions(DictTypeEnum typeEnum, Object checkedValue) {
		StringBuffer optionsBuffer = new StringBuffer();

		List<EnumDetail> enums = DictUtils.getDictList(typeEnum);
		for (EnumDetail enumDetail : enums) {
			optionsBuffer.append("<option value='").append(enumDetail.getEnumValue()).append("'");
			if (checkedValue != null && String.valueOf(checkedValue).equals(enumDetail.getEnumValue())) {
				optionsBuffer.append(" selected ");
			}
			optionsBuffer.append(">");
			optionsBuffer.append(enumDetail.getEnumText());
			optionsBuffer.append("</option>");
		}
		return optionsBuffer.toString();
	}

	protected String htmlSelectOptions(List<LabelValueBean> labelValueBeans, String checkedValue) {
		StringBuffer optionsBuffer = new StringBuffer();

		for (LabelValueBean bean : labelValueBeans) {
			optionsBuffer.append("<option value='").append(bean.getValue()).append("'");
			if (checkedValue != null && String.valueOf(checkedValue).equals(bean.getValue())) {
				optionsBuffer.append(" selected ");
			}
			optionsBuffer.append(">");
			optionsBuffer.append(bean.getLabel());
			optionsBuffer.append("</option>");
		}
		return optionsBuffer.toString();
	}

	protected <T> String htmlSelectOptions(List<T> list, LabelValueBeanConverter<T> converter, String checkedValue) {
		StringBuffer optionsBuffer = new StringBuffer();

		for (LabelValueBean bean : converter.toLabelValueBean(list)) {
			optionsBuffer.append("<option value='").append(bean.getValue()).append("'");
			if (checkedValue != null && checkedValue.equals(bean.getValue())) {
				optionsBuffer.append(" selected ");
			}
			optionsBuffer.append(">");
			optionsBuffer.append(bean.getLabel());
			optionsBuffer.append("</option>");
		}
		return optionsBuffer.toString();
	}

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private static SerializeConfig mapping = new SerializeConfig();

	static {
		mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		mapping.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		mapping.put(Time.class, new SimpleDateFormatSerializer("HH:mm:ss"));
		mapping.put(java.util.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView handleException(Exception ex, HttpServletRequest request) {

		String errorMsg = "系统发生错误，请稍后重试。";
		if (ex instanceof BusinessDoneException) {
			log.error(ex.getMessage());
			errorMsg = ex.getMessage();
		} else {
			log.error(ex.getMessage(), ex);
		}
		String jsonString = JSON.toJSONString(ScoMessage.error(ex.getMessage()), mapping, new SerializerFeature[0]);
		// return new ModelAndView().addObject("error", ex);
		ModelAndView modelAndView = new ModelAndView();
		ScoMessage sm = ScoMessage.error(errorMsg);
		modelAndView.addObject("data", sm.getData());
		modelAndView.addObject("message", sm.getMessage());
		modelAndView.addObject("status", sm.getStatus());
		// return ScoMessage.error(ex.getMessage());
		return modelAndView;
	}

	/**
	 * ywgl.js 此处的ajax为同步,把global-vars-**.properties里的配置信息装载到全局变量GlobalVars里.
	 * 除此之外新增属性webCtx;
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loadGlobalVars")
	@ResponseBody
	public AjaxMessage loadGlobalVars(HttpServletRequest request) {
		VelocityViewResolver viewResolver = SpringContextHolder.getBean(VelocityViewResolver.class);
		Map<String, Object> data = viewResolver.getAttributesMap();
		data.put("webCtx", request.getContextPath());
		return AjaxMessage.success(data);
	}

	@RequestMapping(value = "printPreview")
	public String ireport2(ModelMap model, String businessId, String jasperPath, HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		model.put("businessId", businessId);
		model.put("jasperPath", jasperPath);
		return "base/print/print_preview";
	}

	@RequestMapping(value = "ireport")
	public void ireport(String businessId, String reportName, String jasperPath, HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		DynamicDataSource dds = (DynamicDataSource) SpringContextHolder.getApplicationContext().getBean("dataSource");
		JasperPrint jasperPrint = JasperUtils.getJasperPrint(request, businessId, jasperPath, dds.getConnection(),
				false);
		JasperUtils.exportHtml(jasperPrint, "c:/" + reportName + ".html", request, response, "1");
	}

	@RequestMapping("print")
	public void print(String businessId, String jasperPath,ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer;
		try {
			writer = response.getWriter();
			String a = request.getContextPath();
			String str = "<object ID=\"PDFReader\" WIDTH=\"0\" HEIGHT=\"0\" CLASSID=\"CLSID:CA8A9780-280D-11CF-A24D-444553540000\"><param name=\"src\" value=\""
					+ a + "/base/exportPdf?businessId=" + businessId + "&jasperPath="+jasperPath+"\"></object>";
			writer.print(str);
			writer.flush();
			writer.close();
			// response.setHeader("P3P", "CP=CAO PSA OUR");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("exportPdf")
	public void ExportPdf(String businessId, String jasperPath, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			DynamicDataSource dds = (DynamicDataSource) SpringContextHolder.getApplicationContext()
					.getBean("dataSource");
			JasperPrint jasperPrint = JasperUtils.getJasperPrint(request, businessId, jasperPath, dds.getConnection(),
					false);
			JasperUtils.exportPdf(jasperPrint, null, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "menuCrumbs")
	public void menuCrumbs(Integer startLevel,String menuCode,HttpServletRequest request,HttpServletResponse response) {
		Menu menu = menuService.getByCode(menuCode);
		if (null == menu) return ;
		List<MenuOperate> menus=menuOperateDao.selectTreePath(startLevel, menu);
		StringBuilder builder = new StringBuilder();
		builder.append("<ol class=\"ui-step\">")
	   	   	   .append("<li><a class=\"ui-action-home\">")
	   	       .append("<i class=\"fa fa-home\"></i>")
	           .append("</a> <span class=\"arrow\"> <i class=\"fa fa-angle-right\"></i></span></li>");
		for (int i = 0; i < menus.size()-1; i++) {
			MenuOperate m=menus.get(i);
			builder.append("<li><a>"+m.getMname()+"</a><span class=\"arrow\"> <i class=\"fa fa-angle-right\"></i></span></li>");
		}
		builder.append("<li class=\"current\"><span>")
		       .append("<a>"+menu.getName()+"</a>")
		       .append("</span></li></ol>");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(builder.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
