package com.supconit.visitor.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supconit.base.domains.Organization;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.utils.excel.ExcelAnnotation;
import com.supconit.common.web.entities.AuditExtend;


public class VisitorReservation extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	@ExcelAnnotation(exportName = "接待人部门")
	private String deptName;
	@ExcelAnnotation(exportName = "接待人姓名")
	private String reversationName;
	@ExcelAnnotation(exportName = "访客姓名")
	private String visitors;
	@ExcelAnnotation(exportName = "访客人数")
	private Integer expectVisitors;  //预期来访人数
	@ExcelAnnotation(exportName = "预约来访时间")
	private Date visitTime;        //来访时间
	@ExcelAnnotation(exportName = "来访事由")
	private String visitReason;      //来访事由 0:商务;1:技术交流;2:会议;3:项目实施;4:其他
	@ExcelAnnotation(exportName = "访客状态")
	private String visitStatus;      //来访状态0:未访;1:在访;2:已访
	@ExcelAnnotation(exportName = "登记人")
	private String creator;
	@ExcelAnnotation(exportName = "登记时间")
	private Date createDate;
	
	private Long receptorID;         //接待人员ID
	private Integer visitDays;       //预计来访天数
	
	
	private String remark;           //备注
	
	private Long grantPersonID;      //发证人ID
	private Date grantTime;          //发证时间
	private Long revokePersonID;     //收证人ID
	private Date revokeTime;         //收证时间
	private Date actualVisitTime;    //实际来访时间
	private Date actualLeaveTime;    //实际离开时间
	private Date expectLeaveTime;    //预计离开时间
	private Integer visitCars;		 //车辆数量	
	private String visitCarNos;		 //车牌号
	
	private Integer actualVisitors;  //实际来访人数
	//扩展Bean
	private String visitorName;
	private Long deptNameId;
	private List<Long> deptChildIds;
	private String visitorCardNo;
    private boolean flag;           //flag为true代表来访登记和离开登记查询，为false代表所有访客的查询
    
	private Date startTime;       //List页面查询条件,starttime.
    private Date endTime;         //List页面查询条件,endtime.

    /** 手机端查询条件 **/
    private long endNum;//结束行数
    private long startNum;//起始行数
    private long visitorId;//查询初始的数据最后一行ID
    private long visitPersonId;//接待人ID
    private String visitorDate;//近几个月
    private String startDate;//接待访客起始日期
    private String endDate;//接待访客结束日期
    private long visitorTotal;//总共条数
    private String visitorSearch;//查询条件
    private long rw;//数据行数
    private String startVisit;//YYYY/MM/dd格式的起始访问日期
    private String endVisit;//结束访问日期
    private String isToday;//是否今天来访
    private String visitReasonName;
     
	private List<Visitor> visitorList = new ArrayList<Visitor>();
	
	private Visitor visitor = new Visitor();
	
	public String getVisitors() {
		if(this.visitors==null){
			return "";
		}
		return visitors.replace(",", "");
	}
	public void setVisitors(String visitors) {
		this.visitors = visitors;
	}
	
    public Visitor getVisitor() {
		return visitor;
	}
	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	public Long getReceptorID()
    {
        return receptorID;
    }
    public void setReceptorID(Long receptorID)
    {
        this.receptorID = receptorID;
    }
    public Integer getVisitDays()
    {
        return visitDays;
    }
    public void setVisitDays(Integer visitDays)
    {
        this.visitDays = visitDays;
    }
    public Date getVisitTime()
    {
        return visitTime;
    }
    public void setVisitTime(Date visitTime)
    {
        this.visitTime = visitTime;
    }
	public String getVisitReason()
    {
        return visitReason;
    }
    public void setVisitReason(String visitReason)
    {
        this.visitReason = visitReason;
    }
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    public String getVisitStatus()
    {
        return visitStatus;
    }
    public void setVisitStatus(String visitStatus)
    {
        this.visitStatus = visitStatus;
    }
    
    public Long getGrantPersonID()
    {
        return grantPersonID;
    }
    public void setGrantPersonID(Long grantPersonID)
    {
        this.grantPersonID = grantPersonID;
    }
    public Date getGrantTime()
    {
        return grantTime;
    }
    public void setGrantTime(Date grantTime)
    {
        this.grantTime = grantTime;
    }
    public Long getRevokePersonID()
    {
        return revokePersonID;
    }
    public void setRevokePersonID(Long revokePersonID)
    {
        this.revokePersonID = revokePersonID;
    }
    public Date getRevokeTime()
    {
        return revokeTime;
    }
    public void setRevokeTime(Date revokeTime)
    {
        this.revokeTime = revokeTime;
    }
    public Date getActualVisitTime()
    {
        return actualVisitTime;
    }
    public void setActualVisitTime(Date actualVisitTime)
    {
        this.actualVisitTime = actualVisitTime;
    }
    public String getReversationName()
    {
        return reversationName;
    }
    public void setReversationName(String reversationName)
    {
        this.reversationName = reversationName;
    }
    public String getVisitorName()
    {
        return visitorName;
    }
    public void setVisitorName(String visitorName)
    {
        this.visitorName = visitorName;
    }
    public List<Visitor> getVisitorList()
    {
        return visitorList;
    }
    public void setVisitorList(List<Visitor> visitorList)
    {
        this.visitorList = visitorList;
    }
    
    public Long getDeptNameId()
    {
        return deptNameId;
    }
    public void setDeptNameId(Long deptNameId)
    {
        this.deptNameId = deptNameId;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    
    public List<Long> getDeptChildIds()
    {
        return deptChildIds;
    }
    public void setDeptChildIds(List<Long> deptChildIds)
    {
        this.deptChildIds = deptChildIds;
    }
    public Date getStartTime()
    {
        return startTime;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    public Date getEndTime()
    {
        return endTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    public String getVisitorCardNo()
    {
        return visitorCardNo;
    }
    public void setVisitorCardNo(String visitorCardNo)
    {
        this.visitorCardNo = visitorCardNo;
    }
    public Date getActualLeaveTime()
    {
        return actualLeaveTime;
    }
    public void setActualLeaveTime(Date actualLeaveTime)
    {
        this.actualLeaveTime = actualLeaveTime;
    }
    public Integer getExpectVisitors()
    {
        return expectVisitors;
    }
    public void setExpectVisitors(Integer expectVisitors)
    {
        this.expectVisitors = expectVisitors;
    }
    public Integer getActualVisitors()
    {
        return actualVisitors;
    }
    public void setActualVisitors(Integer actualVisitors)
    {
        this.actualVisitors = actualVisitors;
    }
    public boolean isFlag()
    {
        return flag;
    }
    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
    public Date getExpectLeaveTime() {
		return expectLeaveTime;
	}
	public void setExpectLeaveTime(Date expectLeaveTime) {
		this.expectLeaveTime = expectLeaveTime;
	}
	public Integer getVisitCars() {
		return visitCars;
	}
	public void setVisitCars(Integer visitCars) {
		this.visitCars = visitCars;
	}
	public String getVisitCarNos() {
		return visitCarNos;
	}
	public void setVisitCarNos(String visitCarNos) {
		this.visitCarNos = visitCarNos;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	//转义--------------------------------------
	public String getDeptName() {
		if(this.receptorID==null){
			return "";
		}
  		List<Organization> orList = OrganizationUtils.getFullDeptNameByPersonId(this.receptorID);
  		deptName = OrganizationUtils.joinFullDeptName(orList);
		return deptName;
	}

    public long getEndNum() {
        return endNum;
    }

    public void setEndNum(long endNum) {
        this.endNum = endNum;
    }

    public long getStartNum() {
        return startNum;
    }

    public void setStartNum(long startNum) {
        this.startNum = startNum;
    }

    public long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }

    public long getVisitPersonId() {
        return visitPersonId;
    }

    public void setVisitPersonId(long visitPersonId) {
        this.visitPersonId = visitPersonId;
    }

    public String getVisitorDate() {
        return visitorDate;
    }

    public void setVisitorDate(String visitorDate) {
        this.visitorDate = visitorDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public long getVisitorTotal() {
        return visitorTotal;
    }

    public void setVisitorTotal(long visitorTotal) {
        this.visitorTotal = visitorTotal;
    }

    public String getVisitorSearch() {
        return visitorSearch;
    }

    public void setVisitorSearch(String visitorSearch) {
        this.visitorSearch = visitorSearch;
    }

    public long getRw() {
        return rw;
    }

    public void setRw(long rw) {
        this.rw = rw;
    }

    public String getStartVisit() {
        return startVisit;
    }

    public void setStartVisit(String startVisit) {
        this.startVisit = startVisit;
    }

    public String getEndVisit() {
        return endVisit;
    }

    public void setEndVisit(String endVisit) {
        this.endVisit = endVisit;
    }

    public String getIsToday() {
        return isToday;
    }

    public void setIsToday(String isToday) {
        this.isToday = isToday;
    }

    public String getVisitReasonName() {
        return visitReasonName;
    }

    public void setVisitReasonName(String visitReasonName) {
        this.visitReasonName = visitReasonName;
    }
}
