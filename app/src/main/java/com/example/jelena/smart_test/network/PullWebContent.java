package com.example.jelena.smart_test.network;


import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.jelena.smart_test.utils.AppParams;

import java.util.HashMap;
import java.util.Map;

public class PullWebContent<T> {

    private WebRequestCallbackInterface<T> webRequestCallbackInterface;
    //private Context context;
    private String url;
    private Class<T> t;
    private VolleySingleton mVolleySingleton;


    public PullWebContent(Class<T> mClass, String url, VolleySingleton mVolleySingleton) {

        //this.context = context;
        webRequestCallbackInterface = null;
        this.url = url;
        this.t = mClass;
        this.mVolleySingleton = mVolleySingleton;

    }

    public void setCallbackListener(WebRequestCallbackInterface<T> listener) {
        this.webRequestCallbackInterface = listener;

    }

    /**
     * function to pull list of all categories form web server
     */
    public void pullList() {
        // Tag used to cancel the request
        String tag_string_req = "req_" + url;


        final GsonRequest<T> gsonRequest = new GsonRequest<T>(url, t, null, new Response.Listener<T>() {

            @Override
            public void onResponse(T model) {

                if (model != null) {
                    webRequestCallbackInterface.webRequestSuccess(true, model);
                    Log.d("tag_string_req", "NOT NULL");
                    Log.d("tag_string_req", model.toString());

                } else {
                    Log.d("pullCategoriesResp", "NULL RESPONSE");
                    webRequestCallbackInterface.webRequestSuccess(false, model);
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                webRequestCallbackInterface.webRequestError(error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Post params
                Map<String, String> params = new HashMap<>();
                params.put("action", "pull " + t.getName());
                params.put("id", url);
                return params;
            }
        };

        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(AppParams.DEFAULT_TIMEOUT_MS, AppParams.DEFAULT_MAX_RETRIES, AppParams.DEFAULT_BACKOFF_MULT));
        // Adding request to  queue
        mVolleySingleton.addToRequestQueue(gsonRequest);
    }
}

