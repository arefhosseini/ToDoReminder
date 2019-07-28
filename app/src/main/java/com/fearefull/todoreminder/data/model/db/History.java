package com.fearefull.todoreminder.data.model.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fearefull.todoreminder.data.model.other.DataConverter;
import com.fearefull.todoreminder.data.model.other.persian_date.PersianDate;
import com.fearefull.todoreminder.data.model.other.type.AlarmTitleType;
import com.fearefull.todoreminder.data.model.other.type.MonthType;
import com.fearefull.todoreminder.databinding.ItemHistoryBinding;

import java.io.Serializable;

@Entity(tableName = "histories")
@TypeConverters({DataConverter.class})
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
    @ColumnInfo(name = "title_type")
    private AlarmTitleType titleType;

    @NonNull
    @ColumnInfo(name = "time")
    private Long time;

    @ColumnInfo(name = "note")
    private String note;

    @Ignore
    private ItemHistoryBinding binding;

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
     * Control {@link #titleType}
     */
    @NonNull
    public AlarmTitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(@NonNull AlarmTitleType titleType) {
        this.titleType = titleType;
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


    /**
     * Control {@link #binding}
     */
    public ItemHistoryBinding getBinding() {
        return binding;
    }

    public void setBinding(ItemHistoryBinding binding) {
        this.binding = binding;
    }


    public History(@NonNull Boolean isDone, @NonNull String title, @NonNull AlarmTitleType titleType,
                   @NonNull Long time) {
        this.isDone = isDone;
        this.title = title;
        this.titleType = titleType;
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
