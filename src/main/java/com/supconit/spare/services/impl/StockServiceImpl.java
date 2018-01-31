package com.supconit.spare.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.honeycomb.util.StringUtils;
import com.supconit.spare.daos.SpareCategoryDao;
import com.supconit.spare.daos.StockDao;
import com.supconit.spare.entities.Stock;
import com.supconit.spare.services.StockService;

import hc.base.domains.Pageable;

@Service
public class StockServiceImpl extends AbstractBaseBusinessService<Stock, Long>
                implements StockService {

    @Autowired
    private StockDao stockDao;
    @Resource
    private SpareCategoryDao        spareCategoryDao;   
    private static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
    /**
     * Get stock by ID
     * 
     * @param id
     *            stock id
     * @return object instance
     */
    @Override
    @Transactional(readOnly = true)
    public Stock getById(Long id) {
        if (null == id || id <= 0)
            return null;
        Stock stock = stockDao.getById(id);

        return stock;
    }

    /**
     * Find Stock list by condition
     * 
     * @param pageNo
     *            page number
     * @param pageSize
     *            page size
     * @param condition
     *            Query condition
     * @return
     */
    @Override
    @Transactional(readOnly = true)
	public Pageable<Stock> findByCondition(Pageable<Stock> pager,
			Stock condition) {
    	 if(condition.getCategoryId()!=null){
             List<Long> ids= spareCategoryDao.selecChilrenCategorieIds(condition.getCategoryId());
             condition.setCategoryId(null);
             condition.setCategoryIds(ids);
           }
         if(StringUtils.isBlank(condition.getOrderField())){
         	condition.setOrderField("t.ID DESC");
         }
         System.out.println("====================================");
         System.out.println(condition.getOrderField());
         System.out.println("====================================");
		return stockDao.findByPage(pager, condition);
	}
    @Override
	public Pageable<Stock> selectStockWaring(Pageable<Stock> pager,
			Long spareCategoryId) {
    	 List<Long> categoryIds=new ArrayList<Long>();
         if(spareCategoryId!=null){
             categoryIds= spareCategoryDao.selecChilrenCategorieIds(spareCategoryId);
         }
		return stockDao.selectStockWaring(pager, categoryIds);
	}

    @Override
	public Pageable<Stock> selectWarehouseWaring(Pageable<Stock> pager,
			Stock condition) {
    	List<Long> sarehouseIds = stockDao.selectWarehouseId();
		return stockDao.selectWarehouseWaring(pager, sarehouseIds);
	}
    /**
     * update Stock
     * 
     * @param stock
     *            instance
     * @return
     */
    @Override
    @Transactional
    public void update(Stock stock) {
        if (!isAllowSave(stock))
            throw new NullPointerException("stock  ?????");

        stockDao.update(stock);
    }

    /**
     * insert Stock
     * 
     * @param stock
     *            instance
     * @return
     */
    @Override
    @Transactional
    public void insert(Stock stock) {

        if (!isAllowSave(stock))
            throw new NullPointerException("stock  ?????");

        stockDao.insert(stock);
    }

    // Check that allows you to save
    private boolean isAllowSave(Stock stock) {
        return true;
    }

    @Override
    public void inCreaseStock(Long wareHouseId, Long spareId, Integer qty,
    		Integer availableQty, Integer backupQty,
    		Integer freezeQty) {
        BigDecimal.ZERO.abs();
        Stock stock=new Stock();
        stock.setWarehouseId(wareHouseId);
        stock.setSpareId(spareId);
        stock.setQty(qty);// add现存量
        stock.setAvailableQty(availableQty);// add可用量
        stock.setBackupQty(backupQty);// add 备料量
        stock.setFreezeQty(freezeQty);// add 冻结量
        int count=stockDao.inCreaseStock(stock);
        if (count != 1) {
            logger.error("增加库存失败。[wareHouseId="
                            + wareHouseId + " spareId=" + spareId + " qty="
                            + spareId + "]");
            throw new BusinessDoneException("入库失败。");
        }
    }

    @Override
    public void reduceStock(Long wareHouseId, Long spareId, Integer qty,
    		Integer availableQty, Integer backupQty,
                    Integer freezeQty) {
        Stock stock=new Stock();
        stock.setWarehouseId(wareHouseId);
        stock.setSpareId(spareId);
        stock.setQty(qty);// reduce现存量
        stock.setAvailableQty(availableQty);// reduce可用量
        stock.setBackupQty(backupQty);// reduce 备料量
        stock.setFreezeQty(freezeQty);// reduce 冻结量
        int count = stockDao.reduceStock(stock);
        if (count != 1) {
            logger.error("减少库存失败。[wareHouseId="
                            + wareHouseId + " spareId=" + spareId + " qty="
                            + spareId + "]");
            throw new BusinessDoneException("出库失败。");
        }

    }

    @Override
    public void deleteById(Long id) {
        throw new BusinessDoneException("库存不允许直接删除。");

    }
    
    private BigDecimal nvl(BigDecimal decimal){
        if(decimal==null){
            return new BigDecimal(0);
        }else{
            return decimal;
        }
    }

    @Override
    public List<Stock> selectBySpareIds(Long warehouseId,List<Long> spareIds) {
        return stockDao.selectBySpareIds(warehouseId,spareIds);
    }

    @Override
    public List<Stock> selectByWarehouseId(Long warehouseId) {
        return stockDao.selectByWarehouseId(warehouseId);
    }


	@Override
	public Pageable<Stock> findCostsQtyByCondition(Pageable<Stock> pager,
			Stock condition) {
		 if(condition.getCategoryId()!=null){
	            List<Long> ids= spareCategoryDao.selecChilrenCategorieIds(condition.getCategoryId());
	            condition.setCategoryId(null);
	            condition.setCategoryIds(ids);
	          }
		return stockDao.findCostsQtyByCondition(pager, condition);
	}

	

	

	
	

}
