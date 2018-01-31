
package com.supconit.spare.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;


public class Stock extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long spareId;
        private Long warehouseId;
        private Integer qty;
        private Integer availableQty;
        private Integer backupQty;
        private Integer freezeQty;
    
        public Long getSpareId(){
            return spareId;
        }
        public void setSpareId(Long spareId) {
		this.spareId = spareId;
	    }        
        public Long getWarehouseId(){
            return warehouseId;
        }
        public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	    }
        public Integer getQty() {
            return qty;
        }
        public void setQty(Integer qty) {
            this.qty = qty;
        }
        public Integer getAvailableQty() {
            return availableQty;
        }
        public void setAvailableQty(Integer availableQty) {
            this.availableQty = availableQty;
        }
        public Integer getBackupQty() {
            return backupQty;
        }
        public void setBackupQty(Integer backupQty) {
            this.backupQty = backupQty;
        }
        public Integer getFreezeQty() {
            return freezeQty;
        }
        public void setFreezeQty(Integer freezeQty) {
            this.freezeQty = freezeQty;
        }        
 
        //======浠ヤ笅闈炴暟鎹簱瀛楁锛屽彧鏄负浜嗘煡璇㈡柟渚�=======
        private String spareName;
        private String warehouseCode;
        private String warehouseName;

        private String spareCode;
        private String supplyName;
        private String categoryName;
        private String spec;//瑙勬牸
        private String model;//鍨嬪彿
        private String unit;//璁￠噺鍗曚綅
        //========
        private Integer upperQty;
        private Integer safeQty;
        private Integer lowerQty;
        private Long categoryId;
        private List<Long> categoryIds;
        private List<Long> spareIds;
        
        private List<Long> spareCategoryIds;
        private List<Long> warhouseIds;
        
        private Date startCreateDate;
        private Date endCreateDate;
        private Integer costsQty;
        
        
        public List<Long> getSpareCategoryIds() {
			return spareCategoryIds;
		}
		public void setSpareCategoryIds(List<Long> spareCategoryIds) {
			this.spareCategoryIds = spareCategoryIds;
		}
		public List<Long> getWarhouseIds() {
			return warhouseIds;
		}
		public void setWarhouseIds(List<Long> warhouseIds) {
			this.warhouseIds = warhouseIds;
		}
		public String getSpareName() {
            return spareName;
        }
        public void setSpareName(String spareName) {
            this.spareName = spareName;
        }
        public String getWarehouseName() {
            return warehouseName;
        }
        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }
        public String getWarehouseCode() {
            return warehouseCode;
        }
        public void setWarehouseCode(String warehouseCode) {
            this.warehouseCode = warehouseCode;
        }
        public String getSpareCode() {
            return spareCode;
        }
        public void setSpareCode(String spareCode) {
            this.spareCode = spareCode;
        }
        public Long getCategoryId() {
            return categoryId;
        }
        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }
        public List<Long> getCategoryIds() {
            return categoryIds;
        }
        public void setCategoryIds(List<Long> categoryIds) {
            this.categoryIds = categoryIds;
        }
        
        public List<Long> getSpareIds() {
            return spareIds;
        }
        public void setSpareIds(List<Long> spareIds) {
            this.spareIds = spareIds;
        }
        public String getSupplyName() {
            return supplyName;
        }
        public void setSupplyName(String supplyName) {
            this.supplyName = supplyName;
        }
        public String getCategoryName() {
            return categoryName;
        }
        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
        public String getSpec() {
            return spec;
        }
        public void setSpec(String spec) {
            this.spec = spec;
        }
        public String getModel() {
            return model;
        }
        public void setModel(String model) {
            this.model = model;
        }
        public Integer getUpperQty() {
            return upperQty;
        }
        public void setUpperQty(Integer upperQty) {
            this.upperQty = upperQty;
        }
        public Integer getSafeQty() {
            return safeQty;
        }
        public void setSafeQty(Integer safeQty) {
            this.safeQty = safeQty;
        }
        public Integer getLowerQty() {
            return lowerQty;
        }
        public void setLowerQty(Integer lowerQty) {
            this.lowerQty = lowerQty;
        }
        /** 
         *@鏂规硶鍚嶇О:getUpperQtyDiff
         *@浣�   鑰�涓侀槼鍏�         *@鍒涘缓鏃ユ湡:2013骞�鏈�9鏃�         *@鏂规硶鎻忚堪: 瓒呭瓨閲�鐜板瓨閲�鏈�珮搴撳瓨閲忥紝姝ｅ�琛ㄧず瓒呰繃鏈�珮搴撳瓨閲忛渶瑕侀�褰撴帶鍒讹紝璐熷�琛ㄧず浣庝簬鏈�珮搴撳瓨閲�
         * @return BigDecimal
         */
        public Integer getUpperQtyDiff() {
            if(upperQty==null){
                return null;
            }
            return qty-upperQty;
        }
        /** 
         *@鏂规硶鍚嶇О:getSafeQtyDiff
         *@浣�   鑰�涓侀槼鍏�         *@鍒涘缓鏃ユ湡:2013骞�鏈�9鏃�         *@鏂规硶鎻忚堪:銆��瀹夊叏宸噺=鐜板瓨閲�瀹夊叏搴撳瓨閲忥紝姝ｅ�琛ㄧず澶勪簬瀹夊叏閲忎箣涓婏紝璐熷�琛ㄧず宸茬粡浣庝簬瀹夊叏閲忎簡銆� 
         * @return BigDecimal
         */
        public Integer getSafeQtyDiff() {
            if(safeQty==null){
                return null;
            } 
            return qty-safeQty;
        }
        /** 
         *@鏂规硶鍚嶇О:getLowerQtyDiff
         *@浣�   鑰�涓侀槼鍏�         *@鍒涘缓鏃ユ湡:2013骞�鏈�9鏃�         *@鏂规硶鎻忚堪:鐭己閲�鏈�綆搴撳瓨閲�鐜板瓨閲忥紝姝ｅ�琛ㄧず浣庝簬鏈�綆搴撳瓨闇�鎺у埗锛岃礋鍊艰〃绀洪珮浜庢渶浣庡簱瀛�  
         * @return BigDecimal
         */
        public Integer getLowerQtyDiff() {
            if(lowerQty==null){
                return null;
            } 
            return lowerQty-qty;
        }
        public String getUnit() {
            return unit;
        }
        public void setUnit(String unit) {
            this.unit = unit;
        }
        public Date getStartCreateDate() {
            return startCreateDate;
        }
        public void setStartCreateDate(Date startCreateDate) {
            this.startCreateDate = startCreateDate;
        }
        public Date getEndCreateDate() {
            return endCreateDate;
        }
        public void setEndCreateDate(Date endCreateDate) {
            this.endCreateDate = endCreateDate;
        }
        public Integer getCostsQty() {
            return costsQty;
        }
        public void setCostsQty(Integer costsQty) {
            this.costsQty = costsQty;
        }

        
}

