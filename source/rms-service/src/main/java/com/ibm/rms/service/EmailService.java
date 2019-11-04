package com.ibm.rms.service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.EmailMessage;
@Service
public class EmailService {
		
	@Value("${gmail.username}")
	private String username;
	@Value("${gmail.password}")
	private String password;
	
	public void sendmail(EmailMessage emailmessage) throws RmsApplicationException {
			
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
					 new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					  });
			
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(username, false));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailmessage.getTo_address()));
			msg.setSubject(emailmessage.getSubject());
			msg.setText(emailmessage.getBody());
			msg.setSentDate(new Date());
			
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RmsApplicationException("E-mail configuration failed",e);
		}
		
			
//		MimeBodyPart messageBodyPart = new MimeBodyPart();
//		messageBodyPart.setContent(emailmessage.getBody(), "text/html");
//		Multipart multipart = new MimeMultipart();
//		multipart.addBodyPart(messageBodyPart);
//		MimeBodyPart attachPart = new MimeBodyPart();
//		attachPart.attachFile("C:\\talk2amareswaran-downloads\\mysql2.png");
//		multipart.addBodyPart(attachPart);
//		msg.setContent(multipart);
		try {
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RmsApplicationException("E-mail sending failed",e);
		}	
		}
	}
