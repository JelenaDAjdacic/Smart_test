package com.example.jelena.smart_test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by mirna on 26.1.2016.
 */
public class CalendarOperations {
    Date dueDate;
    Date currentDate;

    public CalendarOperations(){


    }

    public String convertDateFormat(String date){
        String formattedDate=null;
        Date oldDate=null;
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat targetFormat = new SimpleDateFormat("MMM dd yyyy");
        try {
            oldDate = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         formattedDate = targetFormat.format(oldDate);

        return formattedDate;
    }
    public int daysBetween(String dueDate) {

        int period=0;

        Calendar c= Calendar.getInstance();
        Date current=c.getTime();

        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date=null;
        try {
            date = originalFormat.parse(dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

            period=(int)( (date.getTime() - current.getTime()) / (1000 * 60 * 60 * 24));



   return period;
    }
}
