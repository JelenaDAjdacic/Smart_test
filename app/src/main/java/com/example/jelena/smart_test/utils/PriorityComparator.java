package com.example.jelena.smart_test.utils;

import com.example.jelena.smart_test.model.Tasks;

import java.util.Comparator;


public class PriorityComparator implements Comparator<Tasks> {


    @Override
    public int compare(Tasks lhs, Tasks rhs) {
        if (lhs.getPriority() == rhs.getPriority())
            return CalendarOperations.stringToDateConversion(lhs.getTargetDate(), "yyyy-MM-dd HH:mm:ss").compareTo(CalendarOperations.stringToDateConversion(rhs.getTargetDate(), "yyyy-MM-dd HH:mm:ss"));

        return lhs.getPriority().compareTo(rhs.getPriority());
    }
}
