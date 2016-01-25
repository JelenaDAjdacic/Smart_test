package com.example.jelena.smart_test;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text= (TextView) findViewById(R.id.text);

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "fonts/AmsiPro-Bold.otf");

        text.setTypeface(face);
    }
}
