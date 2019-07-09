package com.fearefull.todoreminder.data.model.other;

public enum TimeType {
    AM(0, "قبل از ظهر", "am"),
    PM(1, "بعد از ظهر", "pm");

    private int index;
    private String persianText, englishText;

    TimeType(int index, String persianText, String englishText) {
        this.index = index;
        this.persianText = persianText;
        this.englishText = englishText;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPersianText() {
        return persianText;
    }

    public void setPersianText(String persianText) {
        this.persianText = persianText;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }
}
