package com.supconit.base.services.impl.pojos;


import com.supconit.common.utils.excel.ExcelAnnotation;

import java.util.List;

public class ImpType{
    private Long id;
	/** 类别名称 **/
	@ExcelAnnotation(exportName = "类别名称")
	private String typeName;
	
	/** 父类别 **/
	private long parentTypeId;
	@ExcelAnnotation(exportName = "上级类别")
    private String parentTypeName;


    @ExcelAnnotation(exportName = "类别编码")
    private String typeCode;

	/** 备注说明 **/
	@ExcelAnnotation(exportName = "类别描述")
	private String typeRemark;
	
	@ExcelAnnotation(exportName = "序号")
	private String num;


	private List<ImpType> children;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(long parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeRemark() {
        return typeRemark;
    }

    public void setTypeRemark(String typeRemark) {
        this.typeRemark = typeRemark;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<ImpType> getChildren() {
        return children;
    }

    public void setChildren(List<ImpType> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
