package com.example.jelena.smart_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jelena.smart_test.model.Response;
import com.example.jelena.smart_test.model.Tasks;
import com.example.jelena.smart_test.network.PullWebContent;
import com.example.jelena.smart_test.network.VolleySingleton;
import com.example.jelena.smart_test.network.WebRequestCallbackInterface;
import com.example.jelena.smart_test.ui.AlertDialog;
import com.example.jelena.smart_test.utils.AppParams;
import com.example.jelena.smart_test.utils.CachingFragmentStatePagerAdapter;
import com.example.jelena.smart_test.utils.CalendarOperations;
import com.example.jelena.smart_test.utils.SharedPreferenceUtils;
import com.example.jelena.smart_test.utils.StringUtils;
import com.example.jelena.smart_test.utils.TimeUtils;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    //last position before screen rotation
    public int lastPagerPosition;

    private List<Tasks> mTaskList;

    private Context mContext;
    ViewPager vpPager;
    PagerTabStrip pTab;

    private LinearLayout logo;
    private LinearLayout introImage;

    // custom infinite pager
    private CachingFragmentStatePagerAdapter adapterViewPager;
    private VolleySingleton mVolleySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        StringUtils.setActionBarFont(this, getSupportActionBar(), getString(R.string.task_title));
        mVolleySingleton = VolleySingleton.getsInstance(this);

        componentInitialization();

        showIntro();


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
            if (savedInstanceState == null) {

                lastPagerPosition = TimeUtils.getPositionForDay(Calendar.getInstance());

            } else {

                lastPagerPosition = savedInstanceState.getInt(getResources().getString(R.string.last_calendar_position));

            }
            getTasks();
        } else {
            showAlert();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        showTasks();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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

            return FragmentContent.newInstance(timeForPosition, getTasksList());
        }

        @Override
        public CharSequence getPageTitle(int position) {

            cal = TimeUtils.getDayForPosition(position);
            return StringUtils.capitalize(CalendarOperations.convertDateFormat(TimeUtils.getFormattedDate(mContext, cal.getTimeInMillis()), getString(R.string.date_format), getString(R.string.short_date_format)));

        }


    }

    private void showIntro() {

        vpPager.setVisibility(View.GONE);
        logo.setVisibility(View.VISIBLE);
        introImage.setVisibility(View.VISIBLE);

    }

    private void showAlert() {

        AlertDialog dialog = new AlertDialog();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), getString(R.string.alert_tag));

    }

    private void hideIntro() {

        if (logo.getVisibility() == View.VISIBLE && introImage.getVisibility() == View.VISIBLE) {

            logo.setVisibility(View.GONE);
            introImage.setVisibility(View.GONE);
        }
        vpPager.setVisibility(View.VISIBLE);

    }

    private void componentInitialization() {

        logo = (LinearLayout) findViewById(R.id.logoImage);
        introImage = (LinearLayout) findViewById(R.id.illustrationImage);
        vpPager = (ViewPager) findViewById(R.id.vpPager);

        pTab = (PagerTabStrip) findViewById(R.id.pager_header);
        pTab.setDrawFullUnderline(false);
        pTab.setTabIndicatorColor(ContextCompat.getColor(mContext, R.color.backgroundColor));


    }


    //Checking Internet connection
    private boolean isConnectingToInternet(Context applicationContext) {

        ConnectivityManager cm = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;

    }


    private void showTasks() {

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(TimeUtils.getPositionForDay(TimeUtils.getDayForPosition(lastPagerPosition)));

    }

    public List<Tasks> getTasksList() {

        return mTaskList;

    }

    public void setTasks(List<Tasks> list) {
        mTaskList = list;

    }

    private void initStatus(List<Tasks> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            if (SharedPreferenceUtils.getString(mContext, MODE_PRIVATE, AppParams.KEY_STATUS, tasks.get(i).getId()).isEmpty()) {

                SharedPreferenceUtils.putString(mContext, MODE_PRIVATE, AppParams.KEY_STATUS, tasks.get(i).getId(), AppParams.UNRESOLVED);

            }
        }


    }

    private void getTasks() {

        // Showing progress dialog
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage(getString(R.string.progress_dialog_message));
        pDialog.setCancelable(false);
        pDialog.show();

        PullWebContent<Response> content =
                new PullWebContent<>(Response.class, AppParams.URL_TASKS, mVolleySingleton);

        content.setCallbackListener(new WebRequestCallbackInterface<Response>() {
            @Override
            public void webRequestSuccess(boolean success, Response response) {
                if (success) {
                    // Dismiss the progress dialog
                    if (pDialog.isShowing())
                        pDialog.dismiss();

                    Log.d("TASKS", "" + response.getTasks().size());
                    setTasks(response.getTasks());
                    initStatus(getTasksList());
                    hideIntro();
                    showTasks();

                }

            }

            @Override
            public void webRequestError(String error) {
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        });

        content.pullList();
    }


}
