package com.example.socialdebt;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
    private Button btnSave, btnCancel;

    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_activity, null);

        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity act = new Activity(txtName.getText().toString(), (int) sldScore.getValue());
                //int score = act.getPoints();
                listener.addActivity(act);
                dismiss();
            }
        });
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        builder.setView(view);
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
