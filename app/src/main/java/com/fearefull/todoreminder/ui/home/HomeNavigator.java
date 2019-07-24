package com.fearefull.todoreminder.ui.home;

import com.fearefull.todoreminder.data.model.db.Alarm;

public interface HomeNavigator {
    void showAlarmManagerFragment(Alarm alarm);
}
