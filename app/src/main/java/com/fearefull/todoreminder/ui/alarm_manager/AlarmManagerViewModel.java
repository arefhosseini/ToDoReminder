package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class AlarmManagerViewModel extends BaseViewModel<AlarmManagerNavigator> {

    private Alarm alarm;
    private final ObservableField<String> time = new ObservableField<>();
    private final ObservableField<String> date = new ObservableField<>();
    private final ObservableField<String> repeat = new ObservableField<>();

    public AlarmManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onTimePickerClick() {
        getNavigator().openTimePickerFragment();
    }

    public void onDatePickerClick() {
        getNavigator().openDatePickerFragment();
    }

    public void onRepeatPickerClick() {
        getNavigator().openRepeatPickerFragment();
    }

    Alarm getAlarm() {
        return alarm;
    }

    void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    void updateAlarm() {
        updateTime();
        updateDate();
        updateRepeat();
    }

    void updateTime() {
        time.set(alarm.getTime().toString());
    }

    void updateDate() {
        date.set(alarm.getDate().toString());
    }

    void updateRepeat() {
        repeat.set(alarm.getRepeat().toString());
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public ObservableField<String> getRepeat() {
        return repeat;
    }
}
