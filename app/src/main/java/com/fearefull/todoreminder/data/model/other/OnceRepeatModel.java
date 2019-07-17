package com.fearefull.todoreminder.data.model.other;

import timber.log.Timber;

public class OnceRepeatModel {
    private int minute;
    private int hour;
    private int day;
    private int month;

    public OnceRepeatModel() {
        reset();
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void reset() {
        minute = -1;
        hour = -1;
        day = -1;
        month = -1;
    }

    public boolean isValid() {
        return minute != -1 && hour != -1 && day != -1 && month != -1;
    }
}
