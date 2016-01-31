package com.example.jelena.smart_test;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Win 7 on 29.1.2016.
 */
public class CommentDialog extends DialogFragment {
    Button no;
    Button yes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.comment_dialog, null);

        no= (Button) view.findViewById(R.id.no);
        yes= (Button) view.findViewById(R.id.yes);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetails taskDetails= (TaskDetails) getActivity();
                taskDetails.showCommentEntry();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prev = getFragmentManager().findFragmentByTag("CommentDialog");
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }
            }
        });


        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        TaskDetails taskDetails= (TaskDetails) getActivity();
        taskDetails.onNoClicked();
    }


}
