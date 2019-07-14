package com.fearefull.todoreminder.ui.alarm_manager;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AlarmManagerFragmentProvider {

    @ContributesAndroidInjector
    abstract AlarmManagerFragment provideAlarmManagerFragmentFactory();
}
