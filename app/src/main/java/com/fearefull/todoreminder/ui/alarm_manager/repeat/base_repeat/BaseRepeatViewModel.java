package com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.RepeatModel;
import com.fearefull.todoreminder.data.model.other.type.RepeatResponseType;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import timber.log.Timber;

public abstract class BaseRepeatViewModel<W extends BaseRepeatNavigator> extends BaseViewModel<W> {
    private Alarm alarm;
    private RepeatModel repeatModel;
    private final MutableLiveData<Integer> currentTabPager;
    private final MutableLiveData<Integer> pageLimitPager;

    public BaseRepeatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        repeatModel = new RepeatModel();
        currentTabPager = new MutableLiveData<>();
        pageLimitPager = new MutableLiveData<>();
        setRepeatModel(new RepeatModel());
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
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

    public void checkForSend(Repeat repeat) {
        getRepeatModel().setRepeat(repeat);
        if (repeat == Repeat.ONCE)
            getRepeatModel().setYear(getAlarm().getDefaultYear());
        RepeatResponseType response = getRepeatModel().isValid(getAlarm());
        //Timber.e("type: %s, min: %d, hour: %d, day: %d, month: %d", getRepeatModel().getRepeat().getText(), getRepeatModel().getMinute(), getRepeatModel().getHour(), getRepeatModel().getDayMonth(), getRepeatModel().getMonth());
        if (response == RepeatResponseType.TRUE) {
            getAlarm().addRepeatModel(getRepeatModel());
            getRepeatModel().reset();
            getNavigator().send();
        }
        else if (response == RepeatResponseType.FALSE){
            getRepeatModel().reset();
            getNavigator().showError();
        }
        else if (response == RepeatResponseType.DUPLICATE) {
            getRepeatModel().reset();
            getNavigator().send();
            //getNavigator().showDuplicate();
        }
    }

    public MutableLiveData<Integer> getCurrentTabPager() {
        return currentTabPager;
    }

    public MutableLiveData<Integer> getPageLimitPager() {
        return pageLimitPager;
    }
}
