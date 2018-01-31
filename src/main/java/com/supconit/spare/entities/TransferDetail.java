
package com.supconit.spare.entities;




public class TransferDetail extends Stock{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long transferId;
        private Long spareId;
        private Integer quantity;
        private Integer availableQty;
    
        public Long getTransferId(){
            return transferId;
        }
        public void setTransferId(Long transferId) {
		this.transferId = transferId;
	    }        
        public Long getSpareId(){
            return spareId;
        }
        public void setSpareId(Long spareId) {
		this.spareId = spareId;
	    }
        public Integer getQuantity() {
            return quantity;
        }
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
        public Integer getAvailableQty() {
            return availableQty;
        }
        public void setAvailableQty(Integer availableQty) {
            this.availableQty = availableQty;
        }        
     
}
