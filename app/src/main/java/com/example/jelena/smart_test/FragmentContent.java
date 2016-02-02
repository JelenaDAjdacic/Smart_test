package com.example.jelena.smart_test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.PriorityComparator;
import com.example.jelena.smart_test.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import com.example.jelena.smart_test.utils.*;




public class FragmentContent extends Fragment {

    private ListView taskListView;
    ArrayList<HashMap<String, String>> tasksList;
    ArrayList<HashMap<String, String>> sortedDailyList;
    CalendarOperations calendarOperations;

    MyAdapter adapter;
    ArrayListManipulator arrayListManipulator;
    ImageView emptyScreen;

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
        emptyScreen= (ImageView) view.findViewById(R.id.emptyScreenImage);
        final long mills =getArguments().getLong(KEY_DATE);
        sortedDailyList=null;



        if ( mills > 0) {
            final Context context = getActivity();
            if (context != null) {
                taskListView=(ListView)view.findViewById(R.id.todayTasks);
                arrayListManipulator = new ArrayListManipulator(tasksList,getContext());
                calendarOperations=new CalendarOperations();
                sortedDailyList=new ArrayListManipulator(tasksList,getContext()).sortTasksForDate(TimeUtils.getFormattedDate(context, mills));
                if (sortedDailyList.size()>0) emptyScreen.setVisibility(View.GONE);
                adapter = new MyAdapter(getContext(), sortedDailyList);
                taskListView.setAdapter(adapter);
                taskListView.setItemsCanFocus(true);
                taskListView.setClickable(true);
                taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MainActivity mainActivity= (MainActivity) getActivity();

                        Intent intent=new Intent(getContext(),TaskDetails.class);
                        intent.putExtra(getResources().getString(R.string.sorted_array),sortedDailyList);
                        intent.putExtra(getResources().getString(R.string.clicked_item_position),position);
                        intent.putExtra(getResources().getString(R.string.calendar_position),mainActivity.lastPagerPosition);
                        startActivity(intent);
                    }
                });

           }
        }

        return view;
    }




}