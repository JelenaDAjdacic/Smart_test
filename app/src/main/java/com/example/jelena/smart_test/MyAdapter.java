package com.example.jelena.smart_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jelena.smart_test.utils.AppParams;
import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.SharedPreferenceUtils;
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
    FrameLayout container;

    LinearLayout overlay;
    LinearLayout grid;


    public MyAdapter(Context context,ArrayList<HashMap<String, String>> dailyTasks){

        this.context=context;
        this.dailyTasks=dailyTasks;


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
        updateView(row,position);


        return row;
    }
    private void componentInitialization(View row){

        container= (FrameLayout) row.findViewById(R.id.customRow);
        title= (TextView) row.findViewById(R.id.title);
        dueDay= (TextView) row.findViewById(R.id.dueDate);
        countdown= (TextView) row.findViewById(R.id.countdown);
        priority= (TextView) row.findViewById(R.id.priority);
        overlay= (LinearLayout) row.findViewById(R.id.overlay);
        grid= (LinearLayout) row.findViewById(R.id.grid);

    }

    public void updateView(View row, int position){

        componentInitialization(row);

        dueDateValue=dailyTasks.get(position).get(AppParams.TAG_DUE_DATE);
        titleValue=dailyTasks.get(position).get(AppParams.TAG_TITLE);
        priorityValue=dailyTasks.get(position).get(AppParams.TAG_PRIORITY);
        idValue=dailyTasks.get(position).get(AppParams.TAG_ID);

        dueDay.setText(StringUtils.capitalize(CalendarOperations.convertDateFormat(dueDateValue, context.getResources().getString(R.string.date_format), context.getResources().getString(R.string.short_date_format))));
        title.setText(titleValue);
        countdown.setText(CalendarOperations.daysBetweenDates(dueDateValue, context.getResources().getString(R.string.date_format)));
        priority.setText(priorityValue);


        if(SharedPreferenceUtils.getString(context, Context.MODE_PRIVATE, AppParams.KEY_STATUS, idValue).equals(AppParams.RESOLVED)){

            colorRowByStatus(row, position, context, R.drawable.row_resolved,  R.color.green, View.GONE);

        }
        else  if (SharedPreferenceUtils.getString(context, Context.MODE_PRIVATE, AppParams.KEY_STATUS, idValue).equals(AppParams.CANT_RESOLVE)){

            colorRowByStatus(row, position, context, R.drawable.row_unresolved,  R.color.red, View.GONE);

       }
        else {

            colorRowByStatus(row, position, context, R.drawable.row,  R.color.red, View.VISIBLE);
        }

    }
    private void colorRowByStatus(View row, int position, Context context, int idDrawable, int idColor, int priorityVisibility){

        row.setBackgroundResource(idDrawable);
        dueDay.setTextColor(ContextCompat.getColor(context, idColor));
        title.setTextColor(ContextCompat.getColor(context, idColor));
        countdown.setTextColor(ContextCompat.getColor(context, idColor));
        titleValue=dailyTasks.get(position).get(AppParams.TAG_TITLE);
        priority.setVisibility(priorityVisibility);

    }
}
