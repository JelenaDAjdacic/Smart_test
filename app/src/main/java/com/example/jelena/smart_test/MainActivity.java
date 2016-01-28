package com.example.jelena.smart_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.HttpClient;
import com.example.jelena.smart_test.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends FragmentActivity {
    private final static String url="https://demo8035300.mockable.io/tasks";
    ListView tasksListView;
    JSONObject[] jsonObjects;



    ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_TASKS = "tasks ";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TARGET_DATE = "TargetDate";
    private static final String TAG_DUE_DATE = "DueDate";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_PRIORITY = "Priority";
    private static final String KEY_TASK="KeyTask";

    // tasks JSONArray
    JSONArray tasks = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> tasksList;

    HttpClient client;

    ArrayListManipulator arrayListManipulator;


    Context mContext;
    ViewPager vpPager;

    private CachingFragmentStatePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);


        if (savedInstanceState==null) {


                if (isConnectingToInternet(getApplicationContext())) {


                        new GetContacts().execute();

                    } else {

                    Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_LONG).show();
                }
            vpPager.setCurrentItem(TimeUtils.getPositionForDay(Calendar.getInstance()));

        }



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
            Log.d("Smart",TimeUtils.getFormattedDate(mContext, TimeUtils.getDayForPosition(position).getTimeInMillis()));
            return FragmentContent.newInstance(timeForPosition,tasksList);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar cal = TimeUtils.getDayForPosition(position);
            Log.d("Smart"," getPageTitle(int position) "+position);
            return TimeUtils.getFormattedDate(mContext, cal.getTimeInMillis());
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
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.d("Smart", "onPreExecution");

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            client=new HttpClient(url);
            Log.d("Smart","doInBackground..");

            tasksList = new ArrayList<>();
            // Making a request to url and getting response
            String jsonStr = client.getContent();

        //    Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    tasks = jsonObj.getJSONArray(TAG_TASKS);

                    // looping through All Contacts
                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject tasksJSONObject = tasks.getJSONObject(i);

                        String id = tasksJSONObject.getString(TAG_ID);
                        String title = tasksJSONObject.getString(TAG_TITLE);
                        String targetDate = tasksJSONObject.getString(TAG_TARGET_DATE);
                        String dueDate = tasksJSONObject.getString(TAG_DUE_DATE);
                        String description = tasksJSONObject.getString(TAG_DESCRIPTION);
                        String priority = tasksJSONObject.getString(TAG_PRIORITY);


                        // tmp hashmap for single contact
                        HashMap<String, String> task = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        task.put(TAG_ID, id);
                        task.put(TAG_TITLE, title);
                        task.put(TAG_TARGET_DATE, targetDate);
                        task.put(TAG_DUE_DATE, dueDate);
                        task.put(TAG_DESCRIPTION, description);
                        task.put(TAG_PRIORITY, priority);

                        // adding contact to contact list
                        tasksList.add(task);


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

            vpPager.setAdapter(adapterViewPager);
            vpPager.setCurrentItem(TimeUtils.getPositionForDay(Calendar.getInstance()));


        }

    }


}
