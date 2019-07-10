package com.fearefull.todoreminder.ui.alarm_manager;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.other.MyDate;
import com.fearefull.todoreminder.data.model.other.MyTime;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class AlarmManagerViewModel extends BaseViewModel<AlarmManagerNavigator> {

    private MyTime myTime;
    private MyDate myDate;
    private final ObservableField<String> time = new ObservableField<>();
    private final ObservableField<String> date = new ObservableField<>();

    public AlarmManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onTimePickerClick() {
        getNavigator().openTimePickerFragment();
    }

    public void onDatePickerClick() {
        getNavigator().openDatePickerFragment();
    }

    MyTime getMyTime() {
        return myTime;
    }

    void setMyTime(MyTime myTime) {
        this.myTime = myTime;
        time.set(myTime.toString());
    }

    public MyDate getMyDate() {
        return myDate;
    }

    public void setMyDate(MyDate myDate) {
        this.myDate = myDate;
        date.set(myDate.toString());
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public ObservableField<String> getDate() {
        return date;
    }
}
