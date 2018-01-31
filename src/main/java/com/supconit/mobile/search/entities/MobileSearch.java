package com.supconit.mobile.search.entities;

/**
 * Created by wangwei on 2016-5-20.
 */
public class MobileSearch {

    private String deviceSearchType;
    private Long parentId;
    private long searchId;
    private String searchName;
    private long deviceCodeId;
    private String deviceSearch;
    private long spareCodeId;
    private String spareSearch;

    public String getDeviceSearchType() {
        return deviceSearchType;
    }

    public void setDeviceSearchType(String deviceSearchType) {
        this.deviceSearchType = deviceSearchType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public long getSearchId() {
        return searchId;
    }

    public void setSearchId(long searchId) {
        this.searchId = searchId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public long getDeviceCodeId() {
        return deviceCodeId;
    }

    public void setDeviceCodeId(long deviceCodeId) {
        this.deviceCodeId = deviceCodeId;
    }

    public String getDeviceSearch() {
        return deviceSearch;
    }

    public void setDeviceSearch(String deviceSearch) {
        this.deviceSearch = deviceSearch;
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
