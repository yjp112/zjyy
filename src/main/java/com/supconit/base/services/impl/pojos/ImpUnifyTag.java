package com.supconit.base.services.impl.pojos;


import com.supconit.common.utils.excel.ExcelAnnotation;

public class ImpUnifyTag{
    private Long id;

    @ExcelAnnotation(exportName = "hpid")
    private String hpid;

	@ExcelAnnotation(exportName = "位号名")
	private String tagName;
	
	@ExcelAnnotation(exportName = "位号描述")
	private String description;
	// 13 开关量，15 字符串 其他 模拟量
	@ExcelAnnotation(exportName = "位号类型")
	private String tagTypeName;
	private long tagTypeId;
	
	private long isWrite;
	@ExcelAnnotation(exportName = "是否可写")
	private String isWriteText;
	
	private long isAlarmTag;
	@ExcelAnnotation(exportName = "是否报警点")
	private String isAlarmTagText;
	
	@ExcelAnnotation(exportName = "ON")
	private String ON;	
	@ExcelAnnotation(exportName = "OFF")
	private String OFF;	
	@ExcelAnnotation(exportName = "HH")
	private String HH;	
	@ExcelAnnotation(exportName = "H")
	private String H;	
	@ExcelAnnotation(exportName = "L")
	private String L;	
	@ExcelAnnotation(exportName = "LL")
	private String LL;	
	@ExcelAnnotation(exportName = "PRIN")
	private String PRIN;	
	@ExcelAnnotation(exportName = "NRIN")
	private String NRIN;
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTagTypeName() {
		return tagTypeName;
	}
	public void setTagTypeName(String tagTypeName) {
		this.tagTypeName = tagTypeName;
	}
	public long getTagTypeId() {
        if(tagTypeName!=null&&!"".equals(tagTypeName)){
            if("开关量".equals(tagTypeName)){
                tagTypeId = 13;
            }else if("开关量".equals(tagTypeName)){
                tagTypeId = 15;
            }else{
                tagTypeId = 14;
            }
        }
		return tagTypeId;
	}
	public void setTagTypeId(long tagTypeId) {
		this.tagTypeId = tagTypeId;
	}
	public long getIsWrite() {
        if(isWriteText!=null&&!"".equals(isWriteText)){
            if("是".equals(isWriteText)){
                isWrite = 1;
            }else{
                isWrite = 0;
            }
        }

        return isWrite;
	}
	public void setIsWrite(long isWrite) {
		this.isWrite = isWrite;
	}
	public String getIsWriteText() {
		return isWriteText;
	}
	public void setIsWriteText(String isWriteText) {
		this.isWriteText = isWriteText;
	}
	public long getIsAlarmTag() {
        if(isAlarmTagText!=null&&!"".equals(isAlarmTagText)){
            if("报警点".equals(isAlarmTagText)){
                isAlarmTag = 1;
            }else if("运行点".equals(isAlarmTagText)){
                isAlarmTag = 0;
            }else{
                isAlarmTag = 2;
            }
        }
		return isAlarmTag;
	}
	public void setIsAlarmTag(long isAlarmTag) {
		this.isAlarmTag = isAlarmTag;
	}
	public String getIsAlarmTagText() {
		return isAlarmTagText;
	}
	public void setIsAlarmTagText(String isAlarmTagText) {
		this.isAlarmTagText = isAlarmTagText;
	}
	public String getON() {
		return ON;
	}
	public void setON(String oN) {
		ON = oN;
	}
	public String getOFF() {
		return OFF;
	}
	public void setOFF(String oFF) {
		OFF = oFF;
	}
	public String getHH() {
		return HH;
	}
	public void setHH(String hH) {
		HH = hH;
	}
	public String getH() {
		return H;
	}
	public void setH(String h) {
		H = h;
	}
	public String getL() {
		return L;
	}
	public void setL(String l) {
		L = l;
	}
	public String getLL() {
		return LL;
	}
	public void setLL(String lL) {
		LL = lL;
	}
	public String getPRIN() {
		return PRIN;
	}
	public void setPRIN(String pRIN) {
		PRIN = pRIN;
	}
	public String getNRIN() {
		return NRIN;
	}
	public void setNRIN(String nRIN) {
		NRIN = nRIN;
	}

    public String getHpid() {
        return hpid;
    }

    public void setHpid(String hpid) {
        this.hpid = hpid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
