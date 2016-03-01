package com.example.jelena.smart_test.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.jelena.smart_test.R;


public class CustomTextView extends TextView {


    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context) {
        super(context);
    }


    public void setTypeface(Typeface tf, int style) {
        if (style == Typeface.BOLD) {

            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), getResources().getString(R.string.font_bold))/*, -1*/);

        } else {

            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), getResources().getString(R.string.font_regular))/*, -1*/);

        }
    }
}

