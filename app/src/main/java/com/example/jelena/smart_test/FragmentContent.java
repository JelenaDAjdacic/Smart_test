package com.example.jelena.smart_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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
    SharedPreferences sharedPreferencesStatus;
    SharedPreferences.Editor editor;

    MyAdapter adapter;
    ArrayListManipulator arrayListManipulator;
    ImageView emptyScreen;




    public static FragmentContent newInstance(long date,ArrayList<HashMap<String, String>> tasksList) {
        FragmentContent fragmentFirst = new FragmentContent();
        Bundle args = new Bundle();
        args.putLong(AppParams.BUNDLE_KEY_DATE, date);
        args.putSerializable(AppParams.BUNDLE_KEY_TASKS, tasksList);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tasksList= (ArrayList<HashMap<String, String>>) getArguments().getSerializable(AppParams.BUNDLE_KEY_TASKS);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        emptyScreen= (ImageView) view.findViewById(R.id.emptyScreenImage);
        final long mills =getArguments().getLong(AppParams.BUNDLE_KEY_DATE);
        sortedDailyList=null;
        sharedPreferencesStatus=getActivity().getSharedPreferences(AppParams.KEY_STATUS, Context.MODE_PRIVATE);
        editor=sharedPreferencesStatus.edit();



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
                taskListView.setLongClickable(true);
                taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MainActivity mainActivity = (MainActivity) getActivity();

                        Intent intent = new Intent(getContext(), TaskDetails.class);
                        intent.putExtra(getResources().getString(R.string.sorted_array), sortedDailyList);
                        intent.putExtra(getResources().getString(R.string.clicked_item_position), position);
                        intent.putExtra(getResources().getString(R.string.calendar_position), mainActivity.lastPagerPosition);
                        startActivity(intent);
                    }
                });
                taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                        if ((CalendarOperations.currentDate(getString(R.string.date_format)).compareTo( sortedDailyList.get(position).get(AppParams.TAG_DUE_DATE))<=0)&&(sharedPreferencesStatus.getString(sortedDailyList.get(position).get(AppParams.TAG_ID),"").equals(AppParams.UNRESOLVED))) {
                            //taskListView.getItemAtPosition(position).

                            final LinearLayout overlay = (LinearLayout) view.findViewById(R.id.overlay);
                            overlay.setVisibility(View.VISIBLE);

                            ImageButton btnResolve= (ImageButton) view.findViewById(R.id.btnResolved);
                            ImageButton btnNotResolve= (ImageButton) view.findViewById(R.id.btnNotResolved);

                            btnResolve.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    editor.putString(sortedDailyList.get(position).get(AppParams.TAG_ID),AppParams.RESOLVED);
                                    editor.commit();
                                    adapter.updateView(view, position);
                                    overlay.setVisibility(View.GONE);


                                }
                            });

                            btnNotResolve.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    editor.putString(sortedDailyList.get(position).get(AppParams.TAG_ID),AppParams.CANT_RESOLVE);
                                    editor.commit();
                                    adapter.updateView(view, position);
                                    overlay.setVisibility(View.GONE);

                                }
                            });

                        }
                        return true;

                    }
                });

           }
        }

        return view;
    }




}