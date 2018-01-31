package com.supconit.nhgl.utils;

public enum TaskTypeEnum {


    DEVICE_STATE(1,"设备运行状态"),ELECTRIC(2,"电"),WATER(3,"水"),GAS(4,"气");

    private int no;
    private String desc;

    TaskTypeEnum(int no,String desc) {
        this.no = no;
        this.desc = desc;
    }

    public int getNo(){
        return this.no;
    }

    public String getDesc(){
        return this.desc;
    }

}
