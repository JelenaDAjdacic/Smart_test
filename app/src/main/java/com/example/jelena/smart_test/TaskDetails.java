package com.example.jelena.smart_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.jelena.smart_test.utils.*;

/**
 * Created by mirna on 28.1.2016.
 */
public class TaskDetails extends AppCompatActivity {
    ArrayList<HashMap<String, String>> tasksList;
    int position;
    TextView titleDetail;
    TextView dueDateDetail;
    TextView priorityDetail;
    TextView descriptionDetail;
    TextView daysLeftDetail;
    CalendarOperations operations;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);
        Intent i=getIntent();
        tasksList= (ArrayList<HashMap<String, String>>) i.getSerializableExtra("SortedArray");
        position=i.getIntExtra("Clicked",0);
        operations=new CalendarOperations();

        titleDetail= (TextView) findViewById(R.id.titleDetail);
        dueDateDetail= (TextView) findViewById(R.id.dueDateDetail);
        priorityDetail= (TextView) findViewById(R.id.priorityDetail);
        descriptionDetail= (TextView) findViewById(R.id.descriptionDetail);
        daysLeftDetail= (TextView) findViewById(R.id.daysLeftDetail);


        titleDetail.setText(tasksList.get(position).get(AppParams.TAG_TITLE));
        dueDateDetail.setText(CalendarOperations.convertDateFormat(tasksList.get(position).get(AppParams.TAG_DUE_DATE), "yyyy-MM-dd","MMM dd"));
        priorityDetail.setText(tasksList.get(position).get(AppParams.TAG_PRIORITY));
        descriptionDetail.setText(tasksList.get(position).get(AppParams.TAG_DESCRIPTION));
        daysLeftDetail.setText(CalendarOperations.daysBetweenDates(tasksList.get(position).get(AppParams.TAG_DUE_DATE),"yyyy-MM-dd"));



    }
}
