package com.supconit.common.utils.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class StatisticsInfoInterceptor implements Interceptor {
	private transient static final Logger log = LoggerFactory.getLogger(StatisticsInfoInterceptor.class);
	private boolean showFullSql = true;// 是否显示完整的sql语句("?"已经被替换成真实值)
	private boolean likeScHandle = true;// like特殊字符(Special character)处理
	private long slowSqlMill = -1;// 慢SQL统计,slowSqlMill<0,不打印慢SQL，slowSqlMill>0,执行时间超过slowSqlMill的被视为慢SQL，打印

	private String databaseId = "";

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String showFullSql = properties.getProperty("showFullSql", "true");
		String likeScHandle = properties.getProperty("likeScHandle", "true");
		String slowSqlMill = properties.getProperty("slowSqlMill", "-1");
		this.showFullSql = Boolean.valueOf(showFullSql);
		this.likeScHandle = Boolean.valueOf(likeScHandle);
		this.slowSqlMill = Long.valueOf(slowSqlMill);
	}

	public Object intercept(Invocation invocation) throws Throwable {
		// 修改like语句，特殊字符处理
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		databaseId = mappedStatement.getDatabaseId();
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		if (likeScHandle) {
			if (boundSql.getSql().toLowerCase().contains("like")
					&& !boundSql.getSql().toLowerCase().contains("escape")) {
				ReflectUtil.setFieldValue(boundSql, "sql", modifyLikeSql(boundSql.getSql()));
				final BoundSql newBoundSql = boundSql;// buildBoundSql(mappedStatement,
														// boundSql,
														// modifyLikeSql(boundSql.getSql()));
				MappedStatement newMappedStatement = buildMappedStatement(mappedStatement, new SqlSource() {
					@Override
					public BoundSql getBoundSql(Object paramObject) {
						return newBoundSql;
					}
				});
				invocation.getArgs()[0] = newMappedStatement;
				mappedStatement = newMappedStatement;
			}
			modifyParameter(mappedStatement.getConfiguration(), boundSql);

		}

		// 打印完整SQL和慢查询SQL
		long start = System.currentTimeMillis();
		Object returnValue = invocation.proceed();
		long time = (System.currentTimeMillis() - start);
		if (showFullSql) {
			log.info(getSql(mappedStatement.getConfiguration(), boundSql, mappedStatement.getId(), time));
		}
		if (slowSqlMill > 0 && time > slowSqlMill) {
			log.warn("this sql is too slow:"
					+ getSql(mappedStatement.getConfiguration(), boundSql, mappedStatement.getId(), time));
		}
		return returnValue;
	}

	private String getSql(Configuration configuration, BoundSql boundSql, String sqlId, long time) {
		String sql = fillParameters(configuration, boundSql);
		StringBuilder str = new StringBuilder(100);
		str.append("\r\n耗时["+time+"]ms,");
		str.append("\r\nSQL[");
		str.append(sql);
		str.append("],");
		str.append("\r\nmapper_Id["+sqlId+"]");
		return str.toString();
	}

	private String getParameterValue(Object obj) {
		String value = null;
		if(obj==null){
			return null;
		}
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(obj) + "'";
		} else{
				value = obj.toString();
		}
		return value;
	}

	public String fillParameters(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		/*if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}*/
		TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
		if (parameterMappings != null) {
		    for (int i = 0; i < parameterMappings.size(); i++) {
		      ParameterMapping parameterMapping = parameterMappings.get(i);
		      if (parameterMapping.getMode() != ParameterMode.OUT) {
		        Object value;
		        String propertyName = parameterMapping.getProperty();
		        if (boundSql.hasAdditionalParameter(propertyName)) { 
		          value = boundSql.getAdditionalParameter(propertyName);
		        } else if (parameterObject == null) {
		          value = null;
		        } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
		          value = parameterObject;
		        } else {
		          MetaObject metaObject = configuration.newMetaObject(parameterObject);
		          value = metaObject.getValue(propertyName);
		        }
		        String val=getParameterValue(value);
		        if(val==null){
		        	val="null";
		        }
		        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(val));
		      }
		    }
		  }		
		return sql;
	}

	private MappedStatement buildMappedStatement(MappedStatement ms, SqlSource sqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource,
				ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
			StringBuilder keyProperties = new StringBuilder();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		builder.lang(ms.getLang());
		builder.databaseId(ms.getDatabaseId());
		return builder.build();
	}

	public String modifyLikeSql(String sql) {
		/*
		 * if(parameterObject instanceof HashMap){ }else{ return sql; }
		 */
		if (!sql.toLowerCase().contains("like") || sql.toLowerCase().contains("escape"))
			return sql;
		// sql=" and name LIKE '%' || #{name} || '%' \n " +"and
		// email LIKE #{email} || '%' ";
		//sql=sql.toLowerCase();
		String reg = "\\s.+\\sLIKE\\s*('%'\\s*(\\|{2}|\\+)\\s*)?(.*?\\?)(\\s*(\\|{2}|\\+)\\s*'%')?";// "order\\s+by\\s+.+"
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);

		List<String> replaceEscape = new ArrayList<String>();
		while (matcher.find()) {
			replaceEscape.add(matcher.group());
		}

		// sql = matcher.replaceAll(reg+" 1111");
		for (String s : replaceEscape) {
			sql=StringUtils.replaceOnce(sql, s, s + " ESCAPE '/' ");
			//sql=StringUtils.replaceOnce(sql,"like", "###");
		}
		//return sql.replaceAll("###", "like");
		return sql;
	}

	private void modifyParameter(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		List<Integer> idx = searchLikeEscape(boundSql.getSql());
		if (idx.size() <= 0) {
			// 没有like escape关键字，无需修改参数
			return;
		}
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				if (idx.contains(0)) {
					ReflectUtil.setFieldValue(boundSql, "parameterObject", getParameterValue(parameterObject));
				}
			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				int i = 0;
				for (ParameterMapping parameterMapping : parameterMappings) {
					if (!idx.contains(i)) {
						i++;
						continue;
					}
					i++;
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object val = metaObject.getValue(propertyName);
						metaObject.setValue(propertyName, transIllegalString(val));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object val = boundSql.getAdditionalParameter(propertyName);
						boundSql.setAdditionalParameter(propertyName, transIllegalString(val));
					}
				}
			}
		}
	}

	private <X> X transSpecialCharacter(X obj) {
		if (obj == null) {
			return obj;
		}

		Field[] field = obj.getClass().getDeclaredFields();
		for (int j = 0; j < field.length; j++) {
			String type = field[j].getGenericType().toString();
			if (type.equals("class java.lang.String")) {
				String name = field[j].getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				try {
					Method m = obj.getClass().getMethod("get" + name);
					String value = (String) m.invoke(obj);
					if (value != null) {
						field[j].setAccessible(true);
						field[j].set(obj, transIllegalString(value));
					}
				} catch (NoSuchMethodException e) {
					continue;
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		}
		return obj;
	}

	private Object transIllegalString(Object obj) {
		if (obj != null && !(obj instanceof String)) {
			return obj;
		}
		String str = (String) obj;
		// System.err.print(str+"\t");
		if ("".equals(str))
			return "";
		if (str.indexOf("/") > -1) {
			str = str.replaceAll("/", "//");
		}
		if (str.indexOf("%") > -1) {
			String reg = "\\s*%\\s*(.*)\\s*%\\s*";
			Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			if (!matcher.matches()) {
				str = str.replaceAll("%", "/%");
			} else {
				str = "%" + str.substring(1, str.length() - 1).replaceAll("%", "/%") + "%";
			}
		}
		if (str.indexOf("_") > -1) {
			str = str.replaceAll("_", "/_");
		}
		if ("sqlserver".equals(databaseId)) {
			if (str.indexOf("[") > -1)
				str = str.replaceAll("\\[", "/[");
			if (str.indexOf("]") > -1)
				str = str.replaceAll("\\]", "/]");
		}
		// System.err.println(str);
		return str;
	}

	/**
	 * 利用反射进行操作的一个工具类
	 * 
	 */
	private static class ReflectUtil {
		/**
		 * 利用反射获取指定对象的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标属性的值
		 */
		public static Object getFieldValue(Object obj, String fieldName) {
			Object result = null;
			final Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				field.setAccessible(true);
				try {
					result = field.get(obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		/**
		 * 利用反射获取指定对象里面的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标字段
		 */
		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException e) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
				}
			}
			return field;
		}

		/**
		 * 利用反射设置指定对象的指定属性为指定的值
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @param fieldValue
		 *            目标值
		 */
		public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
			final Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				try {
					field.setAccessible(true);
					field.set(obj, fieldValue);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查找sql里 LIKE ? ESCAPE 在字符串中的位置
	 * 
	 * @param sql
	 * @return
	 */
	private List<Integer> searchLikeEscape(String sql) {
		Pattern pattern1 = Pattern.compile("\\S+\\s*\\?\\s*\\w*", Pattern.CASE_INSENSITIVE);
		Pattern pattern2 = Pattern.compile("like\\s*\\?\\s*(ESCAPE)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern1.matcher(sql);
		int i = 0;
		List<Integer> idxList = new ArrayList<Integer>();
		while (matcher.find()) {
			// System.err.println(matcher.group());
			if (pattern2.matcher(matcher.group()).matches()) {
				idxList.add(i);
			}
			i++;
		}
		return idxList;
	}

	public static void main(String[] args) {
		String sql = "WHERE ROOM_NAME LIKE ? 			";
		sql = "							AND t2.AREA_NAME "
				+"LIKE ?";
		String reg = "\\s.+\\sLIKE\\s*('%'\\s*(\\|{2}|\\+)\\s*)?(.*?\\?)(\\s*(\\|{2}|\\+)\\s*'%')?";// "order\\s+by\\s+.+"
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);

		while (matcher.find()) {
			System.err.println(matcher.group());
		}

		/*pattern = Pattern.compile("\\S+\\s*\\?\\s*\\w*", Pattern.CASE_INSENSITIVE);
		Pattern pattern3 = Pattern.compile("like\\s*\\?\\s*(ESCAPE)", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(sql);
		int i = 0;
		int j = 0;
		while (matcher.find()) {
			System.err.println(matcher.group());
			if (pattern3.matcher(matcher.group()).matches()) {
				System.out.println(i);
			}
			i++;
		}*/
		
	   System.out.println(sql.replaceFirst("\\?", Matcher.quoteReplacement("${condition.modelCode}")));
	   //System.out.println(sql.replaceFirst("${condition.modelCode}", "\\$"));
	}
}
