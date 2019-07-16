package com.fearefull.todoreminder.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private final MutableLiveData<List<Alarm>> alarmItemsLiveData;

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        alarmItemsLiveData = new MutableLiveData<>();
        fetchAlarmData();
    }

    private void fetchAlarmData() {
        getCompositeDisposable().add(getDataManager()
                .getAllAlarms()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(alarmItemsLiveData::setValue, throwable -> {

                })
        );
    }

    public MutableLiveData<List<Alarm>> getAlarmItemsLiveData() {
        return alarmItemsLiveData;
    }

    void reloadAlarmData() {
        fetchAlarmData();
    }
}