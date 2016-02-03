package com.example.jelena.smart_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import com.example.jelena.smart_test.utils.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    JSONObject[] jsonObjects;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    ProgressDialog pDialog;

    //
    private static final String KEY_TASK="KeyTask";
    int lastPagerPosition;

    // tasks JSONArray
    JSONArray tasks = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> tasksList;

    HttpClient client;

    Context mContext;
    ViewPager vpPager;
    PagerTabStrip pTab;


    private CachingFragmentStatePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext = this;
        sharedPref = mContext.getSharedPreferences(AppParams.KEY_STATUS, Context.MODE_PRIVATE);
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        vpPager.setVisibility(View.GONE);


        pTab= (PagerTabStrip) findViewById(R.id.pager_header);
        pTab.setDrawFullUnderline(false);
        pTab.setTabIndicatorColor(ContextCompat.getColor(mContext,R.color.backgroundColor));



        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                lastPagerPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (isConnectingToInternet(getApplicationContext())) {
                if (savedInstanceState==null) {

                        lastPagerPosition=TimeUtils.getPositionForDay(Calendar.getInstance());


                    } else {
                    lastPagerPosition=savedInstanceState.getInt(getResources().getString(R.string.last_calendar_position));

                }
            new GetContacts().execute();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(TimeUtils.getPositionForDay(TimeUtils.getDayForPosition(lastPagerPosition)));

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.last_calendar_position), lastPagerPosition);
    }

    public class MyPagerAdapter extends CachingFragmentStatePagerAdapter {

        private Calendar cal;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return TimeUtils.DAYS_OF_TIME;
        }

        @Override
        public Fragment getItem(int position) {

            long timeForPosition = TimeUtils.getDayForPosition(position).getTimeInMillis();
            return FragmentContent.newInstance(timeForPosition,tasksList);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            cal = TimeUtils.getDayForPosition(position);
            return StringUtils.capitalize(CalendarOperations.convertDateFormat(TimeUtils.getFormattedDate(mContext, cal.getTimeInMillis()), getString(R.string.date_format), getString(R.string.short_date_format)));

        }


    }

    //Checking Internet connection
    private boolean isConnectingToInternet(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(getString(R.string.progress_dialog_message));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            client=new HttpClient(AppParams.URL_TASKS);

            tasksList = new ArrayList<>();
            // Making a request to url and getting response
            String jsonStr = client.getContent();


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    tasks = jsonObj.getJSONArray(AppParams.TAG_TASKS);

                    // looping through All Contacts
                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject tasksJSONObject = tasks.getJSONObject(i);

                        String id = tasksJSONObject.getString(AppParams.TAG_ID);
                        String title = tasksJSONObject.getString(AppParams.TAG_TITLE);
                        String targetDate = tasksJSONObject.getString(AppParams.TAG_TARGET_DATE);
                        String dueDate = tasksJSONObject.getString(AppParams.TAG_DUE_DATE);
                        String description = tasksJSONObject.getString(AppParams.TAG_DESCRIPTION);
                        String priority = tasksJSONObject.getString(AppParams.TAG_PRIORITY);


                        // tmp hashmap for single contact
                        HashMap<String, String> task = new HashMap<>();

                        // adding each child node to HashMap key => value
                        task.put(AppParams.TAG_ID, id);
                        task.put(AppParams.TAG_TITLE, title);
                        task.put(AppParams.TAG_TARGET_DATE, targetDate);
                        task.put(AppParams.TAG_DUE_DATE, dueDate);
                        task.put(AppParams.TAG_DESCRIPTION, description);
                        task.put(AppParams.TAG_PRIORITY, priority);

                        // adding contact to contact list
                        tasksList.add(task);
                        if (sharedPref.getString(id,"").isEmpty()){

                            editor= sharedPref.edit();
                            editor.putString(id, AppParams.UNRESOLVED);
                            editor.commit();

                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             */
            vpPager.setVisibility(View.VISIBLE);
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
            vpPager.setAdapter(adapterViewPager);
            vpPager.setCurrentItem(TimeUtils.getPositionForDay(TimeUtils.getDayForPosition(lastPagerPosition)));


        }

    }
}
