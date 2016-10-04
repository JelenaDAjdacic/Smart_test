package com.example.jelena.smart_test;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jelena.smart_test.model.Tasks;
import com.example.jelena.smart_test.utils.AppParams;
import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.SharedPreferenceUtils;
import com.example.jelena.smart_test.utils.StringUtils;

import java.util.List;


class MyAdapter extends BaseAdapter {

    private List<Tasks> dailyTasksList = null;
    private Context context;
    private TextView title;
    private TextView dueDay;
    private TextView countdown;
    private TextView priority;

    private String titleValue = "";

    private FrameLayout container;
    private LinearLayout overlay;
    private LinearLayout grid;


    public MyAdapter(Context context, List<Tasks> dailyTasksList) {

        this.context = context;
        this.dailyTasksList = dailyTasksList;


    }

    @Override
    public int getCount() {
        return dailyTasksList.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyTasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_row, parent, false);
        } else {
            row = convertView;
        }
        updateView(row, position);


        return row;
    }

    private void componentInitialization(View row) {

        container = (FrameLayout) row.findViewById(R.id.customRow);
        title = (TextView) row.findViewById(R.id.title);
        dueDay = (TextView) row.findViewById(R.id.dueDate);
        countdown = (TextView) row.findViewById(R.id.countdown);
        priority = (TextView) row.findViewById(R.id.priority);
        overlay = (LinearLayout) row.findViewById(R.id.overlay);
        grid = (LinearLayout) row.findViewById(R.id.grid);

    }

    public void updateView(View row, int position) {

        componentInitialization(row);

        String dueDateValue = dailyTasksList.get(position).getDueDate();
        titleValue = dailyTasksList.get(position).getTitle();
        int priorityValue = dailyTasksList.get(position).getPriority();
        String idValue = dailyTasksList.get(position).getId();

        dueDay.setText(StringUtils.capitalize(CalendarOperations.convertDateFormat(dueDateValue, context.getResources().getString(R.string.date_format), context.getResources().getString(R.string.short_date_format))));
        title.setText(titleValue);
        countdown.setText(CalendarOperations.daysBetweenDates(dueDateValue, context.getResources().getString(R.string.date_format)));
        priority.setText(context.getString(R.string.priority, priorityValue));


        if (SharedPreferenceUtils.getString(context, Context.MODE_PRIVATE, AppParams.KEY_STATUS, idValue).equals(AppParams.RESOLVED)) {

            colorRowByStatus(row, position, context, R.drawable.row_resolved, R.color.green, View.GONE);

        } else if (SharedPreferenceUtils.getString(context, Context.MODE_PRIVATE, AppParams.KEY_STATUS, idValue).equals(AppParams.CANT_RESOLVE)) {

            colorRowByStatus(row, position, context, R.drawable.row_unresolved, R.color.red, View.GONE);

        } else {

            colorRowByStatus(row, position, context, R.drawable.row, R.color.red, View.VISIBLE);
        }

    }

    private void colorRowByStatus(View row, int position, Context context, int idDrawable, int idColor, int priorityVisibility) {

        row.setBackgroundResource(idDrawable);
        dueDay.setTextColor(ContextCompat.getColor(context, idColor));
        title.setTextColor(ContextCompat.getColor(context, idColor));
        countdown.setTextColor(ContextCompat.getColor(context, idColor));
        titleValue = dailyTasksList.get(position).getTitle();
        priority.setVisibility(priorityVisibility);

    }
}
