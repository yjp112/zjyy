package com.supconit.spare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.web.entities.AuditExtend;

import hc.bpm.Bpmable;


public class Inventory extends AuditExtend  implements Bpmable {

    private static final long serialVersionUID = 1L;

    private String inventoryCode;
    private String bpmKey;
    private Long processId;
    private Long warehouseId;
    private Date inventoryTime;
    private Long inventoryPersonId;
    private String inventoryPersonName;
    private Long status;
    private String remark;
    private List<InventoryDetail> inventoryDetailList = new ArrayList<InventoryDetail>();

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Date getInventoryTime() {
        return inventoryTime;
    }

    public void setInventoryTime(Date inventoryTime) {
        this.inventoryTime = inventoryTime;
    }

    public Long getInventoryPersonId() {
        return inventoryPersonId;
    }

    public void setInventoryPersonId(Long inventoryPersonId) {
        this.inventoryPersonId = inventoryPersonId;
    }

    public String getInventoryPersonName() {
        return inventoryPersonName;
    }

    public void setInventoryPersonName(String inventoryPersonName) {
        this.inventoryPersonName = inventoryPersonName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InventoryDetail> getInventoryDetailList() {
        return inventoryDetailList;
    }

    public void setInventoryDetailList(List<InventoryDetail> inventoryDetailList) {
        this.inventoryDetailList = inventoryDetailList;
    }
    // ======以下非数据库字段，只是为了查询方便========
    private String warehouseName;
    private String statusName;
    
    private Date startInventoryTime;
    private Date endInventoryTime;
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Date getStartInventoryTime() {
        return startInventoryTime;
    }

    public void setStartInventoryTime(Date startInventoryTime) {
        this.startInventoryTime = startInventoryTime;
    }

    public Date getEndInventoryTime() {
        return endInventoryTime;
    }

    public void setEndInventoryTime(Date endInventoryTime) {
        this.endInventoryTime = endInventoryTime;
    }    
    
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
        return "update INVENTORY set PROCESS_ID =? where id = "+this.getId();
    }

    @Override
    public String getProcessInstanceId() {
        if(processId==null) return "";
        return processId.toString();
    }
}
