package com.example.jelena.smart_test;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
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


import java.util.ArrayList;
import java.util.Calendar;
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

        Intent i=getIntent();
        tasksList= (ArrayList<HashMap<String, String>>) i.getSerializableExtra("SortedArray");
        position=i.getIntExtra("Clicked", 0);
        lastpagerpos=i.getIntExtra("calPos",0);
        sharedPreferences = getApplicationContext().getSharedPreferences(AppParams.KEY_STATUS, Context.MODE_PRIVATE);
        sharedPreferencesComments = getApplicationContext().getSharedPreferences(AppParams.KEY_COMMENTS, Context.MODE_PRIVATE);
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

    @Override
    protected void onStart() {
        super.onStart();
        updateTask();
    }

 /*   @Override
    protected void onPause() {
        super.onPause();
        Intent i=new Intent(this,MainActivity.class);
        i.putExtra("lastPager",lastpagerpos);

        startActivity(i);
        }
*/
    public void showDialog(){
        CommentDialog dialog=new CommentDialog();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "CommentDialog");

    }
    public void showCommentEntry(){
        Comment dialog=new Comment();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(),"CommentEntry");
    }


    public void onNoClicked() {
        updateTask();

    }
    public  void updateTask(){

        titleDetail.setText(tasksList.get(position).get(AppParams.TAG_TITLE));
        dueDateDetail.setText(StringUtils.capitalize(CalendarOperations.convertDateFormat(tasksList.get(position).get(AppParams.TAG_DUE_DATE), getResources().getString(R.string.date_format), getResources().getString(R.string.short_date_format))));
        priorityDetail.setText(tasksList.get(position).get(AppParams.TAG_PRIORITY));
        descriptionDetail.setText(tasksList.get(position).get(AppParams.TAG_DESCRIPTION));
        daysLeftDetail.setText(CalendarOperations.daysBetweenDates(tasksList.get(position).get(AppParams.TAG_DUE_DATE), getResources().getString(R.string.date_format)));


        if (sharedPreferences.getString(id,"").equals(AppParams.RESOLVED)){
            image.setImageResource(R.drawable.resolved_sign);
            statusDetail.setText("Resolved");
            buttonContainer.setVisibility(View.GONE);

        }
        if (sharedPreferences.getString(id,"").equals(AppParams.CANT_RESOLVE)){
            image.setImageResource(R.drawable.unresolved_sign);
            statusDetail.setText("Unresolved");
            buttonContainer.setVisibility(View.GONE);

        }

        if (sharedPreferences.getString(id,"").equals(AppParams.UNRESOLVED)){
            statusDetail.setText("Unresolved");

            if (CalendarOperations.currentDate("yyyy-MM-dd").compareTo(tasksList.get(position).get(AppParams.TAG_DUE_DATE))<0)

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
