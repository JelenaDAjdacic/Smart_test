package com.example.jelena.smart_test.utils;

import android.app.Application;
import android.graphics.Typeface;
import android.widget.TextView;
import com.example.jelena.smart_test.R;

/**
 * Created by Win 7 on 3.2.2016.
 */
public class CustomApp extends Application {
    private Typeface normalFont;
    private Typeface boldFont;


    // -- Fonts -- //
    public void setTypeface(TextView textView) {
        if(textView != null) {
            if(textView.getTypeface() != null && textView.getTypeface().isBold()) {
                textView.setTypeface(getBoldFont());
            } else {
                textView.setTypeface(getNormalFont());
            }
        }
    }

    private Typeface getNormalFont() {
        if(normalFont == null) {
            normalFont = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_regular));
        }
        return this.normalFont;
    }

    private Typeface getBoldFont() {
        if(boldFont == null) {
            boldFont = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_bold));
        }
        return this.boldFont;
    }
}
