package com.supconit.spare.services.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.supconit.spare.daos.StockInDao;
import com.supconit.spare.daos.StockInDetailDao;
import com.supconit.spare.entities.StockIn;
import com.supconit.spare.entities.StockInDetail;
import com.supconit.spare.services.StockInService;
import com.supconit.spare.services.StockService;

import hc.base.domains.Pageable;
import hc.bpm.context.annotations.BpmSupport;


@Service
public class StockInServiceImpl extends
AbstractBaseBusinessService<StockIn, Long> implements
                StockInService {

    @Autowired
    private StockInDao stockInDao;
    @Autowired
    private StockInDetailDao stockInDetailDao;
    @Resource
    private StockService stockService;

    private static final int batchMaxSize = 100;

    /**
     * Get stockIn by ID
     * 
     * @param id
     *            stockIn id
     * @return object instance
     */
    @Override
    @Transactional(readOnly = true)
    public StockIn getById(Long id) {
        if (null == id || id <= 0)
            return null;
        StockIn stockIn = stockInDao.getById(id);

        return stockIn;
    }

    /**
     * delete StockIn by ID
     * 
     * @param id
     *            stockIn ID
     * @return
     */
    @Override
    @Transactional
    public void deleteById(Long id) {

        checkDelete(id);

        stockInDao.deleteById(id);
        stockInDetailDao.deleteByStockInId(id);
    }

    /**
     * delete StockIn by ID array
     * 
     * @param ids
     *            stockIn ID array
     * @return
     */
    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {

        for (int i = 0; i < ids.length; i++) {
            checkDelete(ids[i]);

            stockInDao.deleteById(ids[i]);
            stockInDetailDao.deleteByStockInId(ids[i]);

        }

    }

    /**
     * Find StockIn list by condition
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
	public Pageable<StockIn> findByCondition(Pageable<StockIn> pager,
			StockIn condition) {
		return stockInDao.findByPage(pager, condition);
	}
    /**
     * update StockIn
     * 
     * @param stockIn
     *            instance
     * @return
     */
    @Override
    @Transactional
    public void update(StockIn stockIn) {

        checkToSave(stockIn);

        List<StockInDetail> stockInDetailList = stockIn.getStockInDetailList();

        // delete Detail DataTable
        stockInDetailDao.deleteByStockInId(stockIn.getId());

        // update master datatable
        stockInDao.update(stockIn);

        // Save detail datatable
        StockInDetail stockInDetail;
        Iterator<StockInDetail> iter = stockInDetailList.iterator();
        while (iter.hasNext()) {
            stockInDetail = iter.next();
            if (stockInDetail == null||stockInDetail.getSpareId()==null) {
                iter.remove();
            }
            stockInDetail.setStockInId(stockIn.getId());
        }

        // update detail datatable
        for (List<StockInDetail> list : ListUtils.partition(stockInDetailList,
                        batchMaxSize)) {
            stockInDetailDao.insert(list);
        }

    }

    /**
     * insert StockIn
     * 
     * @param stockIn
     *            instance
     * @return
     */
    @Override
    @Transactional
    public void insert(StockIn stockIn) {

        checkToSave(stockIn);
        stockIn.setStockInCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.STOCK_IN));
        stockIn.setProcessInstanceName("入库单："+stockIn.getStockInCode());
        stockIn.setStatus(BillStatusEnum.SAVED.getValue());
        StockInDetail stockInDetail;
        List<StockInDetail> stockInDetailList = stockIn.getStockInDetailList();

        // save master datatable
        stockInDao.insert(stockIn);

        // Save detail datatable
        Iterator<StockInDetail> iter = stockInDetailList.iterator();
        while (iter.hasNext()) {
            stockInDetail = iter.next();
            if (stockInDetail == null||stockInDetail.getSpareId()==null) {
                iter.remove();
            }
            stockInDetail.setStockInId(stockIn.getId());
        }

        // update detail datatable
        for (List<StockInDetail> list : ListUtils.partition(stockInDetailList,
                        batchMaxSize)) {
        	try{
            stockInDetailDao.insert(list);
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
    }

    // Check that allows you to delete
    private void checkDelete(Long id) {
        StockIn stockIn = stockInDao.getById(id);
        if (stockIn.getStatus() != BillStatusEnum.SAVED.getValue()) {
            throw new DeleteDenyException("入库单[" + stockIn.getStockInCode()
                            + "]状态为[" + BillStatusEnum.SAVED.getDesc()
                            + "],不允许删除。");
        }
    }

    // Check that allows you to save
    private void checkToSave(StockIn stockIn) {
        StockInDetail tmp;
        List<StockInDetail> stockInDetailList = stockIn.getStockInDetailList();
        Iterator<StockInDetail> iter = stockInDetailList.iterator();
        while (iter.hasNext()) {
            tmp = iter.next();
            if (tmp == null||tmp.getSpareId()==null) {
                iter.remove();
                continue;
            }
        }
        if(stockInDetailList==null||stockInDetailList.size()<=0){
            throw new BusinessDoneException("入库单中至少要有一条备件入库的记录。");
        }
        Map<Long, StockInDetail> map=new HashMap<Long, StockInDetail>();
        for (StockInDetail stockInDetail : stockInDetailList) {
            Long spareId=stockInDetail.getSpareId();
            if(map.containsKey(spareId)){
                throw new BusinessDoneException("入库单中不能出现多条具有相同备件的记录。");
            }
            map.put(spareId, stockInDetail);
            if(stockInDetail.getQty()==null||stockInDetail.getQty()<=0){
                throw new BusinessDoneException("入库数量必须大于0。");
            }
        }
    }

    @Override
    public StockIn findById(Long id) {
        return stockInDao.findById(id);
    }

    @Override
    public void takeEffect(Long stockInId) {
        StockIn stockIn = stockInDao.findById(stockInId);
        // 1.检查单据是否已经生效，如果已经生效，则程序终止
        for (StockInDetail detail : stockIn.getStockInDetailList()) {
            stockService.inCreaseStock(stockIn.getWarehouseId(),
                            detail.getSpareId(), detail.getQty(),
                            detail.getQty(), null, null);
        }
        stockIn.setStatus(BillStatusEnum.PASS.getValue());
        stockInDao.updateSelective(stockIn);
    }

	

	

}
