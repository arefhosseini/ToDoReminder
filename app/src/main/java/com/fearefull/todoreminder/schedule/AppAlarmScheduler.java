package com.fearefull.todoreminder.schedule;

import android.content.Context;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.db.Snooze;
import com.fearefull.todoreminder.data.model.other.RepeatModel;
import com.fearefull.todoreminder.data.model.other.persian_date.PersianDate;
import com.fearefull.todoreminder.data.model.other.persian_date.PersianDateFormat;
import com.fearefull.todoreminder.utils.AppConstants;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@Singleton
public class AppAlarmScheduler implements AlarmScheduler {
    private final Context context;
    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public AppAlarmScheduler(Context context, DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        this.context = context;
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void schedule() {
        cancelSchedule();

        compositeDisposable.add(dataManager.getAllEnabledAlarms()
                .subscribeOn(schedulerProvider.io())
                .subscribe(this::scheduleClosestAlarm, Timber::e)
        );
    }

    private void scheduleClosestAlarm(List<Alarm> alarms) {
        Alarm closestAlarm;
        Alarm currentAlarm = new Alarm("");
        RepeatModel closestRepeatModel;
        Snooze snooze = new Snooze();
        long closestTime = Long.MAX_VALUE;
        long currentTime = System.currentTimeMillis();
        long checkTime = 0;

        for (Alarm alarm: alarms) {
            for (int index = 0; index < alarm.getRepeatCount(); index++) {
                if (alarm.getRepeat(index) == Repeat.ONCE)
                    checkTime = scheduleOnceRepeat(alarm.getRepeatModel(index), currentTime);
                else if (alarm.getRepeat(index) == Repeat.DAILY)
                    checkTime = scheduleDailyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                if (checkTime > 1000 && checkTime < closestTime) {
                    closestTime = checkTime;
                    closestRepeatModel = alarm.getRepeatModel(index);
                    closestAlarm = alarm;
                    snooze.setAlarmId(closestAlarm.getId());
                    snooze.setModel(closestRepeatModel);
                }
            }
        }

        List<Snooze> snoozeList = dataManager.getAllSnoozes();
        if (snoozeList.size() > 0) {
            for (Snooze s: snoozeList) {
                checkTime = AppConstants.SNOOZE_TIMER;
                if (checkTime < closestTime) {
                    closestTime = checkTime;
                    snooze = s;
                }
            }
        }

        if (!snooze.isNull()) {
            PersistableBundleCompat extras = new PersistableBundleCompat();

            extras.putString(Snooze.SNOOZE_KEY, Snooze.toJson(snooze));
            int scheduleId = DemoSyncJob.scheduleJob(closestTime, extras);
            dataManager.setSchedule(scheduleId);
            Timber.e("Starting time: %s", closestTime);
        }
    }

    private long scheduleOnceRepeat(RepeatModel repeatModel, long currentTime) {
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(repeatModel.getDayMonth());
        checkDate.setShMonth(repeatModel.getMonth());
        checkDate.setShYear(repeatModel.getYear());
        PersianDateFormat format = new PersianDateFormat();
        Timber.i(format.format(checkDate));
        return checkDate.getTime() - currentTime;
    }

    private long scheduleDailyRepeat(RepeatModel repeatModel, long currentTime, Alarm currentAlarm) {
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(currentAlarm.getNowDay());
        checkDate.setShMonth(currentAlarm.getNowMonth());
        checkDate.setShYear(currentAlarm.getNowYear());
        PersianDateFormat format = new PersianDateFormat();

        boolean isFindNearTime = false;
        long checkTime = -1;
        while (!isFindNearTime) {
            checkTime = checkDate.getTime() - currentTime;
            if (checkTime > 1000)
                isFindNearTime = true;
            else {
                checkDate.addDay(1);
                checkDate.setMinute(repeatModel.getMinute());
                checkDate.setHour(repeatModel.getHour());
            }
        }
        Timber.i(format.format(checkDate));
        return checkTime;
    }

    private void cancelSchedule() {
        int scheduleId = dataManager.getSchedule();
        if (scheduleId != -1) {
            JobManager.instance().cancel(scheduleId);
            dataManager.setSchedule(-1);
        }
    }
}
