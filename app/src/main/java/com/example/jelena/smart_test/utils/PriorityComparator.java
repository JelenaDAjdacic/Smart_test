package com.example.jelena.smart_test.utils;

import android.util.Log;

import com.example.jelena.smart_test.MainActivity;
import com.example.jelena.smart_test.R;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Win 7 on 27.1.2016.
 */
public class PriorityComparator implements Comparator<HashMap<String,String>>
{



    @Override
    public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
    if (Integer.parseInt(lhs.get("Priority"))==Integer.parseInt(rhs.get("Priority")))
        return CalendarOperations.stringToDateConversion(lhs.get("TargetDate"), "yyyy-MM-dd HH:mm:ss").compareTo(CalendarOperations.stringToDateConversion(rhs.get("TargetDate"), "yyyy-MM-dd HH:mm:ss"));

        return lhs.get("Priority").compareTo(rhs.get("Priority"));
    }
}
