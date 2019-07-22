package com.fearefull.todoreminder.ui.alarm_manager.repeat.weekly_repeat;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class WeeklyRepeatViewModel extends BaseRepeatViewModel<WeeklyRepeatNavigator> {
    public WeeklyRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getPageLimitPager().setValue(1);
    }

    @Override
    public void onAddClick() {
        getNavigator().onAddRepeat();
    }
}
