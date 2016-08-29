package com.maaz.lms.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Value("${mail.sender}")
	String username;
	
	@Value("${mail.sender.password}")
	String password;
	
	@Value("${mail.smtp.host}")
	String host;
	
	@Value("${mail.smtp.port}")
	String port;
	
	
	@Override
	public void sendEmail(String emailTo, String[] emailCc, String strMessage, String subject) {
		
		logger.info("Will now attempt to send email to: {}, message: {}, subject: {}", new Object[] {emailTo, strMessage, subject});

		// Get system properties
		Properties properties = System.getProperties();
		
		// Setup mail server
		properties.put("mail.smtp.user", username);
		properties.put("mail.smtp.password", password);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		

		// Get the default Session object.
		Session session = Session.getInstance(properties,
			      new javax.mail.Authenticator() {
			        protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(username, password);
			        }
			      });

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(username));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			
			for(String emailId : emailCc) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(emailId));
			}
			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			//message.setText(strMessage);
			message.setContent(strMessage, "text/html; charset=utf-8");

			// Send message
			Transport.send(message);
			logger.info("Email sent successfully to: {}, message: {}, subject: {}", new Object[] {emailTo, strMessage, subject});
		} catch (MessagingException mex) {
			logger.error("MessagingException occurred sending email: ",mex);
		} catch (Exception e) {
			logger.error("Exception occurred sending email: ",e);
		}
	}
}
