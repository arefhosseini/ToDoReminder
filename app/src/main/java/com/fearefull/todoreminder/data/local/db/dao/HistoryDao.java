package com.fearefull.todoreminder.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.History;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    public void insert(History... histories);

    @Update
    public void update(History... histories);

    @Delete
    public void delete(History history);

    @Query("SELECT * FROM histories ORDER BY time DESC")
    public List<History> getAllHistories();

    @Query("SELECT * FROM histories WHERE id = :id")
    public History getHistoryById(Long id);

    @Query("DELETE FROM histories")
    public void resetTable();
}
