package com.ywy.service.impl;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ywy.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private final static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Resource
	private JavaMailSenderImpl mailSender;
	
	@Override
	public int sendEmail(String[] to, String[] cc,
			String subject, String body, boolean isHtml) {
//		try {
//			SimpleMailMessage smm = new SimpleMailMessage();
//			 // 设定邮件参数
//		    smm.setFrom("56334968@163.com");
//		    smm.setTo(to);
//		    smm.setSubject(title);
//		    smm.setText(body);
//		    mailSender.send(smm);
//		    return 1;
//		} catch (Exception e) {
//			logger.error("fail to send ...", e);
//			return 0;
//		}
	   
		MimeMessage msg = mailSender.createMimeMessage();
		try {
		    MimeMessageHelper helper = new MimeMessageHelper(msg, true,"utf-8");
		 
		    helper.setFrom(mailSender.getUsername());
		    if (to != null) {
		    	helper.setTo(to);
		    }
		    if (cc != null) {
		    	helper.setCc(cc);
		    }
		    helper.setSubject(subject);
		    helper.setText(body,isHtml);

		    mailSender.send(msg);
		} catch (Exception e) {
			logger.error("fail send ", e);
		}
		return 1;
	}

}
