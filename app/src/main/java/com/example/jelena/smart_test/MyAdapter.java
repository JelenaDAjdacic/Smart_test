package com.example.jelena.smart_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jelena.smart_test.utils.AppParams;
import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;


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

    String idValue="";
    SharedPreferences sharedPreferences;
    LinearLayout container;


    public MyAdapter(Context context,ArrayList<HashMap<String, String>> dailyTasks){

        this.context=context;
        this.dailyTasks=dailyTasks;
        sharedPreferences = context.getSharedPreferences(AppParams.KEY_STATUS, Context.MODE_PRIVATE);


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

        container= (LinearLayout) row.findViewById(R.id.customRow);
        title= (TextView) row.findViewById(R.id.title);
        dueDay= (TextView) row.findViewById(R.id.dueDate);
        countdown= (TextView) row.findViewById(R.id.countdown);
        priority= (TextView) row.findViewById(R.id.priority);

        dueDateValue=dailyTasks.get(position).get(AppParams.TAG_DUE_DATE);
        titleValue=dailyTasks.get(position).get(AppParams.TAG_TITLE);
        priorityValue=dailyTasks.get(position).get(AppParams.TAG_PRIORITY);
        idValue=dailyTasks.get(position).get(AppParams.TAG_ID);

        dueDay.setText(StringUtils.capitalize(CalendarOperations.convertDateFormat(dueDateValue, context.getResources().getString(R.string.date_format), context.getResources().getString(R.string.short_date_format))));
        title.setText(titleValue);
        countdown.setText(CalendarOperations.daysBetweenDates(dueDateValue, context.getResources().getString(R.string.date_format)));
        priority.setText(priorityValue);


        if(sharedPreferences.getString(idValue,"").equals(AppParams.RESOLVED)){
            row.setBackgroundResource(R.drawable.row_resolved);
            dueDay.setTextColor(ContextCompat.getColor(context, R.color.green));
            title.setTextColor(ContextCompat.getColor(context, R.color.green));
            countdown.setTextColor(ContextCompat.getColor(context, R.color.green));
            titleValue=dailyTasks.get(position).get(AppParams.TAG_TITLE);
        }
        else  if (sharedPreferences.getString(idValue,"").equals(AppParams.CANT_RESOLVE)){

            row.setBackgroundResource(R.drawable.row_unresolved);
        }
        else {
            row.setBackgroundResource(R.drawable.row);
            priority.setVisibility(View.VISIBLE);

        }

        return row;
    }
}
