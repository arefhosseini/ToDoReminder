package com.fearefull.todoreminder.ui.main;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.LoggedInMode;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}