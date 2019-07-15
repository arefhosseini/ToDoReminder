package com.fearefull.todoreminder.ui.main;

import android.text.TextUtils;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private final ObservableField<String> appVersion = new ObservableField<>();

    private final ObservableField<String> username = new ObservableField<>();

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    void onNavigationMenuCreated() {
        final String currentUsername = getDataManager().getCurrentUsername();
        if (!TextUtils.isEmpty(currentUsername)) {
            username.set(currentUsername);
        }
    }

    void updateAppVersion(String version) {
        appVersion.set(version);
    }

    void removeAlarms() {
        getCompositeDisposable().add(getDataManager()
                .removeAllAlarms()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    logout();
                }, throwable -> {

                })
        );
    }

    void logout() {
        setIsLoading(true);
        getDataManager().setUserAsLoggedOut();
        setIsLoading(false);
        getNavigator().openLoginActivity();
    }

    public void onOpenAlarmManager() {
        getNavigator().openAlarmManagerActivity();
    }
}