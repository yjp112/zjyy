package com.supconit.nhgl.report.utils;

public enum  ReportSpecEnum {

    TEST("TEST_CODE");

    private String code;

    ReportSpecEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}
