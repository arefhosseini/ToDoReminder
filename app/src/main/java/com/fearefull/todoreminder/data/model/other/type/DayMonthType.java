package com.fearefull.todoreminder.data.model.other.type;

import java.util.ArrayList;
import java.util.List;

public enum DayMonthType {
    DAY_1 (0, 1, "اول", "یک"),
    DAY_2 (1, 2,"دوم", "دو"),
    DAY_3 (2,3, "سوم", "سه"),
    DAY_4 (3,4, "چهارم", "چهار"),
    DAY_5 (4, 5,"پنجم", "پنج"),
    DAY_6 (5,6, "ششم", "شش"),
    DAY_7 (6, 7,"هفتم", "هفت"),
    DAY_8 (7, 8,"هشتم", "هشت"),
    DAY_9 (8, 9,"نهم", "نه"),
    DAY_10(9, 10,"دهم", "ده"),
    DAY_11(10, 11,"یازدهم", "یازده"),
    DAY_12(11, 12,"دوازهم", "دوازه"),
    DAY_13(12, 13,"سیزدهم", "سیزده"),
    DAY_14(13, 14,"چهاردهم", "چهارده"),
    DAY_15(14, 15,"پانزدهم", "پانزده"),
    DAY_16(15, 16,"شانزدهم", "شانزده"),
    DAY_17(16, 17,"هفدهم", "هفده"),
    DAY_18(17, 18,"هجدهم", "هجده"),
    DAY_19(18, 19,"نوزدهم", "نوزده"),
    DAY_20(19, 20,"بیستم", "بیست"),
    DAY_21(20, 21,"بیست و یکم", "بیست و یک"),
    DAY_22(21, 22,"بیست و دوم", "بیست و دو"),
    DAY_23(22, 23,"بیست و سوم", "بیست و سه"),
    DAY_24(23, 24,"بیست و چهارم", "بیست و چهار"),
    DAY_25(24, 25,"بیست و پنجم", "بیست و پنج"),
    DAY_26(25, 26,"بیست و ششم", "بیست و شش"),
    DAY_27(26, 27,"بیست و هفتم", "بیست و هفت"),
    DAY_28(27, 28,"بیست و هشتم", "بیست و هشت"),
    DAY_29(28, 29,"بیست و نهم", "بیست و نه"),
    DAY_30(29, 30,"سی ام", "سی"),
    DAY_31(30, 31,"سی و یکم", "سی و یک");

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

    public static List<String> getDayMonthString(int max) {
        List<String> values = new ArrayList<>();
        for(DayMonthType type : values()){
            if(type.value <= max){
                values.add(type.value + " ام");
            }
        }
        return values;
    }
}
