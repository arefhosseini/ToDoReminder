package com.fearefull.todoreminder.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fearefull.todoreminder.data.local.db.dao.AlarmDao;
import com.fearefull.todoreminder.data.model.db.Alarm;

@Database(entities = {Alarm.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDao alarmDao();
}
