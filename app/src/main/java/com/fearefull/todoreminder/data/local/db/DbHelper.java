package com.fearefull.todoreminder.data.local.db;

import com.fearefull.todoreminder.data.model.db.Alarm;

import java.util.List;

import io.reactivex.Observable;


public interface DbHelper {
    Observable<Boolean> insertAlarm(final Alarm alarm);
    Observable<List<Alarm>> getAllAlarms();
    Observable<Boolean> removeAllAlarms();
}
