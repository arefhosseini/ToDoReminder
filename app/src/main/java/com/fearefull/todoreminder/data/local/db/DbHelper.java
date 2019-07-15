package com.fearefull.todoreminder.data.local.db;

import com.fearefull.todoreminder.data.model.db.AlarmModel;
import com.fearefull.todoreminder.data.model.db.User;
import com.fearefull.todoreminder.data.model.other.Alarm;

import java.util.List;

import io.reactivex.Observable;


public interface DbHelper {
    Observable<Boolean> insertUser(final User user);
    Observable<Boolean> insertAlarm(final AlarmModel alarmModel);
    Observable<List<Alarm>> getAllAlarms();
    Observable<Boolean> removeAllAlarms();
}
