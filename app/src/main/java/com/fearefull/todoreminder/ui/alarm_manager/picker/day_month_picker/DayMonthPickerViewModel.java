package com.fearefull.todoreminder.ui.alarm_manager.picker.day_month_picker;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.other.type.MonthType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

public class DayMonthPickerViewModel extends BaseViewModel<DayMonthPickerNavigator> {
    private int day;
    private MutableLiveData<List<String>> dayMonthPickerValues;
    private MutableLiveData<Integer> dayMonthPickerMaxIndex;
    private MutableLiveData<Integer> dayMonthPickerDefaultIndex;

    public DayMonthPickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        dayMonthPickerValues = new MutableLiveData<>();
        dayMonthPickerMaxIndex = new MutableLiveData<>();
        dayMonthPickerDefaultIndex = new MutableLiveData<>();
    }

    void init(int day) {
        this.day = day;

        dayMonthPickerValues.setValue(AlarmUtils.getDays(MonthType.AZAR));
        dayMonthPickerMaxIndex.setValue(MonthType.AZAR.getDays() - 1);
        dayMonthPickerDefaultIndex.setValue(Alarm.dayMonthToIndex(day));
    }

    public void onDayMonthPickerValueChange(int oldVal, int newVal) {
        day = Alarm.indexToDayMonth(newVal);
    }

    public int getDay() {
        return day;
    }

    public MutableLiveData<List<String>> getDayMonthPickerValues() {
        return dayMonthPickerValues;
    }

    public MutableLiveData<Integer> getDayMonthPickerMaxIndex() {
        return dayMonthPickerMaxIndex;
    }

    public MutableLiveData<Integer> getDayMonthPickerDefaultIndex() {
        return dayMonthPickerDefaultIndex;
    }
}
