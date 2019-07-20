package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.RepeatModel;
import com.fearefull.todoreminder.data.model.other.type.RepeatResponseType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class OnceRepeatViewModel extends BaseViewModel<OnceRepeatNavigator> {
    private Alarm alarm;
    private RepeatModel repeatModel;
    private final MutableLiveData<Integer> currentTabPager;
    private final MutableLiveData<Integer> pageLimitPager;

    public OnceRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        repeatModel = new RepeatModel();
        currentTabPager = new MutableLiveData<>();
        pageLimitPager = new MutableLiveData<>();
        pageLimitPager.setValue(2);
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public void onTimePickerClick() {
        getNavigator().timePickerClick();
        currentTabPager.setValue(0);
    }

    public void onDatePickerClick() {
        getNavigator().datePickerClick();
        currentTabPager.setValue(1);
    }

    public void onAddClick() {
        getNavigator().onAddRepeat();
    }

    public RepeatModel getRepeatModel() {
        return repeatModel;
    }

    public void setRepeatModel(RepeatModel repeatModel) {
        this.repeatModel = repeatModel;
    }

    void checkForSend() {
        repeatModel.setRepeat(Repeat.ONCE);
        repeatModel.setYear(alarm.getDefaultYear());
        RepeatResponseType response = repeatModel.isValid(alarm);
        if (response == RepeatResponseType.TRUE) {
            alarm.addRepeatModel(repeatModel);
            repeatModel.reset();
            getNavigator().send();
        }
        else if (response == RepeatResponseType.FALSE){
            getNavigator().showError();
        }
        else if (response == RepeatResponseType.DUPLICATE)
            getNavigator().showDuplicate();
    }

    public MutableLiveData<Integer> getCurrentTabPager() {
        return currentTabPager;
    }

    public MutableLiveData<Integer> getPageLimitPager() {
        return pageLimitPager;
    }
}
