package com.fearefull.todoreminder.ui.alarm_manager.picker.half_hour_time_picker;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.other.type.HalfHourType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

public class HalfHourTimePickerViewModel extends BaseViewModel<HalfHourTimePickerNavigator> {

    private int minute;
    private int hour;
    private HalfHourType halfHourType;

    private MutableLiveData<List<String>> minutePickerValues;
    private MutableLiveData<Integer> minutePickerMaxIndex;
    private MutableLiveData<Integer> minutePickerDefaultIndex;

    private MutableLiveData<List<String>> hourPickerValues;
    private MutableLiveData<Integer> hourPickerMaxIndex;
    private MutableLiveData<Integer> hourPickerDefaultIndex;

    private MutableLiveData<List<String>> halfHourTypePickerValues;
    private MutableLiveData<Integer> halfHourTypePickerMaxIndex;
    private MutableLiveData<Integer> halfHourTypePickerDefaultIndex;

    public HalfHourTimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider,
                                       Settings settings) {
        super(dataManager, schedulerProvider, settings);

        minutePickerValues = new MutableLiveData<>();
        minutePickerMaxIndex = new MutableLiveData<>();
        minutePickerDefaultIndex = new MutableLiveData<>();

        hourPickerValues = new MutableLiveData<>();
        hourPickerMaxIndex = new MutableLiveData<>();
        hourPickerDefaultIndex = new MutableLiveData<>();

        halfHourTypePickerValues = new MutableLiveData<>();
        halfHourTypePickerMaxIndex = new MutableLiveData<>();
        halfHourTypePickerDefaultIndex = new MutableLiveData<>();
    }

    void init(int minute, int hour) {
        this.minute = minute;
        this.hour = hour;
        this.halfHourType = Alarm.hourToHalfHourType(hour);

        minutePickerValues.setValue(AlarmUtils.getMinutes());
        minutePickerMaxIndex.setValue(59);
        minutePickerDefaultIndex.setValue(Alarm.minuteToIndex(minute));

        hourPickerValues.setValue(AlarmUtils.get12Hours());
        hourPickerMaxIndex.setValue(11);
        hourPickerDefaultIndex.setValue(Alarm.halfHourToIndex(Alarm.hourToHalfHour(hour)));

        halfHourTypePickerValues.setValue(AlarmUtils.getHalfHourTypes());
        halfHourTypePickerMaxIndex.setValue(1);
        halfHourTypePickerDefaultIndex.setValue(Alarm.halfHourTypeToIndex(halfHourType));
    }

    public void onHourPickerValueChange(int oldVal, int newVal) {
        if ((Alarm.indexTo12Hour(newVal) == 12 && Alarm.indexTo12Hour(oldVal) == 11) ||
                (Alarm.indexTo12Hour(newVal) == 11 && Alarm.indexTo12Hour(oldVal) == 12))
            changeHalfHourType();
        hour = Alarm.halfHourToHour(Alarm.indexTo12Hour(newVal), halfHourType);
        hourPickerDefaultIndex.setValue(newVal);
    }

    public void onMinutePickerValueChange(int oldVal, int newVal) {
        minute = Alarm.indexToMinute(newVal);
        //checkChangeHour(Alarm.indexToMinute(oldVal), minute);
        minutePickerDefaultIndex.setValue(newVal);
    }

    public void onHalfHourTypePickerValueChange(int oldVal, int newVal) {
        halfHourType = Alarm.indexToHalfHourType(newVal);
        hour = Alarm.halfHourToHour(Alarm.hourToHalfHour(hour), halfHourType);
        halfHourTypePickerDefaultIndex.setValue(newVal);
    }

    private void checkChangeHour(int oldMinute, int newMinute) {
        if (oldMinute == 59 && newMinute == 0)
            forwardHour();
        else if (oldMinute == 0 && newMinute == 59)
            backwardHour();
    }

    private void forwardHour() {
        int value = Alarm.indexTo12Hour(hourPickerDefaultIndex.getValue());
        if (value == 11) {
            changeHalfHourType();
            value ++;
        }
        else if (value == 12)
            value = 1;
        else
            value ++;
        hourPickerDefaultIndex.setValue(Alarm.halfHourToIndex(value));
        hour = Alarm.halfHourToHour((value), halfHourType);
    }

    private void backwardHour() {
        int value = Alarm.indexTo12Hour(hourPickerDefaultIndex.getValue());
        if (value == 12) {
            changeHalfHourType();
            value --;
        }
        else if (value == 1)
            value = 12;
        else
            value --;
        hourPickerDefaultIndex.setValue(Alarm.halfHourToIndex(value));
        hour = Alarm.halfHourToHour((value), halfHourType);
    }

    private void changeHalfHourType() {
        if (halfHourType == HalfHourType.AM)
            halfHourType = HalfHourType.PM;
        else
            halfHourType = HalfHourType.AM;
        halfHourTypePickerDefaultIndex.setValue(Alarm.halfHourTypeToIndex(halfHourType));
    }

    int getMinute() {
        return minute;
    }

    int getHour() {
        return hour;
    }

    public MutableLiveData<List<String>> getMinutePickerValues() {
        return minutePickerValues;
    }

    public MutableLiveData<Integer> getMinutePickerMaxIndex() {
        return minutePickerMaxIndex;
    }

    public MutableLiveData<Integer> getMinutePickerDefaultIndex() {
        return minutePickerDefaultIndex;
    }

    public MutableLiveData<List<String>> getHourPickerValues() {
        return hourPickerValues;
    }

    public MutableLiveData<Integer> getHourPickerMaxIndex() {
        return hourPickerMaxIndex;
    }

    public MutableLiveData<Integer> getHourPickerDefaultIndex() {
        return hourPickerDefaultIndex;
    }

    public MutableLiveData<List<String>> getHalfHourTypePickerValues() {
        return halfHourTypePickerValues;
    }

    public MutableLiveData<Integer> getHalfHourTypePickerMaxIndex() {
        return halfHourTypePickerMaxIndex;
    }

    public MutableLiveData<Integer> getHalfHourTypePickerDefaultIndex() {
        return halfHourTypePickerDefaultIndex;
    }
}
