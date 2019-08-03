package com.fearefull.todoreminder.ui.main;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private MainNavigationItem navigationItem;

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
    }

    void onNavigationMenuCreated() {
    }

    public MainNavigationItem getNavigationItem() {
        return navigationItem;
    }

    public void setNavigationItem(MainNavigationItem navigationItem) {
        this.navigationItem = navigationItem;
    }
}