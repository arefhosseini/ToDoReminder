package com.fearefull.todoreminder.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.fearefull.todoreminder.data.model.db.Snooze;
import com.fearefull.todoreminder.di.PreferenceInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_SCHEDULE = "PREF_KEY_SCHEDULE";
    private static final String PREF_KEY_SNOOZE = "PREF_KEY_SNOOZE";
    private final SharedPreferences prefs;
    private final Gson gson;

    @Inject
    AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName, Gson gson) {
        prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        this.gson = gson;
    }

    @Override
    public int getSchedule() {
        return prefs.getInt(PREF_KEY_SCHEDULE, -1);
    }

    @Override
    public void setSchedule(int schedule) {
        prefs.edit().putInt(PREF_KEY_SCHEDULE, schedule).apply();
    }

    @Override
    public List<Snooze> getSnoozeList() {
        Type type = new TypeToken<List<Snooze>>(){}.getType();
        return gson.fromJson(prefs.getString(PREF_KEY_SNOOZE, ""), type);
    }

    @Override
    public void setSnoozeList(List<Snooze> snoozes) {
        String json = gson.toJson(snoozes);
        prefs.edit().putString(PREF_KEY_SNOOZE, json).apply();
    }
}