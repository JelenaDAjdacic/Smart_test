package com.example.jelena.smart_test;

/**
 * Created by Win 7 on 26.1.2016.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.PriorityComparator;
import com.example.jelena.smart_test.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;




public class FragmentContent extends Fragment {

    private ListView taskListView;
    ArrayList<HashMap<String, String>> tasksList;
    ArrayList<HashMap<String, String>> sortedDailyList;
    CalendarOperations calendarOperations;

    MyAdapter adapter;
    ArrayListManipulator arrayListManipulator;

    private static final String KEY_DATE = "date";
    private static final String KEY_ARRAY = "tasks";


    public static FragmentContent newInstance(long date,ArrayList<HashMap<String, String>> tasksList) {
        FragmentContent fragmentFirst = new FragmentContent();
        Bundle args = new Bundle();
        args.putLong(KEY_DATE, date);
        args.putSerializable(KEY_ARRAY, tasksList);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tasksList= (ArrayList<HashMap<String, String>>) getArguments().getSerializable(KEY_ARRAY);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        final long mills =getArguments().getLong(KEY_DATE);
        sortedDailyList=null;

        if ( mills > 0) {
            final Context context = getActivity();
            if (context != null) {
                taskListView=(ListView)view.findViewById(R.id.todayTasks);
                arrayListManipulator = new ArrayListManipulator(tasksList);
                calendarOperations=new CalendarOperations();
                sortedDailyList=arrayListManipulator.findArrayByDate(TimeUtils.getFormattedDate(context, mills));
                Collections.sort(sortedDailyList, new PriorityComparator());
                adapter = new MyAdapter(getContext(), sortedDailyList);
                Log.d("Smart", "onCreateView ");
        taskListView.setAdapter(adapter);}
        }

        return view;
    }


}