package com.fearefull.todoreminder.ui.alarm_manager.date_picker;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.MyDate;
import com.fearefull.todoreminder.data.model.other.TimeType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.TimeUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.Date;

import timber.log.Timber;

public class DatePickerViewModel extends BaseViewModel<DatePickerNavigator> {
    private MyDate myDate;

    public DatePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    String[] getYears() {
        return TimeUtils.getYears().toArray(new String[0]);
    }

    String[] getMonths() {
        return TimeUtils.getMonths().toArray(new String[0]);
    }

    String[] getDays() {
        return TimeUtils.getDays(myDate.getMonth()).toArray(new String[0]);
    }

    MyDate getMyDate() {
        return myDate;
    }

    void setDefaultDate(MyDate myDate) {
        if (!myDate.isChanged())
            myDate.change(new Date());
        this.myDate = myDate;
    }

    public void onYearsPickerValueChange(int oldVal, int newVal) {
        Timber.i("year index %d", newVal);
        myDate.setYear(TimeUtils.indexToYear(newVal));
    }

    public void onMonthsPickerValueChange(int oldVal, int newVal) {
        Timber.i("month index %d", newVal);
        myDate.setMonth(TimeUtils.indexToMonth(newVal));
        if (TimeUtils.shouldDayChange(TimeUtils.indexToMonth(oldVal), TimeUtils.indexToMonth(newVal))) {
            myDate.setDay(TimeUtils.changeDay(myDate.getDay(), myDate.getMonth()));
            getNavigator().onMonthChanged();
        }
    }

    public void onDaysPickerValueChange(int oldVal, int newVal) {
        Timber.i("day index %d", newVal);
        myDate.setDay(TimeUtils.indexToDay(newVal));
    }
}
