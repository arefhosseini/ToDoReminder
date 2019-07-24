package com.fearefull.todoreminder.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fearefull.todoreminder.data.local.db.dao.AlarmDao;
import com.fearefull.todoreminder.data.local.db.dao.HistoryDao;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.History;

@Database(entities = {Alarm.class, History.class}, version = 9)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDao alarmDao();
    public abstract HistoryDao historyDao();
}
