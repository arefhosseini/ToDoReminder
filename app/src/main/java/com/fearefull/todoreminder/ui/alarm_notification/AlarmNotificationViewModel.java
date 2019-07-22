package com.fearefull.todoreminder.ui.alarm_notification;

import android.os.Handler;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Snooze;
import com.fearefull.todoreminder.data.model.other.type.DayMonthType;
import com.fearefull.todoreminder.data.model.other.type.MonthType;
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

    private final ObservableField<String> titleString;
    private final ObservableField<String> minuteString;
    private final ObservableField<String> hourString;
    private final ObservableField<String> dayString;
    private final ObservableField<String> monthString;

    public AlarmNotificationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AlarmScheduler alarmScheduler) {
        super(dataManager, schedulerProvider);
        this.alarmScheduler = alarmScheduler;
        runnable = this::goOff;
        handler = new Handler();
        titleString = new ObservableField<>();
        minuteString = new ObservableField<>();
        hourString = new ObservableField<>();
        dayString = new ObservableField<>();
        monthString = new ObservableField<>();
    }

    void init(Snooze snooze) {
        this.snooze = snooze;
        getCompositeDisposable().add(getDataManager()
                .getAlarmById(snooze.getAlarmId())
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(
                alarm -> {
                    this.alarm = alarm;
                    setTime();
                },
                throwable -> {})
        );
    }

    private void setTime() {
        this.alarm.setNowTime();
        titleString.set(alarm.getTitle());
        minuteString.set(String.valueOf(alarm.getNowMinute()));
        hourString.set(String.valueOf(alarm.getNowHour()));
        dayString.set(String.valueOf(DayMonthType.getDayMonthTypeByValue(alarm.getNowDay()).getValue()));
        monthString.set(MonthType.getMonthType(alarm.getNowMonth()).getText());

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

    public void onDismissClick() {
        cancelCountdown();
        goOff();
    }

    public void onConfirmClick() {
        cancelCountdown();
        getDataManager().removeSnooze(snooze);
        alarmScheduler.schedule();
        getNavigator().destroy();
    }

    public ObservableField<String> getTitleString() {
        return titleString;
    }

    public ObservableField<String> getMinuteString() {
        return minuteString;
    }

    public ObservableField<String> getHourString() {
        return hourString;
    }

    public ObservableField<String> getDayString() {
        return dayString;
    }

    public ObservableField<String> getMonthString() {
        return monthString;
    }
}
