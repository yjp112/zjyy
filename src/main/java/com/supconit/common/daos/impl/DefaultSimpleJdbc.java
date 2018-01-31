package com.supconit.common.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.alibaba.druid.wall.WallUtils;
import com.supconit.common.daos.SimpleJdbc;

import hc.jdbc.exceptions.JdbcException;
import hc.jdbc.handlers.BeanListResultSetHandler;
import hc.jdbc.handlers.BeanSingleResultSetHandler;
import hc.jdbc.handlers.ProcessResultSetHandler;
import jodd.util.ArraysUtil;

public class DefaultSimpleJdbc /* extends DefaultJdbcProcessor */ implements SimpleJdbc {
	private static final transient Logger logger = LoggerFactory.getLogger(DefaultSimpleJdbc.class);
	private static final transient boolean isDebug = logger.isDebugEnabled();

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataSource dataSource;

	protected DatabaseIdProvider databaseIdProvider;

	protected String databaseId;

	protected boolean injectDetection = false;


	private static final ProcessResultSetHandler BEAN_LIST_HANDLER = new BeanListResultSetHandler();

	public int[] batchUpdate(String sql, Object[][] parameters) {
		if (StringUtils.isBlank(sql))
			throw new JdbcException("SQL is blank.");
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = preparedStatement(conn, sql);
			ps.clearBatch();
			for (int i = 0; i < parameters.length; i++) {
				fillStatement(ps, parameters[i]);
				ps.addBatch();
			}
			return ps.executeBatch();
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(ps, conn);
		}
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> params) {

		Object[][] paramArray = params.toArray(new Object[0][]);
		return batchUpdate(sql, paramArray);
	}

	@Override
	public int[] batchUpdate(String[] sqls) {
		if (sqls == null || sqls.length == 0)
			throw new JdbcException("SQL is blank.");
		Statement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ps = conn.createStatement();
			ps.clearBatch();
			conn.setAutoCommit(false);
			for (String sql : sqls) {
				ps.addBatch(sql);
			}
			int[] counts = ps.executeBatch();
			conn.commit();
			return counts;
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(ps, conn);
		}
	}

	@Override
	public int[] batchUpdate(List<String> sqls) {
		return batchUpdate(sqls.toArray(new String[0]));
	}

	protected void closeStatementAndConnection(Statement ps, Connection conn) {
		try {
			if ((null != ps) && (!ps.isClosed()))
				ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DataSourceUtils.releaseConnection(conn, dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected Connection getConnection() {
		if (null != this.dataSource)
			return DataSourceUtils.getConnection(this.dataSource);
		throw new JdbcException("could not get connection.");
	}

	protected void injectDetect(String sql) {
		if (!this.injectDetection)
			return;
		if (null == this.databaseId)
			throw new JdbcException("databaseId is null.");
		if ("mysql".equals(this.databaseId)) {
			if (!WallUtils.isValidateMySql(sql))
				throw new JdbcException(sql + " inject detected failure.");
			return;
		}
		if ("oracle".equals(this.databaseId)) {
			if (!WallUtils.isValidateOracle(sql))
				throw new JdbcException(sql + " inject detected failure.");
			return;
		}
		if ("sqlserver".equals(this.databaseId)) {
			if (!WallUtils.isValidateSqlServer(sql))
				throw new JdbcException(sql + " inject detected failure.");
			return;
		}
		throw new JdbcException("not support database type : " + this.databaseId);
	}

	public <X> X get(Class<X> beanClass, String sql, Object... parameters) {
		return query(getConnection(), beanClass, new BeanSingleResultSetHandler<X>(), sql, parameters);
	}

	public <X> List<X> find(Class<X> beanClass, String sql, Object... parameters) {
		return (List)query(getConnection(), beanClass, BEAN_LIST_HANDLER, sql, parameters);
	}

	public int update(String sql, Object... parameters) {
		if (StringUtils.isBlank(sql))
			throw new JdbcException("SQL is blank.");
		PreparedStatement ps = null;
		Connection conn=null;
		try {
			conn=getConnection();
			if (isDebug) {
				logger.debug("[JDBC][" + Integer.toHexString(conn.hashCode()) + "] >> " + sql);
			}
			ps = fillStatement(conn, sql, parameters);
			int rows = ps.executeUpdate();
			return rows;
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(ps, conn);
		}
	}

	protected <X> X query(Connection conn, Class<X> beanClass, ProcessResultSetHandler<X> handler, String sql,
			Object... parameters) {
		if (StringUtils.isBlank(sql)) {
			throw new JdbcException("SQL is blank.");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (isDebug) {
			logger.debug("[JDBC][" + Integer.toHexString(conn.hashCode()) + "] >> " + sql + "\n[PARAMETERS:] "
					+ ArraysUtil.toString(parameters));
		}
		try {
			ps = fillStatement(conn, sql, parameters);
			rs = ps.executeQuery();
			return handler.process(beanClass, rs);
		} catch (Exception e) {
			throw new JdbcException(e.getMessage(), e);
		} finally {
			closeResultSet(rs);
			closeStatementAndConnection(ps, conn);
		}
	}

	protected PreparedStatement preparedStatement(Connection conn, String sql) throws SQLException {
		return conn.prepareStatement(sql);
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

	protected PreparedStatement fillStatement(Connection conn, String sql, Object... parameters) throws SQLException {
		PreparedStatement ps = preparedStatement(conn, sql);
		if ((null == parameters) || (parameters.length == 0)) {
			return ps;
		}

		fillStatement(ps, parameters);
		return ps;
	}

	protected void closeResultSet(ResultSet rs) {
		try {
			if ((null != rs) && (!rs.isClosed()))
				rs.close();
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage(), e);
		}
	}

	protected void closePreparedStatement(PreparedStatement ps) {
		try {
			if ((null != ps) && (!ps.isClosed()))
				ps.close();
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage(), e);
		}
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setInjectDetection(boolean injectDetection) {
		this.injectDetection = injectDetection;
	}

	public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
		this.databaseIdProvider = databaseIdProvider;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

}
