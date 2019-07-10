package com.fearefull.todoreminder.data.model.other;

public enum CustomRepeatType {
    MINUTE(0, "دقیقه"),
    HOUR(1, "ساعت"),
    DAY(2, "روز"),
    WEEK(3, "هفته"),
    MONTH(4, "ماه"),
    YEAR(5, "سال ");

    private int index;
    private String text;

    CustomRepeatType(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }
}
