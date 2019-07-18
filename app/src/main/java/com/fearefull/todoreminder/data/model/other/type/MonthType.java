package com.fearefull.todoreminder.data.model.other.type;

public enum  MonthType {
    FARVARDIN(1, "فروردین", 31),
    ORDIBEHESHT(2, "اردیبهشت", 31),
    KHORDAD(3, "خرداد", 31),
    TIR(4, "تیر", 31),
    MORDAD(5, "مرداد", 31),
    SHAHRIVAR(6, "شهریور", 31),
    MEHR(7, "مهر", 30),
    ABAN(8, "آبان", 30),
    AZAR(9, "آذر", 30),
    DEY(10, "دی", 30),
    BAHMAN(11, "بهمن", 30),
    ESFAND(12, "اسفند", 29);

    private int value;
    private String text;
    private int days;

    MonthType(int value, String text, int days) {
        this.value = value;
        this.text = text;
        this.days = days;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public int getDays() {
        return days;
    }

    public static MonthType getMonthType(Integer value){
        for(MonthType type : values()){
            if(type.value == value){
                return type;
            }
        }
        return MonthType.TIR;
    }
}
