package com.supconit.hikvision.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.commons.codec.CharEncoding;

/**
 * <p>
 * token生成工具类
 * </p>
 *
 * @author caoshiyan
 * @version V1.0
 * @date 2015年12月29日 上午10:16:14
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年12月29日
 * @since
 */
public class TokenGenerateUtil {
	
	/**
	 * <p>
	 * MD5加密工具类
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年11月30日 下午4:40:16
	 * @param s
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年11月30日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public final static String md5(String s) {
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			try {
				// 最重要的是这句,需要加上编码类型
				mdTemp.update(s.getBytes(CharEncoding.UTF_8));
			} catch (UnsupportedEncodingException e) {
				mdTemp.update(s.getBytes());
			}
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * <p>
	 * 生成token
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年11月18日 下午6:28:01
	 * @param url GET请求URL带参数串；POST请求URL不带参数串，参数以JSON格式传入paramJson
	 * @param paramJson POST参数JSON格式
	 * @param secret 加密secret
	 * @return String 生成的token值
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年11月18日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public final static String buildToken(String url, String paramJson, String secret) {
		String tempUrl = null;
		tempUrl = url.substring("http://".length());
		int index = tempUrl.indexOf("/");
		String URI = tempUrl.substring(index);
		String[] ss = URI.split("\\?");
		if (ss.length > 1) {
			return md5(ss[0] + ss[1] + secret);
		} else {
			return md5(ss[0] + paramJson + secret);
		}
	}
}
