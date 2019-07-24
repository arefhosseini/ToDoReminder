package com.fearefull.todoreminder.data.model.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fearefull.todoreminder.data.model.other.persian_date.PersianDate;
import com.fearefull.todoreminder.data.model.other.type.MonthType;

import java.io.Serializable;

@Entity(tableName = "histories")
public class History implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "is_done")
    private Boolean isDone;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "time")
    private Long time;

    @ColumnInfo(name = "note")
    private String note;

    /**
     * Control {@link #id}
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Control {@link #isDone}
     */
    @NonNull
    public Boolean getDone() {
        return isDone;
    }

    public void setDone(@NonNull Boolean done) {
        isDone = done;
    }


    /**
     * Control {@link #title}
     */
    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }


    /**
     * Control {@link #time}
     */
    @NonNull
    public Long getTime() {
        return time;
    }

    public void setTime(@NonNull Long time) {
        this.time = time;
    }


    /**
     * Control {@link #note}
     */
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public History(@NonNull Boolean isDone, @NonNull String title, @NonNull Long time) {
        this.isDone = isDone;
        this.title = title;
        this.time = time;
        note = "";
    }

    @Ignore
    public static Long getTimeStampTime(int minute, int hour, int dayMonth, int month, int year) {
        PersianDate persianDate = new PersianDate();
        persianDate.setSecond(0);
        persianDate.setMinute(minute);
        persianDate.setHour(hour);
        persianDate.setShDay(dayMonth);
        persianDate.setShMonth(month);
        persianDate.setShYear(year);
        return persianDate.getTime();
    }

    @Ignore
    public String timeToString() {
        PersianDate persianDate = new PersianDate(time);
        return persianDate.getHour() + ":" + persianDate.getMinute() + " " + persianDate.getShDay() +
                " " + MonthType.getMonthType(persianDate.getShMonth()).getText() + " " + persianDate.getShYear();
    }
}
