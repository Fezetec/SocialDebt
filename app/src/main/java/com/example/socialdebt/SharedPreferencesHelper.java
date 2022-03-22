package com.example.socialdebt;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper {
    final static String spPointsKey = "spPoints";
    final static String spActivitiesKey = "spActivities";
    public Gson gson;

    public SharedPreferencesHelper(){
        gson = new Gson();
    }

    public ArrayList<Activity> GetActivities(Context context){
        String jsonGetActivities = GetSharedPreferences(spActivitiesKey, context).getString(context.getString(R.string.spActivities), "");
        return gson.fromJson(jsonGetActivities, new TypeToken<List<Activity>>() {}.getType());
    }

    public void SetActivities(ArrayList<Activity> activities, Context context){
        String jsonSetActivities = gson.toJson(activities);
        SharedPreferences.Editor editor = GetSharedPreferences(spActivitiesKey, context).edit();
        editor.putString(context.getString(R.string.spActivities), jsonSetActivities);
        editor.commit();
    }

    public Activity GetActivity(int id, Context context){
        String jsonGetActivities = GetSharedPreferences(spActivitiesKey, context).getString(context.getString(R.string.spActivities), "");
        ArrayList<Activity> activities = gson.fromJson(jsonGetActivities, new TypeToken<List<Activity>>() {}.getType());
        for (Activity act : activities) {
            if(act.id == id){
                return act;
            }
        }
        return null;
    }

    public int GetTotalPoints(Context context){
        return GetSharedPreferences(spPointsKey, context).getInt(context.getString(R.string.spTotalPoints), 0);
    }

    public void SetTotalPoints(int totalPoints, Context context){
        SharedPreferences.Editor editor = GetSharedPreferences(spPointsKey, context).edit();
        editor.putInt(context.getString(R.string.spTotalPoints), totalPoints);
        editor.commit();
    }

    private SharedPreferences GetSharedPreferences(String key, Context context){
        return context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    public String GsonThis(Activity act){
        return gson.toJson(act);
    }

    public Activity GsonThis(String str){
        return gson.fromJson(str, new TypeToken<Activity>() {}.getType());
    }
}
