package com.fearefull.todoreminder.ui.alarm_manager.picker.full_hour_time_picker;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

public class FullHourTimePickerViewModel extends BaseViewModel<FullHourTimePickerNavigator> {

    private int minute;
    private int hour;

    private MutableLiveData<List<String>> minutePickerValues;
    private MutableLiveData<Integer> minutePickerMaxIndex;
    private MutableLiveData<Integer> minutePickerDefaultIndex;

    private MutableLiveData<List<String>> hourPickerValues;
    private MutableLiveData<Integer> hourPickerMaxIndex;
    private MutableLiveData<Integer> hourPickerDefaultIndex;

    public FullHourTimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);

        minutePickerValues = new MutableLiveData<>();
        minutePickerMaxIndex = new MutableLiveData<>();
        minutePickerDefaultIndex = new MutableLiveData<>();

        hourPickerValues = new MutableLiveData<>();
        hourPickerMaxIndex = new MutableLiveData<>();
        hourPickerDefaultIndex = new MutableLiveData<>();
    }

    void init(int minute, int hour) {
        this.minute = minute;
        this.hour = hour;

        minutePickerValues.setValue(AlarmUtils.getMinutes());
        minutePickerMaxIndex.setValue(59);
        minutePickerDefaultIndex.setValue(Alarm.minuteToIndex(minute));

        hourPickerValues.setValue(AlarmUtils.get24Hours());
        hourPickerMaxIndex.setValue(23);
        hourPickerDefaultIndex.setValue(Alarm.hourToIndex(hour));
    }

    public void onHourPickerValueChange(int oldVal, int newVal) {
        hour = Alarm.indexTo24Hour(newVal);
        hourPickerDefaultIndex.setValue(newVal);
    }

    public void onMinutePickerValueChange(int oldVal, int newVal) {
        minute = Alarm.indexToMinute(newVal);
        minutePickerDefaultIndex.setValue(newVal);
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
}
