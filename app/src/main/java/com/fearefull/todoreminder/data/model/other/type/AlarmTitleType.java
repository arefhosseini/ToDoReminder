package com.fearefull.todoreminder.data.model.other.type;

import com.fearefull.todoreminder.R;

import java.io.Serializable;

public enum AlarmTitleType implements Serializable {
    CUSTOM(1,"پیشفرض", R.drawable.alarm_title_custom),
    TO_DO(2, "انجام دادن", R.drawable.alarm_title_to_do),
    BIRTHDAY(3, "تولد", R.drawable.alarm_title_birthday),
    ANNIVERSARY(4, "سالگرد", R.drawable.alarm_title_anniversary),
    CALL(5, "تماس", R.drawable.alarm_title_call),
    SHOPPING(6, "خرید", R.drawable.alarm_title_shopping),
    GET_UP(7, "بیدار شدن", R.drawable.alarm_title_get_up),
    DRINK_WATER(8, "نوشیدن آب", R.drawable.alarm_title_water),
    TAKE_PILL(9, "خوردن قرص", R.drawable.alarm_title_pill),
    MEETING(10, "ملاقات", R.drawable.alarm_title_meeting),
    COURSE(11, "درس", R.drawable.alarm_title_course),
    EXERCISE(12, "ورزش", R.drawable.alarm_title_exercise),
    GREETING(13, "تبریک گفتن", R.drawable.alarm_title_greeting),
    TRAVEL(14, "مسافرت", R.drawable.alarm_title_travel),
    CREDIT_CARD(15, "پرداخت کردن", R.drawable.alarm_title_credit_card);

    private int value;
    private String text;
    private int imageRes;

    AlarmTitleType(int value, String text, int imageRes) {
        this.value = value;
        this.text = text;
        this.imageRes = imageRes;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public int getImageRes() {
        return imageRes;
    }

    public static AlarmTitleType getAlarmTitleTypeByValue(Integer value){
        for(AlarmTitleType type : values()){
            if(type.value == value){
                return type;
            }
        }
        return AlarmTitleType.CUSTOM;
    }

    public static AlarmTitleType getDefault() {
        return AlarmTitleType.CUSTOM;
    }
}
