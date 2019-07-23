package com.fearefull.todoreminder.data.model.other.type;

public enum DayWeekType {
    DAY_1(1, "شنبه"),
    DAY_2(2, "یک شنبه"),
    DAY_3(3, "دوشنبه"),
    DAY_4(4, "سه شنبه"),
    DAY_5(5, "چهارشنبه"),
    DAY_6(6, "پنجشنبه"),
    DAY_7(7, "جمعه");

    private int value;
    private String text;

    DayWeekType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static DayWeekType getDayWeekTypeByValue(Integer value){
        for(DayWeekType type : values()){
            if(type.value == value){
                return type;
            }
        }
        return DayWeekType.DAY_1;
    }
}
