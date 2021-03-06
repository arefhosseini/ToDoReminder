package com.fearefull.todoreminder.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.lifecycle.MutableLiveData;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AlarmUtils;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private final AlarmScheduler alarmScheduler;
    private final MutableLiveData<List<Alarm>> alarmItemsLiveData;
    private Alarm deletingAlarm;
    private DialogInterface.OnClickListener deleteAlarmOnClickListener = (dialog, which) -> {
        if (which == AlertDialog.BUTTON_POSITIVE) {
            deleteAlarm();
        }
    };

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider,
                         AlarmScheduler alarmScheduler, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        this.alarmScheduler = alarmScheduler;
        alarmItemsLiveData = new MutableLiveData<>();
        fetchAlarmData();
    }

    private void fetchAlarmData() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAllAlarms()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::sortAlarms, Timber::e)
        );
    }

    private void sortAlarms(List<Alarm> alarmList) {
        getCompositeDisposable().add(AlarmUtils.sortAlarms(alarmList)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(alarms -> {
                    alarmItemsLiveData.setValue(alarms);
                    setIsLoading(false);
                }, Timber::e)
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
        getNavigator().showAlarmManagerFragment(-1);
    }

    void setDeletingAlarm(Alarm deletingAlarm) {
        this.deletingAlarm = deletingAlarm;
    }

    DialogInterface.OnClickListener getDeleteAlarmOnClickListener() {
        return deleteAlarmOnClickListener;
    }

    private void deleteAlarm() {
        getCompositeDisposable().add(getDataManager()
                .deleteHistoriesByAlarm(deletingAlarm)
                .subscribeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(historyResult -> {
                    if (historyResult) {
                        getCompositeDisposable().add(getDataManager().deleteAlarm(deletingAlarm)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(result -> {
                                    if (result && Objects.requireNonNull(alarmItemsLiveData.getValue()).contains(deletingAlarm)) {
                                        fetchAlarmData();
                                        getDataManager().deleteSnoozeByAlarm(deletingAlarm);
                                        deletingAlarm = null;
                                        alarmScheduler.schedule();
                                    }

                                }, Timber::e)
                        );
                    }
                }, Timber::e)
        );
    }

    void updateAlarm(Alarm alarm) {
        getCompositeDisposable().add(getDataManager().updateAlarm(alarm)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result) {
                        fetchAlarmData();
                        getDataManager().deleteSnoozeByAlarm(alarm);
                        alarmScheduler.schedule();
                    }

                }, Timber::e)
        );
    }
}