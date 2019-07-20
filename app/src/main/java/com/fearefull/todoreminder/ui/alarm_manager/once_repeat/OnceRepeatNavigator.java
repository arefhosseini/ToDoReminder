package com.fearefull.todoreminder.ui.alarm_manager.once_repeat;

public interface OnceRepeatNavigator {
    void timePickerClick();
    void datePickerClick();
    void onAddRepeat();
    void send();
    void showError();
    void showDuplicate();
}
