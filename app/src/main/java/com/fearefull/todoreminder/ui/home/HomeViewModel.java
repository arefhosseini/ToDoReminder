package com.fearefull.todoreminder.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

import timber.log.Timber;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private final AlarmScheduler alarmScheduler;
    private final MutableLiveData<List<Alarm>> alarmItemsLiveData;

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AlarmScheduler alarmScheduler) {
        super(dataManager, schedulerProvider);
        this.alarmScheduler = alarmScheduler;
        alarmItemsLiveData = new MutableLiveData<>();
        fetchAlarmData();
    }

    private void fetchAlarmData() {
        getCompositeDisposable().add(getDataManager()
                .getAllAlarms()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(alarmItemsLiveData::setValue, Timber::e)
        );
    }

    public MutableLiveData<List<Alarm>> getAlarmItemsLiveData() {
        return alarmItemsLiveData;
    }

    void reloadAlarmData() {
        fetchAlarmData();
        alarmScheduler.schedule();
    }

    public void onOpenAlarmManager() {
        getNavigator().showAlarmManagerFragment(new Alarm("-"));
    }
}