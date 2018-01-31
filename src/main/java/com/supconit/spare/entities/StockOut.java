package com.supconit.spare.entities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.web.entities.AuditExtend;

import hc.bpm.Bpmable;


public class StockOut extends AuditExtend implements Bpmable{ 

    private static final long serialVersionUID = 1L;

    private String stockOutCode;
    private String bpmKey;
    private Long processId;
    private Date outTime;
    private Long warehouseId;
    private Long outPersonId;
    private String outPersonName;
    private Integer outType;
    private String descripton;
    private Long status;
    private List<StockOutDetail> stockOutDetailList = new ArrayList<StockOutDetail>();

    private String handlePersonCode;//下一步骤处理人
	private String processType;//01提交;02驳回
	private String processInstanceName;//处理页面单号名
	
	
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

	public String getProcessInstanceName() {
		return processInstanceName;
	}

	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}

	public String getStockOutCode() {
        return stockOutCode;
    }

    public void setStockOutCode(String stockOutCode) {
        this.stockOutCode = stockOutCode;
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

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getOutPersonId() {
        return outPersonId;
    }

    public void setOutPersonId(Long outPersonId) {
        this.outPersonId = outPersonId;
    }

    public String getOutPersonName() {
        return outPersonName;
    }

    public void setOutPersonName(String outPersonName) {
        this.outPersonName = outPersonName;
    }

    public Integer getOutType() {
        return outType;
    }

    public void setOutType(Integer outType) {
        this.outType = outType;
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

    public List<StockOutDetail> getStockOutDetailList() {
        return stockOutDetailList;
    }

    public void setStockOutDetailList(List<StockOutDetail> stockOutDetailList) {
        this.stockOutDetailList = stockOutDetailList;
    }

    // ======以下非数据库字段，只是为了查询方便========
    private String warehouseName;
    private String statusName;
    private String managerName;

    private Date startOutTime;
    private Date endOutTime;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Date getStartOutTime() {
        return startOutTime;
    }

    public void setStartOutTime(Date startOutTime) {
        this.startOutTime = startOutTime;
    }

    public Date getEndOutTime() {
        return endOutTime;
    }

    public void setEndOutTime(Date endOutTime) {
        this.endOutTime = endOutTime;
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
        return "update STOCK_OUT set PROCESS_ID =? where id = "+this.getId();
    }

    @Override
    public String getProcessInstanceId() {
        if(processId==null) return "";
        return processId.toString();
    }

   
    
}
