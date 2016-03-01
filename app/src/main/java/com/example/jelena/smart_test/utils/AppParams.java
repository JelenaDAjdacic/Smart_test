package com.example.jelena.smart_test.utils;


public class AppParams {

    //JSON Url
    public final static String URL_TASKS = "http://demo5172197.mockable.io/tasks";

    // JSON Node names
    public static final String TAG_TASKS = "tasks ";
    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_TARGET_DATE = "TargetDate";
    public static final String TAG_DUE_DATE = "DueDate";
    public static final String TAG_DESCRIPTION = "Description";
    public static final String TAG_PRIORITY = "Priority";

    //Shared Preferences Keys
    public static final String KEY_STATUS = "AppStatusSharedPref";
    public static final String KEY_COMMENTS = "AppCommentsSharedPref";

    //Tasks states
    public static final String RESOLVED = "Resolved";
    public static final String UNRESOLVED = "Unresolved";
    public static final String CANT_RESOLVE = "Can't resolve";

    //FragmentContent Bundle Keys
    public static final String BUNDLE_KEY_DATE = "DATE";
    public static final String BUNDLE_KEY_TASKS = "TASKS";
}
