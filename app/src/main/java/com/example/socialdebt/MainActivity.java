package com.example.socialdebt;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ResetDialog.ResetDialogListener, ActivityDialog.ActivityDialogListener, DeleteDialog.DeleteDialogListener {
    //region Variables
    static int totalPoints = 0;
    static ArrayList<Activity> activities;
    ResetDialog resetDialog;
    ActivityDialog activityDialog;
    DeleteDialog deleteDialog;
    SharedPreferencesHelper sharedPreferencesHelper;
    //endregion

    //region View Elements
    Button btnNewActivity;
    Button btnReset;
    TextView txtPoints;
    View llLayout;
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

        ConnectViews();
        RenderPoints();
        ListActivities();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNewActivity:
                OpenActivityDialog(-1);
                break;
            case R.id.btnReset:
                OpenResetDialog();
                break;
            default:
                break;
        }
    }

    //region Render methods
    private void ConnectViews() {
        btnNewActivity = findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(this);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        txtPoints = findViewById(R.id.txtPoints);
        llLayout = findViewById(R.id.llLayout);
    }

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
        ((LinearLayout) llLayout).removeAllViews();

        for (Activity act : activities) {
            LinearLayout activityLayout = new LinearLayout(getBaseContext());
            activityLayout.setOrientation(LinearLayout.HORIZONTAL);
            activityLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplyScore(act.getPoints());
                }
            });

            TextView txtView = new TextView(this);
            txtView.setText(act.getName());
            txtView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtView.setPadding(30, 30, 30, 30);
            ((LinearLayout) activityLayout).addView(txtView);

            ImageButton imgEditButton = new ImageButton(getBaseContext());
            imgEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenActivityDialog(act.getId());
                }
            });
            ((LinearLayout) activityLayout).addView(imgEditButton);

            ImageButton imgDeleteButton = new ImageButton(getBaseContext());
            imgDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenDeleteConfirmation(act.getId());
                }
            });
            ((LinearLayout) activityLayout).addView(imgDeleteButton);

            ((LinearLayout) llLayout).addView(activityLayout);
        }
    }
    public void ApplyScore(int score) {
        totalPoints += score;
        RenderPoints();
        sharedPreferencesHelper.SetTotalPoints(totalPoints, this.getBaseContext());
    }
    //endregion

    //region Dialog listener overrides
    @Override
    public void Reset() {
        totalPoints = 0;
        sharedPreferencesHelper.SetTotalPoints(totalPoints, this.getBaseContext());
        RenderPoints();
    }

    @Override
    public void SaveActivity(Activity act) {
        Activity existObject = sharedPreferencesHelper.GetActivity(act.getId(), this.getBaseContext());
        if(existObject == null){
            activities.add(act);
        }else {
            for (Activity a : activities) {
                if(a.getId() == act.getId()){
                    a.setName(act.getName());
                    a.setPoints(act.getPoints());
                }
            }
        }
        sharedPreferencesHelper.SetActivities(activities, this.getBaseContext());
        ListActivities();
    }

    @Override
    public void DeleteActivity(Activity act) {
        Activity existObject = sharedPreferencesHelper.GetActivity(act.getId(), this.getBaseContext());
        if(existObject != null)
        {
            int index = 0;
            for (Activity a : activities) {
                if(a.getId() == act.getId()){
                    break;
                }
                index++;
            }
            activities.remove(index);
            sharedPreferencesHelper.SetActivities(activities, getBaseContext());
        }
        ListActivities();
    }
    //endregion

    //region Dialog methods
    private void OpenActivityDialog(int id) {
        activityDialog = new ActivityDialog();
        Bundle args = new Bundle();
        if(id != -1){
            args.putString("activity", sharedPreferencesHelper.GsonThis(sharedPreferencesHelper.GetActivity(id, this.getBaseContext())));
        }
        activityDialog.setArguments(args);
        activityDialog.show(getSupportFragmentManager(), "EDIT ACTIVITY");
    }

    private void OpenResetDialog() {
        resetDialog = new ResetDialog();
        resetDialog.show(getSupportFragmentManager(), "RESET DEBT");
    }

    private void OpenDeleteConfirmation(int id) {
        deleteDialog = new DeleteDialog();
        Bundle args = new Bundle();
        if(id != -1){
            args.putString("activity", sharedPreferencesHelper.GsonThis(sharedPreferencesHelper.GetActivity(id, this.getBaseContext())));
        }
        deleteDialog.setArguments(args);
        deleteDialog.show(getSupportFragmentManager(), "DELETE CONFIRMATION");
    }
    //endregion
}