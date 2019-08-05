package com.fearefull.todoreminder.data.model.db;

import android.net.Uri;

import com.fearefull.todoreminder.data.model.other.type.RingtoneType;

import java.io.Serializable;

public class Ringtone implements Serializable {
    private final RingtoneType type;
    private final String title;
    private final Uri uri;

    public Ringtone() {
        this.type = RingtoneType.getDefaultRingtone();
        this.title = type.getName();
        this.uri = null;
    }

    public Ringtone(RingtoneType type, String title, Uri uri) {
        this.type = type;
        this.title = title;
        this.uri = uri;
    }

    public RingtoneType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Uri getUri() {
        return uri;
    }
}
