package com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class DailyRepeatViewModel extends BaseRepeatViewModel<DailyRepeatNavigator> {

    public DailyRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getPageLimitPager().setValue(1);
    }
}
