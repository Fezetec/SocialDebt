package com.example.socialdebt;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddDebtDialog.AddDebtDialogListener, PayOffDebtDialog.PayOffDebtDialogListener {
    // Variables
    static int totalPoints = 0;
    static ArrayList<Activity> activities;
    static SharedPreferences spPoints, spActivities;
    final static String spPointsKey = "spPoints";
    final static String spActivitiesKey = "spActivities";
    Gson gson;

    // View Elements
    Button btnAddDebt;
    Button btnPayOffDebt;
    Button btnSettings;
    TextView txtPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();

        // Shared Preferences, totalPoints
        spPoints = getSharedPreferences(spPointsKey, Context.MODE_PRIVATE);
        totalPoints = spPoints.getInt("totalPoints", 0);

        // Shared Preferences, activities
        spActivities = getSharedPreferences(spActivitiesKey, Context.MODE_PRIVATE);
        String jsonGetActivities = spActivities.getString("activities", "");
        activities = gson.fromJson(jsonGetActivities, new TypeToken<List<Activity>>() {}.getType());

        if(activities == null)
        {
            activities = new ArrayList<>();
            activities.add(new Activity("Do dishes", 2));
            activities.add(new Activity("Massage wife", 5));
            activities.add(new Activity("Game", -2));
            String jsonSetActivities = gson.toJson(activities);
            SharedPreferences.Editor editor = spActivities.edit();
            editor.putString("activities", jsonSetActivities);
            editor.commit();
        }

        btnAddDebt = findViewById(R.id.btnAddDebt);
        btnAddDebt.setOnClickListener(this);
        btnPayOffDebt = findViewById(R.id.btnPayOffDebt);
        btnPayOffDebt.setOnClickListener(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);
        txtPoints = findViewById(R.id.txtPoints);

        RenderPoints();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddDebt:
                openAddDebtDialog();
                break;
            case R.id.btnPayOffDebt:
                openPayOffDebtDialog();
                break;
            case R.id.btnSettings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void applyAddDebtScore(int score) {
        totalPoints += score;
        RenderPoints();
        SharedPreferences.Editor editor = spPoints.edit();
        editor.putInt("totalPoints", totalPoints);
        editor.commit();
    }

    @Override
    public void applyPayOffDebtScore(int score) {
        totalPoints += score;
        RenderPoints();
        SharedPreferences.Editor editor = spPoints.edit();
        editor.putInt("totalPoints", totalPoints);
        editor.commit();
    }

    private void openAddDebtDialog() {
        AddDebtDialog addDebtDialog = new AddDebtDialog();
        addDebtDialog.show(getSupportFragmentManager(), "ADD DEBT");
    }

    private void openPayOffDebtDialog() {
        PayOffDebtDialog payOffDebtDialog = new PayOffDebtDialog();
        payOffDebtDialog.show(getSupportFragmentManager(), "PAY OFF DEBT");
    }

    private void RenderPoints() {
        txtPoints.setText(totalPoints + " Points");
        if(totalPoints > 0){
            txtPoints.setTextColor(Color.GREEN);
        }else {
            txtPoints.setTextColor(Color.RED);
        }
    }
}