package com.fearefull.todoreminder.ui.alarm_manager;

import com.fearefull.todoreminder.data.model.db.Alarm;

public interface RepeatCallBack {
    void onAlarmChange(Alarm alarm);
}
