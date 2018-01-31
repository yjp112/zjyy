package com.supconit.mobile.pick.entities;

/**
 * Created by wangwei on 2016-4-15.
 */
public class MobilePicker{

    private String value;
    private String displayValue;
    //DutyGroupPersonPicker
    private String personIdJson;
    private String personNameJson;

    //维修详情--维修模式
    private String repairMode;
    private String repairModeName;

    //访客管理--来访事由
    private String visitReason;//来访事由
    private String visitReasonName;//来访事由名称

    public String getPersonNameJson() {
        return personNameJson;
    }

    public void setPersonNameJson(String personNameJson) {
        this.personNameJson = personNameJson;
    }

    public String getPersonIdJson() {
        return personIdJson;
    }

    public void setPersonIdJson(String personIdJson) {
        this.personIdJson = personIdJson;
    }

    public String getRepairMode() {
        return repairMode;
    }

    public void setRepairMode(String repairMode) {
        this.repairMode = repairMode;
    }

    public String getRepairModeName() {
        return repairModeName;
    }

    public void setRepairModeName(String repairModeName) {
        this.repairModeName = repairModeName;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getVisitReasonName() {
        return visitReasonName;
    }

    public void setVisitReasonName(String visitReasonName) {
        this.visitReasonName = visitReasonName;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
