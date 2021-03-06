package com.example.jelena.smart_test;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jelena.smart_test.model.Tasks;
import com.example.jelena.smart_test.ui.CommentDialog;
import com.example.jelena.smart_test.ui.CustomButton;
import com.example.jelena.smart_test.utils.AppParams;
import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.SharedPreferenceUtils;
import com.example.jelena.smart_test.utils.StringUtils;

import java.util.List;


public class TaskDetails extends AppCompatActivity {

    List<Tasks> tasksList;
    int position;

    private TextView titleDetail;
    private TextView dueDateDetail;
    private TextView priorityDetail;
    private TextView descriptionDetail;
    private TextView daysLeftDetail;
    private TextView statusDetail;

    private Button resolveButton;
    private Button cantresovleButton;
    private LinearLayout buttonContainer;
    private ImageView image;
    public String id = "";
    private int lastpagerpos;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //get data for selected day
        Intent i = getIntent();
        tasksList = (List<Tasks>) i.getSerializableExtra(getString(R.string.sorted_array));
        position = i.getIntExtra(getString(R.string.clicked_item_position), 0);
        lastpagerpos = i.getIntExtra(getString(R.string.calendar_position), 0);

        StringUtils.setActionBarFont(this, getSupportActionBar(), getString(R.string.task_detail));
        componentInitialization();
        updateTask();
    }

    public void showDialog() {

        CommentDialog dialog = new CommentDialog();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), getString(R.string.dialog));

    }

    private void componentInitialization() {

        id = tasksList.get(position).getId();
        titleDetail = (TextView) findViewById(R.id.titleDetail);
        dueDateDetail = (TextView) findViewById(R.id.dueDateDetail);
        priorityDetail = (TextView) findViewById(R.id.priorityDetail);
        descriptionDetail = (TextView) findViewById(R.id.descriptionDetail);
        daysLeftDetail = (TextView) findViewById(R.id.daysLeftDetail);
        statusDetail = (TextView) findViewById(R.id.statusDetail);
        resolveButton = (CustomButton) findViewById(R.id.resolve);
        cantresovleButton = (CustomButton) findViewById(R.id.can_not_resolve);
        buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);
        image = (ImageView) findViewById(R.id.imageView);

    }

    public void onNoClicked() {

        updateTask();

    }

    public void updateTask() {

        titleDetail.setText(tasksList.get(position).getTitle());
        dueDateDetail.setText(StringUtils.capitalize(CalendarOperations.convertDateFormat(tasksList.get(position).getDueDate(), getResources().getString(R.string.date_format), getResources().getString(R.string.short_date_format))));
        priorityDetail.setText(getString(R.string.priority,tasksList.get(position).getPriority()));
        descriptionDetail.setText(tasksList.get(position).getDescription());
        daysLeftDetail.setText(CalendarOperations.daysBetweenDates(tasksList.get(position).getDueDate(), getResources().getString(R.string.date_format)));


        if (SharedPreferenceUtils.getString(this, MODE_PRIVATE, AppParams.KEY_STATUS, id).equals(AppParams.RESOLVED)) {

            image.setImageResource(R.drawable.resolved_sign);
            colorViewsByStatus(getString(R.string.resolved), R.color.green, R.drawable.oval_shape_resolved, R.color.green);

        }
        if (SharedPreferenceUtils.getString(this, MODE_PRIVATE, AppParams.KEY_STATUS, id).equals(AppParams.CANT_RESOLVE)) {

            image.setImageResource(R.drawable.unresolved_sign);
            colorViewsByStatus(getString(R.string.unresolved), R.color.red, R.drawable.oval_style_unresolved, R.color.red);

        }

        if (SharedPreferenceUtils.getString(this, MODE_PRIVATE, AppParams.KEY_STATUS, id).equals(AppParams.UNRESOLVED)) {

            colorViewsByStatus(getString(R.string.unresolved), R.color.backgroundColor, R.drawable.oval_style_unresolved, R.color.red);
            if (CalendarOperations.currentDate(getString(R.string.date_format)).compareTo(tasksList.get(position).getDueDate()) <= 0)

                enableStatusChange();
        }

    }

    private void enableStatusChange() {

        buttonContainer.setVisibility(View.VISIBLE);

        resolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferenceUtils.putString(getApplicationContext(), MODE_PRIVATE, AppParams.KEY_STATUS, id, AppParams.RESOLVED);
                showDialog();


            }
        });
        cantresovleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferenceUtils.putString(getApplicationContext(), MODE_PRIVATE, AppParams.KEY_STATUS, id, AppParams.CANT_RESOLVE);
                showDialog();


            }
        });
    }

    private void colorViewsByStatus(String statusText, int statusColor, int idPriorityDrawable, int idColor) {

        statusDetail.setText(statusText);
        buttonContainer.setVisibility(View.GONE);
        priorityDetail.setBackgroundResource(idPriorityDrawable);
        titleDetail.setTextColor(ContextCompat.getColor(this, idColor));
        dueDateDetail.setTextColor(ContextCompat.getColor(this, idColor));
        daysLeftDetail.setTextColor(ContextCompat.getColor(this, idColor));
        statusDetail.setTextColor(ContextCompat.getColor(this, statusColor));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
