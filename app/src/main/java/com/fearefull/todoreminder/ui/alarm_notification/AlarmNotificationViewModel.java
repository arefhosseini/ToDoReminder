package com.fearefull.todoreminder.ui.alarm_notification;

import android.os.Handler;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Snooze;
import com.fearefull.todoreminder.data.model.other.type.SnoozeType;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AppConstants;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

public class AlarmNotificationViewModel extends BaseViewModel<AlarmNotificationNavigator> {
    private final AlarmScheduler alarmScheduler;
    private Alarm alarm;
    private Snooze snooze;
    private final Runnable runnable;
    private final Handler handler;

    public AlarmNotificationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AlarmScheduler alarmScheduler) {
        super(dataManager, schedulerProvider);
        this.alarmScheduler = alarmScheduler;
        runnable = this::goOff;
        handler = new Handler();
    }


    Alarm getAlarm() {
        return alarm;
    }

    public Snooze getSnooze() {
        return snooze;
    }

    void init(Snooze snooze) {
        this.snooze = snooze;
        getCompositeDisposable().add(getDataManager()
                .getAlarmById(snooze.getAlarmId())
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(
                alarm -> this.alarm = alarm,
                throwable -> {})
        );

        setCountdown();
        snooze.log();
    }

    private void setCountdown() {
        handler.postDelayed(runnable, AppConstants.COUTNT_DOWN_ALARM_TIMER);
    }

    void cancelCountdown() {
        handler.removeCallbacks(runnable);
    }

    void goOff() {
        if (snooze.getType() != SnoozeType.THIRD) {
            snooze.setNextSnooze();
            getDataManager().addSnooze(snooze);
        }
        else {
            getDataManager().removeSnooze(snooze);
        }
        alarmScheduler.schedule();
        getNavigator().destroy();
    }


}
