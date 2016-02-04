package com.example.jelena.smart_test;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Win 7 on 3.2.2016.
 */
public class AlertDialog extends DialogFragment {

    Button exit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.alert_dialog, null);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_style);
        exit= (com.example.jelena.smart_test.utils.CustomButton) view.findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void onDetach() {
        super.onDetach();
        MainActivity activity= (MainActivity) getActivity();
        activity.finish();
    }
}
