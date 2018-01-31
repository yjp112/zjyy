package com.supconit.common.web.directives;

import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.honeycomb.business.authorization.entities.Menu;
import com.supconit.honeycomb.business.authorization.services.MenuService;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CustomCrumbsDirective extends Directive {

    private static Logger log = LoggerFactory.getLogger(CustomCrumbsDirective.class);

    @Override
    public String getName() {
        return "crumbs";
    }

    @Override
    public int getType() {
        return LINE;
    }

    /**
     * 自定义面包屑，包含两个参数，一个是MenuCode（必填），一个是是否进行菜单结构递归（必填，1：只取当前菜单名；2:递归出当前菜单的父节点树结构）
     *
     * @param context
     * @param writer
     * @param node
     * @return
     * @throws java.io.IOException
     * @throws org.apache.velocity.exception.ResourceNotFoundException
     * @throws org.apache.velocity.exception.ParseErrorException
     * @throws org.apache.velocity.exception.MethodInvocationException
     */
    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        if (node.jjtGetNumChildren() <= 1 ) throw new ParseErrorException("No Enough Params,You Should Use This Macro With Two Params,One for MenuCode,The Other For Recursive Flag");//如果没有足够参数，则直接返回
        SimpleNode firstNode = (SimpleNode) node.jjtGetChild(0);
        String menuCode = (String) firstNode.value(context);//菜单Code
        SimpleNode secondNode = (SimpleNode) node.jjtGetChild(1);
        Integer type = (Integer) secondNode.value(context);//是否进行递归

        MenuService menuService = SpringContextHolder.getBean(MenuService.class);

        StringBuilder crumbsBuilder = new StringBuilder();
        if(type == 2) {
            Menu menu = menuService.getByCode(menuCode);
            List<String> menuNameList = new ArrayList<String>();
            menuNameList.add(menu.getName());

            while (null != menu && null != menu.getPid()){//
                menu =menuService.getById(menu.getPid());
                if ((null == menu) || ("ROOT_FUNCTION".equals(menu.getCode())) || ("ROOT_SYSTEM".equals(menu.getCode()))) break;
                menuNameList.add(menu.getName());
            }

            for(int i = menuNameList.size();i > 0;i--) {
                crumbsBuilder.append(menuNameList.get(i-1)+" ");
            }
        }else {
            Menu menu = menuService.getByCode(menuCode);
            crumbsBuilder.append(null!= menu?menu.getName():"");
        }

        log.debug(crumbsBuilder.toString());

        writer.write(crumbsBuilder.toString());

        return true;
    }
}
