package com.fearefull.todoreminder.ui.main;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private final ObservableField<String> appVersion = new ObservableField<>();
    private MainNavigationItem navigationItem;

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

    public MainNavigationItem getNavigationItem() {
        return navigationItem;
    }

    public void setNavigationItem(MainNavigationItem navigationItem) {
        this.navigationItem = navigationItem;
    }
}