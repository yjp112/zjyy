package com.supconit.spare.entities;

public class StockBackDetail extends Spare {
	private static final long serialVersionUID = 1L;

	private Long stockBackId;
	private Long spareId;
	private Integer qty;
	private String remark;
	
	public Long getStockBackId() {
		return stockBackId;
	}
	public void setStockBackId(Long stockBackId) {
		this.stockBackId = stockBackId;
	}
	public Long getSpareId() {
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
