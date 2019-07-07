package com.fearefull.todoreminder.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fearefull.todoreminder.data.local.db.dao.UserDao;
import com.fearefull.todoreminder.data.model.db.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
