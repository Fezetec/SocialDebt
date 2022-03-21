package com.example.socialdebt;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper {
    public Gson gson;

    public SharedPreferencesHelper(){
        gson = new Gson();
    }

    public ArrayList<Activity> GetActivities(SharedPreferences sharedPreferences, Context context){
        String jsonGetActivities = sharedPreferences.getString(context.getString(R.string.spActivities), "");
        return this.gson.fromJson(jsonGetActivities, new TypeToken<List<Activity>>() {}.getType());
    }

    public int GetTotalPoints(SharedPreferences sharedPreferences, Context context){
        return sharedPreferences.getInt(context.getString(R.string.spTotalPoints), 0);
    }

    public void SetActivities(SharedPreferences sharedPreferences, ArrayList<Activity> activities, Context context){
        String jsonSetActivities = gson.toJson(activities);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.spActivities), jsonSetActivities);
        editor.commit();
    }

    public void SetTotalPoints(SharedPreferences sharedPreferences, int totalPoints, Context context){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.spTotalPoints), totalPoints);
        editor.commit();
    }
}
