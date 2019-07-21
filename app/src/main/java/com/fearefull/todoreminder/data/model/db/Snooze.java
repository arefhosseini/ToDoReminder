package com.fearefull.todoreminder.data.model.db;

import com.fearefull.todoreminder.data.model.other.RepeatModel;
import com.fearefull.todoreminder.data.model.other.type.SnoozeType;
import com.google.gson.Gson;

import java.io.Serializable;

import timber.log.Timber;

public class Snooze implements Serializable {
    public static final String SNOOZE_KEY = "SNOOZE_KEY";

    private SnoozeType type;
    private long alarmId;
    private RepeatModel model;

    public Snooze() {
        type = SnoozeType.NONE;
        model = new RepeatModel();
        alarmId = -1;
    }

    public SnoozeType getType() {
        return type;
    }

    public void setType(SnoozeType type) {
        this.type = type;
    }

    public long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(long alarmId) {
        this.alarmId = alarmId;
    }

    public RepeatModel getModel() {
        return model;
    }

    public void setModel(RepeatModel model) {
        this.model = model;
    }

    public void setNextSnooze() {
        if (type == SnoozeType.NONE)
            type = SnoozeType.FIRST;
        else if (type == SnoozeType.FIRST)
            type = SnoozeType.SECOND;
        else if (type == SnoozeType.SECOND)
            type = SnoozeType.THIRD;
    }

    public boolean isSame(Snooze snooze) {
        return alarmId == snooze.getAlarmId() && model.isSame(snooze.getModel());
    }

    public void set(Snooze snooze) {
        type = snooze.getType();
        alarmId = snooze.getAlarmId();
        model = snooze.getModel();
    }

    public static String toJson(Snooze snooze) {
        Gson gson = new Gson();
        return gson.toJson(snooze, Snooze.class);
    }

    public static Snooze jsonToSnooze(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Snooze.class);
    }

    public void log() {
        if (type == SnoozeType.NONE)
            Timber.e("None time snooze");
        else if (type == SnoozeType.FIRST)
            Timber.e("First time snooze");
        else if (type == SnoozeType.SECOND)
            Timber.e("Second time snooze");
        else if (type == SnoozeType.THIRD)
            Timber.e("Third time snooze");
    }

    public boolean isNull() {
        return alarmId == -1;
    }
}
