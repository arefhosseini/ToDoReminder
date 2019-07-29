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
        Alarm currentAlarm = new Alarm();
        RepeatModel closestRepeatModel;
        Snooze selectedSnooze = new Snooze();
        long closestTime = Long.MAX_VALUE;
        long currentTime = System.currentTimeMillis();
        long checkTime = 0;

        for (Alarm alarm: alarms) {
            for (int index = 0; index < alarm.getRepeatCount(); index++) {
                if (alarm.getRepeat(index) == Repeat.ONCE)
                    checkTime = scheduleOnceRepeat(alarm.getRepeatModel(index), currentTime);
                else if (alarm.getRepeat(index) == Repeat.DAILY)
                    checkTime = scheduleDailyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                else if (alarm.getRepeat(index) == Repeat.WEEKLY)
                    checkTime = scheduleWeeklyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                else if (alarm.getRepeat(index) == Repeat.MONTHLY)
                    checkTime = scheduleMonthlyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                else if (alarm.getRepeat(index) == Repeat.YEARLY)
                    checkTime = scheduleYearlyRepeat(alarm.getRepeatModel(index), currentTime, currentAlarm);
                if (checkTime > 1000 && checkTime < closestTime) {
                    closestTime = checkTime;
                    closestRepeatModel = alarm.getRepeatModel(index);
                    closestAlarm = alarm;
                    selectedSnooze.setAlarmId(closestAlarm.getId());
                    selectedSnooze.setModel(closestRepeatModel);
                }
            }
        }

        List<Snooze> snoozeList = dataManager.getAllSnoozes();
        if (snoozeList.size() > 0) {
            for (Snooze snooze: snoozeList) {
                for (Alarm alarm: alarms) {
                    if (snooze.getAlarmId() == alarm.getId()) {
                        if (alarm.getSnoozeDelay() < closestTime) {
                            closestTime = alarm.getSnoozeDelay();
                            selectedSnooze = snooze;
                        }
                        break;
                    }
                }
            }
        }

        if (!selectedSnooze.isNull()) {
            PersistableBundleCompat extras = new PersistableBundleCompat();
            extras.putString(Snooze.SNOOZE_KEY, Snooze.toJson(selectedSnooze));
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

    private long scheduleWeeklyRepeat(RepeatModel repeatModel, long currentTime, Alarm currentAlarm) {
        PersianDate nowDate;
        PersianDate bestTime = null;
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(currentAlarm.getNowDay());
        checkDate.setShMonth(currentAlarm.getNowMonth());
        checkDate.setShYear(currentAlarm.getNowYear());
        PersianDateFormat format = new PersianDateFormat();

        boolean isFindNearTime = false;
        long minTime = -1;
        long checkTime;
        while (!isFindNearTime) {
            minTime = Long.MAX_VALUE;
            for (int dayWeek: repeatModel.getDaysWeek()) {
                int dayWeekIndex = Alarm.dayWeekToIndex(dayWeek);
                nowDate = new PersianDate(checkDate.getTime());
                Timber.e("dayOfWeek: %d, dayWeek: %d", nowDate.dayOfWeek(), dayWeekIndex);
                checkTime = nowDate.getTime() - currentTime;
                if (nowDate.dayOfWeek() == dayWeekIndex && checkTime > 1000 && checkTime < minTime) {
                    minTime = checkTime;
                    bestTime = new PersianDate(checkDate.getTime());
                }
                else if (nowDate.dayOfWeek() < dayWeekIndex){
                    nowDate.addDay(dayWeekIndex - nowDate.dayOfWeek());
                    checkTime = nowDate.getTime() - currentTime;
                    if (nowDate.dayOfWeek() == dayWeekIndex && checkTime > 1000 && checkTime < minTime) {
                        minTime = checkTime;
                        bestTime = new PersianDate(checkDate.getTime());
                    }
                }
            }
            if (minTime < Long.MAX_VALUE)
                isFindNearTime = true;
            else {
                checkDate.addDay(7 - checkDate.dayOfWeek() + Alarm.indexToDayWeek(repeatModel.getDaysWeek().get(0)));
            }
        }
        Timber.i(format.format(bestTime));
        return minTime;
    }

    private long scheduleMonthlyRepeat(RepeatModel repeatModel, long currentTime, Alarm currentAlarm) {
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(repeatModel.getDayMonth());
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
                checkDate.addMonth(1);
                checkDate.setMinute(repeatModel.getMinute());
                checkDate.setHour(repeatModel.getHour());
            }
        }
        Timber.i(format.format(checkDate));
        return checkTime;
    }

    private long scheduleYearlyRepeat(RepeatModel repeatModel, long currentTime, Alarm currentAlarm) {
        PersianDate checkDate = new PersianDate();
        checkDate.setSecond(0);
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(repeatModel.getDayMonth());
        checkDate.setShMonth(repeatModel.getMonth());
        checkDate.setShYear(currentAlarm.getNowYear());
        PersianDateFormat format = new PersianDateFormat();

        boolean isFindNearTime = false;
        long checkTime = -1;
        while (!isFindNearTime) {
            checkTime = checkDate.getTime() - currentTime;
            if (checkTime > 1000)
                isFindNearTime = true;
            else {
                checkDate.addYear(1);
                checkDate.setMinute(repeatModel.getMinute());
                checkDate.setHour(repeatModel.getHour());
                checkDate.setShDay(repeatModel.getDayMonth());
                checkDate.setShMonth(repeatModel.getMonth());
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
