package com.example.jelena.smart_test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;



/**
 * Created by Win 7 on 26.1.2016.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jelena.smart_test.utils.CalendarOperations;

import java.util.ArrayList;
import java.util.HashMap;




public class FragmentContent extends Fragment {

    private String tvContentValue;

    private ListView taskListView;
    ArrayList<HashMap<String, String>> tasksList;
    CalendarOperations calendarOperations;

    MyAdapter adapter;
    ArrayListManipulator arrayListManipulator;

    private static final String KEY_DATE = "date";
    private static final String KEY_ARRAY = "date";


    public static FragmentContent newInstance(long date,ArrayList<HashMap<String, String>> tasksList) {
        FragmentContent fragmentFirst = new FragmentContent();
        Bundle args = new Bundle();
        args.putLong(KEY_DATE, date);
        args.putSerializable(KEY_ARRAY, tasksList);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long millis = getArguments().getLong(KEY_DATE);
        tasksList= (ArrayList<HashMap<String, String>>) getArguments().getSerializable(KEY_ARRAY);
      //  Toast.makeText(getContext(),"Lalalala"+tasksList.size(), Toast.LENGTH_SHORT).show();
        if (millis > 0) {
            final Context context = getActivity();
            if (context != null) {

                tvContentValue = "This is the content for the date ";

                return;
            }
        }

        tvContentValue = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity activity= (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        taskListView=(ListView)view.findViewById(R.id.todayTasks);
        arrayListManipulator=new ArrayListManipulator(tasksList);
        calendarOperations=new CalendarOperations();

        adapter = new MyAdapter(getContext(),arrayListManipulator.findArrayByDate(calendarOperations.currentDate("yyyy-MM-dd")));

        taskListView.setAdapter(adapter);
        return view;
    }


}