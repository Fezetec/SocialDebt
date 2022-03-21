package com.example.socialdebt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, Slider.OnSliderTouchListener, ActivityDialog.NewActivityDialogListener, DeleteDialog.DeleteDialogListener {
    //region View Elements
    Button btnSave;
    Button btnNew;
    View llLayout;
    Gson gson;
    DeleteDialog deleteDialog;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        gson = new Gson();

        ListActivities();

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);
    }
    
    private void ListActivities() {
        llLayout = findViewById(R.id.llLayout);
        ((LinearLayout) llLayout).removeAllViews();
        int counter = 0;
        for (Activity act : MainActivity.activities) {
            TextView txtView = new TextView(this);
            txtView.setText(act.getName());
            txtView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtView.setPadding(30, 0, 30, 0);
            ((LinearLayout) llLayout).addView(txtView);

            ImageView img = new ImageView(this);
            img.setId(counter);
            img.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
            img.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenDeleteConfirmation(view);
                }
            });
            ((LinearLayout) llLayout).addView(img);

            Slider slider = new Slider(this);
            slider.setId(counter);
            slider.setValueFrom(-10);
            slider.setValueTo(10);
            slider.setValue(act.getPoints());
            slider.setStepSize(1);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM;
            slider.setLayoutParams(params);
            slider.addOnSliderTouchListener(this);
            ((LinearLayout) llLayout).addView(slider);
            counter++;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                // Save
                String jsonSetActivities = gson.toJson(MainActivity.activities);
                SharedPreferences.Editor editor = MainActivity.spActivities.edit();
                editor.putString(getString(R.string.spActivities), jsonSetActivities);
                editor.commit();
                Toast.makeText(SettingsActivity.this, getString(R.string.toastSaved), Toast.LENGTH_SHORT).show();
                //Redirect to MainActivity
                finish();
                break;
            case R.id.btnNew:
                openActivityDialog();
                break;
            default:
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStartTrackingTouch(@NonNull Slider slider) {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStopTrackingTouch(@NonNull Slider slider) {
        MainActivity.activities.get(slider.getId()).setPoints((int) slider.getValue());
    }

    @Override
    public void addActivity(Activity act) {
        MainActivity.activities.add(act);
        String jsonSetActivities = gson.toJson(MainActivity.activities);
        SharedPreferences.Editor editor = MainActivity.spActivities.edit();
        editor.putString(getString(R.string.spActivities), jsonSetActivities);
        editor.commit();
        ListActivities();
    }

    @Override
    public void deleteActivity(Activity act) {
        MainActivity.activities.remove(act);
        String jsonSetActivities = gson.toJson(MainActivity.activities);
        SharedPreferences.Editor editor = MainActivity.spActivities.edit();
        editor.putString(getString(R.string.spActivities), jsonSetActivities);
        editor.commit();
        ListActivities();
    }

    private void openActivityDialog() {
        ActivityDialog activityDialog = new ActivityDialog();
        activityDialog.show(getSupportFragmentManager(), "ADD ACTIVITY");
    }

    private void OpenDeleteConfirmation(View view) {
        deleteDialog = new DeleteDialog();
        Bundle args = new Bundle();
        args.putInt("id", view.getId());
        deleteDialog.setArguments(args);
        deleteDialog.show(getSupportFragmentManager(), "DELETE CONFIRMATION");
    }
}