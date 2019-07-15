package com.fearefull.todoreminder.data.model.other;

import android.net.Uri;

public class Ringtone {
    private String name;
    private Uri uri;

    public Ringtone() {
        this.name = "پیش فرض";
        this.uri = null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
