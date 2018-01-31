package com.supconit.common.web.startup.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.supconit.common.utils.PropertiesLoader;
import com.supconit.common.web.startup.IStartupPlugin;
import com.supconit.honeycomb.base.context.SpringContextHolder;

/**
 * 加载velocity指令
 * 
 * @author dingyg
 *
 */
public class LoadDirectivesPlugin implements IStartupPlugin {
	private transient static final Logger log = LoggerFactory.getLogger(LoadDirectivesPlugin.class);
	private static final String PREFIX_DIRECTIVE = "directive.";

	@Override
	public void startup() {
		VelocityEngine velocityEngine = SpringContextHolder.getBean("velocityEngine", VelocityEngine.class);
		PropertiesLoader loader = new PropertiesLoader("classpath:velocity.properties");
		for (Map.Entry<Object, Object> entry : loader.getProperties().entrySet()) {
			String key = StringUtils.trimToEmpty(entry.getKey().toString()).toLowerCase();
			String value = StringUtils.trimToEmpty(entry.getValue().toString());
			if (key.startsWith(PREFIX_DIRECTIVE)) {
				try {
					ClassUtils.forName(value, ClassUtils.getDefaultClassLoader());
				} catch (ClassNotFoundException e) {
					log.error("加载velocity指令出错，请检查文件classpath:velocity.properties", e);
				}
				velocityEngine.loadDirective(value);
			}
		}
		
		
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.err.println("===============================");
					DruidDataSource dataSource=SpringContextHolder.getBean("defaultDS");
					System.err.println("getActiveCount="+dataSource.getActiveCount());
					System.err.println("getConnectCount="+dataSource.getConnectCount());
					System.err.println("getCreateCount="+dataSource.getCreateCount());
					System.err.println("getPoolingCount="+dataSource.getPoolingCount());
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
				
			}
		}).start();*/
	}
}
