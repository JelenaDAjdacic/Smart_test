package com.example.jelena.smart_test;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Win 7 on 26.1.2016.
 */
class MyAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> dailyTasks;
    private Context context;
    TextView title;
    TextView dueDay;
    TextView countdown;

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




        dueDay.setText(operations.convertDateFormat(dailyTasks.get(position).get("DueDate")));

        title.setText("Task title "+dailyTasks.get(position).get("title"));
        countdown.setText(operations.daysBetween(dailyTasks.get(position).get("DueDate")));

        return row;
    }
}
