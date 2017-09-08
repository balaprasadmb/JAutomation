package com.balaprasad.testng;

import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer{

	public static void mail(){
		Properties props = new Properties();
        props.put("mail.smtp.host", "");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
        new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("", "");
        } 
        });
        try {
        String  bodyMessage ="Hello,";
        bodyMessage = bodyMessage + "\n"+"Please find attached test script results.,";
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(""));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(""));
//        message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(""));
        message.setSubject("TestScripts Results || Automated Mail");
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent(bodyMessage,"text/html");
        MimeBodyPart messageBodyPart2 = new MimeBodyPart();
        String filename = System.getProperty("user.dir") + "/target/Extent_Report.html";
        DataSource source = new FileDataSource(filename);
        messageBodyPart2.setDataHandler(new DataHandler(source));
        messageBodyPart2.setFileName("testng-tests-results.html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart1);
        multipart.addBodyPart(messageBodyPart2);
        message.setContent(multipart,"text/html; charset=utf-8");
        // finally send the email
        Transport.send(message);
        System.out.println("=================================== Email Sent ===================================");
        }catch (MessagingException e) {
        throw new RuntimeException(e);
        }
	}
}
