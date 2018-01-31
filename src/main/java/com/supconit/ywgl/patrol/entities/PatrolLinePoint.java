package com.supconit.ywgl.patrol.entities;

import com.supconit.common.web.entities.AuditExtend;

public class PatrolLinePoint extends AuditExtend {

	private static final long	serialVersionUID	= 1L;

	
	private Long lineId;             //LINE_ID	巡更路线ID		BIGINT
	private  String lineNmae;        //线路名称
	private Long pointId;            //POINT_ID	巡更点ID		BIGINT
	private String pointName;        //点名称
	private Long sortIdx;            //SORT_IDX	序号		INT
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public String getLineNmae() {
		return lineNmae;
	}
	public void setLineNmae(String lineNmae) {
		this.lineNmae = lineNmae;
	}
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public Long getSortIdx() {
		return sortIdx;
	}
	public void setSortIdx(Long sortIdx) {
		this.sortIdx = sortIdx;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
