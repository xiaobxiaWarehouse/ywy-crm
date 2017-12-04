package com.ywy.service;

public interface EmailService {
	int sendEmail(String[] to, String[] cc, String subject, String body, boolean isHtml);
}
