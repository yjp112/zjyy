package com.supconit.hikvision.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * JAVA原生HTTP调用工具类（主要提供GET请求与POST请求两种请求方法）
 * </p>
 *
 * @author caoshiyan
 * @version V1.0
 * @date 2015年12月29日 上午10:18:48
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年12月29日
 * @since
 */
public class JavaHttpUtil {
	
	public static final String DEFAULT_PRE_URL = "http://172.20.112.151/webapi/service/"; //此处替换成平台SDK所在服务器IP与端口
	public static final String DEFAULT_APPKEY = "b408fd60";//此处替换成申请的appkey
	public static final String DEFAULT_SECRET = "543f361f0bcc46dc83eb71fb1d3ab879";//此处替换成申请的secret
	
	/**
	 * <p>
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 上午11:26:14
	 * @param interfaceName 接口名
	 * @param paramMap 参数Map
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
	 * @date 2015年12月29日 上午11:23:50
	 * @param preUrl 接口URL前缀
	 * @param interfaceName 接口名
	 * @param paramMap 参数Map
	 * @param appkey
	 * @param secret
	 * @return String
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
		StringBuffer result = new StringBuffer();
		InputStream is = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		URL url = null;
		try {
			// 打开和URL之间的连接
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Accept-Charset", "utf-8");
			// 定义 BufferedReader输入流来读取URL的响应
			is = conn.getInputStream();
			inputStreamReader = new InputStreamReader(is);
			reader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * <p>
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 上午11:35:49
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
	 * @date 2015年12月29日 上午11:34:49
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
		StringBuffer result = new StringBuffer();
		InputStream is = null;
		InputStreamReader inputStreamReader = null;
		OutputStream os = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedReader reader = null;
		URL url = null;
		try {
			// 打开和URL之间的连接
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			// 写入url操作
			os = conn.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(os, "UTF-8");
			outputStreamWriter.write(paramStr);
			outputStreamWriter.flush();
			
			// 定义 BufferedReader输入流来读取URL的响应
			is = conn.getInputStream();
			inputStreamReader = new InputStreamReader(is, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStreamWriter != null) {
				try {
					outputStreamWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
}
