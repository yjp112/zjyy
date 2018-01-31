package com.supconit.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


/**
 * @文 件 名：PropertiesLoader.java
 * @创建日期：2013年7月5日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：Properties文件载入工具类. 可载入多个properties文件, 
 * 相同的属性在最后载入的文件中的值将会覆盖之前的值，
 * 但以System的Property优先.
 */
public class PropertiesLoader {

	private static Logger log = LoggerFactory.getLogger(PropertiesLoader.class);

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	private OrderedProperties properties;
	private String[] resourcesPaths;
	public PropertiesLoader(String... resourcesPaths) {
		this.resourcesPaths=resourcesPaths;
		properties = loadProperties(resourcesPaths);
	}

	public Properties getProperties() {
		return properties;
	}

	public void reloadProperties(){
		properties = loadProperties(resourcesPaths);
	}
	/** 
	 *@方法名称:getValue
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述:  取出Property，但以System的Property优先,取不到返回空字符串.
	 * @param key
	 * @return String
	 */
	private String getValue(String key) {
		String systemProperty = System.getProperty(key);
		if (StringUtils.isNotEmpty(systemProperty)) {
			return StringUtils.trimToEmpty(systemProperty);
		}
		if (properties.containsKey(key)) {
	        return StringUtils.trimToEmpty(properties.getProperty(key));
	    }
	    return "";
	}

	/** 
	 *@方法名称:getProperty
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述: 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常. 
	 * @param key
	 * @return String
	 */
	public String getProperty(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return value;
	}

	/** 
	 *@方法名称:getProperty
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述: 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值. 
	 * @param key
	 * @param defaultValue
	 * @return String
	 */
	public String getProperty(String key, String defaultValue) {
		String value = getValue(key);
		return value != null ? value : defaultValue;
	}

	/** 
	 *@方法名称:getInteger
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述:取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.  
	 * @param key
	 * @return Integer
	 */
	public Integer getInteger(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Integer.valueOf(value);
	}
	/** 
	 *@方法名称:getInteger
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述: 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常 
	 * @param key
	 * @param defaultValue
	 * @return Integer
	 */
	public Integer getInteger(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Integer.valueOf(value) : defaultValue;
	}
	
	/**
	 * @param key
	 * @return
	 */
	public Long getLong(String key){
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Long.valueOf(value);
	}
	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Long getLong(String key, Long defaultValue) {
		String value = getValue(key);
		return value != null ? Long.valueOf(value) : defaultValue;
	}
	/** 
	 *@方法名称:getDouble
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述: 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常. 
	 * @param key
	 * @return Double
	 */
	public Double getDouble(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Double.valueOf(value);
	}

	/** 
	 *@方法名称:getDouble
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述:  取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
	 * @param key
	 * @param defaultValue
	 * @return Double
	 */
	public Double getDouble(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Double.valueOf(value) : defaultValue;
	}

	/** 
	 *@方法名称:getBoolean
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述: 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false. 
	 * @param key
	 * @return Boolean
	 */
	public Boolean getBoolean(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Boolean.valueOf(value);
	}

	/** 
	 *@方法名称:getBoolean
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述: 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false. 
	 * @param key
	 * @param defaultValue
	 * @return Boolean
	 */
	public Boolean getBoolean(String key, boolean defaultValue) {
		String value = getValue(key);
		return value != null ? Boolean.valueOf(value) : defaultValue;
	}

	/** 
	 *@方法名称:loadProperties
	 *@作    者:丁阳光
	 *@创建日期:2013年7月5日
	 *@方法描述: 载入多个文件, 文件路径使用Spring Resource格式. 
	 * @param resourcesPaths
	 * @return Properties
	 */
	private OrderedProperties loadProperties(String... resourcesPaths) {
		OrderedProperties props = new OrderedProperties();

		for (String location : resourcesPaths) {

//			logger.debug("Loading properties file from:" + location);

			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				props.load(is);
			} catch (IOException ex) {
				log.error("Could not load properties from path:" + location + ", " + ex.getMessage());
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return props;
	}
}
