package com.fearefull.todoreminder.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fearefull.todoreminder.data.model.db.User;

@Dao
public interface UserDao {
    @Insert
    public void insert(User... users);

    @Update
    public void update(User... users);

    @Delete
    public void delete(User user);

    @Query("SELECT * FROM users WHERE id = :id")
    public User getUserById(Long id);

    @Query("SELECT * FROM users WHERE username = :username")
    public User getUserByUsername(String username);

    @Query("SELECT COUNT(*) from users")
    public int countUsers();

    @Query("DELETE FROM users")
    public void resetTable();
}
