package com.maaz.lms.service;

public interface EmailService {
	public void sendEmail(String emailTo, String[] emailCc, String strMessage, String subject);
}
