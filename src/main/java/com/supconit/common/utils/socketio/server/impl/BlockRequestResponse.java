package com.supconit.common.utils.socketio.server.impl;

import java.util.Arrays;
import java.util.List;

import com.supconit.common.utils.socketio.server.IRequestResponseData;

public class BlockRequestResponse implements IRequestResponseData{
	//private SocketIOClient client;
	private String requestType;
	private String categoryCode;
	private String geoareaCode;
	private String tuCeng;
	private HpidReadBlock[] blocks;
	private Object responseMsg;
	
	/*public SocketIOClient getClient() {
		return client;
	}
	public void setClient(SocketIOClient client) {
		this.client = client;
	}*/
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getGeoareaCode() {
		return geoareaCode;
	}
	public void setGeoareaCode(String geoareaCode) {
		this.geoareaCode = geoareaCode;
	}
	public String getTuCeng() {
		return tuCeng;
	}
	public void setTuCeng(String tuCeng) {
		this.tuCeng = tuCeng;
	}
	public HpidReadBlock[] getBlocks() {
		return blocks;
	}
	/*public void setBlocks(HpidReadBlock[] blocks) {
		this.blocks = blocks;
	}*/
	public void setBlocks(List<HpidReadBlock> blocks) {
		this.blocks = blocks.toArray(new HpidReadBlock[0]);
	}
	@Override
	public String getDebugMsg() {
		return toString();
	}
	
	@Override
	public String toString() {
		return "[requestType=" + requestType +",blocks.size="+(blocks==null?0:blocks.length) +", blocks="
				+ Arrays.toString(blocks) + "]";
	}
	@Override
	public void setResponseMsg(Object o) {
		/*if(o!=null||o instanceof List<?>){
			this.returnBlocks=(List<HpidReadBlock>) o;
		}*/
		this.responseMsg=o;
	}
	@Override
	public Object getResponseMsg() {
		return responseMsg;
	}
	
	
}
