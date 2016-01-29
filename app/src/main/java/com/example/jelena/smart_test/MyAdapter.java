package com.example.jelena.smart_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jelena.smart_test.utils.CalendarOperations;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Win 7 on 26.1.2016.
 */
class MyAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> dailyTasks;
    private Context context;
    TextView title;
    TextView dueDay;
    TextView countdown;
    TextView priority;

    String dueDateValue="";
    String titleValue="";
    String priorityValue="";


    CalendarOperations operations;


    public MyAdapter(Context context,ArrayList<HashMap<String, String>> dailyTasks){

        this.context=context;
        this.dailyTasks=dailyTasks;
        operations=new CalendarOperations();


    }
    @Override
    public int getCount() {
        return dailyTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(R.layout.custom_row, parent,false);
        }
        else {
            row = convertView;
        }



        title= (TextView) row.findViewById(R.id.title);
        dueDay= (TextView) row.findViewById(R.id.dueDate);
        countdown= (TextView) row.findViewById(R.id.countdown);
        priority= (TextView) row.findViewById(R.id.priority);

        dueDateValue=dailyTasks.get(position).get("DueDate");
        titleValue=dailyTasks.get(position).get("title");
        priorityValue=dailyTasks.get(position).get("Priority");


        dueDay.setText("Due date "+operations.convertDateFormat(dueDateValue,"yyyy-MM-dd","MMM dd yyyy"));
        title.setText("Task title "+titleValue);
        countdown.setText("Left "+operations.daysBetweenDates(dueDateValue,"yyyy-MM-dd"));
        priority.setText("Priority "+priorityValue);

        return row;
    }
}
