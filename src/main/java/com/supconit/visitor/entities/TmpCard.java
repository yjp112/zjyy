package com.supconit.visitor.entities;

import com.supconit.common.web.entities.AuditExtend;

import java.util.Date;

/**
 * 临时卡办理bean
 * @author zx
 */
public class TmpCard extends AuditExtend{
	private static final long serialVersionUID = 1L;

    private Long personId;//员工id
    private String personName;//员工name
    private String cardNo;//员工卡号
    private String tmpCardNo;//员工临时卡号
    private Date createDate;//发卡时间

    private Date startTime;//查询使用
    private Date endTime;//查询使用
    private String departId;//查询使用

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTmpCardNo() {
        return tmpCardNo;
    }

    public void setTmpCardNo(String tmpCardNo) {
        this.tmpCardNo = tmpCardNo;
    }

    public Date getEventTime() {
        return createDate;
    }

    public void setEventTime(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }
}
