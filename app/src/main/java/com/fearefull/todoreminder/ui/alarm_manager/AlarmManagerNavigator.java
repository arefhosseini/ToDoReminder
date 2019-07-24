package com.fearefull.todoreminder.ui.alarm_manager;

import com.fearefull.todoreminder.data.model.db.Repeat;

interface AlarmManagerNavigator {
    void goBack();
    void save();
    void openCustomRepeatPickerFragment();
    void closeAllExpansions();
    void shakeBell();
    void createWithShakeBell();
    void clearBell();
    void onShowRepeatManagerDialog();
    void getLastRepeat(Repeat repeat);
}
