package com.example.jelena.smart_test.utils;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.jelena.smart_test.R;
import com.example.jelena.smart_test.ui.CustomTextView;

public class StringUtils {

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void setActionBarFont(Context context, ActionBar actionBar, String title) {
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(context);
        View v = inflator.inflate(R.layout.custom_action_bar, null);
        TextView text = (CustomTextView) v.findViewById(R.id.titleFragment1);
        text.setText(title);
        actionBar.setCustomView(v);

    }
}
