package com.example.jelena.smart_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mirna on 28.1.2016.
 */
public class TaskDetails extends Activity{
    ArrayList<HashMap<String, String>> tasksList;
    int position;
    TextView titleDetail;
    TextView dueDateDetail;
    TextView targetDateDetail;
    TextView priorityDetail;
    TextView descriptionDetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);
        Intent i=getIntent();
        tasksList= (ArrayList<HashMap<String, String>>) i.getSerializableExtra("SortedArray");
        position=i.getIntExtra("Clicked",0);

        titleDetail= (TextView) findViewById(R.id.titleDetail);
        dueDateDetail= (TextView) findViewById(R.id.dueDateDetail);
        targetDateDetail= (TextView) findViewById(R.id.targetDateDetail);
        priorityDetail= (TextView) findViewById(R.id.priorityDetail);
        descriptionDetail= (TextView) findViewById(R.id.descriptionDetail);


        titleDetail.setText(tasksList.get(position).get("title"));
        dueDateDetail.setText(tasksList.get(position).get("DueDate"));
        targetDateDetail.setText(tasksList.get(position).get("TargetDate"));
        priorityDetail.setText(tasksList.get(position).get("PriorityDate"));
        descriptionDetail.setText(tasksList.get(position).get("Description"));



    }
}
