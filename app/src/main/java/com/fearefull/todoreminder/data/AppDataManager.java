package com.fearefull.todoreminder.data;


import android.content.Context;

import com.fearefull.todoreminder.data.local.db.DbHelper;
import com.fearefull.todoreminder.data.local.prefs.PreferencesHelper;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDataManager implements DataManager {

    private final Context context;
    private final DbHelper dbHelper;
    private final Gson gson;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, Gson gson,
                          PreferencesHelper preferencesHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.gson = gson;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public int getSchedule() {
        return preferencesHelper.getSchedule();
    }

    @Override
    public void setSchedule(int schedule) {
        preferencesHelper.setSchedule(schedule);
    }

    @Override
    public Observable<Boolean> insertAlarm(Alarm alarm) {
        return dbHelper.insertAlarm(alarm);
    }

    @Override
    public Observable<Boolean> updateAlarm(Alarm alarm) {
        return dbHelper.updateAlarm(alarm);
    }

    @Override
    public Observable<List<Alarm>> getAllAlarms() {
        return dbHelper.getAllAlarms();
    }

    @Override
    public Observable<List<Alarm>> getAllEnabledAlarms() {
        return dbHelper.getAllEnabledAlarms();
    }

    @Override
    public Observable<Boolean> removeAllAlarms() {
        return dbHelper.removeAllAlarms();
    }
}
