package com.supconit.common.utils;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//@Service
/**
 * @文 件 名：SpringContextHolder.java
 * @创建日期：2013年7月5日
 * @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 */
public class SpringContextHolder implements ApplicationContextAware,
                DisposableBean {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory
                    .getLogger(SpringContextHolder.class);

    /**
     * @方法名称:getApplicationContext
     * @作 者:丁阳光
     * @创建日期:2013年7月5日
     * @方法描述: 取得存储在静态变量中的ApplicationContext.
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * @方法名称:getBean
     * @作 者:丁阳光
     * @创建日期:2013年7月5日
     * @方法描述: 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param name
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * @方法名称:getBean
     * @作 者:丁阳光
     * @创建日期:2013年7月5日
     * @方法描述: 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param requiredType
     * @return T
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * @方法名称:clearHolder
     * @作 者:丁阳光
     * @创建日期:2013年7月5日
     * @方法描述: 清除SpringContextHolder中的ApplicationContext为Null. void
     */
    public static void clearHolder() {
        logger.debug("清除SpringContextHolder中的ApplicationContext:"
                        + applicationContext);
        applicationContext = null;
    }

    /*
     * 实现ApplicationContextAware接口, 注入Context到静态变量中. （非 Javadoc）
     * 
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("注入ApplicationContext到SpringContextHolder:{}",
                        applicationContext);

        if (SpringContextHolder.applicationContext != null) {
            logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                            + SpringContextHolder.applicationContext);
        }

        SpringContextHolder.applicationContext = applicationContext;
    }

    /* 实现DisposableBean接口, 在Context关闭时清理静态变量.
     * （非 Javadoc）
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }

    /** 检查ApplicationContext不为空.
     *@方法名称:assertContextInjected
     *@作    者:丁阳光
     *@创建日期:2013年7月5日
     *@方法描述:   void
     */
    private static void assertContextInjected() {
        Validate.notNull(applicationContext,
                        "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
    }
}