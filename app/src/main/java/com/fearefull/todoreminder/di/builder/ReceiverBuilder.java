package com.fearefull.todoreminder.di.builder;

import com.fearefull.todoreminder.service.AlarmReceiver;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ReceiverBuilder {
    @ContributesAndroidInjector
    abstract AlarmReceiver contributesAlarmReceiver();
}
