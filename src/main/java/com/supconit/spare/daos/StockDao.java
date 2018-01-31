
package com.supconit.spare.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.Stock;

import hc.base.domains.Pageable;



public interface StockDao extends BaseDao<Stock, Long>{
	
    
    public Pageable<Stock> findByPage(Pageable<Stock> pager,Stock condition);
   
    public Pageable<Stock> findCostsQtyByCondition(Pageable<Stock> pager,Stock condition);

	int deleteByIds(Long[] ids);
    
    public  Stock findById(Long id); 
    
    /** 
     *@ćšćłĺç§°:inCreaseStock
     *@ä˝?   č?ä¸éłĺ?     *@ĺĺťşćĽć:2013ĺš?ć?1ć?     *@ćšćłćčż°:  ĺ˘ĺ ĺşĺ­
     * @param stock
     * @return int
     */
    int inCreaseStock(Stock stock);
    /** 
     *@ćšćłĺç§°:reduceStock
     *@ä˝?   č?ä¸éłĺ?     *@ĺĺťşćĽć:2013ĺš?ć?1ć?     *@ćšćłćčż°:  ĺĺ°ĺşĺ­
     * @param stock
     * @return int
     */
    int reduceStock(Stock stock);
    /** 
     *@ćšćłĺç§°:selectStockWaring
     *@ä˝?   č?ä¸éłĺ?     *@ĺĺťşćĽć:2013ĺš?ć?1ć?     *@ćšćłćčż°: ćĽčŻ˘ĺ¤äťśĺşĺ­é˘č­ŚäżĄćŻ 
     * @param pageNo
     * @param pageSize
     * @param spareCategoryIds
     * @return Pager<Stock>
     */
    public Pageable<Stock> selectStockWaring(Pageable<Stock> pager,List<Long> spareCategoryIds);

    /** 
     *@ćšćłĺç§°:selectWarehouseWaring
     *@ä˝?   č?ä¸éłĺ?     *@ĺĺťşćĽć:2013ĺš?ć?1ć?     *@ćšćłćčż°: ćĽčŻ˘äťĺşĺşĺ­é˘č­ŚäżĄćŻ  
     * @param pageNo
     * @param pageSize
     * @return Pager<Stock>
     */
    public Pageable<Stock> selectWarehouseWaring(Pageable<Stock> pager,List<Long> warhouseIds);

    /** 
     *@ćšćłĺç§°:selectWarehouseId
     *@ä˝?   č?ä¸éłĺ?     *@ĺĺťşćĽć:2013ĺš?ć?1ć?     *@ćšćłćčż°: ćĽćžćťĄčśłčżć ˇćĄäťśçäťĺşIDďźäťĺşä¸­çĺ¤äťśé˝ć˛ĄćčŽžç˝ŽĺŽĺ¨ĺşĺ­ďźćä˝ĺşĺ­ďźć?Ťĺşĺ­ 
     * @return List<Long>
     */
    public List<Long> selectWarehouseId();
    
    public List<Stock> selectBySpareIds(Long warehouseId,List<Long> spareIds);
    public List<Stock> selectByWarehouseId(Long warehouseId);
}
