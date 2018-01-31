package com.supconit.common.utils.datasource;

import java.lang.reflect.Field;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{
	private transient static final Logger log = LoggerFactory
			.getLogger(DynamicDataSource.class);
	@Override
	protected Object determineCurrentLookupKey() {
		return DatabaseContextHolder.getDbType();
	}

	@Override
	public String toString() {
		if(log.isDebugEnabled()){
			try {
				Field f=this.getClass().getSuperclass().getDeclaredField("targetDataSources");
				f.setAccessible(true);
				Map<String, DataSource> targetDataSources=(Map<String, DataSource>) f.get(this);
				log.debug("************targetDataSources start:****************");
				for (Entry<String, DataSource> entry : targetDataSources.entrySet()) {
					String key=entry.getKey();
					DataSource ds=entry.getValue();
					DatabaseMetaData dsMetaData=ds.getConnection().getMetaData();
					String url=dsMetaData.getURL();
					String userName=dsMetaData.getUserName();
					log.debug(key+"[url="+url+",userName="+userName+"]");
				}
				log.debug("************targetDataSources end****************");
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			};			
		}
		try {
			DatabaseMetaData metaData=getConnection().getMetaData();
			String url=metaData.getURL();
			String userName=metaData.getUserName();
			return "DynamicDataSource [determineCurrentLookupKey()="
			+ determineCurrentLookupKey() + ",url="+url+",userName="+userName+"]";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return super.toString();	
	}

}
