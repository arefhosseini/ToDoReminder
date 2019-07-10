package com.fearefull.todoreminder.data.model.other;

import java.io.Serializable;

public class Alarm implements Serializable {
    private MyTime time;
    private MyDate date;
    private MyRepeat repeat;

    public Alarm() {
        time = new MyTime();
        date = new MyDate();
        repeat = new MyRepeat();
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
}
