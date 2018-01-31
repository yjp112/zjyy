package com.supconit.visitor.entities;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

/**
 * 统计类
 * @author yuhuan
 * @日期 2015/07
 */
public class VisitorReport extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private String visitDeptName;//访问部门
	private String visitMonth;//访问月份
	private String visitPurpose;//访问事由
	private long visitorNum;//访客数量
	
	//查询用---start
	private Date startMonth;//开始月份
	private String startMonths;//开始月份
	private Date endMonth;//结束月份
	private String endMonths;//结束月份
	private String visitYear;//来访年份
	//查询用---end
	
	
	public String getVisitDeptName() {
		return visitDeptName;
	}
	public void setVisitDeptName(String visitDeptName) {
		this.visitDeptName = visitDeptName;
	}
	public void setVisitMonth(String visitMonth) {
		this.visitMonth = visitMonth;
	}
	public void setVisitPurpose(String visitPurpose) {
		this.visitPurpose = visitPurpose;
	}
	public long getVisitorNum() {
		return visitorNum;
	}
	public void setVisitorNum(long visitorNum) {
		this.visitorNum = visitorNum;
	}
	public Date getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(Date startMonth) {
		this.startMonth = startMonth;
	}
	public String getStartMonths() {
		return startMonths;
	}
	public void setStartMonths(String startMonths) {
		this.startMonths = startMonths;
	}
	public Date getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(Date endMonth) {
		this.endMonth = endMonth;
	}
	public String getEndMonths() {
		return endMonths;
	}
	public void setEndMonths(String endMonths) {
		this.endMonths = endMonths;
	}
	public String getVisitYear() {
		return visitYear;
	}
	public void setVisitYear(String visitYear) {
		this.visitYear = visitYear;
	}
	
	//转义--------------------------------------
	public String getVisitMonth() {
		if(StringUtils.isEmpty(visitMonth)){
			return "";
		}
		String arr[] = visitMonth.split("-");
		visitMonth = arr[0] + (arr[1].length()==1? "0"+arr[1]:arr[1]);
		return visitMonth;
	}
	public String getVisitPurpose() {
		return DictUtils.getDictLabel(DictTypeEnum.VISIT_REASON, this.visitPurpose);
	}
	
}
