package com.fearefull.todoreminder.ui.alarm_notification;

import android.os.Handler;

import androidx.databinding.ObservableField;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.db.Snooze;
import com.fearefull.todoreminder.data.model.other.type.HourType;
import com.fearefull.todoreminder.schedule.AlarmScheduler;
import com.fearefull.todoreminder.ui.base.BaseViewModel;
import com.fearefull.todoreminder.utils.AppConstants;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class AlarmNotificationViewModel extends BaseViewModel<AlarmNotificationNavigator> {
    private final AlarmScheduler alarmScheduler;
    private Alarm alarm;
    private Snooze snooze;
    private final Runnable runnable;
    private final Handler handler;

    private final ObservableField<String> titleString;
    private final ObservableField<Integer> imageRes;
    private final ObservableField<String> timeString;
    private final ObservableField<String> dateString;

    public AlarmNotificationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider,
                                      AlarmScheduler alarmScheduler, Settings settings) {
        super(dataManager, schedulerProvider, settings);
        this.alarmScheduler = alarmScheduler;
        runnable = this::goOff;
        handler = new Handler();
        titleString = new ObservableField<>();
        imageRes = new ObservableField<>();
        timeString = new ObservableField<>();
        dateString = new ObservableField<>();
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
        imageRes.set(alarm.getTitleType().getImageRes());
        if (getSettings().getHourType() == HourType.HALF_HOUR)
            timeString.set(Alarm.getTime12StringByValue(alarm.getNowMinute(), alarm.getNowHour()));
        else
            timeString.set(Alarm.getTime24StringByValue(alarm.getNowMinute(), alarm.getNowHour()));
        dateString.set(alarm.getDateByDayMonthAndMonthByValue(alarm.getNowDay(), alarm.getNowMonth()));

        setCountdown();
        snooze.log();
    }

    private void setCountdown() {
        handler.postDelayed(runnable, AppConstants.COUNT_DOWN_ALARM_TIMER);
    }

    private void cancelCountdown() {
        handler.removeCallbacks(runnable);
    }

    private void goOff() {
        Timber.e("snooze: %s, alarm: %s", snooze.getType().toString(), alarm.getSnoozeType().toString());
        if (snooze.getType() != alarm.getSnoozeType()) {
            snooze.setNextSnooze();
            getDataManager().addSnooze(snooze);
            alarmScheduler.schedule();
            getNavigator().destroy();
        }
        else {
            insertHistory(createHistory(false));
        }
    }

    public void onSnoozeClick() {
        cancelCountdown();
        goOff();
    }

    public void onDismissClick() {
        cancelCountdown();
        insertHistory(createHistory(false));
    }

    public void onConfirmClick() {
        cancelCountdown();
        insertHistory(createHistory(true));
    }

    private History createHistory(boolean isDone) {
        return new History(isDone, alarm.getTitle(), alarm.getTitleType(),
                History.getTimeStampTime(alarm.getNowMinute(), alarm.getNowHour(),
                        alarm.getNowDay(), alarm.getNowMonth(), alarm.getNowYear()));
    }

    private void insertHistory(History history) {
        getCompositeDisposable().add(getDataManager().insertHistory(history)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    getDataManager().deleteSnooze(snooze);
                    alarmScheduler.schedule();
                    getNavigator().destroy();
                }, Timber::e)
        );
    }

    public ObservableField<String> getTitleString() {
        return titleString;
    }

    public ObservableField<Integer> getImageRes() {
        return imageRes;
    }

    public ObservableField<String> getTimeString() {
        return timeString;
    }

    public ObservableField<String> getDateString() {
        return dateString;
    }
}
