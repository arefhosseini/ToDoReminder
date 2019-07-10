package com.fearefull.todoreminder.ui.alarm_manager;

import com.fearefull.todoreminder.data.model.other.Alarm;

public interface AlarmManagerCallBack {
    void onUpdateAlarm(Alarm alarm, String fragmentTag);
}
