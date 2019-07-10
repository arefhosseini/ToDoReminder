package com.fearefull.todoreminder.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.fearefull.todoreminder.data.model.db.LoggedInMode;
import com.fearefull.todoreminder.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_CURRENT_USERNAME = "PREF_KEY_CURRENT_USERNAME";

    private static final String PREF_KEY_CURRENT_PASSWORD = "PREF_KEY_CURRENT_PASSWORD";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private final SharedPreferences prefs;

    @Inject
    AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return prefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                LoggedInMode.LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        prefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getCurrentUsername() {
        return prefs.getString(PREF_KEY_CURRENT_USERNAME, null);
    }

    @Override
    public void setCurrentUsername(String username) {
        prefs.edit().putString(PREF_KEY_CURRENT_USERNAME, username).apply();
    }

    @Override
    public String getCurrentPassword() {
        return prefs.getString(PREF_KEY_CURRENT_PASSWORD, null);
    }

    @Override
    public void setCurrentPassword(String password) {
        prefs.edit().putString(PREF_KEY_CURRENT_PASSWORD, password).apply();
    }
}