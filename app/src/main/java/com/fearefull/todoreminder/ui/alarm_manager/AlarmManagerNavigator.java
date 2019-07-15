package com.fearefull.todoreminder.ui.alarm_manager;

interface AlarmManagerNavigator extends AlarmManagerCallBack{
    void goBack();
    void openDatePickerFragment();
    void openRepeatPickerFragment();
    void openCustomRepeatPickerFragment();
    void openEveryCustomRepeatPickerFragment();
    void openOnCustomRepeatPickerFragment();
    void closeAllExpansions();
}
