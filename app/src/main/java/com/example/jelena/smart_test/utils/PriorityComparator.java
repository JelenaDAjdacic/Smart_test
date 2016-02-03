package com.example.jelena.smart_test.utils;

import android.util.Log;

import com.example.jelena.smart_test.MainActivity;
import com.example.jelena.smart_test.R;

import java.util.Comparator;
import java.util.HashMap;


public class PriorityComparator implements Comparator<HashMap<String,String>>
{



    @Override
    public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
    if (Integer.parseInt(lhs.get(AppParams.TAG_PRIORITY))==Integer.parseInt(rhs.get(AppParams.TAG_PRIORITY)))
        return CalendarOperations.stringToDateConversion(lhs.get(AppParams.TAG_TARGET_DATE), "yyyy-MM-dd HH:mm:ss").compareTo(CalendarOperations.stringToDateConversion(rhs.get(AppParams.TAG_TARGET_DATE), "yyyy-MM-dd HH:mm:ss"));

        return lhs.get(AppParams.TAG_PRIORITY).compareTo(rhs.get(AppParams.TAG_PRIORITY));
    }
}
