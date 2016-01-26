package com.example.jelena.smart_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private final static String url="https://demo8035300.mockable.io/tasks";
    ListView tasksListView;
    JSONObject[] jsonObjects;
   // Calendar c;
    ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_TASKS = "tasks ";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TARGET_DATE = "TargetDate";
    private static final String TAG_DUE_DATE = "DueDate";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_PRIORITY = "Priority";

    // tasks JSONArray
    JSONArray tasks = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> tasksList;

    HttpClient client;

    ArrayListManipulator arrayListManipulator;

   // String formattedDate=null;

    MyAdapter adapter=null;

    CalendarOperations calendarOperation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* c= Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = dateFormat.format(c.getTime());*/

        if(isConnectingToInternet(getApplicationContext()))   {

            // Calling async task to get json
            new GetContacts().execute();}

        else{

            Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_LONG).show();

        }

        tasksList = new ArrayList<HashMap<String, String>>();
        tasksListView= (ListView) findViewById(R.id.todayTasks);

        calendarOperation=new CalendarOperations();


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

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            client=new HttpClient(url);

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
                        task.put(TAG_PRIORITY,priority);

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
            arrayListManipulator=new ArrayListManipulator(tasksList);
            adapter = new MyAdapter(getApplicationContext(),arrayListManipulator.findArrayByDate(calendarOperation.currentDate("yyyy-MM-dd")));
            tasksListView.setAdapter(adapter);

        }

    }


}
