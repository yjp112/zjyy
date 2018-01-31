package com.supconit.common.utils.socketio.server.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supconit.common.utils.DateUtils;
//import com.supconit.montrol.entity.MReadBlock;

public class HpidReadBlock {//extends MReadBlock
	private static Logger log = LoggerFactory.getLogger(HpidReadBlock.class);
	private String hpid;
	private Date updateTime;
	private String SpringEL;

	
	private String value;
	private String block;
	//private List<String> humanReadStatus;//人性化，可读的状态信息，往往页面展示时需要用到
	//以下是扩展字段
	private String str1;
	private String str2;
	private String str3;
	private String str4;
	private String str5;
	
	public HpidReadBlock() {
		super();
	}

	public HpidReadBlock(String hpid, String block) {
		this.hpid = hpid;
		this.setBlock(block);
	}

	public HpidReadBlock(String hpid, String block, String springEL) {
		this.hpid = hpid;
		this.setBlock(block);
		SpringEL = springEL;
	}

	public String getHpid() {
		return hpid;
	}

	public void setHpid(String hpid) {
		this.hpid = hpid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getSpringEL() {
		return SpringEL;
	}

	public void setSpringEL(String springEL) {
		SpringEL = springEL;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getStr4() {
		return str4;
	}

	public void setStr4(String str4) {
		this.str4 = str4;
	}

	public String getStr5() {
		return str5;
	}

	public void setStr5(String str5) {
		this.str5 = str5;
	}
	
	
	
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public boolean isPointValidate() {
    	if(StringUtils.isBlank(getValue())||"-".equals(getValue())){
    		return false;
    	}
        
        return true;
    }
	@Override
	public String toString() {
		return "HpidReadBlock [hpid=" + hpid + ", getBlock()=" + getBlock()
				+ ", getValue()="+ getValue()
				+",updateTime="+DateUtils.format(updateTime)
				+ ", getDwQualtiy()=" + "//" + "]";//getDwQualtiy()
	}
	/*public List<String> getHumanReadStatus() {
		//return humanReadStatus;
		if(humanReadStatus==null||humanReadStatus.size()<=0){
			return "";
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append(humanReadStatus.get(0));
		for (int i = 1; i < humanReadStatus.size(); i++) {
			buffer.append(",").append(humanReadStatus.get(i));
		}
		return buffer.toString();
	}*/

	/*public void setHumanReadStatus(List<String> humanReadStatus) {
		this.humanReadStatus = humanReadStatus;
	}*/

	/*@Override
	public String toString() {
		return "HpidReadBlock [hpid=" + hpid + ", getBlock()=" + getBlock()
				+ ", getValue()="+ getValue()
				+",humanReadStatus="+humanReadStatus
				+",updateTime="+DateUtils.format(updateTime)
				+ ", getDwQualtiy()=" + getDwQualtiy() + "]";
	}*/

	/*@Override
	public String toString() {
		return "HpidReadBlock [hpid=" + hpid + ", getBlock()=" + getBlock()
				+ ", getValue()="+ getValue()
				+",humanReadStatus="+humanReadStatus
				+",updateTime="+DateUtils.format(updateTime)
				+ ", getDwQualtiy()=" + getDwQualtiy() + "]";
	}*/

}
