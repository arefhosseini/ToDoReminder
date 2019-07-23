package com.fearefull.todoreminder.ui.alarm_manager.repeat.monthly_repeat;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class MonthlyRepeatViewModel extends BaseRepeatViewModel<MonthlyRepeatNavigator> {

    public MonthlyRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    int getDefaultDayMonth() {
        if (getAlarm().getDefaultDayMonth() > 30)
            return 30;
        return getAlarm().getDefaultDayMonth();
    }
}
