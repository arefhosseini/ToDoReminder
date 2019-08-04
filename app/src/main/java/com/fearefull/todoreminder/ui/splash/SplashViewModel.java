package com.fearefull.todoreminder.ui.splash;

import android.os.Handler;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
    }

    void start() {
        decideNextActivity();
    }

    private void decideNextActivity() {
        new Handler().postDelayed(() -> getNavigator().openMainActivity(), 2000L);
    }
}