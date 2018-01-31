
package com.supconit.spare.entities;


import java.math.BigDecimal;


public class StockOutDetail extends Stock{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long stockOutId;
        private Long spareId;
        private Integer qty;
        private BigDecimal prc;
        private BigDecimal money;
        private Integer availableQty;
        private Integer backQty;
        private String remark;
    
        public Long getStockOutId(){
            return stockOutId;
        }
        public void setStockOutId(Long stockOutId) {
		this.stockOutId = stockOutId;
	    }        
        public Long getSpareId(){
            return spareId;
        }
        public void setSpareId(Long spareId) {
		this.spareId = spareId;
	    }        
        public Integer getQty(){
            return qty;
        }
        public void setQty(Integer qty) {
		this.qty = qty;
	    }        
        public BigDecimal getPrc(){
            return prc;
        }
        public void setPrc(BigDecimal prc) {
		this.prc = prc;
	    }        
        public BigDecimal getMoney(){
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
        public Integer getAvailableQty() {
            return availableQty;
        }
        public void setAvailableQty(Integer availableQty) {
            this.availableQty = availableQty;
        }
		public Integer getBackQty() {
			return backQty;
		}
		public void setBackQty(Integer backQty) {
			this.backQty = backQty;
		}    
        
}
