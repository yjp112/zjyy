package com.supconit.nhgl.job.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;

import com.supconit.common.utils.DateUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.ywgl.exceptions.BusinessDoneException;

/**
 * 能耗抽取存储过程的执行，如proc_arc_electric_main_byhand
 * 
 * @author
 *
 */
public class ProcedureExecutorJob {
	private transient static final Logger log = LoggerFactory.getLogger(ProcedureExecutorJob.class);
	private static JdbcTemplate jdbcTemplate = new JdbcTemplate((DataSource) SpringContextHolder.getBean("dataSource"));

	public void execute(final String procedureName, final String startDate, final String endDate) throws ParseException {
		Date start = new Date();
		String sql = "{call " + procedureName + "("+startDate+"," + endDate+")}";
		if (log.isInfoEnabled()) {
			log.info("存储过程【{}】开始执行【{}】.........", sql, DateUtils.format(start, "yyyy-MM-dd HH:mm:ss:ssss"));
		}
		try {
			jdbcTemplate.execute(new CallableStatementCreator() {
				
				@Override
				public CallableStatement createCallableStatement(Connection con) throws SQLException {
					CallableStatement st=con.prepareCall("{call "+procedureName+"(?,?)}");
					int parameterIndex=0;
					st.setString(++parameterIndex, startDate);
					st.setString(++parameterIndex, endDate);
					return st;
				}
			},new CallableStatementCallback<Boolean>() {

				@Override
				public Boolean doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
					boolean b=cs.execute();
					return b;
				}
			});
		}finally{
			if (log.isInfoEnabled()) {
				log.info("存储过程【{}】执行完毕【{}】，本次执行耗时【{}】ms", procedureName,
						DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:ssss"),
						DateUtils.dateBetweenHumanRead(start, new Date()));
			}
			
		}

	}

}
