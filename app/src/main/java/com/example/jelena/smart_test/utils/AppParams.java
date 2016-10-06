package com.example.jelena.smart_test.utils;


public class AppParams {

    /**
     * Url for json docs
     */
    public final static String URL_TASKS = "https://factordiary.firebaseapp.com";

    /**
     * The default socket timeout in milliseconds
     */
    public static final int DEFAULT_TIMEOUT_MS = 3000;
    /**
     * The default number of retries
     */
    public static final int DEFAULT_MAX_RETRIES = 4;
    /**
     * The default backoff multiplier
     */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

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
