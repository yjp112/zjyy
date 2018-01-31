package hc.bpm.context.interceptors;

import java.lang.reflect.Method;

import jodd.util.ReflectUtil;
import jodd.util.StringUtil;

import hc.bpm.BpmThreadLocal;
import hc.bpm.context.annotations.BpmSupport;
import hc.bpm.domains.ProcessParameter;
import hc.bpm.exceptions.BpmException;
import hc.bpm.services.ProcessService;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

@Aspect
public class AnnotationBpmHandlerAspectSupport implements BeanFactoryAware,
		InitializingBean {
	private BeanFactory beanFactory;
	private ProcessService processService;

	public void afterPropertiesSet() throws Exception {
		processService = ((ProcessService) beanFactory
				.getBean(ProcessService.class));
	}

	@Pointcut("@annotation(hc.bpm.context.annotations.BpmSupport)")
	public void handleBpm() {
	}

	@After("handleBpm()")
	public void after(JoinPoint point) {
		ProcessParameter processParameter = BpmThreadLocal.get();
		if (null == processParameter) {
			return;
		}
		processService.handle(processParameter, excuteExpressionDomain(point),
				point.getArgs());
	}

	private Method getHandleMethod(JoinPoint point) throws SecurityException,
			NoSuchMethodException {
		//String methodName = point.getSignature().getName();
		String methodName = point.getSignature().toLongString();
		Class<? extends Object> targetClass = point.getTarget().getClass();
		return findDeclaredMethod(targetClass, methodName);
		//return ReflectUtil.findDeclaredMethod(targetClass, methodName);
	}

	private Method findDeclaredMethod(Class c, String methodName) {
		return findDeclaredMethod(c, methodName, false);
	}

	private Method findDeclaredMethod(Class c, String methodName,
			boolean publicOnly) {
		if ((methodName == null) || (c == null)) {
			return null;
		}
		Method[] ms = publicOnly ? c.getMethods() : c.getDeclaredMethods();
		for (Method m : ms) {
			if (m.toString().equals(methodName)) {
				return m;
			}
		}
		return null;
	}

	private ExpressionDomain excuteExpressionDomain(JoinPoint point) {
		Method method = null;
		try {
			method = getHandleMethod(point);
		} catch (SecurityException e) {
			throw new BpmException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			throw new BpmException(e.getMessage(), e);
		}
		if (null == method)
			return null;
		BpmSupport bpmHandler = (BpmSupport) method
				.getAnnotation(BpmSupport.class);
		if (null == bpmHandler)
			throw new BpmException(
					"bpmHandler\u4E3ANULL\uFF0C\u96BE\u9053\u9047\u89C1\u9B3C\u4E86\uFF1F");
		if (StringUtil.isAllBlank(new String[] { bpmHandler.variableKeys(),
				bpmHandler.variables(), bpmHandler.businessKey(),
				bpmHandler.businessDomain() }))
			return null;
		return new ExpressionDomain(point.getTarget().getClass(), method,
				point.getTarget(), bpmHandler.variableKeys(),
				bpmHandler.variables(), bpmHandler.businessKey(),
				bpmHandler.businessDomain());
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
