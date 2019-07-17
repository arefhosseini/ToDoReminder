package com.fearefull.todoreminder.ui.alarm_manager.once_repeat.half_hour_time_picker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.other.HalfHourType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class HalfHourTimePickerViewModel extends BaseViewModel<HalfHourTimePickerNavigator> {

    private int minute;
    private int hour;
    private HalfHourType halfHourType;

    public HalfHourTimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    String[] getHours() {
        return AlarmUtils.get12Hours().toArray(new String[0]);
    }

    String[] getMinutes() {
        return AlarmUtils.getMinutes().toArray(new String[0]);
    }

    String[] getHalfHourTypes() {
        return AlarmUtils.getHalfHourTypes().toArray(new String[0]);
    }

    public void onHourPickerValueChange(int oldVal, int newVal) {
        Timber.i("hour index %d", newVal);
        hour = Alarm.halfHourToHour(Alarm.indexTo12Hour(newVal), halfHourType);
    }

    public void onMinutePickerValueChange(int oldVal, int newVal) {
        Timber.i("minute index %d", newVal);
        minute = Alarm.indexToMinute(newVal);
    }

    public void onHalfHourTypePickerValueChange(int oldVal, int newVal) {
        Timber.i("type index %d", newVal);
        halfHourType = Alarm.indexToHalfHourType(newVal);
        hour = Alarm.halfHourToHour(Alarm.hourToHalfHour(hour), halfHourType);
    }

    int getMinute() {
        return minute;
    }

    void setMinute(int minute) {
        this.minute = minute;
    }

    int getHour() {
        return hour;
    }

    void setHour(int hour) {
        this.hour = hour;
    }

    HalfHourType getHalfHourType() {
        return halfHourType;
    }

    void setHalfHourType(HalfHourType halfHourType) {
        this.halfHourType = halfHourType;
    }

    int getMinuteIndex() {
        return Alarm.minuteToIndex(minute);
    }

    int getHourIndex() {
        return Alarm.halfHourToIndex(Alarm.hourToHalfHour(hour));
    }

    int getHalfHourTypeIndex() {
        return Alarm.halfHourTypeToIndex(halfHourType);
    }
}
