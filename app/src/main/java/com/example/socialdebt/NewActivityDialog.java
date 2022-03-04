package com.example.socialdebt;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.slider.Slider;


public class NewActivityDialog extends AppCompatDialogFragment {

    private NewActivityDialogListener listener;
    private EditText txtName;
    private Slider sldScore;

    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_activity, null);

        builder.setView(view)
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Activity act = new Activity(txtName.getText().toString(), (int) sldScore.getValue());
                    //int score = act.getPoints();
                    listener.addActivity(act);
                });
        txtName = view.findViewById(R.id.txtName);
        sldScore = view.findViewById(R.id.sldScore);
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewActivityDialog.NewActivityDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement NewActivityDialogListener");
        }
    }

    public interface NewActivityDialogListener {
        void addActivity(Activity act);
    }
}
