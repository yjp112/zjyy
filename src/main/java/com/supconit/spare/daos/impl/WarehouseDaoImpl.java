
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.WarehouseDao;
import com.supconit.spare.entities.Warehouse;

import hc.base.domains.Pageable;



@Repository
public class WarehouseDaoImpl extends AbstractBaseDao<Warehouse, Long> implements WarehouseDao {

    private static final String	NAMESPACE	= Warehouse.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Warehouse> findByPage(Pageable<Warehouse> pager,
			Warehouse condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public Warehouse findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
    public List<Warehouse> findAll() {
        return selectList("findAll");
    }
    
    @Override
	public Long countRecordByCode(String code) {
    	Warehouse condition=new Warehouse();
        condition.setWarehouseCode(code);
        return getSqlSession().selectOne(Warehouse.class.getName()+".countByConditions", condition);
	}

	@Override
	public List<Warehouse> findByCode(String warehouseCode, String warehouseName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseCode", warehouseCode);
		map.put("warehouseName", warehouseName);
		return selectList("findByCode",map);
	}
}