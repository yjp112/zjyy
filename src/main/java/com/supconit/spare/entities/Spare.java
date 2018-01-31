
package com.supconit.spare.entities;

import java.math.BigDecimal;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;


public class Spare extends AuditExtend{    
	
    private static final long	serialVersionUID	= 1L;

    private String spareCode;
    private String spareName;
    private Long spareCategoryId;
    private String spec;
    private String model;
    private BigDecimal price;
    private String weight;
    private String unit;
    private String meterial;
    private Long supplierId;
    private Long isShelfLife;
    private String shelfLife;
    private Long alarmDay;
    private Integer upperQty;
    private Integer safeQty;
    private Integer lowerQty;
    private String spareDesc;
    private Long stockOutId;
    private Long stockOutDetailId;

    /** 手机端使用 **/
    private long spareTotal;
    private long spareCodeId;
    private String spareSearch;
    
    public String getSpareCode(){
        return spareCode;
    }
    public void setSpareCode(String spareCode) {
    this.spareCode = spareCode;
    }
    public String getSpareName(){
        return spareName;
    }
    public void setSpareName(String spareName) {
    this.spareName = spareName;
    }
    public Long getSpareCategoryId(){
        return spareCategoryId;
    }
    public void setSpareCategoryId(Long spareCategoryId) {
    this.spareCategoryId = spareCategoryId;
    }
    public String getSpec(){
        return spec;
    }
    public void setSpec(String spec) {
    this.spec = spec;
    }
    public String getModel(){
        return model;
    }
    public void setModel(String model) {
    this.model = model;
    }
    public BigDecimal getPrice(){
        return price;
    }
    public void setPrice(BigDecimal price) {
    this.price = price;
    }
    public String getWeight(){
        return weight;
    }
    public void setWeight(String weight) {
    this.weight = weight;
    }
    public String getUnit(){
        return unit;
    }
    public void setUnit(String unit) {
    this.unit = unit;
    }
    public String getMeterial(){
        return meterial;
    }
    public void setMeterial(String meterial) {
    this.meterial = meterial;
    }
    public Long getSupplierId(){
        return supplierId;
    }
    public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
    }
    public Long getIsShelfLife(){
        return isShelfLife;
    }
    public void setIsShelfLife(Long isShelfLife) {
    this.isShelfLife = isShelfLife;
    }
    public String getShelfLife(){
        return shelfLife;
    }
    public void setShelfLife(String shelfLife) {
    this.shelfLife = shelfLife;
    }
    public Long getAlarmDay(){
        return alarmDay;
    }
    public void setAlarmDay(Long alarmDay) {
    this.alarmDay = alarmDay;
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
    public String getSpareDesc(){
        return spareDesc;
    }
    public void setSpareDesc(String spareDesc) {
    this.spareDesc = spareDesc;
    }

    private String supplierName;
    private String spareCategoryName;
    private List<Long> spareCategoryIds;

    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public List<Long> getSpareCategoryIds() {
        return spareCategoryIds;
    }
    public void setSpareCategoryIds(List<Long> spareCategoryIds) {
        this.spareCategoryIds = spareCategoryIds;
    }
    public String getSpareCategoryName() {
        return spareCategoryName;
    }
    public void setSpareCategoryName(String spareCategoryName) {
        this.spareCategoryName = spareCategoryName;
    }
    public Long getStockOutId() {
        return stockOutId;
    }
    public void setStockOutId(Long stockOutId) {
        this.stockOutId = stockOutId;
    }
    public Long getStockOutDetailId() {
        return stockOutDetailId;
    }
    public void setStockOutDetailId(Long stockOutDetailId) {
        this.stockOutDetailId = stockOutDetailId;
    }

    public long getSpareTotal() {
        return spareTotal;
    }

    public void setSpareTotal(long spareTotal) {
        this.spareTotal = spareTotal;
    }

    public long getSpareCodeId() {
        return spareCodeId;
    }

    public void setSpareCodeId(long spareCodeId) {
        this.spareCodeId = spareCodeId;
    }

    public String getSpareSearch() {
        return spareSearch;
    }

    public void setSpareSearch(String spareSearch) {
        this.spareSearch = spareSearch;
    }
}

