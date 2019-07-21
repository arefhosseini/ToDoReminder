package com.fearefull.todoreminder.data.local.prefs;

import com.fearefull.todoreminder.data.model.db.Snooze;

import java.util.List;

public interface PreferencesHelper {
    int getSchedule();
    void setSchedule(int schedule);
    List<Snooze> getAllSnoozes();
    void addSnooze(Snooze snooze);
    void removeSnooze(Snooze snooze);
    Snooze getSnoozeByAlarmId(long alarmId);
}