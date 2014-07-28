package com.hk.commonProperties;// File Name SendFileEmail.java


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class SendMail {


    private static String toaddress = "nitin.kukna@healthkart.com";
    private static String fromaddress = "nitin.kukna@gmail.com";
    private static String hostname = "smtp.gmail.com";
    private static String password = "Nk$232017";

    public static void sendmail(String attachmentDirectory, String reportDirectory) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", hostname);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");

        properties.put("mail.smtp.auth", "true");
        // Get the default Session object.

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromaddress, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromaddress));


            // Set To: header field of the header.
            message.addRecipient(MimeMessage.RecipientType.TO,
                    new InternetAddress(toaddress));

            // Set Subject: header field
            message.setSubject("Test Cases Result");


            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();


            // Fill the message
            messageBodyPart.setText("Please find the attached screen shot for the report");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            File dir = new File(attachmentDirectory);
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String filename = file.getAbsolutePath();
                    DataSource source = new FileDataSource(filename);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(filename);
                    multipart.addBodyPart(messageBodyPart);
                }

            }
            messageBodyPart = new MimeBodyPart();
            File zipFile = new File(reportDirectory);
            FileDataSource rarFile = new FileDataSource(zipFile);
            messageBodyPart.setDataHandler(new DataHandler(rarFile));
            messageBodyPart.setFileName(rarFile.getName());
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message

           /* Transport.send(message);*/
            Transport transport = session.getTransport("smtps");
            Transport.send(message);
            System.out.println("Sent message successfully...." + message);
            if (zipFile != null);
            {
                zipFile.delete();
            }
            if (files != null) {
                for (File file : files) {
                    String filename = file.getAbsolutePath();
                    file.delete();
                }
            }

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


}

