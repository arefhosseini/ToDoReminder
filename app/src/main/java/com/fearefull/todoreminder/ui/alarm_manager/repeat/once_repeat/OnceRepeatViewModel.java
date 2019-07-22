package com.fearefull.todoreminder.ui.alarm_manager.repeat.once_repeat;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat.BaseRepeatViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class OnceRepeatViewModel extends BaseRepeatViewModel<OnceRepeatNavigator> {

    public OnceRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getPageLimitPager().setValue(2);
    }

    public void onTimePickerClick() {
        getNavigator().timePickerClick();
        getCurrentTabPager().setValue(0);
    }

    public void onDatePickerClick() {
        getNavigator().datePickerClick();
        getCurrentTabPager().setValue(1);
    }
}
