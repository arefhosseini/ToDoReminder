package com.fearefull.todoreminder.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.fearefull.todoreminder.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_SCHEDULE = "PREF_KEY_SCHEDULE";
    private final SharedPreferences prefs;

    @Inject
    AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public int getSchedule() {
        return prefs.getInt(PREF_KEY_SCHEDULE, -1);
    }

    @Override
    public void setSchedule(int schedule) {
        prefs.edit().putInt(PREF_KEY_SCHEDULE, schedule).apply();
    }
}