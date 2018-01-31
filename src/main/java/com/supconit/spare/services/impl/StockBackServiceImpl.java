package com.supconit.spare.services.impl;

import java.util.Iterator;
import java.util.List;

import hc.base.domains.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.BillStatusEnum;
import com.supconit.common.utils.ListUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.spare.daos.StockBackDao;
import com.supconit.spare.daos.StockBackDetailDao;
import com.supconit.spare.entities.StockBack;
import com.supconit.spare.entities.StockBackDetail;
import com.supconit.spare.services.StockBackService;

@Service
public class StockBackServiceImpl  extends AbstractBaseBusinessService<StockBack, Long> implements StockBackService{
	@Autowired
	private StockBackDao stockBackDao;
	@Autowired
	private StockBackDetailDao stockBackDetailDao;
	private static final int batchMaxSize = 100;
	
	@Override
	public StockBack getById(Long id) {
		return stockBackDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(StockBack stockBack) {
		checkToSave(stockBack);
		stockBack.setStockBackCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.STOCK_BACK));
		stockBack.setStatus(BillStatusEnum.SAVED.getValue());
		List<StockBackDetail> stockBackDetailList = stockBack.getStockBackDetailList();
		// save master datatable
        stockBackDao.insert(stockBack);

        StockBackDetail stockBackDetail;
        // Save detail datatable
        Iterator<StockBackDetail> iter = stockBackDetailList.iterator();
        while (iter.hasNext()) {
            stockBackDetail = iter.next();
            if (stockBackDetail == null||stockBackDetail.getSpareId()==null) {
                iter.remove();
            }
            stockBackDetail.setStockBackId(stockBack.getId());
        }

        // update detail datatable
        for (List<StockBackDetail> list : ListUtils.partition(stockBackDetailList,batchMaxSize)) {
        	try{
            stockBackDetailDao.insert(list);
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
	}

	@Override
	@Transactional
	public void update(StockBack stockBack) {
		checkToSave(stockBack);
		List<StockBackDetail> stockBackDetailList = stockBack.getStockBackDetailList();
        // delete Detail DataTable
        stockBackDetailDao.deleteByStockBackId(stockBack.getId());

        // update master datatable
        stockBackDao.update(stockBack);

        // Save detail datatable
        StockBackDetail stockBackDetail;
        Iterator<StockBackDetail> iter = stockBackDetailList.iterator();
        while (iter.hasNext()) {
            stockBackDetail = iter.next();
            if (stockBackDetail == null||stockBackDetail.getSpareId()==null) {
                iter.remove();
            }
            stockBackDetail.setStockBackId(stockBack.getId());
        }

        // update detail datatable
        for (List<StockBackDetail> list : ListUtils.partition(stockBackDetailList,
                        batchMaxSize)) {
            stockBackDetailDao.insert(list);
        }
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public Pageable<StockBack> findByCondition(Pageable<StockBack> pager,
			StockBack condition) {
		return stockBackDao.findByCondition(pager, condition);
	}
	
	private void checkToSave(StockBack stockBack) {
        StockBackDetail tmp;
        List<StockBackDetail> stockBackDetailList = stockBack.getStockBackDetailList();
        Iterator<StockBackDetail> iter = stockBackDetailList.iterator();
        while (iter.hasNext()) {
            tmp = iter.next();
            if (tmp == null||tmp.getSpareId()==null) {
                iter.remove();
                continue;
            }
        }
        if(stockBackDetailList==null||stockBackDetailList.size()==0){
            throw new BusinessDoneException("归还单中至少要有一条备件归还的记录。");
        }
        for (StockBackDetail stockBackDetail : stockBackDetailList) {
            if(stockBackDetail.getQty()==null||stockBackDetail.getQty()<=0){
                throw new BusinessDoneException("归还数量必须大于0。");
            }
        }
    }

}
