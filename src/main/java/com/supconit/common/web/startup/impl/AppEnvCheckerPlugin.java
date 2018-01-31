package com.supconit.common.web.startup.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.supconit.common.utils.PropertiesLoader;
import com.supconit.common.web.startup.IStartupPlugin;
import com.supconit.common.web.startup.StartupLoaderBeforeSpringServlet;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.jobschedule.services.SchedulerManagerService;

/**web 环境监测
 * 1）数据库是否就绪，如未就绪，则等待就绪
 * @author dingyg
 *
 */
//@WebServlet(loadOnStartup = 0, urlPatterns = { "/appEnvChecker" })
public class AppEnvCheckerPlugin implements IStartupPlugin {
	private transient static final Logger log = LoggerFactory
			.getLogger(AppEnvCheckerPlugin.class);
	public AppEnvCheckerPlugin() {
	}

	@Override
	public void startup() {
		ServletConfig config=StartupLoaderBeforeSpringServlet.threadLocal.get();

		String activeProfile=config.getServletContext().getInitParameter("spring.profiles.active");
		String dbConfigFileName="config-dev.properties";
		if("production".equals(activeProfile)){
			dbConfigFileName="config.properties";
		}else if("test".equals(activeProfile)){
			dbConfigFileName="config-test.properties";
		}
		PropertiesLoader loader = new PropertiesLoader(
				"classpath:"+dbConfigFileName);
		while(true){
			loader.reloadProperties();
			String driver=loader.getProperty("jdbc.driver");
			String url=loader.getProperty("jdbc.jdbcUrl");
			String username=loader.getProperty("jdbc.username");
			String password=loader.getProperty("jdbc.password");
			DataSource ds=initDataSource(driver, url, username, password);
			if(isDatabaseReady(ds)){
				break;
			}
			log.error("******************spring.profiles.active["+activeProfile+"],database["+url+"] is not ready,waiting....**************************");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
			
	}
	private DataSource initDataSource(String driver,String url,String username,String password){
		try {
			DriverManagerDataSource ds = new DriverManagerDataSource();  
			ds.setDriverClassName(driver);  
			ds.setUrl(url);  
			ds.setUsername(username);  
			ds.setPassword(password);  
			return ds;
		} catch (Exception e) {
		}
		return null;
	}
	private boolean isDatabaseReady(DataSource ds){
        Connection conn=null;
        boolean ready=false;
		try {
			conn=ds.getConnection();
			DatabaseMetaData metaData=conn.getMetaData();
			if(metaData!=null){
				ready=true;
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ready;
	}

}
