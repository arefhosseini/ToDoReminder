package com.fearefull.todoreminder.ui.alarm_manager;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class AlarmManagerViewModel extends BaseViewModel<AlarmManagerNavigator> {

    public AlarmManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
