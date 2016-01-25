package com.example.jelena.smart_test;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Win 7 on 25.1.2016.
 */
public class ArrayListManipulator {
    ArrayList<HashMap<String, String>> allTasks=null;

    public ArrayListManipulator(ArrayList<HashMap<String, String>> allTasks){
        this.allTasks=allTasks;
    }

    public ArrayList<HashMap<String, String>> findArrayByDate(String date){

        ArrayList<HashMap<String, String>> dailyTasks=new  ArrayList<HashMap<String, String>>();

        for (int i=0;i<allTasks.size();i++){


            if (allTasks.get(i).get("TargetDate").contains(date)) {
                dailyTasks.add(allTasks.get(i));

            }
        }
        return dailyTasks;
    }
}
