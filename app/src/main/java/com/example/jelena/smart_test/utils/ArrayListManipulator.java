package com.example.jelena.smart_test.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jelena.smart_test.R;
import com.example.jelena.smart_test.model.Tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ArrayListManipulator {

    private List<Tasks> allTasks = null;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String currentDate;
    private String status = "";


    public ArrayListManipulator(List<Tasks> allTasks, Context context) {
        this.allTasks = allTasks;
        this.context = context;
        currentDate = CalendarOperations.currentDate(context.getResources().getString(R.string.date_format));
        sharedPreferences = context.getSharedPreferences(AppParams.KEY_STATUS, Context.MODE_PRIVATE);


    }

    public List<Tasks> findArrayByDate(String date) {

        List<Tasks> dailyTasks = new ArrayList<Tasks>();

        for (int i = 0; i < allTasks.size(); i++) {

            status = sharedPreferences.getString(allTasks.get(i).getId(), "");
            Log.d("Provera", "ALL TASKS" + String.valueOf(allTasks.size()));


            if (currentDate.compareTo(date) > 0) {


                if (allTasks.get(i).getDueDate().contains(date) && (status.equals(AppParams.UNRESOLVED))) {

                    dailyTasks.add(allTasks.get(i));
                } else if (allTasks.get(i).getTargetDate().contains(date) && (!status.equals(AppParams.UNRESOLVED))) {
                    dailyTasks.add(allTasks.get(i));
                }

            } else if (currentDate.compareTo(date) <= 0) {

                if (allTasks.get(i).getTargetDate().contains(date)) {
                    dailyTasks.add(allTasks.get(i));
                }
                if (currentDate.compareTo(date) == 0) {

                    if ((date.compareTo(CalendarOperations.convertDateFormat(allTasks.get(i).getDueDate(), context.getResources().getString(R.string.full_time_format), context.getResources().getString(R.string.date_format))) <= 0) && (status.equals(AppParams.UNRESOLVED)) && (date.compareTo(CalendarOperations.convertDateFormat(allTasks.get(i).getTargetDate(), context.getResources().getString(R.string.full_time_format), context.getResources().getString(R.string.date_format))) > 0)) {

                        dailyTasks.add(allTasks.get(i));
                    }
                }

            }


        }
        return dailyTasks;
    }

    public List<Tasks> filterArrayByStatus(String status, List<Tasks> dailyTasks) {

       List<Tasks> statusTasks = new ArrayList<Tasks>();
        for (int i = 0; i < dailyTasks.size(); i++) {
            if (sharedPreferences.getString(dailyTasks.get(i).getId(), "").equals(status)) {
                statusTasks.add(dailyTasks.get(i));
            }
        }
        return statusTasks;
    }

    public List<Tasks> sortTasksForDate(String date) {

        List<Tasks> dailyTasks = new ArrayList<Tasks>();
        List<Tasks> sortedTasks = new ArrayList<Tasks>();

        List<Tasks> resolvedTasks = new ArrayList<Tasks>();
        List<Tasks> unresolvedTasks = new ArrayList<Tasks>();
        List<Tasks> cantresolveTasks = new ArrayList<Tasks>();

        dailyTasks = findArrayByDate(date);

        if (dailyTasks.size() > 0) {

            resolvedTasks = filterArrayByStatus(AppParams.RESOLVED, dailyTasks);
            unresolvedTasks = filterArrayByStatus(AppParams.UNRESOLVED, dailyTasks);
            cantresolveTasks = filterArrayByStatus(AppParams.CANT_RESOLVE, dailyTasks);

            if (resolvedTasks.size() > 1) Collections.sort(resolvedTasks, new PriorityComparator());
            if (unresolvedTasks.size() > 1)
                Collections.sort(unresolvedTasks, new PriorityComparator());
            if (cantresolveTasks.size() > 1)
                Collections.sort(cantresolveTasks, new PriorityComparator());

            if (unresolvedTasks.size() > 0) sortedTasks.addAll(unresolvedTasks);
            if (cantresolveTasks.size() > 0) sortedTasks.addAll(cantresolveTasks);
            if (resolvedTasks.size() > 0) sortedTasks.addAll(resolvedTasks);

        }

        return sortedTasks;
    }


}
