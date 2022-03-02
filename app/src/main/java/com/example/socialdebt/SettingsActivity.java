package com.example.socialdebt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, Slider.OnSliderTouchListener {
    // View Elements
    Button btnSave;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        gson = new Gson();

        ListActivities();

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }
    
    private void ListActivities() {
        View llLayout = findViewById(R.id.llLayout);
        int counter = 0;
        for (Activity act : MainActivity.activities) {
            TextView txtView = new TextView(this);
            txtView.setText(act.getName());
            txtView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout) llLayout).addView(txtView);

            Slider slider = new Slider(this);
            slider.setId(counter);
            slider.setValueFrom(-10);
            slider.setValueTo(10);
            slider.setValue(act.getPoints());
            slider.setStepSize(1);
            slider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            slider.addOnSliderTouchListener(this);
            ((LinearLayout) llLayout).addView(slider);
            counter++;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                String jsonSetActivities = gson.toJson(MainActivity.activities);
                SharedPreferences.Editor editor = MainActivity.spActivities.edit();
                editor.putString("activities", jsonSetActivities);
                editor.commit();
                Toast.makeText(SettingsActivity.this, "Saved!", Toast.LENGTH_SHORT);

                // Save
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("activities", MainActivity.activities);
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
        // TODO Lagre slider.value til MainActivity.activities
        MainActivity.activities.get(slider.getId()).setPoints((int) slider.getValue());

    }
}