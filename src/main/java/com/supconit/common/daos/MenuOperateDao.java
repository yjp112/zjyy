package com.supconit.common.daos;
import java.util.List;

import com.supconit.common.domains.MenuOperate;
import com.supconit.honeycomb.business.authorization.entities.Menu;

public interface MenuOperateDao  extends BaseDao<MenuOperate, Long>{
	public List<MenuOperate> selectAllMenuOperate();
	/**查询menu的完整路径(只保留大于 level 的节点)
	 * @param level
	 * @param menu
	 * @return
	 */
	public List<MenuOperate> selectTreePath(int level,Menu menu);
} 