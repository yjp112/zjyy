package com.supconit.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @文 件 名：Exceptions.java
 * @创建日期：2013年7月5日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：关于异常的工具类.
 */
public class ExceptionUtils {

	/** 将CheckedException转换为UncheckedException.
	 *@方法名称:unchecked
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述:  
	 * @param e
	 * @return RuntimeException
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/** 
	 *@方法名称:getStackTraceAsString
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述:将ErrorStack转化为String.  
	 * @param e
	 * @return String
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/** 
	 *@方法名称:isCausedBy
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述: 判断异常是否由某些底层的异常引起. 
	 * @param ex
	 * @param causeExceptionClasses
	 * @return boolean
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
}
