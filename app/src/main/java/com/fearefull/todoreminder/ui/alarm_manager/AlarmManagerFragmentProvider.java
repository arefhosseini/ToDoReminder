package com.fearefull.todoreminder.ui.alarm_manager;

import com.fearefull.todoreminder.ui.alarm_manager.simple.SimpleFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AlarmManagerFragmentProvider {

    @ContributesAndroidInjector(modules = {
            AlarmManagerFragmentModule.class,
            SimpleFragmentProvider.class
    })
    abstract AlarmManagerFragment provideAlarmManagerFragmentFactory();
}
