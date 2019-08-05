package com.fearefull.todoreminder.data.model.db;

import com.fearefull.todoreminder.data.model.other.type.RingtoneType;

import java.io.Serializable;

public class Ringtone implements Serializable {
    private final RingtoneType type;
    private final String title;
    private final String uriString;

    public Ringtone() {
        this.type = RingtoneType.getDefaultRingtone();
        this.title = type.getName();
        this.uriString = null;
    }

    public Ringtone(RingtoneType type, String title, String uriString) {
        this.type = type;
        this.title = title;
        this.uriString = uriString;
    }

    public RingtoneType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getUriString() {
        return uriString;
    }

    public boolean isSame(Ringtone ringtone) {
        return ringtone.type == type && ringtone.title.equals(title);
    }
}
