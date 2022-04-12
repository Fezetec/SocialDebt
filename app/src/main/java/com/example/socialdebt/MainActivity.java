package com.example.socialdebt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
    ImageButton imgNew;
    ImageButton imgReset;
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
        RenderBalance();
        ListActivities();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgNew:
                OpenActivityDialog(-1);
                break;
            case R.id.imgReset:
                OpenResetDialog();
                break;
            default:
                break;
        }
    }

    //region Render methods
    private void ConnectViews() {
        imgNew = findViewById(R.id.imgNew);
        imgNew.setOnClickListener(this);
        imgReset = findViewById(R.id.imgReset);
        imgReset.setOnClickListener(this);
        txtPoints = findViewById(R.id.txtPoints);
        llLayout = findViewById(R.id.llLayout);
    }

    private void RenderBalance() {
        txtPoints.setText(totalPoints + " " + getString(R.string.points));
        if(totalPoints == 0) {
            txtPoints.setTextColor(getColor(R.color.appFontColor));
        }else if(totalPoints > 0){
            txtPoints.setTextColor(getColor(R.color.plusPointsColor));
        }else {
            txtPoints.setTextColor(getColor(R.color.minusPointsColor));
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
            activityLayout.setBackgroundResource(R.drawable.layout_border);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 20, 20, 20);
            activityLayout.setLayoutParams(params);

            TextView actText = new TextView(this);
            actText.setText(act.getName());
            LinearLayout.LayoutParams actTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            actTextParams.gravity = Gravity.CENTER_VERTICAL;
            actTextParams.weight = 1;
            actText.setLayoutParams(actTextParams);
            actText.setPadding(30, 30, 30, 30);
            ((LinearLayout) activityLayout).addView(actText);

            ImageButton imgEditButton = new ImageButton(getBaseContext());
            imgEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenActivityDialog(act.getId());
                }
            });
            imgEditButton.setBackgroundResource(R.drawable.round_button_edit);
            imgEditButton.setImageResource(R.drawable.ic_edit_24);
            LinearLayout.LayoutParams imgEditParams = new LinearLayout.LayoutParams(100, 100);
            imgEditParams.setMargins(20, 20, 10, 20);
            imgEditButton.setLayoutParams(imgEditParams);
            ((LinearLayout) activityLayout).addView(imgEditButton);

            ImageButton imgDeleteButton = new ImageButton(getBaseContext());
            imgDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenDeleteConfirmation(act.getId());
                }
            });
            imgDeleteButton.setBackgroundResource(R.drawable.round_button_delete);
            imgDeleteButton.setImageResource(R.drawable.ic_delete_24);
            LinearLayout.LayoutParams imgDeleteParams = new LinearLayout.LayoutParams(100, 100);
            imgDeleteParams.setMargins(10, 20, 20, 20);
            imgDeleteButton.setLayoutParams(imgDeleteParams);
            ((LinearLayout) activityLayout).addView(imgDeleteButton);

            ((LinearLayout) llLayout).addView(activityLayout);
        }
    }
    public void ApplyScore(int score) {
        totalPoints += score;
        RenderBalance();
        sharedPreferencesHelper.SetTotalPoints(totalPoints, this.getBaseContext());
    }
    //endregion

    //region Dialog listener overrides
    @Override
    public void Reset() {
        totalPoints = 0;
        sharedPreferencesHelper.SetTotalPoints(totalPoints, this.getBaseContext());
        RenderBalance();
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