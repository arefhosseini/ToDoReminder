package com.fearefull.todoreminder.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

public class AutoStart extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (Objects.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED))
        {
            Intent startServiceIntent = new Intent(context, MyService.class);
            context.startService(startServiceIntent);
        }
    }
}