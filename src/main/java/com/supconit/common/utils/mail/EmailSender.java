package com.supconit.common.utils.mail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.supconit.common.utils.ExpressionParserUtils;
import com.supconit.common.utils.PropertiesLoader;

public class EmailSender {
	private static JavaMailSenderImpl mailSender;
	private static PropertiesLoader loader = new PropertiesLoader("classpath:/email.properties");

	private static ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
	static {
		mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding(loader.getProperty("mail.defaultEncoding", "UTF-8"));
		mailSender.setHost(loader.getProperty("mail.host"));
		mailSender.setUsername(loader.getProperty("mail.username"));
		mailSender.setPassword(loader.getProperty("mail.password"));
		mailSender.setJavaMailProperties(loader.getProperties());
		
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(30);
		taskExecutor.setDaemon(false);
		taskExecutor.initialize();
	}

	public void shutdown(){
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.shutdown();
	}
	/**异步发送邮件
	 * @param mail
	 * @return
	 */
	public static boolean send(Email mail){
		return send(mail,true);
	}
	/**异步发送邮件，每隔startTimeout毫秒，发送一封邮件，一般用于发送太快导致，邮件服务器屏蔽发送的情况
	 * 由于分多份邮件发送，为避免重复抄送，抄送 的功能此处屏蔽
	 * @param mail
	 * @return
	 */
	public static boolean send(Email mail,long startTimeout){
			try {
				List<MimeMessage> mailMessages = prepareMultiMail(mail);
				for (final MimeMessage mimeMessage : mailMessages) {
					//异步
					taskExecutor.execute(new Runnable() {
						@Override
						public void run() {
							mailSender.send(mimeMessage);
						}
					},startTimeout);
				}
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}
	/**
	 * @param mail
	 * @param asynchronous true 异步，false 同步
	 * @return
	 */
	public static boolean send(final Email mail,boolean asynchronous){

		try {
			final MimeMessage mailMessage = prepareMail(mail);
			if(!asynchronous){
				//同步
				mailSender.send(mailMessage);
				return true;
			}
			//异步
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					mailSender.send(mailMessage);
				}
			});
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private static MimeMessage prepareMail(final Email mail) throws MessagingException {
		final MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
		messageHelper.setFrom(mail.getFrom());
		if(StringUtils.isNotBlank(mail.getToSingle())){
			messageHelper.setTo(mail.getToSingle());
		}else{
			messageHelper.setTo(mail.getTo());
		}
		if(StringUtils.isNotBlank(mail.getCcSingle())){
			messageHelper.setCc(mail.getCcSingle());
		}
		if(mail.getCc()!=null&&mail.getCc().length>0){
			messageHelper.setCc(mail.getCc());
		}

		messageHelper.setSubject(mail.getSubject());
		// true 表示启动HTML格式的邮件
		messageHelper.setText(mail.getContent(), true);
		if(!mail.getMailAttach().isEmpty()){
			for (Entry<String, File> entry : mail.getMailAttach().entrySet()) {
				// 添加到附件
				messageHelper.addAttachment(entry.getKey(),entry.getValue());
			}
		}
		return mailMessage;
	}
	/**由于分多份邮件发送，为避免重复发送，抄送 的功能此处屏蔽
	 * @param mail
	 * @return
	 * @throws MessagingException
	 */
	private static List<MimeMessage> prepareMultiMail(final Email mail) throws MessagingException {
		List<MimeMessage> msgs=new ArrayList<MimeMessage>();
		for (String to : mail.getTo()) {
			MimeMessage mailMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
			messageHelper.setFrom(mail.getFrom());
			messageHelper.setTo(to);
			/*if(StringUtils.isNotBlank(mail.getCcSingle())){
				messageHelper.setCc(mail.getCcSingle());
			}else{
				messageHelper.setCc(mail.getCc());
			}*/
			messageHelper.setSubject(mail.getSubject());
			// true 表示启动HTML格式的邮件
			messageHelper.setText(mail.getContent(), true);
			if(!mail.getMailAttach().isEmpty()){
				for (Entry<String, File> entry : mail.getMailAttach().entrySet()) {
					// 添加到附件
					messageHelper.addAttachment(entry.getKey(),entry.getValue());
				}
			}	
			msgs.add(mailMessage);
		}
		
		return msgs;
	}
}