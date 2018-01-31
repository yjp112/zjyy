package com.supconit.common.web.entities;

import java.util.Date;

import hc.base.domains.LongId;
import hc.base.domains.PK;

public class AuditExtend extends LongId implements PK<Long> {

	private static final long	serialVersionUID	= 5225454907469638741L;
	private Long				createId;
	private String				creator;
	private Date				createDate;
	private Long				updateId;
	private String				updator;
	private Date				updateDate;
	private String             orderField;//排序字段，如id asc

	public Long getCreateId() {
		return createId;
	}

	public String getCreator() {
		return creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public String getUpdator() {
		return updator;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

}
