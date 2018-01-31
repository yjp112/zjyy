package com.supconit.common.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.supconit.honeycomb.base.context.SpringContextHolder;

public class ExpressionParserUtils {
	/**
	 * @param expression
	 *            如 #variable? 100.5 : 0 ，如 #variable>5 and #variable<20? 100.5
	 *            : 0
	 * @param variableMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getSprintElExpressionValue(String expression,
			Map variableMap) {
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		for (Object key : variableMap.keySet()) {
			context.setVariable(key.toString(), variableMap.get(key));
		}
		return parser.parseExpression(expression).getValue(context,
				Object.class);
	}

	public static String mergeVelocityTemplateString(String templateString,
			Map<String, Object> params) {
		VelocityEngine engine = new VelocityEngine();
		engine.init();
		VelocityContext context = new VelocityContext();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		StringWriter out = new StringWriter();
		engine.evaluate(context, out, "", templateString);
		return out.toString();
	}

	public static String mergeVelocityTemplateFile(String templateFileLocation,
			Map<String, Object> context) throws VelocityException, IOException {
		VelocityEngineFactoryBean engine=SpringContextHolder.getBean(VelocityEngineFactoryBean.class);
		return VelocityEngineUtils.mergeTemplateIntoString(engine.createVelocityEngine(),
				templateFileLocation, "UTF-8", context);
	}

	public static void main(String[] args) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("name", "zhangsan");
		params.put("where", "中控");
		String str=ExpressionParserUtils.mergeVelocityTemplateString("Welcome  $name  to $where! ", params);
		System.out.println(str);
	}
}
