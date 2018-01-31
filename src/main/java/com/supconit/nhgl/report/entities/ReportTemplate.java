package com.supconit.nhgl.report.entities;

import com.supconit.common.web.entities.AuditExtend;


public class ReportTemplate extends AuditExtend{

    private String code;

    private String name;

    private String content;

    //private String[] contents;

    private int type; //1:月度报表；2：季度报表；3：年度报表

    private int order;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

/*    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }*/

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
