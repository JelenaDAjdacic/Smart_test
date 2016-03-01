package com.example.jelena.smart_test.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferenceUtils {


    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static void openSharedPreferences(Context context, String key, int mode) {

        sharedPreferences = context.getSharedPreferences(key, mode);

    }

    public static void putString(Context context, int mode, String spKey, String key, String value) {

        openSharedPreferences(context, spKey, mode);

        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, int mode, String spKey, String key) {

        openSharedPreferences(context, spKey, mode);
        return sharedPreferences.getString(key, "");
    }

}
