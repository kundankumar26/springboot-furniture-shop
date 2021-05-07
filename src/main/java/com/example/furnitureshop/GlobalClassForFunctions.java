package com.example.furnitureshop;

import com.example.furnitureshop.models.FirstLastUsernameValid;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.PasswordStrength;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
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
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("this is current user id " + userDetails.getId());
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //Get the user id of current user
    public static long getUserIdFromToken(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }

    //Create the template for order placed and order confirmed emails
    public static MimeMessage sendEmailForOrder(MimeMessage mimeMessage, String to, String subject, String OrderType,
                                                StringBuilder ordersStringBuilder, String employeeName) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String message = (OrderType.equals("placed") ? "<h4>Hi " + employeeName + ",</h4>\n" +
                "<p>Hope you are doing great. Thanks for shopping with us. <br> You ordered these products.</p>\n" +
                "\n" : "<p>Hope you are doing great. Your order was confirmed by the vendor. <p>") +
                "<table style=\"width:100%; border: 2px solid rgba(0, 0, 0, 0.5); color: rgba(0, 0, 0, 0.7);\">\n" +
                "\t<thead>\n" +
                "        <tr>\n" +
                "          <th style=\"border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">Order Id</th>\n" +
                "          <th style=\"border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">Item ordered</th>\n" +
                "          <th style=\"border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">Qty</th>\n" +
                "          <th style=\"border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">Shipping Address</th>\n" +
                "          <th style=\"border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">Phone no</th>\n" + (OrderType.equals("placed") ?
                "          <th style=\"border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">Order Date</th>\n" : "<th style=\"border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">Shipped Date</th>\n") +
                "        </tr>\n" +
                "  </thead>\n" +
                "  <tbody>\n" + ordersStringBuilder +
                "    </tbody>\n" +
                "</table>\n" +
                "\n" +
                "<p>Thanks, <br> Perpendicular Shop</p>";
        helper.setText(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("alternate8991@gmail.com");
        return mimeMessage;
    }

    //Check for address and phone number
    public static String checkAddressAndPhoneNo(String address, String phoneNumber){
        StringBuilder stringBuilderForAddAndPhone = new StringBuilder();
        if(address.length() < 5){
            stringBuilderForAddAndPhone.append("Shipping address is too short. ");
        }
        if(phoneNumber.length() < 10){
            stringBuilderForAddAndPhone.append("Phone number is too short. ");
        }
        if(phoneNumber.length() > 10 ){
            stringBuilderForAddAndPhone.append("Phone number is too long. ");
        }
        if(address.length() < 5 || phoneNumber.length() != 10){
            return stringBuilderForAddAndPhone.toString();
        }
        return "";
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

    //Check if the item exist in the database or not
    public static String checkIfItemOrdered(String item){
        HashMap<String, String> itemListMap = new HashMap<>();
        itemListMap.put("Mouse", "Mouse");
        itemListMap.put("Keyboard", "Keyboard");
        itemListMap.put("Table 1", "Table");
        itemListMap.put("Table 2", "Table");
        itemListMap.put("LG Monitor", "Monitor");
        itemListMap.put("HP Monitor", "Monitor");
        itemListMap.put("Chair", "Chair");
        return itemListMap.get(item);
    }

    //Create the template for account verification
    public static String sendEmailForAccountVerification(String token){
        String message = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <style type=\"text/css\">\n" +
                "        @media screen {\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: italic;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: italic;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        /* CLIENT-SPECIFIC STYLES */\n" +
                "        body,\n" +
                "        table,\n" +
                "        td,\n" +
                "        a {\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "        }\n" +
                "\n" +
                "        table,\n" +
                "        td {\n" +
                "            mso-table-lspace: 0pt;\n" +
                "            mso-table-rspace: 0pt;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "        }\n" +
                "\n" +
                "        /* RESET STYLES */\n" +
                "        img {\n" +
                "            border: 0;\n" +
                "            height: auto;\n" +
                "            line-height: 100%;\n" +
                "            outline: none;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            border-collapse: collapse !important;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            height: 100% !important;\n" +
                "            margin: 0 !important;\n" +
                "            padding: 0 !important;\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"background-color: #2d4659; margin: 0 !important; padding: 0 !important;\">\n" +
                "    \n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "        <!-- LOGO -->\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#2d4659\" align=\"center\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#2d4659\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
                "                            <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Welcome!</h1> <img src=\" https://img.icons8.com/clouds/100/000000/handshake.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" />\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\">We're excited to have you get started. First, you need to confirm your account. Just press the button below.</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\">\n" +
                "                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                <tr>\n" +
                "                                    <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
                "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                            <tr>\n" +
                "                                                <td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#2d4659\"><a href=\"http://localhost:8080/confirm-account?token=" + token + "\" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #2d4659; display: inline-block;\">Confirm Account</a></td>\n" +
                "                                            </tr>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr> <!-- COPY -->\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 0px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\">If that doesn't work, click the following link: </p>\n" +
                "                        </td>\n" +
                "                    </tr> <!-- COPY -->\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\"><a href=\"http://localhost:8080/confirm-account?token=" + token + "\" target=\"_blank\" style=\"color: #FFA73B;\">" + "http://localhost:8080/confirm-account?token=" + token + "</a></p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\">If you have any questions, just reply to this email—we're always happy to help out.</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\">Cheers,<br>Perpendicular Shop Team</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        return message;
    }

    //Create the template for shipped order
    public static String sendEmailForShippedDate(long itemOrderId, String itemName, long itemQty, String itemShippingAddres, String itemShippedDate){

        String message = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "  \n" +
                "    <style type=\"text/css\">\n" +
                "        @media screen {\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: italic;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: italic;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        /* CLIENT-SPECIFIC STYLES */\n" +
                "        body,\n" +
                "        table,\n" +
                "        td,\n" +
                "        a {\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "        }\n" +
                "\n" +
                "        table,\n" +
                "        td {\n" +
                "            mso-table-lspace: 0pt;\n" +
                "            mso-table-rspace: 0pt;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "        }\n" +
                "\n" +
                "        /* RESET STYLES */\n" +
                "        img {\n" +
                "            border: 0;\n" +
                "            height: auto;\n" +
                "            line-height: 100%;\n" +
                "            outline: none;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            border-collapse: collapse !important;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            height: 100% !important;\n" +
                "            margin: 0 !important;\n" +
                "            padding: 0 !important;\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"background-color: #2d4659; margin: 0 !important; padding: 0 !important;\">\n" +
                "    \n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "        <!-- LOGO -->\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#2d4659\" align=\"center\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#2d4659\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
                "                          <h2 style=\"font-size: 40px; font-weight: 900; letter-spacing: 0.5px;\">Perpendicular Shop</h2>\n" +
                "                             <img src=\"https://cdn.dribbble.com/users/123981/screenshots/2071818/camion.gif\" width=\"300\" height=\"300\" style=\"display: block; padding: 0px; border: 0;\">\n" +
                "                          <h2 style=\"font-size: 30px; font-weight: 700; letter-spacing: 1px;\">Your order is on its way!</h2>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "      \n" +
                "      \n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px; \">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                  <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                      <table bgcolor=\"#efefef\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"padding: 50px; border-radius: 5px; border: 2px solid #e7e7e7;\">\n" +
                "                        <tr style=\"font-size: 18px; font-weight: 400; padding: 20px;\">\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left; border-right: 2px solid #e7e7e7;\">SUMMARY:</th>\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left;\">SHIPPING ADDRESS:</th>\n" +
                "                        </tr>\n" +
                "                        <tr style=\"font-size: 16px; font-weight: 400; padding: 20px;\">\n" +
                "                          <td style=\"padding-bottom: 10px; border-right: 2px solid #e7e7e7;\">\n" +
                "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                              <tr style=\"padding: 10px;\">\n" +
                "                                <th style=\"padding: 5px 0 5px 20px; text-align: left;\">Order Id:</th>\n" +
                "                                <td style=\"text-align: right; padding: 5px 20px 5px 0\">" + itemOrderId + "</td>\n" +
                "                              </tr>\n" +
                "                              <tr style=\"padding: 20px;\">\n" +
                "                                <th style=\"padding: 5px 0 5px 20px; text-align: left;\">Shipped Date:</th>\n" +
                "                                <td style=\"text-align: right; padding: 5px 20px 5px 0\">" + itemShippedDate + "</td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                          </td>\n" +
                "                          \n" +
                "                          <td style=\"padding-bottom: 10px;\">\n" +
                "                            <table width=\"100%\">\n" +
                "                              <tr style=\"padding: 10px;\">\n" +
                "                                <td style=\"padding: 5px 0 5px 20px; text-align: left;\">" + itemShippingAddres + "</td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        \n" +
                "                        \n" +
                "                      </table>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  \n" +
                "                  <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"padding: 50px;\">\n" +
                "                        <tr bgcolor=\"#efefef\" style=\"border-radius: 5px; border-bottom: 2px solid #e7e7e7;\">\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left;\">ITEM REQUESTED</th>\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left;\">QTY</th>\n" +
                "                        </tr>\n" +
                "                        <tr style=\"border-bottom: 2px solid #e7e7e7; font-size: 16px;\">\n" +
                "                          <td style=\"padding: 10px 0 10px 20px; text-align: left;\">" + itemName + "</td>\n" +
                "                          <td style=\"padding: 10px 0 10px 20px; text-align: left;\">" + itemQty + "</td>\n" +
                "                        </tr>\n" +
                "                      </table>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  \n" +
                "                  \n" +
                "                  \n" +
                "                  \n" +
                "                  \n" +
                "                    \n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\">Thanks,<br>Perpendicular Shop Team</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "      \n" +
                "      \n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "  \n" +
                "</body>\n" +
                "\n" +
                "</html>";
        return message;
    }

    //Create the template for placed order
    public static String sendEmailForPlacedOrder(List<Order> orderDetails){
        StringBuilder stringBuilder = new StringBuilder();
        String address = orderDetails.get(0).getShippingAddress();
        String itemShippingAddress = address.substring(0, 1).toUpperCase() + address.substring(1, address.length()).toLowerCase(Locale.ROOT) +
                "<br>Phn no: " + orderDetails.get(0).getPhnNo();
        String itemOrderDate = orderDetails.get(0).getOrderDate().substring(0, 10);
        for(Order order: orderDetails){
            stringBuilder.append("<tr style=\"font-size: 16px; border-bottom: 2px solid #e7e7e7;\">\n" +
                    "                          <td style=\"padding: 10px 0 10px 20px; text-align: left;\">")
            .append(order.getOrderId())
            .append("</td>\n" + "<td style=\"padding: 10px 0 10px 20px; text-align: left;\">").append(order.getItemRequested())
            .append("</td>\n" + "<td style=\"padding: 10px 0 10px 20px; text-align: left;\">").append(order.getQty())
            .append("</td>\n" + "</tr>\n");
        }

        String message = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap\" rel=\"stylesheet\">\n" +
                "  \n" +
                "    <style type=\"text/css\">\n" +
                "      @import url('https://fonts.googleapis.com/css2?family=Raleway:wght@400;700&display=swap');\n" +
                "        @media screen {\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: italic;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: italic;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        /* CLIENT-SPECIFIC STYLES */\n" +
                "        body,\n" +
                "        table,\n" +
                "        td,\n" +
                "        a {\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "        }\n" +
                "\n" +
                "        table,\n" +
                "        td {\n" +
                "            mso-table-lspace: 0pt;\n" +
                "            mso-table-rspace: 0pt;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "        }\n" +
                "\n" +
                "        /* RESET STYLES */\n" +
                "        img {\n" +
                "            border: 0;\n" +
                "            height: auto;\n" +
                "            line-height: 100%;\n" +
                "            outline: none;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            border-collapse: collapse !important;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            height: 100% !important;\n" +
                "            margin: 0 !important;\n" +
                "            padding: 0 !important;\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"background-color: #2d4659; margin: 0 !important; padding: 0 !important;\">\n" +
                "    \n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "        <!-- LOGO -->\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#2d4659\" align=\"center\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#2d4659\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
                "                          <h2 style=\"font-family: 'Raleway'; letter-spacing: 0.5px; font-size: 40px; font-weight: bold;\">Perpendicular Shop</h2>\n" +
                "                             <img src=\"https://cdn.pixabay.com/photo/2014/04/02/10/12/checkbox-303113_960_720.png\" width=\"100\" height=\"100\" style=\"display: block; padding: 0px; border: 0;\">\n" +
                "                          <h2 style=\"font-family: 'Raleway'; font-size: 30px; font-weight: 900; letter-spacing: 0.8px;\">Your order is placed successfully!</h2>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "      \n" +
                "      \n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px; \">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                  <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                      <table bgcolor=\"#efefef\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"padding: 50px; border-radius: 5px; border: 2px solid #e7e7e7;\">\n" +
                "                        <tr style=\"font-size: 18px; font-weight: 400; padding: 20px;\">\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left; border-right: 2px solid #e7e7e7;\">SUMMARY:</th>\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left;\">SHIPPING ADDRESS:</th>\n" +
                "                        </tr>\n" +
                "                        <tr style=\"font-size: 16px; font-weight: 400; padding: 20px;\">\n" +
                "                          <td style=\"padding-bottom: 10px; border-right: 2px solid #e7e7e7;\">\n" +
                "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                              <tr style=\"padding: 20px;\">\n" +
                "                                <th style=\"padding: 5px 0 5px 20px; text-align: left;\">Order Date:</th>\n" +
                "                                <td style=\"text-align: right; padding: 5px 20px 5px 0\">" + itemOrderDate + "</td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                          </td>\n" +
                "                          \n" +
                "                          <td style=\"padding-bottom: 10px;\">\n" +
                "                            <table width=\"100%\">\n" +
                "                              <tr style=\"padding: 10px;\">\n" +
                "                                <td style=\"padding: 5px 0 5px 20px; text-align: left;\">" + itemShippingAddress + "</td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        \n" +
                "                        \n" +
                "                      </table>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  \n" +
                "                  <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"padding: 50px;\">\n" +
                "                        <tr bgcolor=\"#efefef\" style=\"border-radius: 5px; border-bottom: 2px solid #e7e7e7;\">\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left;\">ORDER ID</th>\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left;\">ITEM REQUESTED</th>\n" +
                "                          <th style=\"padding: 10px 0 10px 20px; text-align: left;\">QTY</th>\n" +
                "                        </tr>\n" + stringBuilder +
                "                      </table>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                  \n" +
                "                  \n" +
                "                  \n" +
                "                  \n" +
                "                  \n" +
                "                    \n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\">Thanks,<br>Perpendicular Shop Team</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "      \n" +
                "      \n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "  \n" +
                "</body>\n" +
                "\n" +
                "</html>";
        return message;
    }
}