package com.supconit.spare.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.exceptions.DeleteDenyException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.BillStatusEnum;
import com.supconit.common.utils.ListUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.spare.daos.StockOutDao;
import com.supconit.spare.daos.StockOutDetailDao;
import com.supconit.spare.entities.Stock;
import com.supconit.spare.entities.StockOut;
import com.supconit.spare.entities.StockOutDetail;
import com.supconit.spare.services.StockOutService;
import com.supconit.spare.services.StockService;

import hc.base.domains.Pageable;
import hc.bpm.context.annotations.BpmSupport;


@Service
public class StockOutServiceImpl extends
AbstractBaseBusinessService<StockOut, Long> implements
                StockOutService {

    @Autowired
    private StockOutDao stockOutDao;
    @Autowired
    private StockOutDetailDao stockOutDetailDao;
    @Resource
    private StockService stockService;

    private static final int batchMaxSize = 100;

    /**
     * Get stockOut by ID
     * 
     * @param id
     *            stockOut id
     * @return object instance
     */
    @Override
    @Transactional(readOnly = true)
    public StockOut getById(Long id) {
        if (null == id || id <= 0)
            return null;
        StockOut stockOut = stockOutDao.getById(id);

        return stockOut;
    }

    /**
     * delete StockOut by ID
     * 
     * @param id
     *            stockOut ID
     * @return
     */
    @Override
    @Transactional
    public void deleteById(Long id) {

        checkDelete(id);
        stockOutDao.deleteById(id);
        stockOutDetailDao.deleteByStockOutId(id);
    }

    /**
     * delete StockOut by ID array
     * 
     * @param ids
     *            stockOut ID array
     * @return
     */
    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {

        for (int i = 0; i < ids.length; i++) {
            checkDelete(ids[i]);
            stockOutDao.deleteById(ids[i]);
            stockOutDetailDao.deleteByStockOutId(ids[i]);

        }

    }

    /**
     * Find StockOut list by condition
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
	public Pageable<StockOut> findByCondition(Pageable<StockOut> pager,
			StockOut condition) {
		return stockOutDao.findByPage(pager, condition);
	}
    /**
     * update StockOut
     * 
     * @param stockOut
     *            instance
     * @return
     */
    @Override
    @Transactional
    public void update(StockOut stockOut) {

        checkToSave(stockOut);

        List<StockOutDetail> stockOutDetailList = stockOut
                        .getStockOutDetailList();

        // delete Detail DataTable
        stockOutDetailDao.deleteByStockOutId(stockOut.getId());

        // update master datatable
        stockOutDao.update(stockOut);

        // Save detail datatable
        StockOutDetail stockOutDetail;
        Iterator<StockOutDetail> iter = stockOutDetailList.iterator();
        while (iter.hasNext()) {
            stockOutDetail = iter.next();
            if (stockOutDetail == null||stockOutDetail.getSpareId()==null) {
                iter.remove();
            }
            stockOutDetail.setStockOutId(stockOut.getId());
        }

        // update detail datatable
        for (List<StockOutDetail> list : ListUtils.partition(
                        stockOutDetailList, batchMaxSize)) {
            stockOutDetailDao.insert(list);
        }

    }

    /**
     * insert StockOut
     * 
     * @param stockOut
     *            instance
     * @return
     */
    @Override
    @Transactional
    public void insert(StockOut stockOut) {
        checkToSave(stockOut);
        stockOut.setStockOutCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.STOCK_OUT));
        stockOut.setStatus(BillStatusEnum.SAVED.getValue());
        StockOutDetail stockOutDetail;
        List<StockOutDetail> stockOutDetailList = stockOut
                        .getStockOutDetailList();

        // save master datatable
        stockOutDao.insert(stockOut);

        // Save detail datatable
        Iterator<StockOutDetail> iter = stockOutDetailList.iterator();
        while (iter.hasNext()) {
            stockOutDetail = iter.next();
            if (stockOutDetail == null||stockOutDetail.getSpareId()==null) {
                iter.remove();
            }
            stockOutDetail.setStockOutId(stockOut.getId());
        }

        // update detail datatable
        for (List<StockOutDetail> list : ListUtils.partition(
                        stockOutDetailList, batchMaxSize)) {
            stockOutDetailDao.insert(list);
        }
    }

    // Check that allows you to delete
    private void checkDelete(Long id) {
        StockOut stockOut = stockOutDao.getById(id);
        if (stockOut.getStatus() != BillStatusEnum.SAVED.getValue()) {
            throw new DeleteDenyException("出库单[" + stockOut.getStockOutCode()
                            + "]状态为[" + BillStatusEnum.SAVED.getDesc()
                            + "],不允许删除。");
        }
    }

    // Check that allows you to save
    private void checkToSave(StockOut stockOut) {
        List<StockOutDetail> details=stockOut.getStockOutDetailList();
        Iterator<StockOutDetail> iter = details.iterator();
        StockOutDetail tmp=null;
        while (iter.hasNext()) {
            tmp = iter.next();
            if (tmp == null||tmp.getSpareId()==null) {
                iter.remove();
                continue;
            }
        }
        if(details==null||details.size()<=0){
            throw new BusinessDoneException("出库单中至少要有一条备件领用的记录。");
        }
        Map<Long, StockOutDetail> map=new HashMap<Long, StockOutDetail>();
        for (StockOutDetail stockOutDetail : details) {
            Long spareId=stockOutDetail.getSpareId();
            if(map.containsKey(spareId)){
                throw new BusinessDoneException("出库单中不能出现多条具有相同备件的记录。");
            }
            map.put(spareId, stockOutDetail);
        }
        List<Long> spareIds=new ArrayList<Long>();
        spareIds.addAll(map.keySet());
        List<Stock> stocks=stockService.selectBySpareIds(stockOut.getWarehouseId(),spareIds);
        if (spareIds.size() != stocks.size()) {
            Transformer transformer = new BeanToPropertyValueTransformer("spareId");
            @SuppressWarnings("unchecked")
            Collection<Long> notFoundList = CollectionUtils.subtract(spareIds,
                            CollectionUtils.collect(stocks, transformer));
            List<String> tmpList=new ArrayList<String>();
            for (Long id : notFoundList) {
                StockOutDetail detail = map.get(id);
                tmpList.add(detail.getSpareName());
            }
            throw new BusinessDoneException("仓库中没有下列备件："+tmpList.toString());
        }
        for (Stock stock : stocks) {
            Long spareId=stock.getSpareId();
            StockOutDetail detail=map.get(spareId);
            if(stock.getAvailableQty()-detail.getQty()<0){
                throw new BusinessDoneException("备件["+detail.getSpareName()+"]可用库存不足。");
            }
            if(detail.getQty()<=0){
                throw new BusinessDoneException("备件["+detail.getSpareName()+"]领用数量必须大于0。"); 
            }
            detail.setAvailableQty(stock.getAvailableQty());
        }
    }

    @Override
    public StockOut findById(Long id) {
        return stockOutDao.findById(id);
    }

    @Override
    public void takeEffect(Long stockOutId) {
        // 1.检查单据是否已经生效，如果已经生效，则程序终止
        StockOut stockOut = stockOutDao.findById(stockOutId);
        for (StockOutDetail detail : stockOut.getStockOutDetailList()) {
            stockService.reduceStock(stockOut.getWarehouseId(),
                            detail.getSpareId(), detail.getQty(),
                            detail.getQty(), null, null);
        }
        stockOut.setStatus(BillStatusEnum.PASS.getValue());
        stockOutDao.updateSelective(stockOut);

    }

	

	
    
}
