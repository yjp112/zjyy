package com.supconit.common.utils;

import java.io.Serializable;

/**
 * @文件名: ResponseMsg
 * @创建日期: 13-7-11
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
public class ResponseMsg implements Serializable {
	public final static int		CLOSE_CURRENT		= 1000;	// 关闭当前窗口
	public final static int		REFRESH_PARENT		= 2000;	// 刷新窗口
	public final static int		REDIRECT			= 3000;	// 重定向
	public final static int		REFRESH_TREE		= 4000;	// 刷新树结构

	/* 返回提示框信息 */
	private final static String	DEFAULT_SUCCESS_MSG	= "操作成功";
	private final static String	DEFAULT_FAILURE_MSG	= "操作失败";
	/* 返回状态信息 */
	private final static int	SUCCESS				= 200;
	private final static int	FAILURE				= 300;

	private int					status;						// 状态

	private String				message;						// 提示框信息

	private int					callbackType;					// 返回操作类型 关闭窗口;刷新父窗口;重定向；刷新树结构

	private String				url;

	private String				exception;

	public ResponseMsg() {
		super();
	}

	public ResponseMsg(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public static ResponseMsg SUCCESS() {
		return new ResponseMsg(SUCCESS, DEFAULT_SUCCESS_MSG);
	}

	public static ResponseMsg SUCCESS(String msg) {
		return new ResponseMsg(SUCCESS, msg);
	}

	public static ResponseMsg FAILURE() {
		return new ResponseMsg(FAILURE, DEFAULT_FAILURE_MSG);
	}

	public static ResponseMsg FAILURE(String msg) {
		return new ResponseMsg(FAILURE, msg);
	}

	public static ResponseMsg FAILURE(String msg, String exception) {
		ResponseMsg resMsg = FAILURE(msg);
		resMsg.setException(exception);
		return resMsg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(int callbackType) {
		this.callbackType = callbackType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
}
