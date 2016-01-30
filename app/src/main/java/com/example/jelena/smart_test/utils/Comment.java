package com.example.jelena.smart_test.utils;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jelena.smart_test.R;

/**
 * Created by Win 7 on 30.1.2016.
 */
public class Comment  extends DialogFragment {
    Button cancel;
    Button submit;
    EditText comment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.comment,null);

        cancel= (Button) view.findViewById(R.id.cancel);
        submit= (Button) view.findViewById(R.id.submit);
        comment= (EditText) view.findViewById(R.id.comment);
        comment.setFocusableInTouchMode(true);
        comment.setFocusable(true);
        return view;
    }

}
