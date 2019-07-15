package com.fearefull.todoreminder.data;


import android.content.Context;

import com.fearefull.todoreminder.data.local.db.DbHelper;
import com.fearefull.todoreminder.data.local.prefs.PreferencesHelper;
import com.fearefull.todoreminder.data.model.api.LoginRequest;
import com.fearefull.todoreminder.data.model.api.LoginResponse;
import com.fearefull.todoreminder.data.model.db.AlarmModel;
import com.fearefull.todoreminder.data.model.db.LoggedInMode;
import com.fearefull.todoreminder.data.model.db.User;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.data.remote.ApiHelper;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper apiHelper;
    private final Context context;
    private final DbHelper dbHelper;
    private final Gson gson;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, ApiHelper apiHelper, Gson gson,
                          PreferencesHelper preferencesHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
        this.gson = gson;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return apiHelper.doServerLoginApiCall(request);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return preferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        preferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUsername() {
        return preferencesHelper.getCurrentUsername();
    }

    @Override
    public void setCurrentUsername(String username) {
        preferencesHelper.setCurrentUsername(username);
    }

    @Override
    public String getCurrentPassword() {
        return preferencesHelper.getCurrentPassword();
    }

    @Override
    public void setCurrentPassword(String password) {
        preferencesHelper.setCurrentPassword(password);
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return dbHelper.insertUser(user);
    }

    @Override
    public Observable<Boolean> insertAlarm(AlarmModel alarmModel) {
        return dbHelper.insertAlarm(alarmModel);
    }

    @Override
    public Observable<List<Alarm>> getAllAlarms() {
        return dbHelper.getAllAlarms();
    }

    @Override
    public Observable<Boolean> removeAllAlarms() {
        return dbHelper.removeAllAlarms();
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                LoggedInMode.LOGGED_OUT,
                null,
                null
        );
    }

    @Override
    public void updateUserInfo(
            LoggedInMode loggedInMode,
            String userName,
            String password) {

        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUsername(userName);
        setCurrentPassword(password);
    }
}
