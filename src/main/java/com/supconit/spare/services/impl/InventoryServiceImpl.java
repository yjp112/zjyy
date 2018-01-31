package com.supconit.spare.services.impl;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.exceptions.DeleteDenyException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.BillStatusEnum;
import com.supconit.common.utils.ListUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.entities.AuditExtend;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.authorization.services.UserService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.spare.daos.InventoryDao;
import com.supconit.spare.daos.InventoryDetailDao;
import com.supconit.spare.daos.StockInDao;
import com.supconit.spare.daos.StockOutDao;
import com.supconit.spare.entities.Inventory;
import com.supconit.spare.entities.InventoryDetail;
import com.supconit.spare.entities.StockIn;
import com.supconit.spare.entities.StockInDetail;
import com.supconit.spare.entities.StockOut;
import com.supconit.spare.entities.StockOutDetail;
import com.supconit.spare.services.InventoryService;
import com.supconit.spare.services.SpareProcessService;
import com.supconit.spare.services.StockInService;
import com.supconit.spare.services.StockOutService;

import hc.base.domains.Pageable;
import hc.bpm.services.UserTaskService;
import hc.safety.manager.SafetyManager;

@Service
public class InventoryServiceImpl extends
AbstractBaseBusinessService<Inventory, Long> implements
                InventoryService {

	 @Autowired
	    private InventoryDao inventoryDao;
	    @Autowired
	    private InventoryDetailDao inventoryDetailDao;
	    @Resource
	    private StockInService stockInService;
	    @Resource
	    private StockOutService stockOutService;
	    @Resource
	    private UserService userService;
	    @Resource
	    private PersonService personService;
	    private StockInDao stockInDao;
	    @Resource
	    private StockOutDao stockOutDao;
	    @Resource
	    private SpareProcessService spareProcessService;
	    @Resource
	    private RuntimeService runtimeService;
	    @Resource
	    private TaskService taskService;
	    @Resource
	    private UserTaskService userTaskService;
	    private static final int batchMaxSize = 100;

	    @Resource
	    private SafetyManager safetyManager;

	    /**
	     * Get inventory by ID
	     *
	     * @param id
	     *            inventory id
	     * @return object instance
	     */
	    @Override
	    @Transactional(readOnly = true)
	    public Inventory getById(Long id) {
	        if (null == id || id <= 0)
	            return null;
	        Inventory inventory = inventoryDao.getById(id);

	        return inventory;
	    }

	    /**
	     * delete Inventory by ID
	     *
	     * @param id
	     *            inventory ID
	     * @return
	     */
	    @Override
	    @Transactional
	    public void deleteById(Long id) {

	        checkDelete(id);
	        inventoryDao.deleteById(id);
	        inventoryDetailDao.deleteByInventoryId(id);
	    }

	    /**
	     * delete Inventory by ID array
	     *
	     * @param ids
	     *            inventory ID array
	     * @return
	     */
	    @Override
	    @Transactional
	    public void deleteByIds(Long[] ids) {
	        for (int i = 0; i < ids.length; i++) {
	            checkDelete(ids[i]);
	            inventoryDao.deleteById(ids[i]);
	            inventoryDetailDao.deleteByInventoryId(ids[i]);

	        }

	    }

	    /**
	     * Find Inventory list by condition
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
		public Pageable<Inventory> findByCondition(Pageable<Inventory> pager,
				Inventory condition) {
			return inventoryDao.findByPage(pager, condition);
		}
	    /**
	     * update Inventory
	     *
	     * @param inventory
	     *            instance
	     * @return
	     */
	    @Override
	    @Transactional
	    public void update(Inventory inventory) {

	        checkToSave(inventory);

	        List<InventoryDetail> inventoryDetailList = inventory
	                        .getInventoryDetailList();

	        // delete Detail DataTable
	        inventoryDetailDao.deleteByInventoryId(inventory.getId());

	        // update master datatable
	        inventoryDao.update(inventory);

	        // Save detail datatable
	        InventoryDetail inventoryDetail;
	        Iterator<InventoryDetail> iter = inventoryDetailList.iterator();
	        while (iter.hasNext()) {
	            inventoryDetail = iter.next();
	            if (inventoryDetail == null || inventoryDetail.getSpareId() == null) {
	                iter.remove();
	            }

	            inventoryDetail.setInventoryId(inventory.getId());
	        }

	        // update detail datatable
	        for (List<InventoryDetail> list : ListUtils.partition(
	                        inventoryDetailList, batchMaxSize)) {
	            inventoryDetailDao.insert(list);
	        }

	    }

	    /**
	     * insert Inventory
	     *
	     * @param inventory
	     *            instance
	     * @return
	     */
	    @SuppressWarnings("deprecation")
	    @Override
	    @Transactional
	    public void insert(Inventory inventory) {
	        checkToSave(inventory);
	        inventory.setInventoryCode(SerialNumberGenerator
	                        .getSerialNumbersByDB(TableAndColumn.INVENTORY));
	        inventory.setStatus(BillStatusEnum.SAVED.getValue());

	        InventoryDetail inventoryDetail;
	        List<InventoryDetail> inventoryDetailList = inventory
	                        .getInventoryDetailList();

	        // save master datatable
	        inventoryDao.insert(inventory);

	        // Save detail datatable
	        Iterator<InventoryDetail> iter = inventoryDetailList.iterator();
	        while (iter.hasNext()) {
	            inventoryDetail = iter.next();
	            if (inventoryDetail == null || inventoryDetail.getSpareId() == null) {
	                iter.remove();
	            }

	            inventoryDetail.setInventoryId(inventory.getId());
	        }

	        // update detail datatable
	        for (List<InventoryDetail> list : ListUtils.partition(
	                        inventoryDetailList, batchMaxSize)) {
	            inventoryDetailDao.insert(list);
	        }
	    }

	    @Override
	    public Inventory findById(Long id) {
	        return inventoryDao.findById(id);
	    }

	    @Override
	    public Inventory findByInventoryCode(String inventoryCode) {
	        if (StringUtils.isBlank(inventoryCode)) {
	            return null;
	        }
	        Inventory inventory = new Inventory();
	        inventory.setInventoryCode(inventoryCode);
	        List<Inventory> list = inventoryDao.selectInventorys(inventory);
	        if (list == null) {
	            return null;
	        }
	        inventory = DataAccessUtils.requiredUniqueResult(list);
	        return inventoryDao.findById(inventory.getId());
	    }

	    @Override
	    public void takeEffect(Long inventoryId) {
	        // 1.检查单据是否已经生效，如果已经生效，则程序终止
	        Inventory inventory = inventoryDao.findById(inventoryId);
	        List<InventoryDetail> details = inventory.getInventoryDetailList();

	        List<StockInDetail> inDetails = Lists.newArrayList();
	        List<StockOutDetail> outDetails = Lists.newArrayList();
	        boolean inBool = false;// 是否盘盈
	        boolean outBool = false;// 是否盘亏
	        // 生成盘盈入库单、盘亏出库单
	        // （盈亏数量>0,则为盘盈，生成盘盈入库单；盈亏数量<0,则为盘亏，生成盘亏出库单；
	        // 盈亏数量=0，无需生成额外的单据）
	        for (InventoryDetail inventoryDetail : details) {
	            Integer balanceQty = inventoryDetail.getBalanceQty();// 盈亏数量
	            //int compare = balanceQty.compareTo(new BigDecimal(0));
	            if (balanceQty > 0) {
	                // 盘盈
	                inBool = true;
	                StockInDetail inDetail = new StockInDetail();
	                inDetail.setSpareId(inventoryDetail.getSpareId());
	                inDetail.setQty(balanceQty);
	                // inDetail.setPrc();
	                // inDetail.setMoney(money);
	                inDetail.setRemark("盘盈入库");
	                inDetails.add(inDetail);
	            } else if (balanceQty < 0) {
	                // 盘亏
	                outBool = true;
	                StockOutDetail outDetail = new StockOutDetail();
	                outDetail.setSpareId(inventoryDetail.getSpareId());
	                outDetail.setQty(-balanceQty);
	                // outDetail.setPrc(prc);
	                // outDetail.setMoney(money);
	                outDetail.setRemark("盘亏出库");

	                outDetails.add(outDetail);
	            }

	        }

	        // 2.盘盈入库
	        if (inBool) {
	            StockIn in = new StockIn();// 入库单
	            in.setStockInCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.STOCK_IN));
	            in.setWarehouseId(inventory.getWarehouseId());
	            in.setInType(StockInService.INTYPE_PANYING);
	            in.setInTime(new Date());
	            in.setInPersonId(getCurrentPersonId());
	            in.setInPersonName(getCurrentPersonName());
	            in.setCheckerId(getCurrentPersonId());
	            in.setCheckerName(getCurrentPersonName());
	            in.setStatus(BillStatusEnum.SAVED.getValue());
	            in.setDescripton("盘盈入库,盘点单号["+inventory.getInventoryCode()+"]");
	            copyCreateInfo(in);
	            in.setStockInDetailList(inDetails);
	            //spareProcessService.stockInProcessDone("",in);
	            stockInService.insert(in);
	            stockInService.takeEffect(in.getId());
	            /*Map<String, Object> map=new HashMap<String, Object>();
	            map.put("stockIn", in);
	            map.put("businessKey", in.getId());
	            map.put("_initiator", "admin");
	            ProcessInstance instance=runtimeService.startProcessInstanceByKey("STOCKIN_PROCESS",map);
	            List<UserTask> tasks=userTaskService.findByExecutionId(instance.getProcessInstanceId());
	            taskService.complete(tasks.get(0).getTaskId(),map);
	            //runtimeService.signal(instance.getId(), map);*/
	            /*StockIn tmp=new StockIn();
	            tmp.setId(in.getId());
	            tmp.setBpmKey("STOCK_IN_"+in.getId());
	            tmp.setProcessId(Long.valueOf(instance.getProcessInstanceId()));
	            stockInDao.updateSelective(tmp);*/
	        }
	        // 3.盘亏出库
	        if (outBool) {
	            StockOut out = new StockOut();// 出库单
	            out.setStockOutCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.STOCK_OUT));
	            out.setOutTime(new Date());
	            out.setWarehouseId(inventory.getWarehouseId());
	            out.setOutPersonId(getCurrentPersonId());
	            out.setOutPersonName(getCurrentPersonName());
	            out.setOutType(StockOutService.OUTTYPE_PANKUI);
	            out.setDescripton("盘亏出库,盘点单号["+inventory.getInventoryCode()+"]");
	            out.setStatus(BillStatusEnum.SAVED.getValue());
	            copyCreateInfo(out);
	            out.setStockOutDetailList(outDetails);
	            stockOutService.insert(out);
	            stockOutService.takeEffect(out.getId());
	            /*Map<String, Object> map=new HashMap<String, Object>();
	            map.put("stockOut", out);
	            map.put("businessKey", out.getId());
	            map.put("_initiator", "admin");//TODO
	            ProcessInstance instance= runtimeService.startProcessInstanceByKey("STOCKOUT_PROCESS",map);
	            List<UserTask> tasks=userTaskService.findByExecutionId(instance.getProcessInstanceId());
	            taskService.complete(tasks.get(0).getTaskId(),map);
	            runtimeService.signal(instance.getId(), map);
	            StockOut tmp=new StockOut();
	            tmp.setId(out.getId());
	            tmp.setBpmKey("STOCK_OUT_"+out.getId());
	            tmp.setProcessId(Long.valueOf(instance.getProcessInstanceId()));
	            stockOutDao.updateSelective(tmp);*/

	        }

	    }

	    // Check that allows you to delete
	    private void checkDelete(Long id) {
	        Inventory inventory = inventoryDao.getById(id);
	        if (inventory.getStatus() != BillStatusEnum.SAVED.getValue()) {
	            throw new DeleteDenyException("盘点单["
	                            + inventory.getInventoryCode()
	                            + "]状态为["
	                            + BillStatusEnum.from(inventory.getStatus())
	                                            .getDesc() + "],不允许删除。");
	        }
	    }

	    // Check that allows you to save
	    private void  checkToSave(Inventory inventory) {
	        InventoryDetail inventoryDetail;
	        List<InventoryDetail> inventoryDetailList = inventory
	                        .getInventoryDetailList();

	        // Save detail datatable
	        Iterator<InventoryDetail> iter = inventoryDetailList.iterator();
	        while (iter.hasNext()) {
	            inventoryDetail = iter.next();
	            if (inventoryDetail == null || inventoryDetail.getSpareId() == null) {
	                iter.remove();
	                continue;
	            }
	            if(inventoryDetail.getInventoryQty()==null){
	                throw new BusinessDoneException("盘点数量不能为空。");
	            }
	            if(inventoryDetail.getInventoryQty()<0){
	                throw new BusinessDoneException("盘点数量不能小于0。");
	            }
	        }
	    }

	    private void copyCreateInfo(AuditExtend audit) {
	        audit.setCreateDate(new Date());
	        audit.setCreateId(getCurrentPersonId());
	        audit.setCreator(getCurrentPersonName());
	    }

	    private Long getCurrentPersonId() {
//	        return userService.getCurrentUser().getPersonId();
	    	return ((User)this.safetyManager.getCurrentUser()).getPersonId();
	    }

	    private String getCurrentPersonName() {
	        long personId=getCurrentPersonId();
	        return personService.getById(personId).getName();
	    }

		
	
}
