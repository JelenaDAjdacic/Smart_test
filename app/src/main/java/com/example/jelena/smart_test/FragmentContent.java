package com.example.jelena.smart_test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jelena.smart_test.utils.AppParams;
import com.example.jelena.smart_test.utils.ArrayListManipulator;
import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.SharedPreferenceUtils;
import com.example.jelena.smart_test.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentContent extends Fragment {

    private ListView taskListView;
    ArrayList<HashMap<String, String>> tasksList;
    ArrayList<HashMap<String, String>> sortedDailyList;


    MyAdapter adapter;
    LinearLayout emptyScreen;
    MainActivity mainActivity;


    public static FragmentContent newInstance(long date, ArrayList<HashMap<String, String>> tasksList) {
        FragmentContent fragmentFirst = new FragmentContent();
        Bundle args = new Bundle();
        args.putLong(AppParams.BUNDLE_KEY_DATE, date);
        args.putSerializable(AppParams.BUNDLE_KEY_TASKS, tasksList);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tasksList = (ArrayList<HashMap<String, String>>) getArguments().getSerializable(AppParams.BUNDLE_KEY_TASKS);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        emptyScreen = (LinearLayout) view.findViewById(R.id.emptyScreenContainer);

        final long mills = getArguments().getLong(AppParams.BUNDLE_KEY_DATE);
        sortedDailyList = null;
        mainActivity = (MainActivity) getActivity();


        if (mills > 0) {
            final Context context = getActivity();
            if (context != null) {

                taskListView = (ListView) view.findViewById(R.id.todayTasks);

                sortedDailyList = new ArrayListManipulator(tasksList, getContext()).sortTasksForDate(TimeUtils.getFormattedDate(context, mills));

                if (sortedDailyList.size() == 0) emptyScreen.setVisibility(View.VISIBLE);

                else {

                    adapter = new MyAdapter(getContext(), sortedDailyList);
                    taskListView.setAdapter(adapter);
                    taskListView.setItemsCanFocus(true);
                    taskListView.setClickable(true);
                    taskListView.setLongClickable(true);
                    taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


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

                            if ((CalendarOperations.currentDate(getString(R.string.date_format)).compareTo(sortedDailyList.get(position).get(AppParams.TAG_DUE_DATE)) <= 0) && (SharedPreferenceUtils.getString(getActivity(), Context.MODE_PRIVATE, AppParams.KEY_STATUS, sortedDailyList.get(position).get(AppParams.TAG_ID)).equals(AppParams.UNRESOLVED))) {

                                final LinearLayout overlay = (LinearLayout) view.findViewById(R.id.overlay);
                                overlay.setVisibility(View.VISIBLE);

                                ImageButton btnResolve = (ImageButton) view.findViewById(R.id.btnResolved);
                                ImageButton btnNotResolve = (ImageButton) view.findViewById(R.id.btnNotResolved);

                                btnResolve.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        SharedPreferenceUtils.putString(getActivity(), Context.MODE_PRIVATE, AppParams.KEY_STATUS, sortedDailyList.get(position).get(AppParams.TAG_ID), AppParams.RESOLVED);
                                        adapter.updateView(view, position);
                                        overlay.setVisibility(View.GONE);
                                        mainActivity.onResume();
                                    }
                                });

                                btnNotResolve.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        SharedPreferenceUtils.putString(getActivity(), Context.MODE_PRIVATE, AppParams.KEY_STATUS, sortedDailyList.get(position).get(AppParams.TAG_ID), AppParams.CANT_RESOLVE);
                                        adapter.updateView(view, position);
                                        overlay.setVisibility(View.GONE);
                                        mainActivity.onResume();
                                    }
                                });

                            }
                            return true;

                        }
                    });

                }
            }
        }

        return view;
    }

}