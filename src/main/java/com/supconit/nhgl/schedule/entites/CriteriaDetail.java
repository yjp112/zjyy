/**
 * 
 */
package com.supconit.nhgl.schedule.entites;

import java.math.BigDecimal;
												

/**
 * @author 
 * @create 2014-06-16 18:01:37 
 * @since 
 * 
 */
public class CriteriaDetail extends hc.base.domains.LongId{

	private static final long	serialVersionUID	= 1L;
		
			
	private String taskCode;		
	private String dtCode;		
	private String crCondition;		
	private BigDecimal crScore;		
	private Long crLevel;
	
					
	public Long getCrLevel() {
		return crLevel;
	}

	public void setCrLevel(Long crLevel) {
		this.crLevel = crLevel;
	}

	public String getTaskCode() {
		return taskCode;
	}
	
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
				
	public String getDtCode() {
		return dtCode;
	}
	
	public void setDtCode(String dtCode) {
		this.dtCode = dtCode;
	}
				
	public String getCrCondition() {
		return crCondition;
	}
	
	public void setCrCondition(String crCondition) {
		this.crCondition = crCondition;
	}
				
	public BigDecimal getCrScore() {
		return crScore;
	}
	
	public void setCrScore(BigDecimal crScore) {
		this.crScore = crScore;
	}
			
	
}
