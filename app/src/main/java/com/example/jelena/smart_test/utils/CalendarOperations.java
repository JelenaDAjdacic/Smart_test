package com.example.jelena.smart_test.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mirna on 26.1.2016.
 */
public class CalendarOperations {

    static Calendar c = null;

    public CalendarOperations() {
        c = Calendar.getInstance();
    }

    public static String currentDate(String format) {
        String formattedDate = "";

        c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        formattedDate = dateFormat.format(c.getTime());

        return formattedDate;
    }

    public static Date currentDate() {
        Date date = null;

        date = c.getTime();

        return date;
    }

    public static String convertDateFormat(String date, String oldFormat, String newFormat) {
        String formattedDate = "";
        Date originalDate = null;
        DateFormat originalFormat = new SimpleDateFormat(oldFormat);
        DateFormat targetFormat = new SimpleDateFormat(newFormat);
        try {
            originalDate = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formattedDate = targetFormat.format(originalDate);

        return formattedDate;
    }

    public static Date stringToDateConversion(String dateString, String format) {

        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String daysBetweenDates(String dueDate, String format) {

        String period = "";
        long diffMillSec = 0;

        Date today = null;
        Date lastDay = null;

        today = currentDate();
        lastDay = stringToDateConversion(dueDate, format);


        if (today.before(lastDay)) {
            diffMillSec = (int) ((lastDay.getTime() - today.getTime()) / (1000 * 60 * 60 * 24)) + 1;

        }
        period = Long.toString(diffMillSec);

        return period;
    }
}
