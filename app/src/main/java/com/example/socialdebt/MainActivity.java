package com.example.socialdebt;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddDebtDialog.AddDebtDialogListener, PayOffDebtDialog.PayOffDebtDialogListener, ResetDialog.ResetDialogListener, ActivityDialog.ActivityDialogListener {
    //region Variables
    static int totalPoints = 0;
    static ArrayList<Activity> activities;
    AddDebtDialog addDebtDialog;
    PayOffDebtDialog payOffDebtDialog;
    ResetDialog resetDialog;
    ActivityDialog activityDialog;
    SharedPreferencesHelper sharedPreferencesHelper;
    //endregion

    //region View Elements
    Button btnAddDebt;
    Button btnPayOffDebt;
    Button btnSettings;
    Button btnNewActivity;
    Button btnReset;
    TextView txtPoints;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesHelper = new SharedPreferencesHelper();

        // Shared Preferences, totalPoints
        totalPoints = sharedPreferencesHelper.GetTotalPoints(this.getBaseContext());

        // Shared Preferences, activities
        activities = sharedPreferencesHelper.GetActivities(this.getBaseContext());

        if(activities == null)
        {
            activities = new ArrayList<>();
            sharedPreferencesHelper.SetActivities(activities, this.getBaseContext());
        }

        ConnectButtons();
        RenderPoints();
        ListActivities();
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

    private void ListActivities() {

    }

    private void ConnectButtons() {
        btnAddDebt = findViewById(R.id.btnAddDebt);
        btnAddDebt.setOnClickListener(this);
        btnPayOffDebt = findViewById(R.id.btnPayOffDebt);
        btnPayOffDebt.setOnClickListener(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);
        btnNewActivity = findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(this);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        txtPoints = findViewById(R.id.txtPoints);
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
            case R.id.btnNewActivity:
                openActivityDialog(-1);
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
        sharedPreferencesHelper.SetTotalPoints(totalPoints, this.getBaseContext());
        addDebtDialog.dismiss();
    }

    @Override
    public void applyPayOffDebtScore(int score) {
        totalPoints += score;
        RenderPoints();
        sharedPreferencesHelper.SetTotalPoints(totalPoints, this.getBaseContext());
        payOffDebtDialog.dismiss();
    }

    @Override
    public void reset() {
        totalPoints = 0;
        sharedPreferencesHelper.SetTotalPoints(totalPoints, this.getBaseContext());
        RenderPoints();
    }

    @Override
    public void saveActivity(Activity act) {
        Activity existObject = sharedPreferencesHelper.GetActivity(act.id, this.getBaseContext());
        if(existObject == null){
            activities.add(act);
        }else {
            for (Activity a : activities) {
                if(a.id == act.id){
                    a.setName(act.getName());
                    a.setPoints(act.getPoints());
                }
            }
        }
        sharedPreferencesHelper.SetActivities(activities, this.getBaseContext());
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

    private void openActivityDialog(int id) {
        activityDialog = new ActivityDialog();
        Bundle args = new Bundle();
        if(id != -1){
            args.putString("activity", sharedPreferencesHelper.GsonThis(sharedPreferencesHelper.GetActivity(id, this.getBaseContext())));
        }
        activityDialog.setArguments(args);
        activityDialog.show(getSupportFragmentManager(), "EDIT ACTIVITY");
    }
    //endregion
}