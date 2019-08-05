package com.fearefull.todoreminder.ui.alarm_manager.picker.ringtone_picker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class RingtonePickerViewModel extends BaseViewModel<RingtonePickerNavigator> {


    public RingtonePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
    }
}
