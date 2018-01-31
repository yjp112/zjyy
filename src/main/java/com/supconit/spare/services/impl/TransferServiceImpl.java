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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.exceptions.DeleteDenyException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.BillStatusEnum;
import com.supconit.common.utils.ListUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.spare.daos.TransferDao;
import com.supconit.spare.daos.TransferDetailDao;
import com.supconit.spare.entities.Stock;
import com.supconit.spare.entities.Transfer;
import com.supconit.spare.entities.TransferDetail;
import com.supconit.spare.services.StockService;
import com.supconit.spare.services.TransferService;

import hc.base.domains.Pageable;


@Service
public class TransferServiceImpl extends
AbstractBaseBusinessService<Transfer, Long> implements
                TransferService {

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private TransferDetailDao transferDetailDao;

    @Resource
    private StockService stockService;
    private static final int batchMaxSize = 100;

    /**
     * Get transfer by ID
     * 
     * @param id
     *            transfer id
     * @return object instance
     */
    @Override
    @Transactional(readOnly = true)
    public Transfer getById(Long id) {
        if (null == id || id <= 0)
            return null;
        Transfer transfer = transferDao.getById(id);

        return transfer;
    }

    /**
     * delete Transfer by ID
     * 
     * @param id
     *            transfer ID
     * @return
     */
    @Override
    @Transactional
    public void deleteById(Long id) {

        checkDelete(id);
        transferDao.deleteById(id);
        transferDetailDao.deleteByTransferId(id);
    }

    /**
     * delete Transfer by ID array
     * 
     * @param ids
     *            transfer ID array
     * @return
     */
    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {

        for (int i = 0; i < ids.length; i++) {
            checkDelete(ids[i]);
            transferDao.deleteById(ids[i]);
            transferDetailDao.deleteByTransferId(ids[i]);

        }

    }

    /**
     * Find Transfer list by condition
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
	public Pageable<Transfer> findByCondition(Pageable<Transfer> pager,
			Transfer condition) {
		return transferDao.findByPage(pager, condition);
	}
    /**
     * update Transfer
     * 
     * @param transfer
     *            instance
     * @return
     */
    @Override
    @Transactional
    public void update(Transfer transfer) {

        checkToSave(transfer);

        List<TransferDetail> transferDetailList = transfer
                        .getTransferDetailList();

        // delete Detail DataTable
        transferDetailDao.deleteByTransferId(transfer.getId());

        // update master datatable
        transferDao.update(transfer);

        // Save detail datatable
        TransferDetail transferDetail;
        Iterator<TransferDetail> iter = transferDetailList.iterator();
        while (iter.hasNext()) {
            transferDetail = iter.next();
            if (transferDetail == null || transferDetail.getSpareId() == null) {
                iter.remove();
            }
            transferDetail.setTransferId(transfer.getId());
        }

        // update detail datatable
        for (List<TransferDetail> list : ListUtils.partition(
                        transferDetailList, batchMaxSize)) {
            transferDetailDao.insert(list);
        }

    }

    /**
     * insert Transfer
     * 
     * @param transfer
     *            instance
     * @return
     */
    @Override
    @Transactional
    public void insert(Transfer transfer) {
        checkToSave(transfer);
        transfer.setTransferCode(SerialNumberGenerator
                        .getSerialNumbersByDB(TableAndColumn.TRANSFER));
        transfer.setStatus(BillStatusEnum.SAVED.getValue());
        // save master datatable
        transferDao.insert(transfer);
        TransferDetail transferDetail;
        List<TransferDetail> transferDetailList = transfer
                        .getTransferDetailList();

        // Save detail datatable
        Iterator<TransferDetail> iter = transferDetailList.iterator();
        while (iter.hasNext()) {
            transferDetail = iter.next();
            if (transferDetail == null || transferDetail.getSpareId() == null) {
                iter.remove();
            }
            transferDetail.setTransferId(transfer.getId());
        }

        // update detail datatable
        for (List<TransferDetail> list : ListUtils.partition(
                        transferDetailList, batchMaxSize)) {
            transferDetailDao.insert(list);
        }
    }

    @Transactional
    @Override
    public void takeEffect(Long transferId) {
        // 1.检查单据是否已经生效，如果已经生效，则程序终止
        Transfer transfer = transferDao.findById(transferId);
        List<TransferDetail> details = transfer.getTransferDetailList();
//        BigDecimal zero = new BigDecimal(0);
        for (TransferDetail transferDetail : details) {
            // 2.调入方新增
            stockService.inCreaseStock(transfer.getInWarehouseId(),
                            transferDetail.getSpareId(),
                            transferDetail.getQuantity(),
                            transferDetail.getQuantity(), 0, 0);
            // 3.调出方减去
            stockService.reduceStock(transfer.getOutWarehouseId(),
                            transferDetail.getSpareId(),
                            transferDetail.getQuantity(),
                            transferDetail.getQuantity(), 0, 0);
        }

    }

    // Check that allows you to delete
    private void checkDelete(Long id) {
        Transfer transfer = transferDao.getById(id);
        if (transfer.getStatus() != BillStatusEnum.SAVED.getValue()) {
            throw new DeleteDenyException("调拨单["
                            + transfer.getTransferCode()
                            + "]状态为["
                            + BillStatusEnum.from(transfer.getStatus())
                                            .getDesc() + "],不允许删除。");
        }
    }

    // Check that allows you to save
    private void checkToSave(Transfer transfer) {
        // TODO 流程依据提交的时候不能修改了
        if (transfer.getInWarehouseId() == transfer.getOutWarehouseId()) {
            throw new BusinessDoneException("调入仓库和调出仓库不能相等。");
        }
        TransferDetail tmp;
        List<TransferDetail> transferDetailList = transfer
                        .getTransferDetailList();

        // Save detail datatable
        Iterator<TransferDetail> iter = transferDetailList.iterator();
        while (iter.hasNext()) {
            tmp = iter.next();
            if (tmp == null || tmp.getSpareId() == null) {
                iter.remove();
                continue;
            }
        }
        if (transfer.getTransferDetailList() == null
                        || transfer.getTransferDetailList().size() <= 0) {
            throw new BusinessDoneException("调拨单中至少要有一条备件调拨的记录。");
        }
        Map<Long, TransferDetail> map = new HashMap<Long, TransferDetail>();
        for (TransferDetail transferDetail : transfer.getTransferDetailList()) {
            Long spareId = transferDetail.getSpareId();
            if (map.containsKey(spareId)) {
                throw new BusinessDoneException("调拨单中不能出现多条具有相同备件的记录。");
            }
            map.put(spareId, transferDetail);
        }
        List<Long> spareIds = new ArrayList<Long>();
        spareIds.addAll(map.keySet());
        List<Stock> stocks = stockService.selectBySpareIds(
                        transfer.getOutWarehouseId(), spareIds);
        if (spareIds.size() != stocks.size()) {
            Transformer transformer = new BeanToPropertyValueTransformer("spareId");
            @SuppressWarnings("unchecked")
            Collection<Long> notFoundList = CollectionUtils.subtract(spareIds,
                            CollectionUtils.collect(stocks, transformer));
            List<String> tmpList=new ArrayList<String>();
            for (Long id : notFoundList) {
                TransferDetail detail = map.get(id);
                tmpList.add(detail.getSpareName());
            }
            throw new BusinessDoneException("调出仓库中没有下列备件："+tmpList.toString());
        }
        for (Stock stock : stocks) {
            Long spareId = stock.getSpareId();
            TransferDetail detail = map.get(spareId);
            if(detail.getQuantity()==null){
                throw new BusinessDoneException("备件[" + detail.getSpareName()
                                + "]调拨数量不能为空。");  
            }
            if (stock.getAvailableQty().compareTo(detail.getQuantity()) < 0) {
                throw new BusinessDoneException("备件[" + detail.getSpareName()
                                + "]可用库存不足。");
            }
            if (detail.getQuantity() <= 0) {
                throw new BusinessDoneException("备件[" + detail.getSpareName()
                                + "]调拨数量必须大于0。");
            }
            detail.setAvailableQty(stock.getAvailableQty());
        }
    }

    @Override
    public Transfer findById(Long id) {
        return transferDao.findById(id);
    }

    @Override
    public Transfer findByTransferCode(String transferCode) {
        if (StringUtils.isBlank(transferCode)) {
            return null;
        }
        Transfer transfer = new Transfer();
        transfer.setTransferCode(transferCode);
        List<Transfer> list = transferDao.selectTransfers(transfer);
        if (list == null) {
            return null;
        }
        transfer = DataAccessUtils.requiredUniqueResult(list);
        return transferDao.findById(transfer.getId());
    }

	

}
