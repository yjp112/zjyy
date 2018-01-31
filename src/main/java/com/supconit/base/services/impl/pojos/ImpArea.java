package com.supconit.base.services.impl.pojos;


import com.supconit.common.utils.excel.ExcelAnnotation;

import java.util.List;

public class ImpArea {

    private Long id;
	/** 类别名称 **/
	@ExcelAnnotation(exportName = "区域名称")
	private String areaName;
	
	/** 父类别 **/
	private long parentAreaId;
	@ExcelAnnotation(exportName = "上级区域")
    private String parentAreaName;


    @ExcelAnnotation(exportName = "区域编码")
    private String areaCode;

	/** 备注说明 **/
	@ExcelAnnotation(exportName = "区域描述")
	private String areaRemark;
	
	@ExcelAnnotation(exportName = "序号")
	private String num;


	private List<ImpArea> children;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public long getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(long parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public String getParentAreaName() {
        return parentAreaName;
    }

    public void setParentAreaName(String parentAreaName) {
        this.parentAreaName = parentAreaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaRemark() {
        return areaRemark;
    }

    public void setAreaRemark(String areaRemark) {
        this.areaRemark = areaRemark;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<ImpArea> getChildren() {
        return children;
    }

    public void setChildren(List<ImpArea> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
