package com.supconit.common.utils.context;

import java.beans.Introspector;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.ClassUtils;

public class LongBeanNameGenerator extends AnnotationBeanNameGenerator {
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String fullClassName = definition.getBeanClassName();
		String shortClassName = ClassUtils.getShortName(definition
				.getBeanClassName());
		int idx=fullClassName.lastIndexOf(".");
		String pre="";//ǰ׺
		if(idx>0){
			pre=fullClassName.substring(0, idx);
		}
		return pre+"."+Introspector.decapitalize(shortClassName);
	}

}