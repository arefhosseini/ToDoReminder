package com.fearefull.todoreminder.ui.alarm_manager.picker.date_picker;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.other.type.MonthType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

public class DatePickerViewModel extends BaseViewModel<DatePickerNavigator> {

    private int month;
    private int day;
    private MutableLiveData<List<String>> monthPickerValues;
    private MutableLiveData<List<String>> dayPickerValues;
    private MutableLiveData<Integer> monthPickerMaxIndex;
    private MutableLiveData<Integer> dayPickerMaxIndex;
    private MutableLiveData<Integer> monthPickerDefaultIndex;
    private MutableLiveData<Integer> dayPickerDefaultIndex;

    public DatePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        monthPickerValues = new MutableLiveData<>();
        dayPickerValues = new MutableLiveData<>();
        monthPickerMaxIndex = new MutableLiveData<>();
        dayPickerMaxIndex = new MutableLiveData<>();
        monthPickerDefaultIndex = new MutableLiveData<>();
        dayPickerDefaultIndex = new MutableLiveData<>();
    }

    void init(int day, int month) {
        this.day = day;
        this.month = month;

        monthPickerValues.setValue(AlarmUtils.getMonths());
        monthPickerMaxIndex.setValue(11);
        monthPickerDefaultIndex.setValue(Alarm.monthToIndex(month));

        dayPickerValues.setValue(AlarmUtils.getDays(MonthType.getMonthType(month)));
        dayPickerMaxIndex.setValue(MonthType.getMonthType(month).getDays() - 1);
        dayPickerDefaultIndex.setValue(Alarm.dayMonthToIndex(day));
    }

    public void onMonthPickerValueChange(int oldVal, int newVal) {
        month = Alarm.indexToMonth(newVal);
        checkChangeDay(AlarmUtils.indexToMonth(oldVal), AlarmUtils.indexToMonth(newVal));
    }

    public void onDayPickerValueChange(int oldVal, int newVal) {
        day = Alarm.indexToDayMonth(newVal);
        if (checkForwardMonth(Alarm.indexToDayMonth(oldVal), day, MonthType.getMonthType(month)))
            forwardMonth();
        else if (checkBackwardMonth(Alarm.indexToDayMonth(oldVal), day, MonthType.getMonthType(month)))
            backwardMonth();
    }

    public boolean checkForwardMonth(int oldDay, int newDay, MonthType monthType) {
        return newDay == 1 && monthType.getDays() == oldDay;
    }

    public boolean checkBackwardMonth(int oldDay, int newDay, MonthType monthType) {
        return oldDay == 1 && monthType.getDays() == newDay;
    }

    private void forwardMonth() {
        int lastMonth = month;
        if (month == 12)
            month = 1;
        else
            month ++;
        monthPickerDefaultIndex.setValue(Alarm.monthToIndex(month));
        checkChangeDay(MonthType.getMonthType(lastMonth), MonthType.getMonthType(month));
    }

    private void backwardMonth() {
        int lastMonth = month;
        if (month == 1)
            month = 12;
        else
            month --;
        monthPickerDefaultIndex.setValue(Alarm.monthToIndex(month));
        checkChangeDay(MonthType.getMonthType(lastMonth), MonthType.getMonthType(month));
    }

    private void checkChangeDay(MonthType oldMonth, MonthType newMonth) {
        if (AlarmUtils.shouldDayChange(oldMonth, newMonth)) {
            day = AlarmUtils.changeDay(day, MonthType.getMonthType(month));
            changeDay();
        }
    }

    public void changeDay() {
        if (dayPickerMaxIndex.getValue() < day) {
            dayPickerValues.setValue(AlarmUtils.getDays(MonthType.getMonthType(month)));
            dayPickerMaxIndex.setValue(MonthType.getMonthType(month).getDays() - 1);
            dayPickerDefaultIndex.setValue(Alarm.dayMonthToIndex(day));
        }
        else {
            dayPickerMaxIndex.setValue(AlarmUtils.getDays(MonthType.getMonthType(month)).size() - 1);
            //dayPickerValues.setValue(AlarmUtils.getDays(MonthType.getMonthType(month)));
            dayPickerDefaultIndex.setValue(Alarm.dayMonthToIndex(day));
        }
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

    public MutableLiveData<List<String>> getMonthPickerValues() {
        return monthPickerValues;
    }

    public void setMonthPickerValues(MutableLiveData<List<String>> monthPickerValues) {
        this.monthPickerValues = monthPickerValues;
    }

    public MutableLiveData<List<String>> getDayPickerValues() {
        return dayPickerValues;
    }

    public void setDayPickerValues(MutableLiveData<List<String>> dayPickerValues) {
        this.dayPickerValues = dayPickerValues;
    }

    public MutableLiveData<Integer> getMonthPickerMaxIndex() {
        return monthPickerMaxIndex;
    }

    public void setMonthPickerMaxIndex(MutableLiveData<Integer> monthPickerMaxIndex) {
        this.monthPickerMaxIndex = monthPickerMaxIndex;
    }

    public MutableLiveData<Integer> getDayPickerMaxIndex() {
        return dayPickerMaxIndex;
    }

    public void setDayPickerMaxIndex(MutableLiveData<Integer> dayPickerMaxIndex) {
        this.dayPickerMaxIndex = dayPickerMaxIndex;
    }

    public MutableLiveData<Integer> getMonthPickerDefaultIndex() {
        return monthPickerDefaultIndex;
    }

    public void setMonthPickerDefaultIndex(MutableLiveData<Integer> monthPickerDefaultIndex) {
        this.monthPickerDefaultIndex = monthPickerDefaultIndex;
    }

    public MutableLiveData<Integer> getDayPickerDefaultIndex() {
        return dayPickerDefaultIndex;
    }

    public void setDayPickerDefaultIndex(MutableLiveData<Integer> dayPickerDefaultIndex) {
        this.dayPickerDefaultIndex = dayPickerDefaultIndex;
    }
}
