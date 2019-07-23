package com.fearefull.todoreminder.data.model.db;

import java.io.Serializable;

public enum Repeat implements Serializable {
    ONCE(0, "فقط یک بار"),
    DAILY(1, "روزانه"),
    WEEKLY(2, "هفتگی"),
    MONTHLY(3, "ماهانه"),
    YEARLY(4, "سالانه");
    //CUSTOM(5, "شخصی سازی");

    private final Integer value;
    private final String text;

    Repeat(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static Repeat getRepeat(Integer value){
        for(Repeat repeat : values()){
            if(repeat.value.equals(value)){
                return repeat;
            }
        }
        return null;
    }

    public static Integer getRepeatInt(Repeat repeat){
        if(repeat != null)
            return repeat.value;
        return  null;
    }

    public static int getCount() {
        return values().length;
    }
}
