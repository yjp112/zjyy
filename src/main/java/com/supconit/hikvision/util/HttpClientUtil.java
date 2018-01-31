package com.supconit.hikvision.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;

/**
 * <p></p>
 *
 * @author  caoshiyan 
 * @version V1.0  
 * @date 2015年12月29日 下午1:35:35 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年12月29日
 * @since  
 */
public class HttpClientUtil {
	
	public static final String DEFAULT_PRE_URL = "http://172.20.112.151/webapi/service/"; //此处替换成平台SDK所在服务器IP与端口
	public static final String DEFAULT_APPKEY = "b408fd60";//此处替换成申请的appkey
	public static final String DEFAULT_SECRET = "543f361f0bcc46dc83eb71fb1d3ab879";//此处替换成申请的secret
	
	/**
	 * <p>
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 下午12:36:08
	 * @param interfaceName
	 * @param paramMap
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年12月29日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public static String doGet(String interfaceName, Map<String, Object> paramMap) {
		return doGet(DEFAULT_PRE_URL, interfaceName, paramMap, DEFAULT_APPKEY, DEFAULT_SECRET);
	}
	
	/**
	 * <p>
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 下午12:36:06
	 * @param preUrl
	 * @param interfaceName
	 * @param paramMap
	 * @param appkey
	 * @param secret
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年12月29日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public static String doGet(String preUrl, String interfaceName, Map<String, Object> paramMap, String appkey,
	        String secret) {
		StringBuffer sb = new StringBuffer("appkey=" + appkey + "&time=" + System.currentTimeMillis());
		// 遍历map
		if (paramMap != null) {
			for (String key : paramMap.keySet()) {
				sb.append("&" + key + "=" + paramMap.get(key));
			}
		}
		String token = TokenGenerateUtil.buildToken(preUrl + interfaceName + "?" + sb.toString(), null, secret);
		String urlStr = preUrl + interfaceName + "?" + sb.toString() + "&token=" + token;
		// HttpClient实例
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("utf-8");
//		// POST方法实例
		GetMethod getMethod = new GetMethod(urlStr);
		try {
			// 执行postMethod
			httpClient.executeMethod(getMethod);
			
			// 获得返回的响应
			InputStream is = getMethod.getResponseBodyAsStream();
			return IOUtils.toString(is,"utf-8");
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return null;
	}
	
	/**
	 * <p>
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 下午12:35:55
	 * @param interfaceName
	 * @param paramMap
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年12月29日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public static String doPost(String interfaceName, Map<String, Object> paramMap) {
		return doPost(DEFAULT_PRE_URL, interfaceName, paramMap, DEFAULT_APPKEY, DEFAULT_SECRET);
	}
	
	/**
	 * <p>
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 下午12:35:58
	 * @param preUrl
	 * @param interfaceName
	 * @param paramMap
	 * @param appkey
	 * @param secret
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年12月29日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public static String doPost(String preUrl, String interfaceName, Map<String, Object> paramMap, String appkey,
	        String secret) {
		if (paramMap != null) {
			if (paramMap.get("appkey") == null) {
				paramMap.put("appkey", appkey);
			}
			if (paramMap.get("time") == null) {
				paramMap.put("time", System.currentTimeMillis());
			}
		} else {
			paramMap = new HashMap<String, Object>();
			paramMap.put("appkey", appkey);
			paramMap.put("time", System.currentTimeMillis());
		}
		String paramStr = JsonUtils.object2Json(paramMap);
		String token = TokenGenerateUtil.buildToken(preUrl + interfaceName, paramStr, secret);
		String urlStr = preUrl + interfaceName + "?token=" + token;
		HttpClient httpClient = new HttpClient();
		
		PostMethod postMethod = null;
		try {
			StringRequestEntity entity = new StringRequestEntity(paramStr, "application/json", "utf-8");
			// System.out.println(params+entity.getContent());
			// 使用POST方法
			postMethod = new PostMethod(urlStr);
			postMethod.setRequestEntity(entity);
			httpClient.executeMethod(postMethod);
			
			InputStream inputStream = postMethod.getResponseBodyAsStream();
			return IOUtils.toString(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return null;
	}
	
	/*
	 * 向指定的url发送Post方法的请求
	 * @param url 发送请求的url
	 */
	public static String doPost(String host, Map<String, Object> msg, String method, String secret, String appkey) {
		String result = "";
		String params = JsonUtils.object2Json(msg);
		System.out.println(params);
		String token = TokenGenerateUtil.buildToken(host + method, params, secret);
		String url = host + method + "?token=" + token;
		System.out.println(url);
		if ("" == url & null == url) {
			return "url为空。。。";
		} else {
			HttpClient httpClient = new HttpClient();
			
			PostMethod postMethod = null;
			try {
				StringRequestEntity entity = new StringRequestEntity(params, "application/json", "utf-8");
				// System.out.println(params+entity.getContent());
				// 使用POST方法
				postMethod = new PostMethod(url);
				postMethod.setRequestEntity(entity);
				httpClient.executeMethod(postMethod);
				
				InputStream inputStream = postMethod.getResponseBodyAsStream();
				result = IOUtils.toString(inputStream);
				// System.out.println(result);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 释放连接
				postMethod.releaseConnection();
			}
		}
		return result;
	}
}
