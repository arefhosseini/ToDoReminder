package com.fearefull.todoreminder.data.local.db;

import com.fearefull.todoreminder.data.model.db.User;

import io.reactivex.Observable;


public interface DbHelper {
    Observable<Boolean> insertUser(final User user);
}
