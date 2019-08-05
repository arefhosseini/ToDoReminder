package com.fearefull.todoreminder.data.local.db;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.History;

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
    public Observable<Boolean> deleteAlarm(Alarm alarm) {
        return Observable.fromCallable(() -> {
            appDatabase.alarmDao().delete(alarm);
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
    public Observable<Boolean> deleteAllAlarms() {
        return Observable.fromCallable(() -> {
           appDatabase.alarmDao().resetTable();
           return true;
        });
    }

    @Override
    public Observable<Boolean> insertHistory(History history) {
        return Observable.fromCallable(() -> {
            appDatabase.historyDao().insert(history);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateHistory(History history) {
        return Observable.fromCallable(() -> {
            appDatabase.historyDao().update(history);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteHistory(History history) {
        return Observable.fromCallable(() -> {
            appDatabase.historyDao().delete(history);
            return true;
        });
    }

    @Override
    public Observable<List<History>> getAllHistories() {
        return Observable.fromCallable(() -> appDatabase.historyDao().getAllHistories());
    }

    @Override
    public Observable<Boolean> updateHistoriesByAlarm(Alarm alarm) {
        return Observable.fromCallable(() -> {
            List<History> historyList = appDatabase.historyDao().getHistoriesByAlarmId(alarm.getId());
            for (History history: historyList) {
                history.setTitle(alarm.getTitle());
                history.setTitleType(alarm.getTitleType());
                appDatabase.historyDao().update(history);
            }
            Timber.e("updateHistoriesByAlarm");
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteHistoriesByAlarm(Alarm alarm) {
        return Observable.fromCallable(() -> {
            appDatabase.historyDao().deleteHistoriesByAlarmId(alarm.getId());
            return true;
        });
    }

    @Override
    public Observable<History> getHistoryById(long id) {
        return Observable.fromCallable(() -> appDatabase.historyDao().getHistoryById(id));
    }

    @Override
    public Observable<Boolean> deleteAllHistories() {
        return Observable.fromCallable(() -> {
            appDatabase.historyDao().resetTable();
            return true;
        });
    }
}
