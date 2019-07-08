package com.fearefull.todoreminder.ui.login;

import android.text.TextUtils;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.api.LoginRequest;
import com.fearefull.todoreminder.data.model.db.LoggedInMode;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.CommonUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    boolean isUsernameAndPasswordValid(String username, String password) {
        // validate email and password
        if (TextUtils.isEmpty(username)) {
            return false;
        }
        return !TextUtils.isEmpty(password);
    }

    public void login(String username, String password) {
        setIsLoading(true);
        setIsLoading(false);
        getDataManager().updateUserInfo(LoggedInMode.LOGGED_IN, username, password);
        getNavigator().openMainActivity();
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }
}