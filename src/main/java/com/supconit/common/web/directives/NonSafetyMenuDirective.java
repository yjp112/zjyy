package com.supconit.common.web.directives;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.honeycomb.business.authorization.constants.BeanNames;
import com.supconit.honeycomb.business.authorization.entities.Menu;
import com.supconit.honeycomb.business.authorization.services.MenuService;

import jodd.util.StringUtil;

public class NonSafetyMenuDirective extends Directive {
	private MenuService		menuService;

	/**
	 * @return the menuService
	 */
	private MenuService getMenuService() {
		if (null == menuService) {
			menuService = SpringContextHolder.getApplicationContext().getBean(BeanNames.MENU_SERVICE, MenuService.class);
		}
		return menuService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#getName()
	 */
	@Override
	public String getName() {
		return "nonSafetyMenu";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#getType()
	 */
	@Override
	public int getType() {
		return BLOCK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#render(org.apache.velocity .context.InternalContextAdapter, java.io.Writer,
	 * org.apache.velocity.runtime.parser.node.Node)
	 */
	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		String varName = "_menu";
		if (node.jjtGetNumChildren() > 0) {
			SimpleNode contextNameNode = (SimpleNode) node.jjtGetChild(0);
			String contextName = (String) contextNameNode.value(context);
			if (StringUtil.isNotBlank(contextName)) {
				varName = contextName;
			}
		}
		String rootCode = null;
		if (node.jjtGetNumChildren() > 1) {
			SimpleNode rootCodeNode = (SimpleNode) node.jjtGetChild(1);
			rootCode = (String) rootCodeNode.value(context);
		}
		Menu menu = getMenuService().getMenuTree(rootCode);
		context.put(varName, menu);
		return true;
	}

}
