package com.tnt.commonutilities;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestMail {
	
//		String message= "test email";
//		String subject="test";
//		String to="";
//		String from="";

	public boolean sendEmail(String message, String subject, String to, String from) {
		
		//get system properties
		Properties properties=System.getProperties();
	
		properties.put("mail.smtp.host", "smtp.gb.tntpost.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "25");
        
		//step to get the session object
		Session session=Session.getInstance(properties, new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("C242WQQ","June2022");
			}
	
		});
		
		session.setDebug(true);
		
		//compose the message
		MimeMessage mimemessage=new MimeMessage(session);
		try {
			//from email
			mimemessage.setFrom(new InternetAddress(from));//from
			
			//adding recipient
			mimemessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//can use TO. BCC, CC
			
			//adding subject
			mimemessage.setSubject(subject);
			
			//set message
			mimemessage.setText(message);
			
			//send message using transport class
			Transport.send(mimemessage);
			
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}	
		
	}
}
