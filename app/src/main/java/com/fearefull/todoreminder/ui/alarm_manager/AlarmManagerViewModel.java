package com.fearefull.todoreminder.ui.alarm_manager;

import android.content.DialogInterface;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.data.model.other.RepeatType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
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

    String[] getRepeats() {
        return AlarmUtils.getRepeatTypes().toArray(new String[0]);
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

    int getRepeatDialogDefaultIndex() {
        return alarm.getRepeat().getType().getIndex();
    }

    DialogInterface.OnClickListener repeatPickerOnClickListener = (dialog, which) -> {
        alarm.getRepeat().setType(AlarmUtils.indexToRepeatType(which));
        if (alarm.getRepeat().getType() != RepeatType.CUSTOM) {
            dialog.dismiss();
            updateRepeat();
        }
    };
}
