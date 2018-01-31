package com.supconit.repair.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;


public class RepairSpare extends AuditExtend {
	private String repairCode;		// 维修单编号
	private Integer	serialNo;		// 序号
	private Long	spareId;	// 备件Id
	private String	spareCode;	// 备件编码
	private String	spareName;	// 备件名称
	private String	spareSpec;	// 规格型号
	private Long	qty;		// 数量
	private Double	price;		// 单价
	private Double	money;		// 总价
	private String	remark;		// 备注
    private Integer spareType;  //备件类型  0：耗材 1：随机备件
    private Date stockTime;     //订货时间
    private Date comeTime;      //到货时间

    public Long getSpareId() {
        return spareId;
    }

    public void setSpareId(Long spareId) {
        this.spareId = spareId;
    }

    public String getSpareCode() {
        return spareCode;
    }

    public void setSpareCode(String spareCode) {
        this.spareCode = spareCode;
    }

    public String getSpareName() {
        return spareName;
    }

    public void setSpareName(String spareName) {
        this.spareName = spareName;
    }

    public String getSpareSpec() {
        return spareSpec;
    }

    public void setSpareSpec(String spareSpec) {
        this.spareSpec = spareSpec;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getRepairCode() {
		return repairCode;
	}

	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getSpareType() {
		return spareType;
	}

	public void setSpareType(Integer spareType) {
		this.spareType = spareType;
	}

	public Date getStockTime() {
		return stockTime;
	}

	public void setStockTime(Date stockTime) {
		this.stockTime = stockTime;
	}

	public Date getComeTime() {
		return comeTime;
	}

	public void setComeTime(Date comeTime) {
		this.comeTime = comeTime;
	}

}
