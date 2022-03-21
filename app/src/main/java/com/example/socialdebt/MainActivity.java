package com.example.socialdebt;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddDebtDialog.AddDebtDialogListener, PayOffDebtDialog.PayOffDebtDialogListener, ResetDialog.ResetDialogListener {
    //region Variables
    static int totalPoints = 0;
    static ArrayList<Activity> activities;
    static SharedPreferences spPoints, spActivities;
    final static String spPointsKey = "spPoints";
    final static String spActivitiesKey = "spActivities";
    AddDebtDialog addDebtDialog;
    PayOffDebtDialog payOffDebtDialog;
    ResetDialog resetDialog;
    SharedPreferencesHelper sharedPreferencesHelper;
    //endregion

    //region View Elements
    Button btnAddDebt;
    Button btnPayOffDebt;
    Button btnSettings;
    Button btnReset;
    TextView txtPoints;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesHelper = new SharedPreferencesHelper();

        // Shared Preferences, totalPoints
        spPoints = getSharedPreferences(spPointsKey, Context.MODE_PRIVATE);
         totalPoints = sharedPreferencesHelper.GetTotalPoints(spPoints, this.getBaseContext());

        // Shared Preferences, activities
        spActivities = getSharedPreferences(spActivitiesKey, Context.MODE_PRIVATE);
         activities = sharedPreferencesHelper.GetActivities(spActivities, this.getBaseContext());

        if(activities == null)
        {
            activities = new ArrayList<>();
             sharedPreferencesHelper.SetActivities(spActivities, activities, this.getBaseContext());
        }

        btnAddDebt = findViewById(R.id.btnAddDebt);
        btnAddDebt.setOnClickListener(this);
        btnPayOffDebt = findViewById(R.id.btnPayOffDebt);
        btnPayOffDebt.setOnClickListener(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        txtPoints = findViewById(R.id.txtPoints);

        RenderPoints();
    }

    //region Render methods
    private void RenderPoints() {
        txtPoints.setText(totalPoints + " " + getString(R.string.points));
        if(totalPoints == 0) {
            txtPoints.setTextColor(Color.GRAY);
        }else if(totalPoints > 0){
            txtPoints.setTextColor(Color.GREEN);
        }else {
            txtPoints.setTextColor(Color.RED);
        }
    }
    //endregion

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
            case R.id.btnReset:
                openResetDialog();
                break;
            default:
                break;
        }
    }

    //region Dialog listener overrides
    @Override
    public void applyAddDebtScore(int score) {
        totalPoints += score;
        RenderPoints();
         sharedPreferencesHelper.SetTotalPoints(spPoints, totalPoints, this.getBaseContext());
        addDebtDialog.dismiss();
    }

    @Override
    public void applyPayOffDebtScore(int score) {
        totalPoints += score;
        RenderPoints();
         sharedPreferencesHelper.SetTotalPoints(spPoints, totalPoints, this.getBaseContext());
        payOffDebtDialog.dismiss();
    }

    @Override
    public void reset() {
        totalPoints = 0;
         sharedPreferencesHelper.SetTotalPoints(spPoints, totalPoints, this.getBaseContext());
        RenderPoints();
    }
    //endregion

    //region Dialog methods
    private void openAddDebtDialog() {
        addDebtDialog = new AddDebtDialog();
        addDebtDialog.show(getSupportFragmentManager(), "ADD DEBT");
    }

    private void openPayOffDebtDialog() {
        payOffDebtDialog = new PayOffDebtDialog();
        payOffDebtDialog.show(getSupportFragmentManager(), "PAY OFF DEBT");
    }

    private void openResetDialog() {
        resetDialog = new ResetDialog();
        resetDialog.show(getSupportFragmentManager(), "RESET DEBT");
    }
    //endregion
}