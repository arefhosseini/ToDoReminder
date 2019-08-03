package com.fearefull.todoreminder.data;


import android.content.Context;

import com.fearefull.todoreminder.data.local.db.DbHelper;
import com.fearefull.todoreminder.data.local.prefs.PreferencesHelper;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.History;
import com.fearefull.todoreminder.data.model.db.Settings;
import com.fearefull.todoreminder.data.model.db.Snooze;
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
    public List<Snooze> getAllSnoozes() {
        return preferencesHelper.getAllSnoozes();
    }

    @Override
    public void addSnooze(Snooze snooze) {
        preferencesHelper.addSnooze(snooze);
    }

    @Override
    public void deleteSnooze(Snooze snooze) {
        preferencesHelper.deleteSnooze(snooze);
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
    public Observable<Boolean> deleteAlarm(Alarm alarm) {
        return dbHelper.deleteAlarm(alarm);
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
    public Observable<Alarm> getAlarmById(long id) {
        return dbHelper.getAlarmById(id);
    }

    @Override
    public Observable<Boolean> deleteAllAlarms() {
        return dbHelper.deleteAllAlarms();
    }

    @Override
    public Observable<Boolean> insertHistory(History history) {
        return dbHelper.insertHistory(history);
    }

    @Override
    public Observable<Boolean> updateHistory(History history) {
        return dbHelper.updateHistory(history);
    }

    @Override
    public Observable<Boolean> deleteHistory(History history) {
        return dbHelper.deleteHistory(history);
    }

    @Override
    public Observable<List<History>> getAllHistories() {
        return dbHelper.getAllHistories();
    }

    @Override
    public Observable<History> getHistoryById(long id) {
        return dbHelper.getHistoryById(id);
    }

    @Override
    public Observable<Boolean> deleteAllHistories() {
        return dbHelper.deleteAllHistories();
    }

    @Override
    public Snooze getSnoozeByAlarm(Alarm alarm) {
        return preferencesHelper.getSnoozeByAlarm(alarm);
    }

    @Override
    public void deleteSnoozeByAlarm(Alarm alarm) {
        preferencesHelper.deleteSnoozeByAlarm(alarm);
    }

    @Override
    public Settings getSettings() {
        return preferencesHelper.getSettings();
    }

    @Override
    public void setSettings(Settings settings) {
        preferencesHelper.setSettings(settings);
    }
}
