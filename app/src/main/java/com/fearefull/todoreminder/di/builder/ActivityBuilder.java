package com.fearefull.todoreminder.di.builder;

import com.fearefull.todoreminder.ui.about.AboutFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_manager.AlarmManagerFragmentProvider;
import com.fearefull.todoreminder.ui.alarm_notification.AlarmNotificationActivity;
import com.fearefull.todoreminder.ui.home.HomeFragmentProvider;
import com.fearefull.todoreminder.ui.main.MainActivity;
import com.fearefull.todoreminder.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            HomeFragmentProvider.class,
            AboutFragmentProvider.class,
            AlarmManagerFragmentProvider.class,
    })
    abstract MainActivity contributesMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity contributesSplashActivity();

    @ContributesAndroidInjector
    abstract AlarmNotificationActivity contributesAlarmNotification();
}
