package com.fearefull.todoreminder.data.model.db;

public enum LoggedInMode {

    LOGGED_OUT(0),
    LOGGED_IN(1);

    private final int mType;

    LoggedInMode(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}