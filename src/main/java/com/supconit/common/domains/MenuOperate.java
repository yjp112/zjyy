package com.supconit.common.domains;

import com.supconit.common.web.entities.AuditExtend;

public class MenuOperate  extends AuditExtend{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2005314507188749668L;
	//Menu
	private Long			mid;
	private String			mcode;
	private String			mparentcode;
	private String			mname;
	private String			mdescription;
	private String				mshowIcon;
	private String				mlinkUrl;
	private String				mresources;
	private Integer				msortValue;
	private boolean			mdisplay				= true;
	private Long				mlft;
	private Long				mrgt;
	private Integer				mlvl;
	private Long				mpid;

	//Operate
	private String			ocode;
	private String			oname;
	private String			odescription;	
	private Long			omenuId;
	private String		    oresources;
	private String			oicon;
	private String			oonclick;
	private Boolean			olistButton			= false;
	private Boolean			ocolumnButton		= false;
	private Integer			osortValue;
	public String getMcode() {
		return mcode;
	}
	public void setMcode(String mcode) {
		this.mcode = mcode;
	}
	
	public String getMparentcode() {
		return mparentcode;
	}
	public void setMparentcode(String mparentcode) {
		this.mparentcode = mparentcode;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMdescription() {
		return mdescription;
	}
	public void setMdescription(String mdescription) {
		this.mdescription = mdescription;
	}
	public String getMshowIcon() {
		return mshowIcon;
	}
	public void setMshowIcon(String mshowIcon) {
		this.mshowIcon = mshowIcon;
	}
	public String getMlinkUrl() {
		return mlinkUrl;
	}
	public void setMlinkUrl(String mlinkUrl) {
		this.mlinkUrl = mlinkUrl;
	}
	public String getMresources() {
		return mresources;
	}
	public void setMresources(String mresources) {
		this.mresources = mresources;
	}
	public Integer getMsortValue() {
		return msortValue;
	}
	public void setMsortValue(Integer msortValue) {
		this.msortValue = msortValue;
	}
	public boolean getMdisplay() {
		return mdisplay;
	}
	public void setMdisplay(boolean mdisplay) {
		this.mdisplay = mdisplay;
	}
	public Long getMlft() {
		return mlft;
	}
	public void setMlft(Long mlft) {
		this.mlft = mlft;
	}
	public Long getMrgt() {
		return mrgt;
	}
	public void setMrgt(Long mrgt) {
		this.mrgt = mrgt;
	}
	public Long getMpid() {
		return mpid;
	}
	public void setMpid(Long mpid) {
		this.mpid = mpid;
	}
	public String getOcode() {
		return ocode;
	}
	public void setOcode(String ocode) {
		this.ocode = ocode;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public String getOdescription() {
		return odescription;
	}
	public void setOdescription(String odescription) {
		this.odescription = odescription;
	}
	public Long getOmenuId() {
		return omenuId;
	}
	public void setOmenuId(Long omenuId) {
		this.omenuId = omenuId;
	}
	public String getOresources() {
		return oresources;
	}
	public void setOresources(String oresources) {
		this.oresources = oresources;
	}
	public String getOicon() {
		return oicon;
	}
	public void setOicon(String oicon) {
		this.oicon = oicon;
	}
	public String getOonclick() {
		return oonclick;
	}
	public void setOonclick(String oonclick) {
		this.oonclick = oonclick;
	}
	public Boolean getOlistButton() {
		return olistButton;
	}
	public void setOlistButton(Boolean olistButton) {
		this.olistButton = olistButton;
	}
	public Boolean getOcolumnButton() {
		return ocolumnButton;
	}
	public void setOcolumnButton(Boolean ocolumnButton) {
		this.ocolumnButton = ocolumnButton;
	}
	public Integer getOsortValue() {
		return osortValue;
	}
	public void setOsortValue(Integer osortValue) {
		this.osortValue = osortValue;
	}
	public Long getMid() {
		return mid;
	}
	public void setMid(Long mid) {
		this.mid = mid;
	}
	public Integer getMlvl() {
		return mlvl;
	}
	public void setMlvl(Integer mlvl) {
		this.mlvl = mlvl;
	}
	
} 