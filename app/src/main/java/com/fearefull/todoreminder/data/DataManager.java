package com.fearefull.todoreminder.data;

import com.fearefull.todoreminder.data.local.db.DbHelper;
import com.fearefull.todoreminder.data.local.prefs.PreferencesHelper;
import com.fearefull.todoreminder.data.model.db.Alarm;
import com.fearefull.todoreminder.data.model.db.Snooze;


public interface DataManager extends DbHelper, PreferencesHelper {
    Snooze getSnoozeByAlarm(Alarm alarm);
}
