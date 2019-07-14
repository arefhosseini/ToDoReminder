package com.fearefull.todoreminder.data.model.other;

import com.fearefull.todoreminder.R;
import com.fearefull.todoreminder.utils.AppConstants;

import java.io.Serializable;

public class Alarm implements Serializable {
    private MyTime time;
    private MyDate date;
    private MyRepeat repeat;
    private String note;

    public Alarm() {
        time = new MyTime();
        date = new MyDate();
        repeat = new MyRepeat();
        note = AppConstants.DEFAULT_NOTE;
    }

    public MyTime getTime() {
        return time;
    }

    public void setTime(MyTime time) {
        this.time = time;
    }

    public MyDate getDate() {
        return date;
    }

    public void setDate(MyDate date) {
        this.date = date;
    }

    public MyRepeat getRepeat() {
        return repeat;
    }

    public void setRepeat(MyRepeat repeat) {
        this.repeat = repeat;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
