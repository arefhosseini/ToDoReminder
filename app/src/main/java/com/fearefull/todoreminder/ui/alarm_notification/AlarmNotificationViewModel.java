package com.fearefull.todoreminder.ui.alarm_notification;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class AlarmNotificationViewModel extends BaseViewModel<AlarmNotificationNavigator> {
    private final AlarmScheduler alarmScheduler;

    public AlarmNotificationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AlarmScheduler alarmScheduler) {
        super(dataManager, schedulerProvider);
        this.alarmScheduler = alarmScheduler;

        alarmScheduler.schedule();
    }


}
