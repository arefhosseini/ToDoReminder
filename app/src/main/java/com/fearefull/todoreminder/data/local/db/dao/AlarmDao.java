package com.fearefull.todoreminder.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fearefull.todoreminder.data.model.db.Alarm;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    public void insert(Alarm... alarms);

    @Update
    public void update(Alarm... alarms);

    @Delete
    public void delete(Alarm alarm);

    @Query("SELECT * FROM alarms")
    public List<Alarm> getAllAlarms();

    @Query("SELECT * FROM alarms WHERE is_enable = 1")
    public List<Alarm> getAllEnabledAlarms();

    @Query("SELECT * FROM alarms WHERE id = :id")
    public Alarm getAlarmById(Long id);

    @Query("DELETE FROM alarms")
    public void resetTable();
}
