package com.supconit.common.web.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.supconit.honeycomb.business.authorization.entities.Menu;
import com.supconit.honeycomb.business.authorization.services.MenuService;

public class MenuInterceptor extends HandlerInterceptorAdapter {
	@Resource
	private MenuService menuService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String menuId=request.getParameter("menuId");
		String menuCode=request.getParameter("menuCode");
		Menu menu=null;
		if(StringUtils.isNotBlank(menuId)){
			menu=menuService.getById(Long.parseLong(menuId));
		}else if(StringUtils.isNotBlank(menuCode)){
			menu=menuService.getByCode(menuCode);
		}
		if(menu!=null&&modelAndView!=null){
				//modelAndView.addObject("menu",menu);
			request.setAttribute("menu",menu);
		}
		super.postHandle(request, response, handler, modelAndView);
	}

}
