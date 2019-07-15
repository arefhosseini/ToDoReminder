package com.fearefull.todoreminder.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fearefull.todoreminder.data.local.db.dao.AlarmDao;
import com.fearefull.todoreminder.data.local.db.dao.UserDao;
import com.fearefull.todoreminder.data.model.db.AlarmModel;
import com.fearefull.todoreminder.data.model.db.User;

@Database(entities = {User.class, AlarmModel.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract AlarmDao alarmDao();
}
