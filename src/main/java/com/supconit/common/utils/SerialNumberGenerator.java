package com.supconit.common.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.supconit.common.utils.datasource.DynamicDataSource;
import com.supconit.honeycomb.base.context.SpringContextHolder;

import jodd.datetime.JDateTime;

/**
 * @文件名: SerialNumberGenerator.java
 * @创建日期: 13-7-5
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本: 1.0
 * @描述: 流水号工具类
 */
public class SerialNumberGenerator {

	private static final int	POINT_NUM	= 4;			// 4位流水号
	private static final String	DATE_FORMAT	= "YYYYMMDD";	// 日期格式如20130704
   
	private static JdbcTemplate	jdbctemplate;

	static {
		jdbctemplate = new JdbcTemplate();
		jdbctemplate.setDataSource(SpringContextHolder.getBean(DynamicDataSource.class));
	}

	 
	public static String getSerialNumbersByDB(TableAndColumn tac) {
		 
		// List<String> serialNums = session.selectList(buildSql(tac, prefix + getDateStr()));
		String str = tac.prefix+getDateStr();
		String serialNum = "";
		List list = jdbctemplate.queryForList(buildSql(tac, str));
		
		if(null == list || list.size()==0){
			serialNum = str+"0";
		}else{
			@SuppressWarnings("rawtypes")
			Map map = (Map) list.get(0);
			serialNum = (String) map.get(tac.getColName());	
		}
			
		return generateSerialNumber(serialNum, str);

	}

	/**
	 * 获取字符串类型日期格式为YYYYMMDD
	 * 
	 * @return
	 */
	private static String getDateStr() {
		return new JDateTime().toString(DATE_FORMAT);
	}
	/**
     * 根据时间戳生成Code
     * @param tac
     * @return
     */
    public static String getSerialNumbers(TableAndColumn tac){
        return tac.prefix+DateUtils.format(new Date(),"yyyyMMddHHmmssS");
    }


    public static String getSerialNumbers(String tac){
        return tac+DateUtils.format(new Date(),"yyyyMMddHHmmssS");
    }
	/**
	 * 生成获取流水号的sql语句
	 * 
	 * @param tac
	 *            枚举类，前缀同列名和表名的对应关系
	 * @param serialNum
	 *            前缀+日期
	 * @return
	 */
	private static String buildSql(TableAndColumn tac, String serialNum) {
		return String.format("select %s from %s where %s like '" + serialNum + "%s' order by %s desc", tac.getColName(),
				tac.getTableName(), tac.getColName(), "%", tac.getColName()).toUpperCase();
	}

	/**
	 * 生成新的流水号
	 * 
	 * @param serialNumber
	 *            老的流水号
	 * @param str
	 *            前缀+日期
	 * @return 新的流水号
	 */
	private static String generateSerialNumber(String serialNumber, String str) {
		//Long num = Long.parseLong(serialNumber.substring((prefix).length())) + 1;
		int n = Integer.parseInt(serialNumber.substring(str.length())) + 1;
        
		return str+ stuffZero(n,POINT_NUM);
	}
	/**
	 * 不足位数前面填充"0"
	 * @param str 待填充数字
	 * @param num 数字位数
	 * @return String
	 */
	private static String stuffZero(int num,int n){
		String str = String.valueOf(num);
		if(str.length()<n){
			int m = POINT_NUM-str.length();
			for(int i=0;i<m;i++){
				str = "0"+str;
			}
		}
		
		return str;
	}

	public enum TableAndColumn {
		TASK("SQD", "REPAIR", "TASK_CODE"),
        REPAIR("WXD","REPAIR","REPAIR_CODE"),
        LEAVE_APPLY("QJD","LEAVE_APPLY","LEAVE_CODE"),
        LUNCH_APPLY("GZC","LUNCH_APPLY","LUNCH_CODE"),
		STOCK_IN("RK", "STOCK_IN", "STOCK_IN_CODE"),
		STOCK_OUT("CK", "STOCK_OUT", "STOCK_OUT_CODE"),
		STOCK_BACK("GH", "STOCK_BACK", "STOCK_BACK_CODE"),
		TRANSFER("DB", "TRANSFER", "TRANSFER_CODE"),
		INVENTORY("PD", "INVENTORY", "INVENTORY_CODE"),
		PRICE_CHANGE("TJ", "PRICE_CHANGE", "BILL_NO"),
		DUTY_CHANGE("TB", "DUTY_CHANGE", "CHANGE_NO"),
        MAINTAIN_PLAN("JHD","MAINTAIN_PLAN","PLAN_CODE"),
        MAINTAIN_ORDER("BYD","MAINTAIN_TASK","MAINTAIN_CODE"),
        INSPECTION_ORDER("XJD","INSPECTION_TASK","INSPECTION_CODE"),
        SPEC_MAINTAIN_PLAN("TZJHD","SPEC_MAINTAIN_PLAN","PLAN_CODE"),
        SPEC_MAINTAIN_ORDER("TZBYD","SPEC_MAINTAIN","SPEC_MAINTAIN_CODE"),
		TASK_PLAN("JHRW","TASK_PLAN","PLAN_CODE");

		private String	prefix;
		private String	tableName;
		private String	colName;

		private TableAndColumn(String prefix, String tableName, String colName) {
			this.prefix = prefix;
			this.tableName = tableName;
			this.colName = colName;
		}

		public String getTableName() {
			return tableName;
		}

		public String getColName() {
			return colName;
		}

		@Override
		public String toString() {
			return "{prefix:" + prefix + " stands by Table is '" + tableName + "',column is '" + colName + "'}";
		}

		public static TableAndColumn findByPrefix(String prefix) {
			for (TableAndColumn tac : values()) {
				if (tac.prefix.equalsIgnoreCase(prefix)) {
					return tac;
				}
			}
			throw new IllegalArgumentException(String.format("非法参数'%s'", prefix));
		}
	}
}
