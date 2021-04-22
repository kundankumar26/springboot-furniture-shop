package com.example.furnitureshop;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalClassForFunctions {

    public static String getCurrentDateAndTime() {
        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date());
    }

    public static String getUserNameFromToken(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static SimpleMailMessage sendEmailForOrder(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("alternate8991@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        return message;
    }
}