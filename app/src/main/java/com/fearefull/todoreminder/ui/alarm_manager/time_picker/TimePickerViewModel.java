package com.fearefull.todoreminder.ui.alarm_manager.time_picker;

import android.widget.NumberPicker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.MyTime;
import com.fearefull.todoreminder.data.model.other.TimeType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.TimeUtils;
import com.fearefull.todoreminder.utils.ViewUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Calendar;
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

    public ArrayList<TimeType> getTimeTypes() {
        return TimeUtils.getTimeTypes();
    }

    String[] getStringTimeTypes() {
        return TimeUtils.getStringTimeTypes().toArray(new String[0]);
    }

    void setDefaultTime(MyTime myTime) {
        if (!myTime.isChanged())
            myTime.change(new Date());
        this.myTime = myTime;
    }

    public void onHourPickerValueChange(int oldVal, int newVal) {
        Timber.i("hour " + String.valueOf(newVal));
        myTime.setHour(newVal + 1);
        myTime.setChanged(true);
    }

    public void onMinutePickerValueChange(int oldVal, int newVal) {
        Timber.i("minute " + String.valueOf(newVal));
        myTime.setMinute(newVal);
        myTime.setChanged(true);
    }

    public void onTypePickerValueChange(int oldVal, int newVal) {
        Timber.i("type " + String.valueOf(newVal));
        if (newVal == 0)
            myTime.setTimeType(TimeType.AM);
        else
            myTime.setTimeType(TimeType.PM);
        myTime.setChanged(true);
    }

    MyTime getMyTime() {
        return myTime;
    }
}
