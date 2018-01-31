package com.supconit.spare.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.spare.entities.Stock;

import hc.base.domains.Pageable;


public interface StockService extends BaseBusinessService<Stock, Long> {

    /**
     * Query by condition
     * 
     * @param pageNo
     *            page number
     * @param pageSize
     *            page size
     * @param condition
     *            Query condition
     * @return
     */
    Pageable<Stock> findByCondition(Pageable<Stock> pager,Stock condition);
	
    /** 
     *@方法名称:findCostsQtyByCondition
     *@作    者:丁阳光
     *@创建日期:2013年9月12日
     *@方法描述:  报表统计 备件消耗
     * @param pageNo
     * @param pageSize
     * @param condition
     * @return Pager<Stock>
     */
    Pageable<Stock> findCostsQtyByCondition(Pageable<Stock> pager,Stock condition);
	
    /**
     * @方法名称:selectStockWaring
     * @作 者:丁阳光
     * @创建日期:2013年7月11日
     * @方法描述: 查询备件库存预警信息
     * @param pageNo
     * @param pageSize
     * @param spareCategoryId
     *            备件类别
     * @return Pager<Stock>
     */
    Pageable<Stock> selectStockWaring(Pageable<Stock> pager, Long spareCategoryId);
	
    /**
     * @方法名称:selectWarehouseWaring
     * @作 者:丁阳光
     * @创建日期:2013年7月11日
     * @方法描述:查询仓库库存预警信息
     * @param pageNo
     * @param pageSize
     * @return Pager<Stock>
     */
    Pageable<Stock> selectWarehouseWaring(Pageable<Stock> pager,Stock condition);
	
    /** 
     *@方法名称:inCreaseStock
     *@作    者:丁阳光
     *@创建日期:2013年7月17日
     *@方法描述:  给指定的仓库新增库存 
     * @param warehouseId 仓库ID
     * @param spareId 备件ID
     * @param qty 现存量
     * @param availableQty 可用量
     * @param backupQty 备料量
     * @param freezeQty 冻结量
     */
    void inCreaseStock(Long warehouseId, Long spareId, Integer qty,
    		Integer availableQty, Integer backupQty,
    		Integer freezeQty);

    /** 
     *@方法名称:reduceStock
     *@作    者:丁阳光
     *@创建日期:2013年7月17日
     *@方法描述: 给指定的仓库减少库存 
     * @param warehouseId 仓库ID
     * @param spareId 备件ID
     * @param qty 现存量
     * @param availableQty 可用量
     * @param backupQty 备料量
     * @param freezeQty 冻结量
     */
    void reduceStock(Long warehouseId, Long spareId, Integer qty,
    		Integer availableQty, Integer backupQty,
    		Integer freezeQty);
    /** 
     *@方法名称:selectBySpareIds
     *@作    者:丁阳光
     *@创建日期:2013年7月29日
     *@方法描述:  
     * @param warehouseId
     * @param spareIds
     * @return List<Stock>
     */
    public List<Stock> selectBySpareIds(Long warehouseId,List<Long> spareIds);
    
    public List<Stock> selectByWarehouseId(Long warehouseId);
}
