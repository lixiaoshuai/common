package com.common.util.email;

import com.common.util.PropertiesUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class SendMessage {
	private static Logger logger = LoggerFactory.getLogger(SendMessage.class);

	public void send(String subject, String content, String[] financeGroupMail) {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost(PropertiesUtil.getValue("mail.host"));
		javaMailSenderImpl.setUsername(PropertiesUtil.getValue("mail.username"));
		javaMailSenderImpl.setPassword(PropertiesUtil.getValue("mail.password"));
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.timeout", "25000");
		javaMailSenderImpl.setJavaMailProperties(properties);
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(PropertiesUtil.getValue("mail.from.sender"));
		if (ArrayUtils.isNotEmpty(financeGroupMail)) {
			simpleMailMessage.setTo(financeGroupMail);
		} else {
			simpleMailMessage.setTo(PropertiesUtil.getValue(
					"mail.finance.manager").split(";"));
		}
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(content);
	
		javaMailSenderImpl.send(simpleMailMessage);
	}
}
