/**
 *
 */
package com.supconit.common.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.supconit.honeycomb.business.authorization.entities.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.authorization.services.MenuService;
import com.supconit.honeycomb.business.authorization.services.UserService;
import com.supconit.honeycomb.business.organization.entities.Person;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.honeycomb.util.SecurityUtils;
import com.supconit.honeycomb.util.StringUtils;

import hc.safety.manager.SafetyManager;

@Controller("index_controller")
@RequestMapping("/")
public class IndexController extends BaseControllerSupport {
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private SafetyManager safetyManager;
    @Autowired
    private MenuService menuService;

    private static final String DEFAULT_ROOTMENUCODE = "ROOT_FUNCTION";
    private static final String KEY_REQUEST_URI = "request_uri";

    @RequestMapping(value = "appWindow")
    public String indexWindow() {
        return "index/app_window";
    }

    @RequestMapping(value = "mobile/index")
    public String indexMobile() {
        return "mobile/todo/list";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index/app_window";
    }

    @RequestMapping(value = "unauthenticated", method = RequestMethod.GET)
    public String unauthenticated(HttpServletRequest req) {
        // return "unauthenticated";
//		return "forward:/relogin";
        return "/login";
    }

    @RequestMapping(value = "unauthorized", method = RequestMethod.GET)
    public String unauthorized() {
        // return "unauthorized";
//		return "forward:/relogin";
        return "/login";
    }

    @RequestMapping("relogin")
    public String relogin(ModelMap model, HttpServletRequest req) {
        String url = (String) req.getAttribute(KEY_REQUEST_URI);
        if (StringUtils.isEmpty(url)) {
            url = (String) req.getAttribute("javax.servlet.error.request_uri");
            String param = req.getQueryString();
            if (null != param && !"".equals(param)) {
                url = url + "?" + param;
            }
            if (StringUtils.isEmpty(url)) {
                url = req.getContextPath() + "/index/" + DEFAULT_ROOTMENUCODE;
            }
        }
        model.put(KEY_REQUEST_URI, url);
        return "index/relogin";
    }

    /**
     * 重写注销 原注销请求：/platform/logout
     */
    @RequestMapping(value = "index/logout/{rootMenuCode}", method = RequestMethod.GET)
    public String logout(ModelMap model, @PathVariable String rootMenuCode,
                         HttpServletRequest req) {
        this.safetyManager.logout();
        if (StringUtils.isEmpty(rootMenuCode)) {
            rootMenuCode = DEFAULT_ROOTMENUCODE;
        }
        req.setAttribute(KEY_REQUEST_URI, req.getContextPath() + "/index/"
                + rootMenuCode);
        return "forward:/relogin";
    }

    @RequestMapping("index/{rootMenuCode}")
    public String index(ModelMap model, @PathVariable String rootMenuCode) {
        User user = (User) this.safetyManager.getCurrentUser();
        if (user.getPersonId() != null) {
            Person person = personService.getById(user.getPersonId());
            model.put("name", person.getName());
        }
        if (StringUtils.isEmpty(rootMenuCode)) {
            rootMenuCode = DEFAULT_ROOTMENUCODE;
        }
        if ("ROOT_NHGL".equals(rootMenuCode)) {
            return "nhgl/index";
        }
        model.put("rootMenuCode", rootMenuCode);
        Menu menu = menuService.getByCode(rootMenuCode);
        model.put("menuName", null != menu ? menu.getName() : "");
        return "index/index";
    }

    /**
     * 系统设置首页 原系统设置首页请求：/platform/index
     */
    @RequestMapping("index/platform")
    public String indexPlatform(ModelMap model, String fromPage) {
        return "platform/index";
    }

    @RequestMapping("platform")
    public String platform(ModelMap model, String fromPage) {
        return "platform/index";
    }

    @RequestMapping(value = "updatePass", method = RequestMethod.GET)
    public String updatePassword(ModelMap model) {
        User user = (User) safetyManager.getCurrentUser();
        model.put("users", user);
        return "index/newPass";
    }

    @ResponseBody
    @RequestMapping(value = "updatePass", method = RequestMethod.POST)
    public ScoMessage updatePassword(String oldPass, String newPass) {
        try {
            User user = (User) safetyManager.getCurrentUser();
            if (SecurityUtils.SHA1(oldPass).toLowerCase().trim()
                    .equals(user.getPassword().trim())) {
                user.setPassword(SecurityUtils.SHA1(newPass).toLowerCase()
                        .trim());
                userService.save(user);
            } else {
                return ScoMessage.error("原始密码错误");
            }
        } catch (Exception e) {
            return ScoMessage.error("未知异常");
        }
        return ScoMessage.success("修改成功，重新登录生效");

    }
}
