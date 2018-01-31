package com.supconit.nhgl.basic.ngDept.entities;

import hc.base.domains.AuditedSimpleEntity;
import hc.base.domains.ImprovedTree;
import hc.base.domains.Suggestable;
import hc.base.domains.Tableable;
import hc.base.domains.XssFilterSupport;
import hc.orm.search.Conditional;
import hc.orm.search.SQLTerm;
import hc.orm.search.Term;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jodd.util.StringUtil;

import com.alibaba.fastjson.annotation.JSONField;
import com.supconit.common.web.entities.AuditExtend;

public class NhDepartment extends AuditedSimpleEntity implements
		ImprovedTree<NhDepartment>, Tableable, Conditional, Suggestable,
		XssFilterSupport {

	public static final long ROOT_PID = 0L;
	public static final String ROOT_CODE = "ROOT";
	private static final long serialVersionUID = 916432388728025719L;
	public static final String TABLE_NAME = "NH_DEPT";
	protected String companyCode;
	protected Long pid;
	protected Long lft;
	protected Long rgt;
	protected String suggestCode;
	protected NhDepartment parent;
	protected List<NhDepartment> children;

	
	public void setSuggestCode(String suggestCode) {
		this.suggestCode = suggestCode;
	}

	public String getSuggestCode() {
		return this.suggestCode;
	}

	public NhDepartment() {
	}

	public NhDepartment(Long id) {
		setId(id);
	}

	public NhDepartment getParent() {
		return this.parent;
	}

	@JSONField(serialize = false)
	public String getTableName() {
		return "HO_NhDepartment";
	}

	public Long getLft() {
		return this.lft;
	}

	public Long getRgt() {
		return this.rgt;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	public void setParent(NhDepartment parent) {
		this.parent = parent;
	}

	public void setChildren(List<NhDepartment> children) {
		this.children = children;
	}

	public List<NhDepartment> getChildren() {
		return this.children;
	}

	public void addChild(NhDepartment child) {
		if (null == this.children) {
			this.children = new ArrayList();
		}
		this.children.add(child);
	}

	public Term getTerm(String prefix) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList();
		if (null != getId()) {
			buildSQLAnd(sql, prefix, "ID");
			params.add(getId());
		}
		if ((null != getPid()) && (getPid().longValue() > 0L)) {
			buildSQLAnd(sql, prefix, "PID");
			params.add(getPid());
		}
		if (StringUtil.isNotBlank(getCode())) {
			buildSQLLike(sql, prefix, "CODE");
			params.add("%" + getCode() + "%");
		}
		if (StringUtil.isNotBlank(getName())) {
			buildSQLLike(sql, prefix, "NAME");
			params.add("%" + getName() + "%");
		}
		if (StringUtil.isNotBlank(getSuggestCode())) {
			sql.append("AND (");
			if (StringUtil.isNotBlank(prefix)) {
				sql.append(prefix).append(".");
			}
			sql.append("SUGGEST_CODE LIKE ? OR ");
			if (StringUtil.isNotBlank(prefix)) {
				sql.append(prefix).append(".");
			}
			sql.append("NAME LIKE ?");
			sql.append(")");
			params.add(getSuggestCode() + "%");
			params.add(getSuggestCode() + "%");
		}
		if (sql.indexOf("AND ") == 0) {
			sql.delete(0, 3);
		}
		SQLTerm term = new SQLTerm(sql.toString());
		term.setParams(params.toArray());
		return term;
	}

}
