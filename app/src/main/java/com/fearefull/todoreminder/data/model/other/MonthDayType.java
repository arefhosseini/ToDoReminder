package com.fearefull.todoreminder.data.model.other;

public enum MonthDayType {
    DAY_1 (0, "اولین"),
    DAY_2 (1, "دومین"),
    DAY_3 (2, "سومین"),
    DAY_4 (3, "چهارمین"),
    DAY_5 (4, "پنجمین"),
    DAY_6 (5, "ششمین"),
    DAY_7 (6, "هفتمین"),
    DAY_8 (7, "هشتمین"),
    DAY_9 (8, "نهمین"),
    DAY_10(9, "دهمین"),
    DAY_11(10, "یازدهمین"),
    DAY_12(11, "دوازهمین"),
    DAY_13(12, "سیزدهمین"),
    DAY_14(13, "چهاردهمین"),
    DAY_15(14, "پانزدهمین"),
    DAY_16(15, "شانزدهمین"),
    DAY_17(16, "هفدهمین"),
    DAY_18(17, "هجدهمین"),
    DAY_19(18, "نوزدهمین"),
    DAY_20(19, "بیستمین"),
    DAY_21(20, "بیست و یکمین"),
    DAY_22(21, "بیست و دومین"),
    DAY_23(22, "بیست و سومین"),
    DAY_24(23, "بیست و چهارمین"),
    DAY_25(24, "بیست و پنجمین"),
    DAY_26(25, "بیست و ششمین"),
    DAY_27(26, "بیست و هفتمین"),
    DAY_28(27, "بیست و هشتمین"),
    DAY_29(28, "بیست و نهمین"),
    DAY_30(29, "سی امین");

    private int index;
    private String text;

    MonthDayType(int index, String text) {
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
