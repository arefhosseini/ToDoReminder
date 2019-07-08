package com.fearefull.todoreminder.data.local.prefs;

import com.fearefull.todoreminder.data.model.db.LoggedInMode;

public interface PreferencesHelper {

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(LoggedInMode mode);

    String getCurrentUsername();

    void setCurrentUsername(String username);

    String getCurrentPassword();

    void setCurrentPassword(String password);
}