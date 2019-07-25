package com.fearefull.todoreminder.data.local.db;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.History;

import java.util.List;

import io.reactivex.Observable;


public interface DbHelper {
    Observable<Boolean> insertAlarm(final Alarm alarm);
    Observable<Boolean> updateAlarm(final Alarm alarm);
    Observable<Boolean> deleteAlarm(final Alarm alarm);
    Observable<List<Alarm>> getAllAlarms();
    Observable<List<Alarm>> getAllEnabledAlarms();
    Observable<Alarm> getAlarmById(long id);
    Observable<Boolean> deleteAllAlarms();

    Observable<Boolean> insertHistory(final History history);
    Observable<Boolean> updateHistory(final History history);
    Observable<Boolean> deleteHistory(final History history);
    Observable<List<History>> getAllHistories();
    Observable<History> getHistoryById(long id);
    Observable<Boolean> deleteAllHistories();

}
