
package com.supconit.spare.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.spare.daos.StockDao;
import com.supconit.spare.entities.Stock;

import hc.base.domains.Pageable;



@Repository
public class StockDaoImpl extends AbstractBaseDao<Stock, Long> implements StockDao {

    private static final String	NAMESPACE	= Stock.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Stock> findByPage(Pageable<Stock> pager,
			Stock condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    
    @Override
	public Stock findById(Long id) {
		return selectOne("findById",id);
	}

    @Override
	public Pageable<Stock> selectStockWaring(Pageable<Stock> pager,
			 List<Long> spareCategoryIds) {
    	Stock condition = new Stock();
    	condition.setSpareCategoryIds(spareCategoryIds);
		return findByPager(pager, "selectStockWaring", "countStockWaring",condition);
	}
    
    @Override
	public Pageable<Stock> selectWarehouseWaring(Pageable<Stock> pager,
			 List<Long> warhouseIds) {
    	  if(warhouseIds==null||warhouseIds.size()<=0){
              return findByPager(pager, "selectWarehouseWaring", "countWarehouseWaring",null);
          	}
    	  Stock condition = new Stock();
      	condition.setWarhouseIds(warhouseIds);
		return findByPager(pager, "selectWarehouseWaring", "countWarehouseWaring",condition);
	}
   

    @Override
    public List<Long> selectWarehouseId() {
        return getSqlSession().selectList("selectWarehouseId");
    }

    @Override
    public int inCreaseStock(Stock stock) {
    	CheckStock(stock);
        return update("inCreaseStock", stock);
    }

    @Override
    public int reduceStock(Stock stock) {
        return update("reduceStock", stock);
    }

    @Override
    public List<Stock> selectBySpareIds(Long warehouseId,List<Long> spareIds) {
        Stock condition=new Stock();
        condition.setWarehouseId(warehouseId);
        condition.setSpareIds(spareIds);
        return selectList("selectBySpareIds", condition);
    }

    @Override
    public List<Stock> selectByWarehouseId(Long warehouseId) {
        return selectList("selectByWarehouseId", warehouseId);
    }


    @Override
	public Pageable<Stock> findCostsQtyByCondition(Pageable<Stock> pager,
			Stock condition) {
		return findByPager(pager, "findByConditions", "countByConditions", condition);
	}
    
    public void CheckStock(Stock stock){
    	if(stock.getQty() == null){
    		stock.setQty(0);
    	}
    	if(stock.getAvailableQty() == null){
    		stock.setAvailableQty(0);
    	}
    	if(stock.getBackupQty() == null){
    		stock.setBackupQty(0);
    	}
    	if(stock.getFreezeQty() == null){
    		stock.setFreezeQty(0);
    	}
    }
}