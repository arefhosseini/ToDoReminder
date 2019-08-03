package com.fearefull.todoreminder.ui.alarm_manager.repeat.daily_repeat;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class DailyRepeatViewModel extends BaseRepeatViewModel<DailyRepeatNavigator> {

    public DailyRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        getPageLimitPager().setValue(1);
    }
}
