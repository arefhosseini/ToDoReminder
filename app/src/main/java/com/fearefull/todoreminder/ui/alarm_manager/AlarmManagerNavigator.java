package com.fearefull.todoreminder.ui.alarm_manager;

interface AlarmManagerNavigator {
    void goBack();
    void save();
    void openCustomRepeatPickerFragment();
    void closeAllExpansions();
    void shakeBell();
    void createWithShakeBell();
    void clearBell();
    void onShowRepeatManagerDialog();
}
