package com.fearefull.todoreminder.data.model.other;

public enum HalfHourType {
    AM(0, "قبل از ظهر", "am"),
    PM(1, "بعد از ظهر", "pm");

    private int index;
    private String persianText, englishText;

    HalfHourType(int index, String persianText, String englishText) {
        this.index = index;
        this.persianText = persianText;
        this.englishText = englishText;
    }

    public int getIndex() {
        return index;
    }

    public String getPersianText() {
        return persianText;
    }

    public String getEnglishText() {
        return englishText;
    }
}
