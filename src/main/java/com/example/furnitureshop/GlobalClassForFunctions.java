package com.example.furnitureshop;

import com.example.furnitureshop.models.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


public class GlobalClassForFunctions {

    public static String getCurrentDateAndTime() {
        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date());
    }

    public static class SortByDate implements Comparator<Order> {
        public int compare(Order a, Order b) {
            return b.getOrderDate().compareTo(a.getOrderDate());
        }
    }

    public static String getUserNameFromToken(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}