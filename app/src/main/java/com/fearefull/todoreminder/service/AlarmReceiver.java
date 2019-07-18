package com.fearefull.todoreminder.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.fearefull.todoreminder.data.DataManager;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationActivity;
import com.fearefull.todoreminder.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class AlarmReceiver extends BroadcastReceiver
{
    private final CompositeDisposable compositeDisposable;
    @Inject
    DataManager dataManager;
    @Inject
    SchedulerProvider schedulerProvider;

    public AlarmReceiver() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        AndroidInjection.inject(this, context);

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire(10*60*1000L /*10 minutes*/);

        context.startActivity(AlarmNotificationActivity.newIntent(context));
        compositeDisposable.add(dataManager.getAllAlarms()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        alarms -> {
                            Timber.e(alarms.get(0).getNote());
                        },
                        throwable -> {}
                )
        );

        wl.release();
    }

    public void setAlarm(Context context)
    {
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 60, pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}