
package com.supconit.spare.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.SpareCategory;

import hc.base.domains.Pageable;




public interface SpareCategoryService extends BaseBusinessService<SpareCategory, Long> {	

	/**
	 * Query by condition
	 * @param condition Query condition
	 * @return
	 */
	Pageable<SpareCategory> findByCondition(Pageable<SpareCategory> pager,SpareCategory condition);
	

	/** 
	 *@方法名称:selectCategories
	 *@作    者:丁阳光
	 *@创建日期:2013年7月12日
	 *@方法描述:  
	 * @return List<SpareCategory>
	 */
	List<SpareCategory> selectCategories();
	
	/**
	 * 
	 *@方法描述：过滤条件的查询方法
	 *@方法名：selectCategoriesByFilter
	 *@参数：@param filter
	 *@参数：@return
	 *@返回：List<SpareCategory>
	 *@exception 
	 *@since
	 */
	List<SpareCategory> selectCategoriesByFilter(Long id);

   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

	/** 手机端使用 **/
	List<SpareCategory> findByParentId(long parentId);
}

