package com.fearefull.todoreminder.ui.alarm_manager;


import com.fearefull.todoreminder.data.model.db.Alarm;

public interface AlarmManagerCallBack {
    void onUpdateAlarm(Alarm alarm, String fragmentTag);
    void onShowRepeatManagerDialog();
}
