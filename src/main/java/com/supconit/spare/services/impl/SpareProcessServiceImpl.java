package com.supconit.spare.services.impl;

import java.util.List;

import hc.bpm.context.annotations.BpmSupport;
import hc.bpm.services.ProcessService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.utils.BillStatusEnum;
import com.supconit.spare.daos.InventoryDao;
import com.supconit.spare.daos.InventoryDetailDao;
import com.supconit.spare.daos.StockBackDao;
import com.supconit.spare.daos.StockInDao;
import com.supconit.spare.daos.StockInDetailDao;
import com.supconit.spare.daos.StockOutDao;
import com.supconit.spare.daos.StockOutDetailDao;
import com.supconit.spare.daos.TransferDao;
import com.supconit.spare.daos.TransferDetailDao;
import com.supconit.spare.entities.Inventory;
import com.supconit.spare.entities.StockBack;
import com.supconit.spare.entities.StockBackDetail;
import com.supconit.spare.entities.StockIn;
import com.supconit.spare.entities.StockOut;
import com.supconit.spare.entities.StockOutDetail;
import com.supconit.spare.entities.Transfer;
import com.supconit.spare.services.InventoryService;
import com.supconit.spare.services.SpareProcessService;
import com.supconit.spare.services.StockBackService;
import com.supconit.spare.services.StockInService;
import com.supconit.spare.services.StockOutService;
import com.supconit.spare.services.TransferService;

import jodd.util.StringUtil;

