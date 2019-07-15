package com.fearefull.todoreminder.data.local.db;

import com.fearefull.todoreminder.data.model.db.AlarmModel;
import com.fearefull.todoreminder.data.model.db.User;
import com.fearefull.todoreminder.data.model.other.Alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper{

    private final AppDatabase appDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return Observable.fromCallable(() -> {
            appDatabase.userDao().insert(user);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAlarm(AlarmModel alarmModel) {
        return Observable.fromCallable(() -> {
            appDatabase.alarmDao().insert(alarmModel);
            return true;
        });
    }

    @Override
    public Observable<List<Alarm>> getAllAlarms() {
        return Observable.fromCallable(() -> {
            List<AlarmModel> alarmModelList = appDatabase.alarmDao().getAllAlarms();
            List<Alarm> alarmList = new ArrayList<>();
            for (AlarmModel alarmModel: alarmModelList) {
                Alarm alarm = new Alarm();
                alarmList.add(alarm);
            }
            return alarmList;
        });
    }

    @Override
    public Observable<Boolean> removeAllAlarms() {
        return Observable.fromCallable(() -> {
           appDatabase.alarmDao().resetTable();
           return true;
        });
    }
}
