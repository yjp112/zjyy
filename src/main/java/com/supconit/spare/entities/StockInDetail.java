
package com.supconit.spare.entities;


import java.math.BigDecimal;


public class StockInDetail extends Spare{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long stockInId;
        private Long spareId;
        private Integer qty;
        private BigDecimal prc;
        private BigDecimal money;
        private String remark;
    
        public Long getStockInId(){
            return stockInId;
        }
        public void setStockInId(Long stockInId) {
		this.stockInId = stockInId;
	    }        
        public Long getSpareId(){
            return spareId;
        }
        public void setSpareId(Long spareId) {
		this.spareId = spareId;
	    }        
        
        public Integer getQty() {
            return qty;
        }
        public void setQty(Integer qty) {
            this.qty = qty;
        }
        public BigDecimal getPrc() {
            return prc;
        }
        public void setPrc(BigDecimal prc) {
            this.prc = prc;
        }
        public BigDecimal getMoney() {
            return money;
        }
        public void setMoney(BigDecimal money) {
            this.money = money;
        }
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }  
      //以下字段非数据库中的实际字段，特为查询添加

}
