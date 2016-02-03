package com.example.jelena.smart_test;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import com.example.jelena.smart_test.utils.*;


public class TaskDetails extends AppCompatActivity {
    ArrayList<HashMap<String, String>> tasksList;
    int position;
    TextView titleDetail;
    TextView dueDateDetail;
    TextView priorityDetail;
    TextView descriptionDetail;
    TextView daysLeftDetail;
    SharedPreferences sharedPreferences;
    TextView statusDetail;
    Button resolveButton;
    Button cantresovleButton;
    LinearLayout buttonContainer;
    SharedPreferences.Editor editor;
    ImageView image;
    public String id="";
    SharedPreferences sharedPreferencesComments;
    int lastpagerpos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);

        //get data for selected day
        Intent i=getIntent();
        tasksList= (ArrayList<HashMap<String, String>>) i.getSerializableExtra(getString(R.string.sorted_array));
        position=i.getIntExtra(getString(R.string.clicked_item_position), 0);
        lastpagerpos=i.getIntExtra(getString(R.string.calendar_position), 0);


        sharedPreferences = getSharedPreferences(AppParams.KEY_STATUS, Context.MODE_PRIVATE);
        sharedPreferencesComments = getSharedPreferences(AppParams.KEY_COMMENTS, Context.MODE_PRIVATE);

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
        image= (ImageView) findViewById(R.id.imageView);

        updateTask();

    }


    public void showDialog(){
        CommentDialog dialog=new CommentDialog();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), getString(R.string.dialog));

    }

    public void onNoClicked() {

        updateTask();

    }
    public  void updateTask(){

        Toast.makeText(getApplicationContext(),sharedPreferencesComments.getString(id,""),Toast.LENGTH_SHORT).show();

        titleDetail.setText(tasksList.get(position).get(AppParams.TAG_TITLE));
        dueDateDetail.setText(StringUtils.capitalize(CalendarOperations.convertDateFormat(tasksList.get(position).get(AppParams.TAG_DUE_DATE), getResources().getString(R.string.date_format), getResources().getString(R.string.short_date_format))));
        priorityDetail.setText(tasksList.get(position).get(AppParams.TAG_PRIORITY));
        descriptionDetail.setText(tasksList.get(position).get(AppParams.TAG_DESCRIPTION));
        daysLeftDetail.setText(CalendarOperations.daysBetweenDates(tasksList.get(position).get(AppParams.TAG_DUE_DATE), getResources().getString(R.string.date_format)));


        if (sharedPreferences.getString(id,"").equals(AppParams.RESOLVED)){
            image.setImageResource(R.drawable.resolved_sign);
            statusDetail.setText(getResources().getString(R.string.resolved));
            buttonContainer.setVisibility(View.GONE);
            priorityDetail.setBackgroundResource(R.drawable.oval_shape_resolved);
            titleDetail.setTextColor(ContextCompat.getColor(this, R.color.green));
            dueDateDetail.setTextColor(ContextCompat.getColor(this, R.color.green));
            daysLeftDetail.setTextColor(ContextCompat.getColor(this, R.color.green));
            statusDetail.setTextColor(ContextCompat.getColor(this, R.color.green));



        }
        if (sharedPreferences.getString(id,"").equals(AppParams.CANT_RESOLVE)){
            image.setImageResource(R.drawable.unresolved_sign);
            statusDetail.setText(getString(R.string.unresolved));
            buttonContainer.setVisibility(View.GONE);
            titleDetail.setTextColor(ContextCompat.getColor(this, R.color.red));
            dueDateDetail.setTextColor(ContextCompat.getColor(this, R.color.red));
            daysLeftDetail.setTextColor(ContextCompat.getColor(this, R.color.red));
            statusDetail.setTextColor(ContextCompat.getColor(this, R.color.red));
        }

        if (sharedPreferences.getString(id,"").equals(AppParams.UNRESOLVED)){
            statusDetail.setText(getString(R.string.unresolved));
            statusDetail.setTextColor(ContextCompat.getColor(this, R.color.backgroundColor));
            priorityDetail.setVisibility(View.VISIBLE);

            if (CalendarOperations.currentDate(getString(R.string.date_format)).compareTo(tasksList.get(position).get(AppParams.TAG_DUE_DATE))<=0)

                buttonContainer.setVisibility(View.VISIBLE);

            resolveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editor = sharedPreferences.edit();
                    editor.putString(id, AppParams.RESOLVED);
                    editor.commit();
                    showDialog();


                }
            });
            cantresovleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = sharedPreferences.edit();
                    editor.putString(id, AppParams.CANT_RESOLVE);
                    editor.commit();
                    showDialog();


                }
            });
        }

    }
    



}
