package com.fearefull.todoreminder.ui.main;

public interface MainNavigator {
    void handleError(Throwable throwable);
    void openLoginActivity();
    void openAlarmManager();
}
