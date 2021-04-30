package com.example.furnitureshop;

import com.example.furnitureshop.models.FirstLastUsernameValid;
import com.example.furnitureshop.models.PasswordStrength;
import com.example.furnitureshop.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.stream.Stream;

public class GlobalClassForFunctions {

    //Get the current date and time and parses it
    public static String getCurrentDateAndTime(String date) {
        String shippedDate = null;
        try {
            shippedDate = new SimpleDateFormat("dd-MM-yyyy").parse(date).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return shippedDate;
    }

    //Get the username from securitycontextholder
    public static String getUserNameFromToken(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //Create the template for order placed and order confirmed emails
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

    //Checks the password strength implementation
    private static StringBuilder checkPasswordStrengthImpl(String password){
        boolean isThereUppercase = false, isThereLowercase = false, isThereNumber = false, isThereSpecialChar = false;
        String specialCharString = "\\!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        for(int i = 0; i < password.length(); i++){
            if(isThereUppercase && isThereLowercase && isThereNumber && isThereSpecialChar){
                break;
            } else if(password.charAt(i) >= 'A' && password.charAt(i) <= 'Z'){
                isThereUppercase = true;
            } else if(password.charAt(i) >= 'a' && password.charAt(i) <= 'z'){
                isThereLowercase = true;
            } else if(password.charAt(i) >= '0' && password.charAt(i) <= '9'){
                isThereNumber = true;
            } else if(specialCharString.indexOf(password.charAt(i)) >= 0){
                isThereSpecialChar = true;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if(!isThereUppercase){
            stringBuilder.append("an uppercase");
            if(!isThereLowercase || !isThereNumber || !isThereSpecialChar){
                stringBuilder.append(", ");
            }
        }
        if(!isThereLowercase){
            stringBuilder.append("a lowercase");
            if(!isThereNumber || !isThereSpecialChar){
                stringBuilder.append(", ");
            }
        }
        if(!isThereNumber){
            stringBuilder.append("a number");
            if(!isThereSpecialChar){
                stringBuilder.append(", ");
            }
        }
        if(!isThereSpecialChar){
            stringBuilder.append("a special character ");
        }
        return stringBuilder;
    }

    //Checks the password strength
    public static StringBuilder checkPasswordStrength(String password,String firstname){

        StringBuilder stringBuilder = checkPasswordStrengthImpl(password);
        StringBuilder stringBuilderForStrength = new StringBuilder();
        if(stringBuilder.length() > 2) {
            stringBuilderForStrength.append("Please include ").append(stringBuilder.toString()).append(" in the password. ");
        }

        //Check if the password has first name
        if(password.toLowerCase(Locale.ROOT).contains(firstname.toLowerCase(Locale.ROOT))){
            stringBuilderForStrength.append("Password must not contain first name. ");
        }
        return stringBuilderForStrength;
    }

    //Checks if the first, last and username has only alphabets
    public static StringBuilder CheckUserRequestForAlphabets(String firstName, String lastName, String username){
        boolean isFirstNameValid = true, isLastNameValid = true, isUserNameValid = true;
        for(int i = 0; i < Math.max(firstName.length(), Math.max(lastName.length(), username.length())); i++){
            if(firstName.length() > i && isFirstNameValid){
                isFirstNameValid = (firstName.charAt(i) >= 'a' && firstName.charAt(i) <= 'z') || (firstName.charAt(i) >= 'A' && firstName.charAt(i) <= 'Z');
            }
            if(lastName.length() > i && isLastNameValid){
                isLastNameValid = (lastName.charAt(i) >= 'a' && lastName.charAt(i) <= 'z') || (lastName.charAt(i) >= 'A' && lastName.charAt(i) <= 'Z');
            }
            if(username.length() > i && isUserNameValid){
                boolean b = username.charAt(i) >= 'a' && username.charAt(i) <= 'z';
                boolean c = (username.charAt(i) >= 'A' && username.charAt(i) <= 'Z');
                if(i == 0){
                    isUserNameValid = b || c;
                } else {
                    isUserNameValid = b || c || (username.charAt(i) >= '0' && username.charAt(i) <= '9');
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if(!isFirstNameValid || !isLastNameValid){
            stringBuilder.append("Please enter only alphabets in the ");
        }
        if(!isFirstNameValid){
            stringBuilder.append("first name");
            if (!isLastNameValid) {
                stringBuilder.append(", ");
            } else {
                stringBuilder.append(".");
            }
        }
        if(!isLastNameValid){
            stringBuilder.append("last name. \n");
        }
        if(!isUserNameValid){
            stringBuilder.append("Username must start from alphabet and should contain only alphabets and numbers. ");
        }
        return stringBuilder;
    }



}