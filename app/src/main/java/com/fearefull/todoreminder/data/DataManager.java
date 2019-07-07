package com.fearefull.todoreminder.data;

import com.fearefull.todoreminder.data.local.db.DbHelper;
import com.fearefull.todoreminder.data.local.prefs.PreferencesHelper;
import com.fearefull.todoreminder.data.model.db.LoggedInMode;
import com.fearefull.todoreminder.data.remote.ApiHelper;


public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {
    void setUserAsLoggedOut();

    void updateUserInfo(
            Long userId,
            LoggedInMode loggedInMode,
            String username,
            String password);
}
