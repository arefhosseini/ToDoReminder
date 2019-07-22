package com.fearefull.todoreminder.ui.alarm_manager.repeat.base_repeat;

public interface BaseRepeatNavigator {
    void timePickerClick();
    void onAddRepeat();
    void send();
    void showError();
    void showDuplicate();
}