@Service
@Transactional
public class SpareProcessServiceImpl /* extends ProcessServiceImpl */implements
		SpareProcessService {

	@Resource
	private StockInService stockInService;
	@Resource
	private StockOutService stockOutService;
	@Resource
	private TransferService transferService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private StockInDao stockInDao;
	@Resource
	private StockInDetailDao stockInDetailDao;
	@Resource
	private StockOutDao stockOutDao;
	@Resource
	private StockOutDetailDao stockOutDetailDao;
	@Resource
	private StockBackService stockBackService;
	@Resource
	private StockBackDao stockBackDao;
	@Resource
	private TransferDao transferDao;
	@Resource
	private TransferDetailDao transferDetailDao;
	@Resource
	private InventoryDao inventoryDao;
	@Resource
	private InventoryDetailDao inventoryDetailDao;
	@Resource
	ProcessService processService;

	@SuppressWarnings("deprecation")
	@Transactional
	@BpmSupport(businessKey = "#stockIn.id", variableKeys = "{'stockIn'}", variables = "{#stockIn}", businessDomain = "#stockIn")
	@Override
	public void stockInProcessDone(String transitionId, StockIn stockIn) {
		if (StringUtil.isBlank(transitionId) || "submit_1".equals(transitionId)) {
			if (stockIn.getId() == null) {
				stockInService.insert(stockIn);
			} else {
				stockInService.update(stockIn);
			}
		}
		StockIn tmp = new StockIn();
		if (StringUtil.isNotBlank(transitionId)) {
			if ("submit_1".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.DOING.getValue());
			} else if ("submit_2".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.PASS.getValue());
			} else if ("audit_reject_2".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.NOPASS.getValue());
			}
		}
		tmp.setId(stockIn.getId());
		tmp.setBpmKey("STOCK_IN_" + stockIn.getId());
		stockInDao.updateSelective(tmp);

		if (tmp.getStatus() != null
				&& tmp.getStatus() == BillStatusEnum.PASS.getValue()) {
			stockInService.takeEffect(stockIn.getId());
		}

	}

	@Override
	public void stockInProcessSave(StockIn stockIn) {
		// TODO审批意见保存
		stockInService.update(stockIn);

	}

	@Transactional
	public void stockInProcessInstanceDelete(Long id) {
		StockIn stockIn = stockInService.getById(id);
		// stockInService.deleteById(id);
		stockInDao.deleteById(id);
		stockInDetailDao.deleteByStockInId(id);
		processService.deleteProcessInstance(stockIn.getProcessInstanceId());
	}

	@SuppressWarnings("deprecation")
	@Transactional
	@BpmSupport(businessKey = "#stockOut.id", variableKeys = "{'stockOut'}", variables = "{#stockOut}", businessDomain = "#stockOut")
	@Override
	public void stockOutProcessDone(String transitionId, StockOut stockOut) {
		if (StringUtil.isBlank(transitionId) || "submit_1".equals(transitionId)) {
			if (stockOut.getId() == null) {
				stockOutService.insert(stockOut);
			} else {
				stockOutService.update(stockOut);
			}
		}
		StockOut tmp = new StockOut();
		if (StringUtil.isNotBlank(transitionId)) {
			if ("submit_1".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.DOING.getValue());
			} else if ("submit_2".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.PASS.getValue());
			} else if ("audit_reject_2".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.NOPASS.getValue());
			}
		}
		tmp.setId(stockOut.getId());
		tmp.setBpmKey("STOCK_OUT_" + stockOut.getId());
		stockOutDao.updateSelective(tmp);
		if (tmp.getStatus() != null
				&& tmp.getStatus() == BillStatusEnum.PASS.getValue()) {
			stockOutService.takeEffect(stockOut.getId());
		}
	}

	@Override
	public void stockOutProcessInstanceDelete(Long id) {
		StockOut stockOut = stockOutService.getById(id);
		// stockInService.deleteById(id);
		stockOutDao.deleteById(id);
		stockOutDetailDao.deleteByStockOutId(id);
		processService.deleteProcessInstance(stockOut.getProcessInstanceId());

	}

	@SuppressWarnings("deprecation")
	@Transactional
	@BpmSupport(businessKey = "#transfer.id", variableKeys = "{'transfer'}", variables = "{#transfer}", businessDomain = "#transfer")
	@Override
	public void transferProcessDone(String transitionId, Transfer transfer) {
		if (StringUtil.isBlank(transitionId) || "submit_1".equals(transitionId)) {
			if (transfer.getId() == null) {
				transferService.insert(transfer);
			} else if (StringUtil.isBlank(transitionId)) {
				transferService.update(transfer);
			}
		}

		Transfer tmp = new Transfer();
		if (StringUtil.isNotBlank(transitionId)) {
			if ("submit_1".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.DOING.getValue());
			} else if ("submit_2".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.PASS.getValue());
			} else if ("audit_reject_2".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.NOPASS.getValue());
			}
		}
		tmp.setBpmKey("TRANSFER_" + transfer.getId());
		tmp.setId(transfer.getId());
		transferDao.updateSelective(tmp);
		if (tmp.getStatus() != null
				&& tmp.getStatus() == BillStatusEnum.PASS.getValue()) {
			transferService.takeEffect(transfer.getId());
		}

	}

	@Override
	public void transferProcessInstanceDelete(Long id) {
		Transfer transfer = transferDao.getById(id);
		transferDao.deleteById(id);
		transferDetailDao.deleteByTransferId(id);
		processService.deleteProcessInstance(transfer.getProcessInstanceId());

	}

	@SuppressWarnings("deprecation")
	@Transactional
	@BpmSupport(businessKey = "#inventory.id", variableKeys = "{'inventory'}", variables = "{#inventory}", businessDomain = "#inventory")
	@Override
	public void invertoryProcessDone(String transitionId, Inventory inventory) {
		if (StringUtil.isBlank(transitionId) || "submit_1".equals(transitionId)) {
			if (inventory.getId() == null) {
				inventoryService.insert(inventory);
			} else {
				inventoryService.update(inventory);
			}
		}

		Inventory tmp = new Inventory();
		if (StringUtil.isNotBlank(transitionId)) {
			if ("submit_1".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.DOING.getValue());
			} else if ("submit_2".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.PASS.getValue());
			} else if ("audit_reject_2".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.NOPASS.getValue());
			}
		}
		tmp.setId(inventory.getId());
		tmp.setBpmKey("INVENTORY_" + inventory.getId());
		inventoryDao.updateSelective(tmp);
		if (tmp.getStatus() != null
				&& tmp.getStatus() == BillStatusEnum.PASS.getValue()) {
			inventoryService.takeEffect(inventory.getId());
		}
	}

	@Override
	public void invertoryProcessInstanceDelete(Long id) {
		Inventory inventory = inventoryDao.getById(id);
		inventoryDao.deleteById(id);
		inventoryDetailDao.deleteByInventoryId(id);
		processService.deleteProcessInstance(inventory.getProcessInstanceId());
	}

	@Override
	public void stockOutProcessSave(StockOut stockOut) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transferProcessSave(Transfer transfer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void invertoryProcessSave(Inventory inventory) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@BpmSupport(businessKey = "#task.id", businessDomain = "#task", variableKeys = "{'task'}", variables = "{#task}")
	@Override
	public void stockBackProcessDone(String transitionId, StockBack task) {
		task.setProcessInstanceName("归还单:"+task.getStockBackCode());
		if (task.getId() == null) {
			stockBackService.insert(task);
		} else {
			stockBackService.update(task);
		}
		StockBack tmp = new StockBack();
		if (StringUtil.isNotBlank(transitionId)) {
			if ("submit_apply".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.DOING.getValue());
			} else if ("submit_audit".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.PASS.getValue());
			} else if ("submit_reject".equals(transitionId)) {
				tmp.setStatus(BillStatusEnum.NOPASS.getValue());
			}
		}
		tmp.setId(task.getId());
		tmp.setBpmKey("STOCK_BACK_" + task.getId());
		stockBackDao.update(tmp);
		if ("submit_audit".equals(transitionId)) {
			List<StockBackDetail> list = task.getStockBackDetailList();
			for (StockBackDetail stockBackDetail : list) {
				StockOutDetail sod = new StockOutDetail();
				sod.setId(stockBackDetail.getStockOutDetailId());
				sod.setAvailableQty(stockBackDetail.getQty());
				sod.setBackQty(stockBackDetail.getQty());
				stockOutDetailDao.updateBackQty(sod);
			}
		} 
		
	}

}
