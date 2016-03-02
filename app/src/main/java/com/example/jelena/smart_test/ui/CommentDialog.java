package com.example.jelena.smart_test;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jelena.smart_test.utils.AppParams;
import com.example.jelena.smart_test.utils.SharedPreferenceUtils;


public class CommentDialog extends DialogFragment {
    Button no;
    Button yes;
    Button cancel;
    EditText comment;
    Button submit;
    LinearLayout questionContainer;
    LinearLayout commentContainer;
    TaskDetails taskDetails;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.comment_dialog, null);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_style);

        taskDetails = (TaskDetails) getActivity();

        questionContainer = (LinearLayout) view.findViewById(R.id.questionContainer);
        commentContainer = (LinearLayout) view.findViewById(R.id.commentContainer);

        no = (Button) view.findViewById(R.id.no);
        yes = (Button) view.findViewById(R.id.yes);

        cancel = (Button) view.findViewById(R.id.cancel);
        submit = (Button) view.findViewById(R.id.submit);

        comment = (EditText) view.findViewById(R.id.comment);
        comment.setFocusableInTouchMode(true);
        comment.setFocusable(true);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                questionContainer.setVisibility(View.GONE);
                commentContainer.setVisibility(View.VISIBLE);

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferenceUtils.putString(getActivity(), Context.MODE_PRIVATE, AppParams.KEY_COMMENTS, taskDetails.id, comment.getText().toString());
                taskDetails.onNoClicked();
                dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        TaskDetails taskDetails = (TaskDetails) getActivity();
        taskDetails.onNoClicked();
    }


}