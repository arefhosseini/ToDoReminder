package com.fearefull.todoreminder.data.model.other.type;

public enum DayMonthType {
    DAY_1 (0, 1, "اولین", "یک"),
    DAY_2 (1, 2,"دومین", "دو"),
    DAY_3 (2,3, "سومین", "سه"),
    DAY_4 (3,4, "چهارمین", "چهار"),
    DAY_5 (4, 5,"پنجمین", "پنج"),
    DAY_6 (5,6, "ششمین", "شش"),
    DAY_7 (6, 7,"هفتمین", "هفت"),
    DAY_8 (7, 8,"هشتمین", "هشت"),
    DAY_9 (8, 9,"نهمین", "نه"),
    DAY_10(9, 10,"دهمین", "ده"),
    DAY_11(10, 11,"یازدهمین", "یازده"),
    DAY_12(11, 12,"دوازهمین", "دوازه"),
    DAY_13(12, 13,"سیزدهمین", "سیزده"),
    DAY_14(13, 14,"چهاردهمین", "چهارده"),
    DAY_15(14, 15,"پانزدهمین", "پانزده"),
    DAY_16(15, 16,"شانزدهمین", "شانزده"),
    DAY_17(16, 17,"هفدهمین", "هفده"),
    DAY_18(17, 18,"هجدهمین", "هجده"),
    DAY_19(18, 19,"نوزدهمین", "نوزده"),
    DAY_20(19, 20,"بیستمین", "بیست"),
    DAY_21(20, 21,"بیست و یکمین", "بیست و یک"),
    DAY_22(21, 22,"بیست و دومین", "بیست و دو"),
    DAY_23(22, 23,"بیست و سومین", "بیست و سه"),
    DAY_24(23, 24,"بیست و چهارمین", "بیست و چهار"),
    DAY_25(24, 25,"بیست و پنجمین", "بیست و پنج"),
    DAY_26(25, 26,"بیست و ششمین", "بیست و شش"),
    DAY_27(26, 27,"بیست و هفتمین", "بیست و هفت"),
    DAY_28(27, 28,"بیست و هشتمین", "بیست و هشت"),
    DAY_29(28, 29,"بیست و نهمین", "بیست و نه"),
    DAY_30(29, 30,"سی امین", "سی"),
    DAY_31(30, 31,"سی و یکمین", "سی و یک");

    private int index, value;
    private String textTh, textNormal;

    DayMonthType(int index, int value, String textTh, String textNormal) {
        this.index = index;
        this.value = value;
        this.textTh = textTh;
        this.textNormal = textNormal;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    public String getTextTh() {
        return textTh;
    }

    public String getTextNormal() {
        return textNormal;
    }

    public static DayMonthType getDayMonthTypeByIndex(Integer index){
        for(DayMonthType type : values()){
            if(type.index == index){
                return type;
            }
        }
        return DayMonthType.DAY_1;
    }

    public static DayMonthType getDayMonthTypeByValue(Integer value){
        for(DayMonthType type : values()){
            if(type.value == value){
                return type;
            }
        }
        return DayMonthType.DAY_1;
    }
}
