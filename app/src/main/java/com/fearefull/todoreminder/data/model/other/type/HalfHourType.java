package com.fearefull.todoreminder.data.model.other.type;

public enum HalfHourType {
    AM(0, "قبل از ظهر", "ق.ظ", "am"),
    PM(1, "بعد از ظهر", "ب.ظ", "pm");

    private int index;
    private String persianText, persianShortText, englishText;

    HalfHourType(int index, String persianText, String persianShortText, String englishText) {
        this.index = index;
        this.persianText = persianText;
        this.persianShortText = persianShortText;
        this.englishText = englishText;
    }

    public int getIndex() {
        return index;
    }

    public String getPersianText() {
        return persianText;
    }

    public String getPersianShortText() {
        return persianShortText;
    }

    public String getEnglishText() {
        return englishText;
    }
}
