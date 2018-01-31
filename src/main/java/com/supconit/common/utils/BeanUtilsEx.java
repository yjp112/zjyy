package com.supconit.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 用来避免BeanUtils copy date时出现错误
 * @author liyingling
 *
 */
public final class BeanUtilsEx
    extends BeanUtils {

  private static Map cache = new HashMap();
  private static Log logger = LogFactory.getFactory().getInstance(BeanUtilsEx.class);

  private BeanUtilsEx() {
  }

  static {
 // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空 
    ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.sql.Date.class); 
    ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.util.Date.class);  
    ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlTimestampConverter(null), java.sql.Timestamp.class); 
    // 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空 
  }

  public static void copyProperties(Object target, Object source) throws
      InvocationTargetException, IllegalAccessException {
    //支持对日期copy

    org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);

  }
}