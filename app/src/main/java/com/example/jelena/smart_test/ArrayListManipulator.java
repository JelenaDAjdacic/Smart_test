package com.example.jelena.smart_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jelena.smart_test.utils.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Win 7 on 25.1.2016.
 */
public class ArrayListManipulator {
    ArrayList<HashMap<String, String>> allTasks=null;
    Context context;
    SharedPreferences sharedPreferences;
    String currentDate;
    String status="";



    public ArrayListManipulator(ArrayList<HashMap<String, String>> allTasks,Context context){
        this.allTasks=allTasks;
        this.context=context;
        currentDate=CalendarOperations.currentDate("yyyy-MM-dd");
        sharedPreferences=context.getSharedPreferences(AppParams.KEY_STATUS, Context.MODE_PRIVATE);

    }

    public ArrayList<HashMap<String, String>> findArrayByDate(String date){

        ArrayList<HashMap<String, String>> dailyTasks=new  ArrayList<HashMap<String, String>>();



       // if (currentDate.compareTo(date)==0) Log.d("NJNJ",date+" ==");

        for (int i=0;i<allTasks.size();i++){

            status=sharedPreferences.getString(allTasks.get(i).get(AppParams.TAG_ID),"");


            if (currentDate.compareTo(date)>0) {

                if (allTasks.get(i).get(AppParams.TAG_DUE_DATE).contains(date)&&(status.equals(AppParams.UNRESOLVED))){
                    dailyTasks.add(allTasks.get(i));
                }
                else if (allTasks.get(i).get(AppParams.TAG_TARGET_DATE).contains(date)&&(!status.equals(AppParams.UNRESOLVED))){
                    dailyTasks.add(allTasks.get(i));
                }

            }
            else if (currentDate.compareTo(date)<=0) {

                if (allTasks.get(i).get(AppParams.TAG_TARGET_DATE).contains(date)){
                    dailyTasks.add(allTasks.get(i));
                }
                if (currentDate.compareTo(date)==0){

                 if ((date.compareTo(CalendarOperations.convertDateFormat(allTasks.get(i).get(AppParams.TAG_DUE_DATE),context.getResources().getString(R.string.full_time_format),context.getResources().getString(R.string.date_format)))<=0)&&(status.equals(AppParams.UNRESOLVED))&&(date.compareTo(CalendarOperations.convertDateFormat(allTasks.get(i).get(AppParams.TAG_TARGET_DATE),context.getResources().getString(R.string.full_time_format),context.getResources().getString(R.string.date_format)))>=0)){
                     Log.d("NJNJ","NJNJN"+ date.compareTo(CalendarOperations.convertDateFormat(allTasks.get(i).get(AppParams.TAG_DUE_DATE),context.getResources().getString(R.string.full_time_format),context.getResources().getString(R.string.date_format))));

                     Log.d("NJNJ", "NJNJN" + CalendarOperations.convertDateFormat(allTasks.get(i).get(AppParams.TAG_DUE_DATE), context.getResources().getString(R.string.full_time_format), context.getResources().getString(R.string.date_format)));
                    dailyTasks.add(allTasks.get(i));}
                }

            }


        }
        return dailyTasks;
    }
    public ArrayList<HashMap<String, String>> filterArrayByStatus(String status,ArrayList<HashMap<String, String>> dailyTasks){

        ArrayList<HashMap<String, String>> statusTasks=new  ArrayList<HashMap<String, String>>();
        for (int i=0;i<dailyTasks.size();i++){
            if (sharedPreferences.getString(dailyTasks.get(i).get(AppParams.TAG_ID),"").equals(status)) {
                statusTasks.add(dailyTasks.get(i));
            }
        }
        return statusTasks;
    }
    public ArrayList<HashMap<String, String>> sortTasksForDate(String date){

        ArrayList<HashMap<String, String>> dailyTasks=new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String, String>> sortedTasks=new ArrayList<HashMap<String, String>>();

        ArrayList<HashMap<String, String>> resolvedTasks=new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String, String>> unresolvedTasks=new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String, String>> cantresolveTasks=new ArrayList<HashMap<String, String>>();

        dailyTasks=findArrayByDate(date);

        if (dailyTasks.size()>1) {

            resolvedTasks = filterArrayByStatus(AppParams.RESOLVED,dailyTasks);
            unresolvedTasks = filterArrayByStatus(AppParams.UNRESOLVED,dailyTasks);
            cantresolveTasks = filterArrayByStatus(AppParams.CANT_RESOLVE,dailyTasks);

            if (resolvedTasks.size()>1) Collections.sort(resolvedTasks, new PriorityComparator());
            if (unresolvedTasks.size()>1) Collections.sort(unresolvedTasks, new PriorityComparator());
            if (cantresolveTasks.size()>1) Collections.sort(cantresolveTasks, new PriorityComparator());

            if (unresolvedTasks.size()>0) sortedTasks.addAll(unresolvedTasks);
            if (cantresolveTasks.size()>0) sortedTasks.addAll(cantresolveTasks);
            if (resolvedTasks.size()>0) sortedTasks.addAll(resolvedTasks);

        }

   return sortedTasks; }


}
