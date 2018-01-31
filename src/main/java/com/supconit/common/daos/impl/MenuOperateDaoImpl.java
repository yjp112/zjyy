package com.supconit.common.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.common.daos.MenuOperateDao;
import com.supconit.common.domains.MenuOperate;
import com.supconit.honeycomb.business.authorization.entities.Menu;


@Repository
public class MenuOperateDaoImpl extends AbstractBaseDao<MenuOperate, Long> implements MenuOperateDao {
	private static final String	NAMESPACE = MenuOperate.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<MenuOperate> selectAllMenuOperate() {
		return selectList("selectAllMenuOperate");
	}
	@Override
	public List<MenuOperate> selectTreePath(int level, Menu menu) {
		MenuOperate menuOperate=new MenuOperate();
		menuOperate.setMlvl(level);
		menuOperate.setMlft(menu.getLft());
		menuOperate.setMrgt(menu.getRgt());
		return selectList("selectTreePath",menuOperate);
	}
	
	

}
