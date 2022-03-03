package com.example.socialdebt;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class AddDebtDialog extends AppCompatDialogFragment {
    private AddDebtDialogListener listener;
    private LinearLayout llLayout;

    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        llLayout = view.findViewById(R.id.llLayout);

        for (Activity act : MainActivity.activities) {
            if (act.getPoints() < 0) {
                TextView txtView = new TextView(view.getContext());
                txtView.setText(act.getName());
                txtView.setId(act.getPoints());
                txtView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                txtView.setPadding(30, 30, 30, 30);
                txtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int score = view.getId();
                        listener.applyAddDebtScore(score);
                    }
                });
                ((LinearLayout) llLayout).addView(txtView);
            }
        }

        builder.setView(view)
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddDebtDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddDebtDialogListener");
        }
    }

    public interface AddDebtDialogListener {
        void applyAddDebtScore(int score);
    }
}
