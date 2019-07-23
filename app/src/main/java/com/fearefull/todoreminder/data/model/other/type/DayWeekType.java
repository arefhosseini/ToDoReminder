package com.fearefull.todoreminder.data.model.other.type;

public enum DayWeekType {
    DAY_1(1, 0, "شنبه"),
    DAY_2(2, 1, "یک شنبه"),
    DAY_3(3, 2, "دوشنبه"),
    DAY_4(4, 3, "سه شنبه"),
    DAY_5(5, 4, "چهارشنبه"),
    DAY_6(6, 5, "پنجشنبه"),
    DAY_7(7, 6, "جمعه");

    private int value, index;
    private String text;

    DayWeekType(int value, int index, String text) {
        this.value = value;
        this.index = index;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public int getIndex() {
        return index;
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
