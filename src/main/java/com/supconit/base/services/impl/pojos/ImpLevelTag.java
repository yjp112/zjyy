package com.supconit.base.services.impl.pojos;


public class ImpLevelTag {
    private Long typeId;

	private long  valueId;
	

    private String name;


    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public long getValueId() {
        return valueId;
    }

    public void setValueId(long valueId) {
        this.valueId = valueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImpLevelTag(){

    }

    public ImpLevelTag(Long typeId ,String name,Long level){
        this.typeId = typeId;
        this.name =name;
        this.valueId =level;
    }
}
