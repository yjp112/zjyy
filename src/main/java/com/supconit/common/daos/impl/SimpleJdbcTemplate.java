package com.supconit.common.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import com.supconit.common.daos.SimpleJdbc;

import hc.jdbc.DefaultJdbcProcessor;
import hc.jdbc.exceptions.JdbcException;
import hc.jdbc.handlers.BeanListResultSetHandler;
import hc.jdbc.handlers.BeanSingleResultSetHandler;
import hc.jdbc.handlers.ProcessResultSetHandler;
import jodd.util.StringUtil;

public class SimpleJdbcTemplate  /*extends DefaultJdbcProcessor*/ implements SimpleJdbc {
	private transient static final Logger log = LoggerFactory.getLogger(SimpleJdbcTemplate.class);
	private JdbcTemplate jdbc;
	private DataSource dataSource;
	@SuppressWarnings("rawtypes")
	private static final ProcessResultSetHandler BEAN_SINGLE_HANDLER = new BeanSingleResultSetHandler();
	@SuppressWarnings("rawtypes")
	private static final ProcessResultSetHandler BEAN_LIST_HANDLER = new BeanListResultSetHandler();
	public void setDataSource(DataSource dataSource) {
		this.dataSource=dataSource;
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <X> List<X> find(Class<X> beanClass, String sql, Object... args) {
		return (List)query(beanClass, BEAN_LIST_HANDLER, sql, args);
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public <X> X get(Class<X> beanClass, String sql, Object... args) {
		return (X) query(beanClass, BEAN_SINGLE_HANDLER, sql, args);
	}

	@Override
	public int[] batchUpdate(String sql, Object[][] args) {
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (Object[] objects : args) {
			batchArgs.add(objects);
		}
		return jdbc.batchUpdate(sql, batchArgs);
	}

	@Override
	public int update(String sql, Object... args) {
		return jdbc.update(sql, args);
	    /* if (StringUtil.isBlank(sql)) throw new JdbcException("SQL is blank.");
	     PreparedStatement ps = null;
	     try {
	       ps = fillStatement(DataSourceUtils.getConnection(this.dataSource), sql, args);
	       int rows = ps.executeUpdate();
	       return rows;
	     } catch (SQLException e) {
	       throw new JdbcException(e.getMessage(), e);
	     } finally {
	         try {
	             if ((null != ps) && (!ps.isClosed())) ps.close();
	           } catch (SQLException e) {
	             throw new JdbcException(e.getMessage(), e);
	           }
	     }*/
	}
	@Override
	public int[] batchUpdate(String sql, List<Object[]> params) {
		return jdbc.batchUpdate(sql, params);
	}

	@Override
	public int[] batchUpdate(String[] sql) {
		return jdbc.batchUpdate(sql);
	}

	@Override
	public int[] batchUpdate(List<String> sql) {
		return jdbc.batchUpdate(sql.toArray(new String[0]));
	}

	protected <X> X query(Class<X> beanClass, ProcessResultSetHandler<X> handler, String sql,
			Object... parameters) {
		if (StringUtil.isBlank(sql)) {
			throw new JdbcException("SQL is blank.");
		}
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn=jdbc.getDataSource().getConnection();
			ps = fillStatement(conn, sql, parameters);
			//log.debug("执行SQL[{}]开始...",sql);
			rs = ps.executeQuery();
			//log.debug("执行SQL[{}]完成",sql);
			return handler.process(beanClass, rs);
		} catch (Exception e) {
			throw new JdbcException(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			DataSourceUtils.releaseConnection(conn, jdbc.getDataSource());
		}
	}

	protected PreparedStatement fillStatement(Connection conn, String sql, Object... parameters) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		if ((null == parameters) || (parameters.length == 0)) {
			return ps;
		}

		fillStatement(ps, parameters);
		return ps;
	}

	protected void fillStatement(PreparedStatement ps, Object... parameters) throws SQLException {
		for (int i = 0; i < parameters.length; i++) {
			Object parameter = parameters[i];
			if (null != parameter) {
				ps.setObject(i + 1, parameter);
			} else {
				ps.setNull(i + 1, 12);
			}
		}
	}
}
