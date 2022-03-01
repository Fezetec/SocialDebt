package com.example.socialdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ListOutActivities();
    }
    
    private void ListOutActivities() {
        View llLayout = findViewById(R.id.llLayout);
        for (Activity act : MainActivity.activities) {
            TextView txtView = new TextView(this);
            txtView.setText(act.getName());
            txtView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            //RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            //lParams.addRule(RelativeLayout.BELOW, txtView.getId());
            //txtView.setLayoutParams(lParams);
            ((LinearLayout) llLayout).addView(txtView);

            Slider slider = new Slider(this);
            slider.setValueFrom(-10);
            slider.setValueTo(10);
            slider.setValue(act.getPoints());
            slider.setStepSize(1);
            slider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout) llLayout).addView(slider);
        }
    }
}