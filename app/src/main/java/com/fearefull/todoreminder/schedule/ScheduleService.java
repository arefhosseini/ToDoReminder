package com.fearefull.todoreminder.schedule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ScheduleService extends Service
{
    @Inject
    AlarmScheduler alarmScheduler;

    public void onCreate()
    {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarmScheduler.schedule();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
