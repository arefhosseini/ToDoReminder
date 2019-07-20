package com.fearefull.todoreminder.schedule;

import android.content.Context;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Repeat;
import com.fearefull.todoreminder.data.model.other.RepeatModel;
import com.fearefull.todoreminder.data.model.other.persian_date.PersianDate;
import com.fearefull.todoreminder.data.model.other.persian_date.PersianDateFormat;
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
    public AppAlarmScheduler(Context context, DataManager dataManager, SchedulerProvider schedulerProvider) {
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

        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 22);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long triggerTime = calendar.getTimeInMillis() - System.currentTimeMillis();
        Timber.i("triggerTime %d", triggerTime);*/
    }

    private void scheduleClosestAlarm(List<Alarm> alarms) {
        Alarm closestAlarm = null;
        long closestTime = Long.MAX_VALUE;
        long currentTime = System.currentTimeMillis();
        long checkTime = 0;

        for (Alarm alarm: alarms) {
            for (int index = 0; index < alarm.getRepeatCount(); index++) {
                if (alarm.getRepeat(index) == Repeat.ONCE)
                    checkTime = scheduleOnceRepeat(alarm.getRepeatModel(index), currentTime);
                    if (checkTime > 1000 && checkTime < closestTime) {
                        closestTime = checkTime;
                        closestAlarm = alarm;
                    }
            }
        }

        if (closestAlarm != null) {
            PersistableBundleCompat extras = new PersistableBundleCompat();
            extras.putString("key", "Hello world");
            int scheduleId = DemoSyncJob.scheduleJob(closestTime, extras);
            dataManager.setSchedule(scheduleId);
            Timber.e("Starting time: %s", closestTime);
        }
    }

    private long scheduleOnceRepeat(RepeatModel repeatModel, long currentTime) {
        PersianDate checkDate = new PersianDate();
        checkDate.setMinute(repeatModel.getMinute());
        checkDate.setHour(repeatModel.getHour());
        checkDate.setShDay(repeatModel.getDayMonth());
        checkDate.setShMonth(repeatModel.getMonth());
        checkDate.setShYear(repeatModel.getYear());
        checkDate.setSecond(0);
        PersianDateFormat format = new PersianDateFormat();
        Timber.i(format.format(checkDate));
        long triggerTime = checkDate.getTime() - currentTime;
        Timber.i("triggerTime %d", triggerTime);
        return triggerTime;
    }

    private void cancelSchedule() {
        int scheduleId = dataManager.getSchedule();
        if (scheduleId != -1) {
            JobManager.instance().cancel(scheduleId);
            dataManager.setSchedule(-1);
        }
    }
}
