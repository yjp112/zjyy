/**
 * 
 */
package com.supconit.nhgl.schedule.entites;
										

/**
 * @author 
 * @create 2014-06-17 00:48:22 
 * @since 
 * 
 */
public class TaskCatagory extends hc.base.domains.LongId{

	private static final long	serialVersionUID	= 1L;
		
			
	private String catagoryText;		
	private String catagoryCode;		
	private String parentCode;		
	private String remark;		
		
					
	public String getCatagoryText() {
		return catagoryText;
	}
	
	public void setCatagoryText(String catagoryText) {
		this.catagoryText = catagoryText;
	}
				
	public String getCatagoryCode() {
		return catagoryCode;
	}
	
	public void setCatagoryCode(String catagoryCode) {
		this.catagoryCode = catagoryCode;
	}
				
	public String getParentCode() {
		return parentCode;
	}
	
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
				
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
			
	
}
