package com.fearefull.todoreminder.data.model.other;

public enum DayMonthType {
    DAY_1 (0, "اولین", "یک"),
    DAY_2 (1, "دومین", "دو"),
    DAY_3 (2, "سومین", "سه"),
    DAY_4 (3, "چهارمین", "چهار"),
    DAY_5 (4, "پنجمین", "پنج"),
    DAY_6 (5, "ششمین", "شش"),
    DAY_7 (6, "هفتمین", "هفت"),
    DAY_8 (7, "هشتمین", "هشت"),
    DAY_9 (8, "نهمین", "نه"),
    DAY_10(9, "دهمین", "ده"),
    DAY_11(10, "یازدهمین", "یازده"),
    DAY_12(11, "دوازهمین", "دوازه"),
    DAY_13(12, "سیزدهمین", "سیزده"),
    DAY_14(13, "چهاردهمین", "چهارده"),
    DAY_15(14, "پانزدهمین", "پانزده"),
    DAY_16(15, "شانزدهمین", "شانزده"),
    DAY_17(16, "هفدهمین", "هفده"),
    DAY_18(17, "هجدهمین", "هجده"),
    DAY_19(18, "نوزدهمین", "نوزده"),
    DAY_20(19, "بیستمین", "بیست"),
    DAY_21(20, "بیست و یکمین", "بیست و یک"),
    DAY_22(21, "بیست و دومین", "بیست و دو"),
    DAY_23(22, "بیست و سومین", "بیست و سه"),
    DAY_24(23, "بیست و چهارمین", "بیست و چهار"),
    DAY_25(24, "بیست و پنجمین", "بیست و پنج"),
    DAY_26(25, "بیست و ششمین", "بیست و شش"),
    DAY_27(26, "بیست و هفتمین", "بیست و هفت"),
    DAY_28(27, "بیست و هشتمین", "بیست و هشت"),
    DAY_29(28, "بیست و نهمین", "بیست و نه"),
    DAY_30(29, "سی امین", "سی");

    private int index;
    private String textTh, textNormal;

    DayMonthType(int index, String textTh, String textNormal) {
        this.index = index;
        this.textTh = textTh;
        this.textNormal = textNormal;
    }

    public int getIndex() {
        return index;
    }

    public String getTextTh() {
        return textTh;
    }

    public String getTextNormal() {
        return textNormal;
    }

    public static DayMonthType getDayMonthType(Integer index){
        for(DayMonthType type : values()){
            if(type.index == index){
                return type;
            }
        }
        return DayMonthType.DAY_1;
    }
}
