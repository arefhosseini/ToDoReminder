package com.fearefull.todoreminder.ui.alarm_manager.time_picker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.data.model.other.MyTime;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.TimeUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.Date;

import timber.log.Timber;

public class TimePickerViewModel extends BaseViewModel<TimePickerNavigator> {
    private Alarm alarm;

    public TimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    String[] getHours() {
        return TimeUtils.get12Hours().toArray(new String[0]);
    }

    String[] getMinutes() {
        return TimeUtils.getMinutes().toArray(new String[0]);
    }

    String[] getTimeTypes() {
        return TimeUtils.getTimeTypes().toArray(new String[0]);
    }

    void setAlarm(Alarm alarm) {
        if (! alarm.getTime().isChanged())
            alarm.getTime().change(new Date());
        this.alarm = alarm;
    }

    public void onHoursPickerValueChange(int oldVal, int newVal) {
        Timber.i("hour index %d", newVal);
        alarm.getTime().setHour(TimeUtils.indexToHour(newVal));
    }

    public void onMinutesPickerValueChange(int oldVal, int newVal) {
        Timber.i("minute index %d", newVal);
        alarm.getTime().setMinute(TimeUtils.indexToMinute(newVal));
    }

    public void onTypesPickerValueChange(int oldVal, int newVal) {
        Timber.i("type index %d", newVal);
        alarm.getTime().setTimeType(TimeUtils.indexToTimeType(newVal));
    }



    Alarm getAlarm() {
        return alarm;
    }
}
