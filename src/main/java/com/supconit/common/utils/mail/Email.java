package com.supconit.common.utils.mail;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.core.ReflectUtils;

public class Email {
	// 发件人
	private String from;
	// 收件人(单人)
	private String toSingle;
	// 收件人
	private String[] to;
	// 抄送
	private String ccSingle;
	// 抄送
	private String[] cc;
	// 邮件主题
	private String subject;
	// 邮件内容
	private String content;
	// 附件 name=file
	private Map<String, File> mailAttach=new HashMap<String, File>();

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getToSingle() {
		return toSingle;
	}

	public void setToSingle(String toSingle) {
		this.toSingle = toSingle;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}
	public void setTo(List<String> to) {
		if(to!=null&&to.size()>0){
			this.to = to.toArray(new String[0]);
		}
	}

	public String getCcSingle() {
		return ccSingle;
	}

	public void setCcSingle(String ccSingle) {
		this.ccSingle = ccSingle;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public void setCc(List<String> cc) {
		if(cc!=null&&cc.size()>0){
			this.cc = cc.toArray(new String[0]);
		}
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, File> getMailAttach() {
		return mailAttach;
	}
	
	public void addMailAttach(String fileName,File file) {
		mailAttach.put(fileName, file);
	}

	public Map<String, Object> toHashMap(){
		Map<String, Object> map=new HashMap<String, Object>();
		PropertyDescriptor[] descriptors= ReflectUtils.getBeanGetters(this.getClass());
		for (PropertyDescriptor propertyDescriptor : descriptors) {
			Method method=propertyDescriptor.getReadMethod();
			try {
				map.put(propertyDescriptor.getName(),method.invoke(this));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

}
