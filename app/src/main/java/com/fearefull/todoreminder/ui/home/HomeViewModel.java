package com.fearefull.todoreminder.ui.home;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}