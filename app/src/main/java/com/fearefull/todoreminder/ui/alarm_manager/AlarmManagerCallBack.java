package com.fearefull.todoreminder.ui.alarm_manager;

import com.fearefull.todoreminder.data.model.other.MyDate;
import com.fearefull.todoreminder.data.model.other.MyTime;

public interface AlarmManagerCallBack {
    void onGetTime(MyTime myTime);
    void onGetDate(MyDate myDate);
}
