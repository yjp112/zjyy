
package com.supconit.spare.entities;


public class InventoryDetail extends Spare{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long inventoryId;
        private Long spareId;
        private Integer accountQty;
        private Integer inventoryQty;
        private String descripton;
    
        public Long getInventoryId(){
            return inventoryId;
        }
        public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	    }        
        public Long getSpareId(){
            return spareId;
        }
        public void setSpareId(Long spareId) {
		this.spareId = spareId;
	    }        
         
        public Integer getAccountQty() {
            return accountQty;
        }
        public void setAccountQty(Integer accountQty) {
            this.accountQty = accountQty;
        }
        public Integer getInventoryQty() {
            return inventoryQty;
        }
        public void setInventoryQty(Integer inventoryQty) {
            this.inventoryQty = inventoryQty;
        }
        public String getDescripton(){
            return descripton;
        }
        public void setDescripton(String descripton) {
		this.descripton = descripton;
	    }    

        public Integer getBalanceQty() {
            if(inventoryQty==null||accountQty==null){
                return null;
            }
            return inventoryQty-accountQty;
        }
}
