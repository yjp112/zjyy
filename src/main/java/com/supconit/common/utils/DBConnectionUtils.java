package com.supconit.common.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnectionUtils {
	private String driverClass;
	private String url;
	private String userName;
	private String password;
	private transient static final Logger log = LoggerFactory
			.getLogger(DBConnectionUtils.class);
	public DBConnectionUtils() {
		super();
	}
	public DBConnectionUtils(String driverClass, String url, String userName,
			String password) {
		super();
		this.driverClass = driverClass;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public  Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int[] executeBatch(List<String> sqlList) {
		return executeBatch(getConnection(), sqlList);
	}
	public static int[] executeBatch(DataSource dataSource,List<String> sqlList) {
		try {
			return executeBatch(dataSource.getConnection(), sqlList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new int[0];
	}
	
	public static int[] executeBatch(Connection conn,List<String> sqlList) {
		Statement ps = null;
		try {
			ps = conn.createStatement();
			conn.setAutoCommit(false);
			for (String sql : sqlList) {
				ps.addBatch(sql);
			}
			int[] counts = ps.executeBatch();
			conn.commit();
			return counts;
		} catch (SQLException e) {
			e.getNextException().printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**此方法只支持只有IN参数或者没有参数的存储过程,且存储过程不返回值
	 * @param conn
	 * @param procedureName 存储过程名称,如set_death_age(?, ?)
	 * @param parameters 存储过程的参数
	 * @throws SQLException 
	 */
	@SuppressWarnings("rawtypes")
	public static void executeProcedure(Connection conn,String procedureName,Object ... parameters) throws SQLException{
		CallableStatement st = null;
		String parameterStr= parameters!=null?Arrays.asList(parameters).toString():"";
		log.info("存储过程"+procedureName+",参数:"+parameterStr+"开始执行【" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:ssss") + "】...");
		long start = System.currentTimeMillis();
		try {
			st=conn.prepareCall("{call "+procedureName+" }");
			
			for (int i = 0; parameters!=null&&i < parameters.length; i++) {
				st.setObject(i+1, parameters[i]);
			}
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	        log.info("存储过程"+procedureName+",参数:"+parameterStr+"执行完毕【" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:ssss") + "】，本次执行耗时【"
	                + (System.currentTimeMillis() - start) + "】ms");
		}
	}
	/**此方法只支持只有IN参数或者没有参数的存储过程,且存储过程不返回值
	 * @param conn
	 * @param procedureName 存储过程名称,如set_death_age(?, ?)
	 * @param parameters 存储过程的参数
	 * @throws SQLException 
	 */
	@SuppressWarnings("rawtypes")
	public static void executeProcedure(DataSource dataSource,String procedureName,Object ... parameters) throws SQLException{
		executeProcedure(dataSource.getConnection(), procedureName, parameters);
	}
	/**此方法只支持只有IN参数或者没有参数的存储过程,且存储过程不返回值
	 * @param conn
	 * @param procedureName 存储过程名称,如set_death_age(?, ?)
	 * @param parameters 存储过程的参数
	 * @throws SQLException 
	 */
	@SuppressWarnings("rawtypes")
	public void executeProcedure(String procedureName,Object ... parameters) throws SQLException{
		executeProcedure(getConnection(), procedureName, parameters);
	}
	public int executeUpdate(String sql) {
		return executeUpdate(getConnection(), sql);
	}
	public static int executeUpdate(DataSource dataSource,String sql) {
		try {
			return executeUpdate(dataSource.getConnection(), sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static int executeUpdate(Connection conn,String sql) {
		Statement ps = null;
		try {
			ps = conn.createStatement();
			return ps.executeUpdate(sql);
		} catch (SQLException e) {
			e.getNextException().printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
}