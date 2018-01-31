
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.SpareCategoryDao;
import com.supconit.spare.entities.SpareCategory;

import hc.base.domains.Pageable;



@Repository
public class SpareCategoryDaoImpl extends AbstractBaseDao<SpareCategory, Long> implements SpareCategoryDao {

    private static final String	NAMESPACE	= SpareCategory.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	
	@Override
	public Pageable<SpareCategory> findByPage(Pageable<SpareCategory> pager,
			SpareCategory condition) {
		if(condition.getId()!=null){
	        List<Long> ids=getSqlSession().selectList("selectIds", condition);
	        if(ids!=null && ids.size()>0){
	        	SpareCategory ST = new SpareCategory();
	        	ST.setIds(ids);
	        	return findByPager(pager,"selectByIds", "countByIds", ST);
	        }else{
	        	   return null;
	        	}
	    }else{
	    	return findByPager(pager,"findByCondition", "countByCondition", condition);
	    }
		
	}



	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public SpareCategory findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
    public List<SpareCategory> selecCategories() {
        return selectList("findByCategories");
    }

    @Override
    public List<Long> selecChilrenCategorieIds(Long id) {
        SpareCategory condition=new SpareCategory();
        condition.setId(id);
        return getSqlSession().selectList(SpareCategory.class.getName()+".selectIds", condition);
    }
    @Override
    public Long countSpareCategoryIdUsed(Long spareCategoryId) {
        return getSqlSession().selectOne(SpareCategory.class.getName()+".countSpareCategoryIdUsed", spareCategoryId);
    }

    @Override
    public Long countRecordByCategoryCode(String spareCategoryCode) {
        SpareCategory condition=new SpareCategory();
        condition.setCategoryCode(spareCategoryCode);
        return getSqlSession().selectOne(SpareCategory.class.getName()+".countByCategoryCode", condition);
    }

    @Override
    public List<SpareCategory> selectCategoriesByFilter(Long id)
    {
        // TODO Auto-generated method stub
        return selectList("findByFilter", id);
    }


	@Override
	public List<SpareCategory> findByCode(String spareCategoryCode) {
		// TODO Auto-generated method stub
		return selectList("findByCode", spareCategoryCode);
	}

	/** 手机端使用 **/
	@Override
	public List<SpareCategory> findByParentId(long parentId) {
		return selectList("findByParentId", parentId);
	}
}