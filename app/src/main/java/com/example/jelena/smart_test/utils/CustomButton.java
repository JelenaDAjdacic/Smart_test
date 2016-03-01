package com.example.jelena.smart_test.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.jelena.smart_test.R;

/**
 * Created by Win 7 on 3.2.2016.
 */
public class CustomButton extends Button {

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context) {
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
