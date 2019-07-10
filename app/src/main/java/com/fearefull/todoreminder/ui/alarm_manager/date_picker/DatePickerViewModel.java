package com.fearefull.todoreminder.ui.alarm_manager.date_picker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.Alarm;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.Date;

import timber.log.Timber;

public class DatePickerViewModel extends BaseViewModel<DatePickerNavigator> {
    private Alarm alarm;

    public DatePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    String[] getYears() {
        return AlarmUtils.getYears().toArray(new String[0]);
    }

    String[] getMonths() {
        return AlarmUtils.getMonths().toArray(new String[0]);
    }

    String[] getDays() {
        return AlarmUtils.getDays(alarm.getDate().getMonth()).toArray(new String[0]);
    }

    Alarm getAlarm() {
        return alarm;
    }

    void setAlarm(Alarm alarm) {
        if (!alarm.getDate().isChanged())
            alarm.getDate().change(new Date());
        this.alarm = alarm;
    }

    public void onYearsPickerValueChange(int oldVal, int newVal) {
        Timber.i("year index %d", newVal);
        alarm.getDate().setYear(AlarmUtils.indexToYear(newVal));
    }

    public void onMonthsPickerValueChange(int oldVal, int newVal) {
        Timber.i("month index %d", newVal);
        alarm.getDate().setMonth(AlarmUtils.indexToMonth(newVal));
        if (AlarmUtils.shouldDayChange(AlarmUtils.indexToMonth(oldVal), AlarmUtils.indexToMonth(newVal))) {
            alarm.getDate().setDay(AlarmUtils.changeDay(alarm.getDate().getDay(), alarm.getDate().getMonth()));
            getNavigator().onMonthChanged();
        }
    }

    public void onDaysPickerValueChange(int oldVal, int newVal) {
        Timber.i("day index %d", newVal);
        alarm.getDate().setDay(AlarmUtils.indexToDay(newVal));
    }
}
