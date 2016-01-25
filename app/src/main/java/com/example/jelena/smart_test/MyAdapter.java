package com.example.jelena.smart_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Win 7 on 26.1.2016.
 */
class MyAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> dailyTasks;
    private Context context;
    TextView text;



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
        View raw;
        if (convertView==null){


            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            raw= inflater.inflate(R.layout.custom_row, parent,false);
        }
        else {
            raw = convertView;
        }
        text= (TextView) raw.findViewById(R.id.textView);

        text.setText(dailyTasks.get(position).get("title"));

        return raw;
    }
}
