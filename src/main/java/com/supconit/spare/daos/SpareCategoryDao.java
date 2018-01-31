
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.SpareCategory;

import hc.base.domains.Pageable;

public interface SpareCategoryDao extends BaseDao<SpareCategory, Long>{
	
	public Pageable<SpareCategory> findByPage(Pageable<SpareCategory> pager,SpareCategory condition);
	
	List<SpareCategory> selecCategories();
	
	List<SpareCategory> selectCategoriesByFilter(Long id);

	List<Long> selecChilrenCategorieIds(Long id);

	int deleteByIds(Long[] ids);
    
    public  SpareCategory findById(Long id); 
    
    

    Long countSpareCategoryIdUsed(Long spareCategoryId);
    
    Long countRecordByCategoryCode(String spareCategoryCode);

	public List<SpareCategory> findByCode(String spareCategoryCode);

	/** 手机端使用 **/
	List<SpareCategory> findByParentId(long parentId);
}
