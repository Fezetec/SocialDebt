package com.example.socialdebt;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteDialog extends AppCompatDialogFragment {
    private SharedPreferencesHelper sharedPreferencesHelper;
    private DeleteDialogListener listener;
    Button btnDelete, btnCancel;
    Bundle args;
    Activity activity;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        sharedPreferencesHelper = new SharedPreferencesHelper();
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteDialog.this.requireContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_delete_confirmation, null);

        btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
                listener.DeleteActivity(activity);
                dismiss();
            }
        });
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
                dismiss();
            }
        });
        builder.setTitle(getString(R.string.dialogDeleteTitle));
        builder.setMessage(getString(R.string.areYouSure));
        builder.setView(view);
        ReadValues();
        return builder.create();
    }

    private void ReadValues()
    {
        args = getArguments();
        activity = sharedPreferencesHelper.GsonThis(args.getString("activity"));
    }

    public interface DeleteDialogListener {
        void DeleteActivity(Activity act);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteDialog.DeleteDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DeleteDialogListener");
        }
    }
}
