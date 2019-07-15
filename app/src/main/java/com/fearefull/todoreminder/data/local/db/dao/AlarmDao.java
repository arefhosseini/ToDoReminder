package com.fearefull.todoreminder.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fearefull.todoreminder.data.model.db.AlarmModel;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    public void insert(AlarmModel... alarmModels);

    @Update
    public void update(AlarmModel... alarmModels);

    @Delete
    public void delete(AlarmModel alarmModels);

    @Query("SELECT * FROM alarms")
    public List<AlarmModel> getAllAlarms();

    @Query("SELECT * FROM alarms WHERE id = :id")
    public AlarmModel getAlarmById(Long id);

    @Query("DELETE FROM alarms")
    public void resetTable();
}
