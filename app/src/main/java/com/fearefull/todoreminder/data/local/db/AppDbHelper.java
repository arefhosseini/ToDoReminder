package com.fearefull.todoreminder.data.local.db;

import com.fearefull.todoreminder.data.model.db.Alarm;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

@Singleton
public class AppDbHelper implements DbHelper{

    private final AppDatabase appDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Observable<Boolean> insertAlarm(Alarm alarm) {
        return Observable.fromCallable(() -> {
            appDatabase.alarmDao().insert(alarm);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateAlarm(Alarm alarm) {
        return Observable.fromCallable(() -> {
            appDatabase.alarmDao().update(alarm);
            return true;
        });
    }

    @Override
    public Observable<List<Alarm>> getAllAlarms() {
        return Observable.fromCallable(() -> appDatabase.alarmDao().getAllAlarms());
    }

    @Override
    public Observable<List<Alarm>> getAllEnabledAlarms() {
        return Observable.fromCallable(() -> appDatabase.alarmDao().getAllEnabledAlarms());
    }

    @Override
    public Observable<Alarm> getAlarmById(long id) {
        return Observable.fromCallable(() -> appDatabase.alarmDao().getAlarmById(id));
    }

    @Override
    public Observable<Boolean> removeAllAlarms() {
        return Observable.fromCallable(() -> {
           appDatabase.alarmDao().resetTable();
           return true;
        });
    }
}
