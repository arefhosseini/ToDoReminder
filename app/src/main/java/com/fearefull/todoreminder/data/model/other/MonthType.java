package com.fearefull.todoreminder.data.model.other;

public enum  MonthType {
    FARVARDIN(0, "فروردین", 31),
    ORDIBEHESHT(1, "اردیبهشت", 31),
    KHORDAD(2, "خرداد", 31),
    TIR(3, "تیر", 31),
    MORDAD(4, "مرداد", 31),
    SHAHRIVAR(5, "شهریور", 31),
    MEHR(6, "مهر", 30),
    ABAN(7, "آبان", 30),
    AZAR(8, "آذر", 30),
    DEY(9, "دی", 30),
    BAHMAN(10, "بهمن", 30),
    ESFAND(11, "اسفند", 29);

    private int index;
    private String text;
    private int days;

    MonthType(int index, String text, int days) {
        this.index = index;
        this.text = text;
        this.days = days;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    public int getDays() {
        return days;
    }
}
