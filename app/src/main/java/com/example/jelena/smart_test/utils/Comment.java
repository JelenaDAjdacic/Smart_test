package com.example.jelena.smart_test.utils;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jelena.smart_test.R;
import com.example.jelena.smart_test.TaskDetails;

/**
 * Created by Win 7 on 30.1.2016.
 */
public class Comment  extends DialogFragment {
    Button cancel;
    Button submit;
    EditText comment;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TaskDetails taskDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.comment,null);

        sharedPreferences = getActivity().getSharedPreferences(AppParams.KEY_COMMENTS, Context.MODE_PRIVATE);

        cancel= (Button) view.findViewById(R.id.cancel);
        submit= (Button) view.findViewById(R.id.submit);
        comment= (EditText) view.findViewById(R.id.comment);
        comment.setFocusableInTouchMode(true);
        comment.setFocusable(true);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor=sharedPreferences.edit();

                taskDetails= (TaskDetails) getActivity();
                editor.putString(taskDetails.id,comment.getText().toString());
                editor.commit();
                taskDetails.onNoClicked();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment prev = getFragmentManager().findFragmentByTag("CommentEntry");
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }


            }
        });

        return view;
    }

}
