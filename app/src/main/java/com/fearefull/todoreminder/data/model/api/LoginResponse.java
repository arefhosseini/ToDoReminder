package com.fearefull.todoreminder.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class LoginResponse {
    @Expose
    @SerializedName("user_id")
    private Long userId;

    @Expose
    @SerializedName("username")
    private String username;

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
