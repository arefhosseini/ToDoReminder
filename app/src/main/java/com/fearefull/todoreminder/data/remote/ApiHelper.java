package com.fearefull.todoreminder.data.remote;

import com.fearefull.todoreminder.data.model.api.LoginRequest;
import com.fearefull.todoreminder.data.model.api.LoginResponse;

import io.reactivex.Single;

public interface ApiHelper {
    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);
}
