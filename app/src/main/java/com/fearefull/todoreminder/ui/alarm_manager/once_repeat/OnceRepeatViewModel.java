package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.other.OnceRepeatModel;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class OnceRepeatViewModel extends BaseViewModel<OnceRepeatNavigator> {
    private Alarm alarm;
    private OnceRepeatModel model;

    public OnceRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        model = new OnceRepeatModel();
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public void onTimePickerClick() {
        getNavigator().showTimePickerFragment();
    }

    public void onDatePickerClick() {
        getNavigator().showDatePickerFragment();
    }

    public void onAddClick() {
        getNavigator().onAddRepeat();
    }

    public OnceRepeatModel getModel() {
        return model;
    }

    public void setModel(OnceRepeatModel model) {
        this.model = model;
    }

    void checkForSend() {
           if (model.isValid()) {
               alarm.addOnceRepeatModel(model);
               model.reset();
               getNavigator().send();
           }
    }
}
