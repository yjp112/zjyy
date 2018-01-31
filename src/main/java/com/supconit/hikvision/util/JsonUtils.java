package com.supconit.hikvision.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>
 * JSON处理工具类
 * </p>
 *
 * @author caoshiyan
 * @version V1.0
 * @date 2015年12月29日 上午10:15:02
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年12月29日
 * @since
 */
public class JsonUtils {
	
	/**
	 * <p>
	 * 对象转JSON字符串
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 上午10:15:21
	 * @param obj
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年12月29日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public static String object2Json(Object obj) {
		String result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * <p>
	 * JSON字符串转Map对象
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 上午10:15:32
	 * @param json
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年12月29日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public static Map<?, ?> jsonToMap(String json) {
		return json2Object(json, Map.class);
	}
	
	/**
	 * <p>
	 * JSON转Object对象
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 上午10:15:49
	 * @param json
	 * @param cls
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年12月29日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public static <T> T json2Object(String json, Class<T> cls) {
		T result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(json, cls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * <p>
	 * </p>
	 * @author caoshiyan
	 * @version V1.0
	 * @date 2015年12月29日 上午10:16:06
	 * @param srcObject
	 * @param destObjectType
	 * @return
	 *
	 * @modificationHistory=========================逻辑或功能性重大变更记录
	 * @modify by user: {修改人} 2015年12月29日
	 * @modify by reason:{方法名}:{原因}
	 * @since
	 */
	public static <T> T conveterObject(Object srcObject, Class<T> destObjectType) {
		String jsonContent = object2Json(srcObject);
		return json2Object(jsonContent, destObjectType);
	}
}
