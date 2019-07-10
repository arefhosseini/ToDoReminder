package com.fearefull.todoreminder.data.model.other;

public enum WeekType {
    DAY_1(0, "شنبه"),
    DAY_2(1, "یک شنبه"),
    DAY_3(2, "دوشنبه"),
    DAY_4(3, "سه شنبه"),
    DAY_5(4, "چهارشنبه"),
    DAY_6(5, "پنجشنبه"),
    DAY_7(6, "جمعه");

    private int index;
    private String text;

    WeekType(int index, String text) {
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
