package com.supconit.spare.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.web.entities.AuditExtend;

import hc.bpm.Bpmable;


public class Transfer extends AuditExtend  implements Bpmable {

    private static final long serialVersionUID = 1L;

    private String transferCode;
    private String bpmKey;
    private Long processId;
    private Date transferTime;
    private Long transferPersonId;
    private String transferPersonName;
    private Long outWarehouseId;
    private Long inWarehouseId;
    private String descripton;
    private Long status;
    private List<TransferDetail> transferDetailList = new ArrayList<TransferDetail>();

    public String getTransferCode() {
        return transferCode;
    }

    public void setTransferCode(String transferCode) {
        this.transferCode = transferCode;
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

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Long getTransferPersonId() {
        return transferPersonId;
    }

    public void setTransferPersonId(Long transferPersonId) {
        this.transferPersonId = transferPersonId;
    }

    public String getTransferPersonName() {
        return transferPersonName;
    }

    public void setTransferPersonName(String transferPersonName) {
        this.transferPersonName = transferPersonName;
    }

    public Long getOutWarehouseId() {
        return outWarehouseId;
    }

    public void setOutWarehouseId(Long outWarehouseId) {
        this.outWarehouseId = outWarehouseId;
    }

    public Long getInWarehouseId() {
        return inWarehouseId;
    }

    public void setInWarehouseId(Long inWarehouseId) {
        this.inWarehouseId = inWarehouseId;
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

    public List<TransferDetail> getTransferDetailList() {
        return transferDetailList;
    }

    public void setTransferDetailList(List<TransferDetail> transferDetailList) {
        this.transferDetailList = transferDetailList;
    }
    // ======以下非数据库字段，只是为了查询方便========
    private String outWarehouseName;
    private String inWarehouseName;
    private Date startTransferTime;
    private Date endTransferTime;
    private String statusName;
    
    public String getOutWarehouseName() {
        return outWarehouseName;
    }

    public void setOutWarehouseName(String outWarehouseName) {
        this.outWarehouseName = outWarehouseName;
    }

    public String getInWarehouseName() {
        return inWarehouseName;
    }

    public void setInWarehouseName(String inWarehouseName) {
        this.inWarehouseName = inWarehouseName;
    }

    public Date getStartTransferTime() {
        return startTransferTime;
    }

    public void setStartTransferTime(Date startTransferTime) {
        this.startTransferTime = startTransferTime;
    }

    public Date getEndTransferTime() {
        return endTransferTime;
    }

    public void setEndTransferTime(Date endTransferTime) {
        this.endTransferTime = endTransferTime;
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
        return "update TRANSFER set PROCESS_ID =? where id = "+this.getId();
    }

    @Override
    public String getProcessInstanceId() {
        if(processId==null) return "";
        return processId.toString();
    }
}
