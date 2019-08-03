package com.fearefull.todoreminder.ui.alarm_manager.repeat_manager;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.other.item.RepeatManagerItem;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.Objects;

public class RepeatManagerViewModel extends BaseViewModel<RepeatManagerNavigator> {
    private Alarm alarm;
    private final MutableLiveData<List<RepeatManagerItem>> repeatManagerItemsLiveData;

    public RepeatManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        repeatManagerItemsLiveData = new MutableLiveData<>();
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
        fetchData();
    }

    private void fetchData() {
        getCompositeDisposable().add(alarm.getRepeatManagerItemList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(repeatManagerItemsLiveData::setValue, throwable -> {

                })
        );
    }

    public MutableLiveData<List<RepeatManagerItem>> getRepeatManagerItemsLiveData() {
        return repeatManagerItemsLiveData;
    }

    void removeRepeatManagerItem(RepeatManagerItem item) {
        if (Objects.requireNonNull(repeatManagerItemsLiveData.getValue()).contains(item)) {
            alarm.removeRepeatManagerData(repeatManagerItemsLiveData.getValue().indexOf(item));
            repeatManagerItemsLiveData.getValue().remove(item);
            repeatManagerItemsLiveData.setValue(repeatManagerItemsLiveData.getValue());
        }
        if (repeatManagerItemsLiveData.getValue().isEmpty())
            getNavigator().finish();
    }
}
