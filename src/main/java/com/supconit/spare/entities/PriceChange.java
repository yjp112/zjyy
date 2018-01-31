
package com.supconit.spare.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PriceChange extends Spare{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String billNo;
        private String bpmKey;
        private Long processId;
        private Long spareId;
        private Date changeDate;
        private Long changePersonId;
        private String changePersonName;
        private Integer changeType;
        private BigDecimal oldPrice;
        private BigDecimal newPrice;
        private String remark;
        private Long status;
    
        public String getBillNo(){
            return billNo;
        }
        public void setBillNo(String billNo) {
		this.billNo = billNo;
	    }        
        public String getBpmKey(){
            return bpmKey;
        }
        public void setBpmKey(String bpmKey) {
		this.bpmKey = bpmKey;
	    }        
        public Long getProcessId(){
            return processId;
        }
        public void setProcessId(Long processId) {
		this.processId = processId;
	    }        
        public Long getSpareId(){
            return spareId;
        }
        public void setSpareId(Long spareId) {
		this.spareId = spareId;
	    }        
        public Date getChangeDate(){
            return changeDate;
        }
        public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	    }        
        public Long getChangePersonId(){
            return changePersonId;
        }
        public void setChangePersonId(Long changePersonId) {
		this.changePersonId = changePersonId;
	    }        
        public String getChangePersonName(){
            return changePersonName;
        }
        public void setChangePersonName(String changePersonName) {
		this.changePersonName = changePersonName;
	    }        
        public Integer getChangeType(){
            return changeType;
        }
        public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	    }        
        public BigDecimal getOldPrice(){
            return oldPrice;
        }
        public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	    }        
        public BigDecimal getNewPrice(){
            return newPrice;
        }
        public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	    }        
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }        
        public Long getStatus(){
            return status;
        }
        public void setStatus(Long status) {
		this.status = status;
	    }        
     // ======以下非数据库字段，只是为了查询方便========
        private String spareCode;
        private String spareName;
        private String spareCategoryName;
        private Long spareCategoryId;
        private List<Long> spareCategoryIds;

        public String getSpareCode() {
            return spareCode;
        }
        public void setSpareCode(String spareCode) {
            this.spareCode = spareCode;
        }
        public String getSpareName() {
            return spareName;
        }
        public void setSpareName(String spareName) {
            this.spareName = spareName;
        }
        public String getSpareCategoryName() {
            return spareCategoryName;
        }
        public void setSpareCategoryName(String spareCategoryName) {
            this.spareCategoryName = spareCategoryName;
        }
        public Long getSpareCategoryId() {
            return spareCategoryId;
        }
        public void setSpareCategoryId(Long spareCategoryId) {
            this.spareCategoryId = spareCategoryId;
        }
        public List<Long> getSpareCategoryIds() {
            return spareCategoryIds;
        }
        public void setSpareCategoryIds(List<Long> spareCategoryIds) {
            this.spareCategoryIds = spareCategoryIds;
        }
        
}

