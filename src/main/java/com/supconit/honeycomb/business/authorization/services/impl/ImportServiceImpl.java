package com.supconit.honeycomb.business.authorization.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.honeycomb.business.authorization.entities.Menu;
import com.supconit.honeycomb.business.authorization.entities.Operate;
import com.supconit.honeycomb.business.authorization.entities.Role;
import com.supconit.honeycomb.business.authorization.exceptions.MenuException;
import com.supconit.honeycomb.business.authorization.services.ImportService;
import com.supconit.honeycomb.business.authorization.services.MenuService;
import com.supconit.honeycomb.business.authorization.services.OperateService;
import com.supconit.honeycomb.business.authorization.services.RoleService;
import com.supconit.honeycomb.business.authorization.xml.entities.MenuXml;
import com.supconit.honeycomb.business.authorization.xml.entities.MenuXml.Scope;
import com.supconit.honeycomb.business.authorization.xml.entities.MenuXmlList;
import com.supconit.honeycomb.business.authorization.xml.entities.OperateXml;
import com.supconit.honeycomb.business.authorization.xml.entities.RoleXml;
import com.supconit.honeycomb.business.authorization.xml.entities.RoleXmlList;
import com.supconit.honeycomb.util.XmlUtil;

@Service
// @DependsOn(value="sqlUpdateApplication")
public class ImportServiceImpl implements ImportService {
	private static final Logger		logger	= LoggerFactory.getLogger(ImportServiceImpl.class);

	@Autowired
	private MenuService				menuService;

	@Autowired
	private RoleService				roleService;

	@Autowired
	private OperateService			operateService;

	private Map<String, MenuXml>	menuMap;

	private List<MenuXml> getInitMenuList() throws Exception {
		logger.debug("load menus............");
		List<MenuXml> menuList = new ArrayList<MenuXml>();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:**/**/*menu.xml");
		MenuXmlList menus = null;
		String scope = System.getProperty("spring.profiles.active");
		Scope sysScope = Scope.valueOf(scope.toUpperCase());
		for (Resource resource : resources) {
			try {
				menus = XmlUtil.converyToJavaBean(resource.getInputStream(), MenuXmlList.class);
				if (menus == null) {
					continue;
				}
				// menuList.addAll(menus.getAll());
				for (MenuXml menu : menus.getAll()) {
					if(StringUtils.isBlank(menu.getParentCode())){
						logger.warn("菜单[{}]父节点编码为空，舍弃。",menu.getName());
						continue;
					}
					if(StringUtils.isBlank(menu.getCode())){
						logger.warn("菜单[{}]节点编码为空，舍弃。",menu.getName());
						continue;
					}
					Scope menuScope;
					if (menu.getScope() != null) {
						menuScope = menu.getScope();
					} else {
						menuScope = Scope.PRODUCTION;
					}
					if (menuScope.compareTo(sysScope) < 0) {
						continue;
					}
					Menu dataMenu = menuService.getByCode(menu.getCode());
					if (dataMenu != null) {
						menu.setExist(true);
						menu.setId(dataMenu.getId());
					}
					menuList.add(menu);
					if (menu.getOperates() == null) {
						continue;
					}

					for (OperateXml operateXml : menu.getOperates().getAll()) {
						Operate dataOperate = operateService.getByCode(operateXml.getCode());
						if (dataOperate != null) {
							operateXml.setExist(true);
							operateXml.setId(dataOperate.getId());
						}
					}
				}

							
			} catch (Exception e) {
				logger.error("菜单配置文件编写错误["+resource.getURL()+"]",e);
			}
		}
		return menuList;
	}

	private String initParentPath(Menu menu, String path) {
		Menu parent = menu.getParent();
		if (parent == null) {
			if (menu.getPid() != null) {
				parent = this.menuService.getById(menu.getPid());
			}
		}

		if (parent == null) {
			return path;
		} else {
			path = parent.getName() + ">" + path;
			return initParentPath(parent, path);
		}
	}

	public List<MenuXml> getSortMenuList() throws Exception {
		Map<String, MenuXml> menuXmls = getInitMenuMap(true);
		List<MenuXml> menuXmlList = new ArrayList<MenuXml>();
		List<MenuXml> topXmls = new ArrayList<MenuXml>();
		for (MenuXml menuXml : menuXmls.values()) {
			if (menuXml.getParentCode() == null || menuXmls.get(menuXml.getParentCode()) == null) {
				topXmls.add(menuXml);
			}
		}

		for (MenuXml menuXml : topXmls) {
			addSortMenuXml(menuXml, menuXmlList, menuXmls);
		}
		return menuXmlList;
	}

	private void addSortMenuXml(MenuXml menuXml, List<MenuXml> menuXmlList, Map<String, MenuXml> menuXmls) {
		menuXmlList.add(menuXml);
		for (MenuXml cmenu : getMenuChilds(menuXml, menuXmls.values())) {
			addSortMenuXml(cmenu, menuXmlList, menuXmls);
		}

	}

	private List<MenuXml> getMenuChilds(MenuXml menuXml, Collection<MenuXml> allMenus) {
		List<MenuXml> xmls = new ArrayList<MenuXml>();
		for (MenuXml menu : allMenus) {
			if (menu.getParentCode().equals(menuXml.getCode())) {
				xmls.add(menu);
			}
		}
		return xmls;
	}

