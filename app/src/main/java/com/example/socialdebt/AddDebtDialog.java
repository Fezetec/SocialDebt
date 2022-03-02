package com.example.socialdebt;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class AddDebtDialog extends AppCompatDialogFragment {
    private Spinner spinner;
    private AddDebtDialogListener listener;

    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        spinner = view.findViewById(R.id.spnActivities);

        ArrayList<Activity> addDebtActivities = new ArrayList<>();
        for (Activity act : MainActivity.activities) {
            if (act.getPoints() < 0) {
                addDebtActivities.add(act);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, addDebtActivities);
        spinner.setAdapter(adapter);

        builder.setView(view)
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Activity act = (Activity) spinner.getSelectedItem();
                    int score = act.getPoints();
                    listener.applyAddDebtScore(score);
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
