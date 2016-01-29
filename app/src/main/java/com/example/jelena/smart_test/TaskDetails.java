package com.example.jelena.smart_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    SharedPreferences sharedPreferences;
    TextView statusDetail;
    Button resolveButton;
    Button cantresovleButton;
    LinearLayout buttonContainer;
    SharedPreferences.Editor editor;
    ImageView image;
    String id="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);
        Intent i=getIntent();
        tasksList= (ArrayList<HashMap<String, String>>) i.getSerializableExtra("SortedArray");
        position=i.getIntExtra("Clicked", 0);
        sharedPreferences = getApplicationContext().getSharedPreferences("AppSharedPreff", Context.MODE_PRIVATE);
        id=tasksList.get(position).get(AppParams.TAG_ID);

        titleDetail= (TextView) findViewById(R.id.titleDetail);
        dueDateDetail= (TextView) findViewById(R.id.dueDateDetail);
        priorityDetail= (TextView) findViewById(R.id.priorityDetail);
        descriptionDetail= (TextView) findViewById(R.id.descriptionDetail);
        daysLeftDetail= (TextView) findViewById(R.id.daysLeftDetail);
        statusDetail= (TextView) findViewById(R.id.statusDetail);
        resolveButton= (Button) findViewById(R.id.resolve);
        cantresovleButton= (Button) findViewById(R.id.can_not_resolve);
        buttonContainer= (LinearLayout) findViewById(R.id.buttonContainer);
        titleDetail.setText(tasksList.get(position).get(AppParams.TAG_TITLE));
        image= (ImageView) findViewById(R.id.imageView);

        dueDateDetail.setText(CalendarOperations.convertDateFormat(tasksList.get(position).get(AppParams.TAG_DUE_DATE), "yyyy-MM-dd", "MMM dd"));
        priorityDetail.setText(tasksList.get(position).get(AppParams.TAG_PRIORITY));
        descriptionDetail.setText(tasksList.get(position).get(AppParams.TAG_DESCRIPTION));
        daysLeftDetail.setText(CalendarOperations.daysBetweenDates(tasksList.get(position).get(AppParams.TAG_DUE_DATE), "yyyy-MM-dd"));


        if (sharedPreferences.getString(id,"Lallal").equals("Resolved")){
            image.setImageResource(R.drawable.resolved_sign);
            statusDetail.setText("Resolved");
        }
        if (sharedPreferences.getString(id,"Lallal").equals("Can't resolve")){
            image.setImageResource(R.drawable.unresolved_sign);
            statusDetail.setText("Unresolved");

        }

        if (sharedPreferences.getString(id,"Lallal").equals("Unresolved")){
            statusDetail.setText("Unresolved");

                buttonContainer.setVisibility(View.VISIBLE);
            resolveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editor = sharedPreferences.edit();
                    editor.putString(id, "Resolved");
                    editor.commit();
                    finish();
                    startActivity(getIntent());


                }
            });
            cantresovleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = sharedPreferences.edit();
                    editor.putString(id, "Can't resolve");
                    editor.commit();
                    finish();
                    startActivity(getIntent());


                }
            });
        }





    }
}
