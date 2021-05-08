package com.example.furnitureshop.security.services;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.models.EmployeeResponseTable;
import com.example.furnitureshop.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service("emailSenderService")
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Async
    public void sendConfirmationEmail(String to, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("Account verification.");
        mimeMessageHelper.setText(GlobalClassForFunctions.sendEmailForAccountVerification(token), true);
        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendConfirmedOrderEmail(Order updatedOrder) throws MessagingException {

        String message = GlobalClassForFunctions.sendEmailForShippedDate(updatedOrder.getOrderId(), updatedOrder.getItemRequested(), updatedOrder.getQty(),
                updatedOrder.getShippingAddress() + "<br>Phn no: " + updatedOrder.getPhnNo(), updatedOrder.getShippedDate().substring(0, 10));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        mimeMessageHelper.setTo(updatedOrder.getEmail());
        mimeMessageHelper.setSubject("Order confirmed successfully.");
        mimeMessageHelper.setText(message, true);
        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendOrderPlacedEmail(List<EmployeeResponseTable> orderDetails) throws MessagingException {
        String message = GlobalClassForFunctions.sendEmailForPlacedOrder(orderDetails);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        mimeMessageHelper.setTo(orderDetails.get(0).getEmail());
        mimeMessageHelper.setSubject("Order placed successfully.");
        mimeMessageHelper.setText(message, true);
        javaMailSender.send(mimeMessage);
    }
}