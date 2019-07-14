package com.fearefull.todoreminder.ui.alarm_manager.time_picker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.Date;

import timber.log.Timber;

public class TimePickerViewModel extends BaseViewModel<TimePickerNavigator> {
    private Alarm alarm;

    public TimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    String[] getHours() {
        return AlarmUtils.get12Hours().toArray(new String[0]);
    }

    String[] getMinutes() {
        return AlarmUtils.getMinutes().toArray(new String[0]);
    }

    String[] getTimeTypes() {
        return AlarmUtils.getTimeTypes().toArray(new String[0]);
    }

    void setAlarm(Alarm alarm) {
        if (! alarm.getTime().isChanged())
            alarm.getTime().change(new Date());
        this.alarm = alarm;
    }

    public void onHoursPickerValueChange(int oldVal, int newVal) {
        Timber.i("hour index %d", newVal);
        alarm.getTime().setHour(AlarmUtils.indexToHour(newVal));
    }

    public void onMinutesPickerValueChange(int oldVal, int newVal) {
        Timber.i("minute index %d", newVal);
        alarm.getTime().setMinute(AlarmUtils.indexToMinute(newVal));
    }

    public void onTypesPickerValueChange(int oldVal, int newVal) {
        Timber.i("type index %d", newVal);
        alarm.getTime().setTimeType(AlarmUtils.indexToTimeType(newVal));
    }

    Alarm getAlarm() {
        return alarm;
    }
}
