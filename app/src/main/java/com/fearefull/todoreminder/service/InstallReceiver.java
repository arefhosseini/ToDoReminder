package com.fearefull.todoreminder.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.adtrace.sdk.AdTrace;
import ir.metrix.sdk.MetrixReferrerReceiver;

import static io.adtrace.sdk.Constants.REFERRER;

public class InstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // AdTrace
        String rawReferrer = intent.getStringExtra(REFERRER);
        if (null == rawReferrer) {
            return;
        }
        AdTrace.getDefaultInstance().sendReferrer(rawReferrer, context);

        // Metrix
        new MetrixReferrerReceiver().onReceive(context, intent);
    }
}
