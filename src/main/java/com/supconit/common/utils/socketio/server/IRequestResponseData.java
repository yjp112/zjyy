package com.supconit.common.utils.socketio.server;


public interface IRequestResponseData {
	/*@JsonIgnore
	public SocketIOClient getClient();

	public void setClient(SocketIOClient client);*/
	
	public String getDebugMsg();
	
	public void setResponseMsg(Object o);
	public Object getResponseMsg();
}
