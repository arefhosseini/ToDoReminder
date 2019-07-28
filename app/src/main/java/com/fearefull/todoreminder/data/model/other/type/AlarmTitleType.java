package com.fearefull.todoreminder.data.model.other.type;

public enum AlarmTitleType {
    CUSTOM(1,"سفارشی"),
    TO_DO(2, "انجام دادن"),
    BIRTHDAY(3, "تولد"),
    ANNIVERSARY(4, "سالگرد"),
    CALL(5, "تماس"),
    SHOPPING(6, "خرید"),
    GET_UP(7, "بیدار شدن"),
    DRINK_WATER(8, "نوشیدن آب"),
    TAKE_PILL(9, "خوردن قرص"),
    MEETING(10, "ملاقات"),
    COURSE(11, "درس"),
    EXERCISE(12, "ورزش"),
    GREETING(13, "تبریک گفتن"),
    TRAVEL(14, "مسافرت"),
    CREDIT_CARD(15, "پرداخت کردن");

    private int value;
    private String text;

    AlarmTitleType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static AlarmTitleType getAlarmTitleTypeByValue(Integer value){
        for(AlarmTitleType type : values()){
            if(type.value == value){
                return type;
            }
        }
        return AlarmTitleType.CUSTOM;
    }
}
