package com.hk.orderPlacement;
import com.hk.property.PropertyHelper;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import java.util.Properties;
public  class ReadInboxMail {

    String from = "sumit1989.chawla@gmail.com";
    String[] to = { "nitin.kukna@gmail.com", "nitin.kukna@healthkart.com" };
    String host = "smtp.gmail.com";
    Multipart multipart = new MimeMultipart();
    Date date = new Date();
    DateFormat dateformat = new SimpleDateFormat();

    public void sendMail() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        // properties.put("mail.smtp.timeout", 200000);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", true);
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "kamalarora");
            }
        });
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(from));
            for (String tos : to) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(tos));
            }
            mimeMessage.setSubject("Test mail");
            mimeMessage.setText("testing the mail functionality");
            System.out.println("before send");

            BodyPart messageBodyPart = new MimeBodyPart();
            String fileName = "Bug" + dateformat.format(date) + ".html";
            File file = new File("C:\\selenium\\Automation_testing_v4\\screenshot\\orderplacement\\signupCODFailure.jpg");
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            mimeMessage.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            Transport.send(mimeMessage);
            System.out.println("After Send");

        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ReadInboxMail test = new ReadInboxMail();
        test.sendMail();
    }
}
