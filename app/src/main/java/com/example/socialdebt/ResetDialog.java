package com.example.socialdebt;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ResetDialog extends AppCompatDialogFragment {
    private ResetDialogListener listener;
    Button btnDelete, btnCancel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResetDialog.this.requireContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_reset_confirmation, null);

        btnDelete = view.findViewById(R.id.btnDelete);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.reset();
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        builder.setTitle(getString(R.string.dialogResetTitle));
        builder.setMessage(getString(R.string.areYouSure));
        builder.setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ResetDialog.ResetDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DeleteDialogListener");
        }
    }

    public interface ResetDialogListener {
        void reset();
    }
}
