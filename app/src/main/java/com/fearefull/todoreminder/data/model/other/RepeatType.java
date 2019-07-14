package com.fearefull.todoreminder.data.model.other;

public enum RepeatType {
    ONCE(0, "فقط یک بار"),
    DAILY(1, "هرروز"),
    WEEKLY(2, "هرهفته"),
    MONTHLY(3, "هرماه"),
    YEARLY(4, "هرسال"),
    CUSTOM(5, "شخصی سازی");

    private int index;
    private String text;

    RepeatType(int index, String text) {
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
