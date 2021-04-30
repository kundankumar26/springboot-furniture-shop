package com.example.furnitureshop;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GlobalClassForFunctions {

    public static String getCurrentDateAndTime(String date) {
        String shippedDate = null;
        try {
            shippedDate = new SimpleDateFormat("dd-MM-yyyy").parse(date).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return shippedDate;
    }

    public static String getUserNameFromToken(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static MimeMessage sendEmailForOrder(MimeMessage mimeMessage, String to, String subject, String OrderType,
                                                StringBuilder ordersStringBuilder, String employeeName) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String message = (OrderType.equals("placed") ? "<h4>Hi " + employeeName + ",</h4>\n" +
                "<p>Hope you are doing great. Thanks for shopping with us. <br> You ordered these products.</p>\n" +
                "\n" : "<p>Hope you are doing great. Your order was confirmed by the vendor. <p>") +
                "<table style=\"width:100%; border: 2px solid black; color: rgba(0, 0, 0, 0.7);\">\n" +
                "\t<thead>\n" +
                "        <tr>\n" +
                "          <th style=\"border-bottom: 1px solid black;\">Order Id</th>\n" +
                "          <th style=\"border-bottom: 1px solid black;\">Item ordered</th>\n" +
                "          <th style=\"border-bottom: 1px solid black;\">Qty</th>\n" +
                "          <th style=\"border-bottom: 1px solid black;\">Shipping Address</th>\n" +
                "          <th style=\"border-bottom: 1px solid black;\">Phone no</th>\n" + (OrderType.equals("placed") ?
                "          <th style=\"border-bottom: 1px solid black;\">Order Date</th>\n" : "<th style=\"border-bottom: 1px solid black;\">Shipped Date</th>\n") +
                "        </tr>\n" +
                "  </thead>\n" +
                "  <tbody>\n" + ordersStringBuilder +
                "    </tbody>\n" +
                "</table>\n" +
                "\n" +
                "<p>Thanks, <br> Furniture Shop Team</p>";
        helper.setText(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("alternate8991@gmail.com");
        return mimeMessage;
    }

    public static boolean checkPasswordStrength(String password){
        boolean isThereUppercase = false, isThereLowercase = false, isThereNumber = false, isThereSpecialChar = false;
        for(int i = 0; i < password.length(); i++){
            if(password.charAt(i) >= 'A' && password.charAt(i) <= 'Z'){
                isThereUppercase = true;
            } else if(password.charAt(i) >= 'a' && password.charAt(i) <= 'z'){
                isThereLowercase = true;
            }
            else if(password.charAt(i) >= '!' || password.charAt(i) <= '.'){
                isThereSpecialChar = true;
            } else if(password.charAt(i) >= '0' && password.charAt(i) <= '9'){
                isThereNumber = true;
            }
        }
        return isThereUppercase && isThereLowercase && isThereNumber && isThereSpecialChar;
    }

}