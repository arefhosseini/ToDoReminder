package com.fearefull.todoreminder.data.remote;

import com.fearefull.todoreminder.data.model.api.LoginRequest;
import com.fearefull.todoreminder.data.model.api.LoginResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper{

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }
}