	public Map<String, MenuXml> getInitMenuMap(boolean reload) throws Exception {
		if (!reload && menuMap != null) {
			return menuMap;
		} else {
			menuMap = new LinkedHashMap<String, MenuXml>();
		}
		List<MenuXml> menuList = getInitMenuList();
		for (MenuXml menu : menuList) {
			menuMap.put(menu.getCode(), menu);
		}
		for (MenuXml menu : menuList) {
			Menu parent = this.menuService.getByCode(menu.getParentCode());
			if (parent == null) {
				parent = menuMap.get(menu.getParentCode());
			}
			if (parent != null) {
				menu.setPid(parent.getId());
				menu.setParent(parent);
			}

		}

		for (MenuXml menuXml : menuList) {
			menuXml.setParentPath(initParentPath(menuXml, ""));
		}
		return menuMap;
	}

	// @PostConstruct
	public void initMenu() throws Exception {
		logger.debug("load menus............");
		List<MenuXml> menus = getInitMenuList();
		Set<Long> addMenuIds = new HashSet<Long>();
		for (Menu menu : menus) {
			menuService.save(menu);
			addMenuIds.add(menu.getId());
		}

		if (addMenuIds.size() == 0) {
			return;
		}

		Role role = roleService.getByCode("ROLE_ADMIN");
		List<Menu> assignedMenus = this.menuService.findByRole(role);
		Set<Long> assignedMenuIds = new HashSet<Long>(assignedMenus.size());
		if (!assignedMenus.isEmpty()) {
			for (Menu m : assignedMenus) {
				assignedMenuIds.add(m.getId());
			}
		}
		assignedMenuIds.addAll(addMenuIds);
		List<Operate> operates = this.operateService.findbyMenuIds(addMenuIds);
		List<Operate> assignedOperates = this.operateService.findByRole(role);
		Set<Long> assignedOperateIds = new HashSet<Long>(assignedOperates.size());
		if (!assignedOperates.isEmpty()) {
			for (Operate op : assignedOperates) {
				assignedOperateIds.add(op.getId());
			}
		}
		for (Operate operate : operates) {
			assignedOperateIds.add(operate.getId());
		}
		roleService.assign(role, assignedMenuIds.toArray(new Long[assignedMenuIds.size()]), assignedOperateIds.toArray(new Long[assignedOperateIds.size()]));
	}

	@Override
	public List<RoleXml> getInitRoleList() throws Exception {
		List<RoleXml> roleList = new ArrayList<RoleXml>();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:role.xml");

		RoleXmlList roles = null;
		for (Resource resource : resources) {
			roles = XmlUtil.converyToJavaBean(resource.getInputStream(), RoleXmlList.class);
			roleList.addAll(roles.getAll());
		}
		for (RoleXml roleXml : roleList) {
			Role role = roleService.getByCode(roleXml.getCode());
			if (role != null)
				roleXml.setExist(true);
			else
				roleXml.setExist(false);
		}
		return roleList;
	}

	@Override
	public Map<String, RoleXml> getInitRoleMap() throws Exception {
		Map<String, RoleXml> roleMap = new HashMap<String, RoleXml>();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:role.xml");
		RoleXmlList roles = null;
		for (Resource resource : resources) {
			roles = XmlUtil.converyToJavaBean(resource.getInputStream(), RoleXmlList.class);
			if (roles == null) {
				continue;
			}
			for (RoleXml roleXml : roles.getAll()) {
				roleMap.put(roleXml.getCode(), roleXml);
			}
		}

		return roleMap;
	}

	@Transactional
	public void saveImportMenus(String[] menuCodes, String[] operateCodes) throws Exception {
		Map<String, MenuXml> menuMap;
		menuMap = getInitMenuMap(true);
		List<String> operateCodeList;
		if (operateCodes != null) {
			operateCodeList = Arrays.asList(operateCodes);
		} else {
			operateCodeList = Collections.emptyList();
		}
		for (String menuCode : menuCodes) {
			MenuXml menuXml = menuMap.get(menuCode);
			save(menuXml, menuMap);
			if (menuXml.getOperates() != null && menuXml.getOperates().size() > 0) {
				for (Operate operate : menuXml.getOperates().getAll()) {
					if (operateCodeList.contains(menuCode + "." + operate.getCode())) {
						operate.setMenuId(menuXml.getId());
						operateService.save(operate);
					}
				}
			}
		}
	}

	private void save(MenuXml menuXml, Map<String, MenuXml> menuMap) {
		Menu parent = menuService.getByCode(menuXml.getParentCode());
		Menu old = menuService.getByCode(menuXml.getCode());
		if (parent == null) {
			parent = menuMap.get(menuXml.getParentCode());
			if (parent == null) {
				throw new MenuException("无法找到父菜单:" + menuXml.getParentCode());
			}
			save((MenuXml)parent, menuMap);
		}
		menuXml.setPid(parent.getId());
		menuXml.setParent(parent);
		if (old != null) {
			menuXml.setLft(old.getLft());
			menuXml.setRgt(old.getRgt());
			menuService.update(menuXml);
		}else{
			menuService.save(menuXml);
		}
	}
}
