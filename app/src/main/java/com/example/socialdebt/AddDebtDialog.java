package com.example.socialdebt;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddDebtDialog extends AppCompatDialogFragment {
    private AddDebtDialogListener listener;
    private LinearLayout llLayout;
    private Button btnClose;

    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDebtDialog.this.requireContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        llLayout = view.findViewById(R.id.llLayout);
        btnClose = view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

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
        builder.setView(view);
        return builder.create();
        // Transparent dialog under
//        AlertDialog dialog = builder.create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.show();
//        return dialog;
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
