package com.fearefull.todoreminder.ui.alarm_manager.once_repeat.date_picker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class DatePickerViewModel extends BaseViewModel<DatePickerNavigator> {

    private int month;
    private int day;

    public DatePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    String[] getMonths() {
        return AlarmUtils.getMonths().toArray(new String[0]);
    }

    String[] getDays() {
        return AlarmUtils.getDays(AlarmUtils.getMonth(month)).toArray(new String[0]);
    }

    public void onMonthPickerValueChange(int oldVal, int newVal) {
        Timber.i("month index %d", newVal);
        month = Alarm.indexToMonth(newVal);
        if (AlarmUtils.shouldDayChange(AlarmUtils.indexToMonth(oldVal), AlarmUtils.indexToMonth(newVal))) {
            day = AlarmUtils.changeDay(day, AlarmUtils.getMonth(month));
            getNavigator().onMonthChanged();
        }
    }

    public void onDayPickerValueChange(int oldVal, int newVal) {
        Timber.i("day index %d", newVal);
        day = Alarm.indexToDayMonth(newVal);
        getNavigator().onDayChanged();
    }

    int getMonth() {
        return month;
    }

    void setMonth(int month) {
        this.month = month;
    }

    int getDay() {
        return day;
    }

    void setDay(int day) {
        this.day = day;
    }

    int getMonthIndex() {
        return Alarm.monthToIndex(month);
    }

    int getDayIndex() {
        return Alarm.dayMonthToIndex(day);
    }
}
