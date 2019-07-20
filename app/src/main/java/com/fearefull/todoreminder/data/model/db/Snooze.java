package com.fearefull.todoreminder.data.model.db;

import com.fearefull.todoreminder.data.model.other.type.SnoozeType;

import java.io.Serializable;

public class Snooze implements Serializable {
    private SnoozeType type;
    private Alarm alarm;

    public Snooze(SnoozeType type, Alarm alarm) {
        this.type = type;
        this.alarm = alarm;
    }

    public SnoozeType getType() {
        return type;
    }

    public void setType(SnoozeType type) {
        this.type = type;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}
