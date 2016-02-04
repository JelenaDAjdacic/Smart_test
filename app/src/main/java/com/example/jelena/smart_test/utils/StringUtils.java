package com.example.jelena.smart_test.utils;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.jelena.smart_test.R;

/**
 * Created by Win 7 on 29.1.2016.
 */
public class StringUtils {

    public static String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    public static void setActionBarFont(Context context,ActionBar actionBar,String title){
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(context);
        View v = inflator.inflate(R.layout.custom_action_bar, null);
        TextView text=(com.example.jelena.smart_test.utils.CustomTextView) v.findViewById(R.id.titleFragment1);
        text.setText(title);
        actionBar.setCustomView(v);

    }
}
