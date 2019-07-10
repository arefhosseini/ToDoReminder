package com.fearefull.todoreminder.ui.alarm_manager.time_picker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.MyTime;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.TimeUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.Date;

import timber.log.Timber;

public class TimePickerViewModel extends BaseViewModel<TimePickerNavigator> {
    private MyTime myTime;

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

    void setDefaultTime(MyTime myTime) {
        if (!myTime.isChanged())
            myTime.change(new Date());
        this.myTime = myTime;
    }

    public void onHoursPickerValueChange(int oldVal, int newVal) {
        Timber.i("hour index %d", newVal);
        myTime.setHour(TimeUtils.indexToHour(newVal));
    }

    public void onMinutesPickerValueChange(int oldVal, int newVal) {
        Timber.i("minute index %d", newVal);
        myTime.setMinute(TimeUtils.indexToMinute(newVal));
    }

    public void onTypesPickerValueChange(int oldVal, int newVal) {
        Timber.i("type index %d", newVal);
        myTime.setTimeType(TimeUtils.indexToTimeType(newVal));
    }



    MyTime getMyTime() {
        return myTime;
    }
}
