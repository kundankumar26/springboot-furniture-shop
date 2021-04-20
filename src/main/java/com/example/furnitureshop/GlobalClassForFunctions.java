package com.example.furnitureshop;

import com.example.furnitureshop.models.Order;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


public class GlobalClassForFunctions {

    public static String getCurrentDateAndTime(long dateAndTime) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        Date date = new Date(dateAndTime);
        //System.out.println(formatter.format(date));
        return formatter.format(date);
    }

    public static class SortByDate implements Comparator<Order> {
        public int compare(Order a, Order b) {
            return b.getOrderDate().compareTo(a.getOrderDate());
        }
    }
}