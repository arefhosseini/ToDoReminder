package com.fearefull.todoreminder.ui.main;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private final ObservableField<String> appVersion = new ObservableField<>();

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    void onNavigationMenuCreated() {
    }

    void updateAppVersion(String version) {
        appVersion.set(version);
    }

    public void onOpenAlarmManager() {
        getNavigator().openAlarmManager(new Alarm("Alarm"));
    }
}