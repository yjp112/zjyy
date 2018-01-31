package com.supconit.visitor.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;

/**
 * 访客信息类
 * @author huanghaitao
 */
public class Visitor extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private Long reservationId;//访客预约ID
	private String visitorName;//访客姓名 
	private String visitorUnit;//访客所在单位
	private String visitorCardType;//访客证件类型  0:身份证;1:护照;2:驾驶证;3:军官证;4:其他
	private String visitorCardNum;//访客证件号码
	private String visitorPhone;//访客联系方式
	private String visitorCardNo;//访客证号码
	private String visitorReturnCard;//是否归还证件 0：未归还;1：已归还
	private Date grantTime;        //发卡时间
	private Date returnTime;        //创建时间
	
	public Long getReservationId()
    {
        return reservationId;
    }
    public Date getGrantTime() {
		return grantTime;
	}
	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public void setReservationId(Long reservationId)
    {
        this.reservationId = reservationId;
    }
    public String getVisitorName()
    {
        return visitorName;
    }
    public void setVisitorName(String visitorName)
    {
        this.visitorName = visitorName;
    }
    public String getVisitorUnit()
    {
        return visitorUnit;
    }
    public void setVisitorUnit(String visitorUnit)
    {
        this.visitorUnit = visitorUnit;
    }
    public String getVisitorCardType()
    {
        return visitorCardType;
    }
    public void setVisitorCardType(String visitorCardType)
    {
        this.visitorCardType = visitorCardType;
    }
    public String getVisitorCardNum()
    {
        return visitorCardNum;
    }
    public void setVisitorCardNum(String visitorCardNum)
    {
        this.visitorCardNum = visitorCardNum;
    }
    public String getVisitorPhone()
    {
        return visitorPhone;
    }
    public void setVisitorPhone(String visitorPhone)
    {
        this.visitorPhone = visitorPhone;
    }
    
    public String getVisitorCardNo()
    {
        return visitorCardNo;
    }
    public void setVisitorCardNo(String visitorCardNo)
    {
        this.visitorCardNo = visitorCardNo;
    }
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    public String getVisitorReturnCard()
    {
        return visitorReturnCard;
    }
    public void setVisitorReturnCard(String visitorReturnCard)
    {
        this.visitorReturnCard = visitorReturnCard;
    }
    
}
