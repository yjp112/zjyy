package com.supconit.common.web.directives;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.springframework.web.servlet.support.RequestContext;

import com.supconit.common.daos.MenuOperateDao;
import com.supconit.common.domains.MenuOperate;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.honeycomb.business.authorization.entities.Menu;
import com.supconit.honeycomb.business.authorization.services.MenuService;

import hc.safety.manager.SafetyManager;
import jodd.util.StringPool;
import jodd.util.StringUtil;

public class BreadCrumbsDirective extends Directive {

	private SafetyManager	safetyManager;
	private MenuService		menuService;
	private MenuOperateDao		menuOperateDao;
	//private SimpleJdbc jdbc = (SimpleJdbc) SpringContextHolder.getBean(DefaultSimpleJdbc.class);

	/**
	 * @return the menuService
	 */
	public MenuService getMenuService() {
		if (null == this.menuService) {
			this.menuService = SpringContextHolder.getBean(MenuService.class);
		}
		return menuService;
	}

	/**
	 * @return the menuOperateDao
	 */
	public MenuOperateDao getMenuOperateDao() {
		if (null == this.menuOperateDao) {
			this.menuOperateDao = SpringContextHolder.getBean(MenuOperateDao.class);
		}
		return menuOperateDao;
	}
	public SafetyManager getSafetyManager() {
		if (null == this.safetyManager) {
			this.safetyManager = SpringContextHolder.getBean(SafetyManager.class);
		}
		return safetyManager;
	}

	/*
	 * @see org.apache.velocity.runtime.directive.Directive#getName()
	 */
	@Override
	public String getName() {
		return "kendo_bread_crumbs";
	}

	/*
	 * @see org.apache.velocity.runtime.directive.Directive#getType()
	 */
	@Override
	public int getType() {
		return LINE;
	}

	/*
	 * @see org.apache.velocity.runtime.directive.Directive#render(org.apache.velocity.context.InternalContextAdapter, java.io.Writer, org.apache.velocity.runtime.parser.node.Node)
	 */
	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		SimpleNode codeNode = (SimpleNode) node.jjtGetChild(0);
		if (null == codeNode) return true;
		String code = (String) codeNode.value(context);
		if (StringUtil.isBlank(code)) return true;
		Menu menu = getMenuService().getByCode(code);
		if (null == menu) return true;
		int startLvl=2;//只查询层级大于2的节点
		
		List<MenuOperate> menus=getMenuOperateDao().selectTreePath(startLvl, menu);
		
		/*<ol class="ui-step">
		<li><a href="javascript:load_index_page()" class="ui-action-home">
		   <i class="fa fa-home"></i></a> 
		   <span class="arrow"> <i class="fa fa-angle-right"></i></span></li>
		 <li><a href="javascript:void(0)">总菜单</a><span class="arrow"> <i class="fa fa-angle-right"></i></span></li>
		 <li><a href="javascript:void(0)">能保管理系统</a><span class="arrow"> <i class="fa fa-angle-right"></i></span></li>
		 <li><a href="javascript:void(0)">设备维修</a><span class="arrow"> <i class="fa fa-angle-right"></i></span></li>
		 <li class="current"><span><a href="/ccyq/repair/process/list">工艺设备维修</a></span></li>
		</ol>*/
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
		writer.write(builder.toString());
		return true;
	}
	/*@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		SimpleNode codeNode = (SimpleNode) node.jjtGetChild(0);
		if (null == codeNode) return true;
		String code = (String) codeNode.value(context);
		if (StringUtil.isBlank(code)) return true;
		Menu menu = getMenuService().getByCode(code);
		if (null == menu) return true;
		RequestContext rc = (RequestContext) context.get("rc");
		String contextPath = null == rc ? StringPool.EMPTY : rc.getContextPath()+"/";
		StringBuilder builder = new StringBuilder();
		StringBuilder sb = null;
		Menu pMenu = menu;
		
		for (;;) {
			if (null == pMenu.getPid()) break;
			pMenu = getMenuService().getById(pMenu.getPid());
			if (null == pMenu) break;
			if (Menu.CODE_FUNCTION.equals(pMenu.getCode()) || Menu.CODE_SYSTEM.equals(pMenu.getCode())) break;
			sb = new StringBuilder();
			sb.append("<li>");
			if(StringUtil.isNotBlank(pMenu.getLinkUrl())){
				sb.append("<a href=\""+contextPath+pMenu.getLinkUrl()+"\" >");
			}else{
				sb.append("<a href=\"javascript:void(0)\">");
			}
			sb.append(pMenu.getName())
			.append("</a><span class=\"arrow\"> <i class=\"fa fa-angle-right\"></i>")
			.append("</span></li>");
			builder.insert(0, sb);
		}
		StringBuilder sbu = new StringBuilder();
		sbu.append("<ol class=\"ui-step\">")
		.append("<li><a href=\"javascript:load_index_page()\" class=\"ui-action-home\">")
		.append("<i class=\"fa fa-home\"></i>")
		.append("</a> <span class=\"arrow\"> <i class=\"fa fa-angle-right\"></i></span></li>");
		builder.insert(0, sbu);
		
		builder.append("<li class=\"current\"><span>")
		.append("<a href=\""+contextPath+menu.getLinkUrl()+"\">"+menu.getName()+"</a>")
		// .append(menu.getName())
		.append("</span></li></ol>");
		writer.write(builder.toString());
		return true;
	}*/

}
