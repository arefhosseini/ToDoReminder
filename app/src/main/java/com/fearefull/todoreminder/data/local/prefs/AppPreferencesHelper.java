package com.fearefull.todoreminder.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.fearefull.todoreminder.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_SAMPLE = "PREF_KEY_SAMPLE";
    private final SharedPreferences prefs;

    @Inject
    AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getSample() {
        return prefs.getString(PREF_KEY_SAMPLE, null);
    }

    @Override
    public void setSample(String sample) {
        prefs.edit().putString(PREF_KEY_SAMPLE, sample).apply();
    }
}