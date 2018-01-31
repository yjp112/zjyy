package com.supconit.spare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.web.entities.AuditExtend;

import hc.bpm.Bpmable;


public class StockIn extends AuditExtend implements Bpmable{

    private static final long serialVersionUID = 1L;
    private String bpmKey;
    private Long processId;
	private String processInstanceName;
    private String stockInCode;
   
    private Long warehouseId;
    private Integer inType;
    private Date inTime;
    private Long inPersonId;
    private String inPersonName;
    private Long checkerId;
    private String checkerName;
    private Long supplierId;
    private String invoiceNo;
    private Long status;
    private String descripton;
    private List<StockInDetail> stockInDetailList = new ArrayList<StockInDetail>();
   
	private String handlePersonCode;//下一步骤处理人
	private String processType;//01提交;02驳回
    
    public String getStockInCode() {
        return stockInCode;
    }

    public void setStockInCode(String stockInCode) {
        this.stockInCode = stockInCode;
    }

    public String getBpmKey() {
        return bpmKey;
    }

 

	public String getProcessInstanceName() {
		return processInstanceName;
	}

	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getInType() {
        return inType;
    }

    public void setInType(Integer inType) {
        this.inType = inType;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Long getInPersonId() {
        return inPersonId;
    }

    public void setInPersonId(Long inPersonId) {
        this.inPersonId = inPersonId;
    }

    public String getInPersonName() {
        return inPersonName;
    }

    public void setInPersonName(String inPersonName) {
        this.inPersonName = inPersonName;
    }

    public Long getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Long checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public List<StockInDetail> getStockInDetailList() {
        return stockInDetailList;
    }

    public void setStockInDetailList(List<StockInDetail> stockInDetailList) {
        this.stockInDetailList = stockInDetailList;
    }
  //以下字段非数据库中的实际字段，特为查询添加  
    private String supplierName;
    private Date startInTime;
    private Date endInTime;
    private String statusName;
    
    public Date getStartInTime() {
        return startInTime;
    }

    public void setStartInTime(Date startInTime) {
        this.startInTime = startInTime;
    }

    public Date getEndInTime() {
        return endInTime;
    }

    public void setEndInTime(Date endInTime) {
        this.endInTime = endInTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getHandlePersonCode() {
		return handlePersonCode;
	}

	public void setHandlePersonCode(String handlePersonCode) {
		this.handlePersonCode = handlePersonCode;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	/**
     * @方法名称:getStatusDictType
     * @作 者:丁阳光
     * @创建日期:2013年7月17日
     * @方法描述: 获取状态的类型值，即ENUM_DETAIL中的TYPE_ID
     * @return Integer
     */
    public Integer getStatusDictType() {
        return DictUtils.DictTypeEnum.BILLSTATUS.getValue();
    }
    @Override
    public String callbackUpdateSQL() {
        return "update STOCK_IN set PROCESS_ID =? where id = "+this.getId();
    }

    @Override
    public String getProcessInstanceId() {
        if(processId==null) return "";
        return processId.toString();
    }
    
}
