package com.supconit.spare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hc.bpm.Bpmable;

import com.supconit.common.web.entities.AuditExtend;

public class StockBack extends AuditExtend implements Bpmable {
	private static final long serialVersionUID = 1L;

	private String processInstanceName;
	private String handlePersonCode;
	private String stockBackCode;
	private String bpmKey;
	private Long processId;
	private Date backTime;
	private Long warehouseId;
	private Long backPersonId;
	private String backPersonName;
	private Integer backType;
	private String descripton;
	private Long status;
	private List<StockBackDetail> stockBackDetailList = new ArrayList<StockBackDetail>();

	private Date startBackTime;
	private Date endBackTime;
	private String statusName;

	@Override
	public String callbackUpdateSQL() {
		return "update STOCK_BACK set PROCESS_ID =? where id = " + this.getId();
	}

	
	public String getProcessInstanceName() {
		return processInstanceName;
	}

	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}

	public String getHandlePersonCode() {
		return handlePersonCode;
	}

	public void setHandlePersonCode(String handlePersonCode) {
		this.handlePersonCode = handlePersonCode;
	}

	public String getStockBackCode() {
		return stockBackCode;
	}

	public void setStockBackCode(String stockBackCode) {
		this.stockBackCode = stockBackCode;
	}

	public String getBpmKey() {
		return bpmKey;
	}

	public void setBpmKey(String bpmKey) {
		this.bpmKey = bpmKey;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getBackPersonId() {
		return backPersonId;
	}

	public void setBackPersonId(Long backPersonId) {
		this.backPersonId = backPersonId;
	}

	public String getBackPersonName() {
		return backPersonName;
	}

	public void setBackPersonName(String backPersonName) {
		this.backPersonName = backPersonName;
	}

	public Integer getBackType() {
		return backType;
	}

	public void setBackType(Integer backType) {
		this.backType = backType;
	}

	public String getDescripton() {
		return descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public List<StockBackDetail> getStockBackDetailList() {
		return stockBackDetailList;
	}

	public void setStockBackDetailList(List<StockBackDetail> stockBackDetailList) {
		this.stockBackDetailList = stockBackDetailList;
	}

	public Date getStartBackTime() {
		return startBackTime;
	}

	public void setStartBackTime(Date startBackTime) {
		this.startBackTime = startBackTime;
	}

	public Date getEndBackTime() {
		return endBackTime;
	}

	public void setEndBackTime(Date endBackTime) {
		this.endBackTime = endBackTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public String getProcessInstanceId() {
		if (processId == null)
			return "";
		return processId.toString();
	}

}
