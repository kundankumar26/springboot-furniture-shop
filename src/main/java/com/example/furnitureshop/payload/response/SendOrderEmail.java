package com.example.furnitureshop.payload.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendOrderEmail {

    @Autowired
    private JavaMailSender mailSender;

    public static ResponseEntity<?> SendEmailForOrder(String to, String subject){
        JavaMailSender mailSender = new JavaMailSenderImpl();
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress("alternate8991@gmail.com"));
                mimeMessage.setSubject(subject);

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);

                //FileSystemResource res = new FileSystemResource(new File(fileToAttach));
                //helper.addInline("identifier1234", res);
            }
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
            return new ResponseEntity<>(new MessageResponse("Order was not sent to the mail"), HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(new MessageResponse("Order was sent to employee mail"), HttpStatus.OK);
    }
}
